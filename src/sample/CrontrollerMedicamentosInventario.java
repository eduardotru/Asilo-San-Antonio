package sample;

import db.EnvaseMedicinaModel;
import db.PacienteMedicamentoModel;
import db.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.EnvaseMedicina;
import objetos.Paciente;
import objetos.PacienteMedicamento;
import objetos.PacienteMedicamentoTabla;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrontrollerMedicamentosInventario extends ControllerBase{
    PacienteModel pacienteModel = new PacienteModel();
    PacienteMedicamentoModel pacienteMedicamentoModel = new PacienteMedicamentoModel();

    private Paciente[] pacientes = null;

    @FXML Label lblNombre;
    @FXML Label lblMedicamento;
    @FXML TableColumn<PacienteMedicamentoTabla, String> columnaMedida;
    @FXML
    public TextField campoSurtir;
    @FXML
    public Button btnAgregarReceta;
    @FXML
    private TableView<PacienteMedicamentoTabla> tablaResumenMedicamentos;
    @FXML
    private ListView<String> listaPacientes;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnMenu;
    @FXML
    private TextField campoBusquedaPacientes;
    @FXML
    private TableColumn<PacienteMedicamentoTabla, String> columnaNombG;
    @FXML
    private TableColumn<PacienteMedicamentoTabla, String> columnaDosisDisp;

    @FXML DatePicker dateFrom;

    private void initTable(int idPaciente){
        columnaNombG.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("medicamento"));
        columnaMedida.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("medidaDosis"));
        columnaDosisDisp.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("dosisDisponibles"));
        tablaResumenMedicamentos.setItems(getMedicamentos(idPaciente));
    }

    @FXML
    private void initialize()
    {
        btnAgregarReceta.setDisable(true);
        ObservableList<String> list = FXCollections.observableArrayList();
        //Obtener la lista de Pacientes
        try {
            pacientes = pacienteModel.selectPacientes();
            for (int i = 0; i < pacientes.length; i++) {
                System.out.println(pacientes[i].getNombre());
                list.add(pacientes[i].getNombre());
                listaPacientes.setItems(list);
            }
        }catch (Exception e) {
            System.out.println("Error al cargar la lista de pacienes");
        }

        setAutoCompletado(pacientes,campoBusquedaPacientes);
    }

    @FXML
    public void pshBtnHome(Event event) throws IOException {
        cargaPantalla(event,"Home.fxml",btnHome);
    }

    @FXML
    public void pshBtnMenu(Event event) throws IOException {
        cargaPantalla(event,"menuMedicinas.fxml",btnHome);
    }

    @FXML
    public void muestraPacienteSeleccionado(){
        int indice = listaPacientes.getSelectionModel().getSelectedIndex();
        rellenaTablaMedicamentoPaciente(pacientes[indice]);
        pacienteClicked();
    }


    private void rellenaTablaMedicamentoPaciente(Paciente paciente){
        initTable(paciente.getId());
    }

    private ObservableList<PacienteMedicamentoTabla> getMedicamentos(int idPaciente){
        ObservableList<PacienteMedicamentoTabla> data = FXCollections.observableArrayList();
        PacienteMedicamento pacienteMedicamentos[];
        PacienteMedicamentoModel model = new PacienteMedicamentoModel();
        try {
            pacienteMedicamentos = model.selectPacienteMedicamento(idPaciente);
            for(int i=0; i<pacienteMedicamentos.length; i++) {
                data.add(new PacienteMedicamentoTabla(pacienteMedicamentos[i]));
                data.get(i).setDosisDisponibles(idPaciente);
            }
        }catch (Exception e) {
            System.out.println("No se pudieron cargar los medicamentos");
        }
        return data;
    }

    @FXML
    public void pressButtonVencer() {
        if (campoBusquedaPacientes.getText() == "") {
            showAlertDialog(Alert.AlertType.INFORMATION, "Favor de especificar el usuario.");
        }
        if (dateFrom.getValue() == null) {
            showAlertDialog(Alert.AlertType.INFORMATION, "Favor de especificar la fecha.");
        }

        Date dateFromEnvase = Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Calendar cal  = Calendar.getInstance();
        cal.setTime(dateFromEnvase);
        int currentDay = cal.get(Calendar.DAY_OF_WEEK);
        int leftDays= Calendar.SATURDAY - currentDay;
        cal.add(Calendar.DATE, leftDays);

        Date dateToEnvase = cal.getTime();

        int indice = listaPacientes.getSelectionModel().getSelectedIndex();
        EnvaseMedicinaModel envaseMedicinaModel = new EnvaseMedicinaModel();
        try {
            EnvaseMedicina[] envasesPorVencer = envaseMedicinaModel.selectEnvasesPorTerminarEntreParaPaciente(
                dateFromEnvase, dateToEnvase, pacientes[indice].getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int idPacienteSeleccionado;
    @FXML
    public void pacienteClicked(){
        try{
            idPacienteSeleccionado = pacienteModel.selectIdPaciente(listaPacientes.getSelectionModel().getSelectedItem());
        }catch (Exception e) {

        }
        System.out.println(idPacienteSeleccionado);
        btnAgregarReceta.setDisable(false);
    }


    @FXML public void pshBtnAgregarReceta() throws IOException{
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("agregarReceta.fxml"));
        root = loader.load();

        ControllerAgregarReceta controller = loader.getController();
        controller.init(idPacienteSeleccionado);
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Seguro Médico");
        stage.setScene(new Scene(root));
        stage.showAndWait();
        muestraPacienteSeleccionado();
    }

    @FXML
    public void preparaSurtido(){
        PacienteMedicamentoTabla pacienteMedi = tablaResumenMedicamentos.getSelectionModel().getSelectedItem();
        lblMedicamento.setText(pacienteMedi.getMedicamento());
        lblNombre.setText(listaPacientes.getSelectionModel().getSelectedItem());
    }

}
