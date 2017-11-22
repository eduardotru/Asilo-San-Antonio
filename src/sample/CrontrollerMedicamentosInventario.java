package sample;

import db.EnvaseMedicinaModel;
import db.FamiliarResponsableModel;
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
import objetos.*;

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

    private Date fechaConsulta;

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
    @FXML Button btnSurtir;
    @FXML
    private TextField campoBusquedaPacientes;
    @FXML
    private TableColumn<PacienteMedicamentoTabla, String> columnaNombG;
    @FXML
    private TableColumn<PacienteMedicamentoTabla, String> columnaDosisDisp;

    @FXML DatePicker dateFrom;

    @FXML ListView listaFamiliares;

    @FXML Label lblMarco;

    private FamiliarResponsable[] familiares;

    private void initTable(int idPaciente){
        columnaNombG.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("medicamento"));
        columnaMedida.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("medidaDosis"));
        columnaDosisDisp.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("dosisDisponibles"));
        tablaResumenMedicamentos.setItems(getMedicamentos(idPaciente));
    }

    @FXML
    private void initialize()
    {
        fechaConsulta = Calendar.getInstance().getTime();
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
        PacienteModel pacienteModel = new PacienteModel();
        try {
            Paciente pacienteBuscado =  pacienteModel.
                    selectPaciente(pacienteModel.selectIdPaciente(campoBusquedaPacientes.getText()));

            ObservableList<String> list = FXCollections.observableArrayList();
            list.add(pacienteBuscado.getNombre());
            listaPacientes.setItems(list);
        } catch (Exception e) {
            showAlertDialog(Alert.AlertType.ERROR, "No se pude encontrar al paciente. Intenta de nuevo.");
        }
    }

    @FXML
    public void pshBtnMenu(Event event) throws IOException {
        cargaPantalla(event,"menuMedicinas.fxml",btnHome);
    }

    @FXML
    public void muestraPacienteSeleccionado(){
        int indice = listaPacientes.getSelectionModel().getSelectedIndex();
        rellenaTablaMedicamentoPaciente(pacientes[indice]);

        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();

        EnvaseMedicinaModel envaseMedicinaModel = new EnvaseMedicinaModel();
        int cantASurtir = Integer.parseInt(campoSurtir.getText());
        int idMedi = tablaResumenMedicamentos.getSelectionModel().getSelectedItem().getIdMedicamento();
        int idPaci = tablaResumenMedicamentos.getSelectionModel().getSelectedItem().getIdPaciente();
        EnvaseMedicina[] envaseNuevo;
        try {
            envaseNuevo = envaseMedicinaModel.selectEnvaseMedicinasPorPacienteMedicamento(idPaci, idMedi);
            envaseNuevo[0].setCantidad(cantASurtir);
            envaseNuevo[0].setFechaSurtimiento(new Date());
        } catch (Exception e) {
            System.out.println("NO se pudo agregar el envase");
        }

        lblMarco.setText(envaseNuevo[0]);
        try {
            ObservableList<String> familiaresTexto = FXCollections.observableArrayList();
            familiares = familiarResponsableModel.selectFamiliarResponsablesPorPaciente(pacientes[indice].getId());
            for (FamiliarResponsable familiar:
                 familiares) {
                familiaresTexto.add(familiar.getNombre() + ": " + familiar.getRelacion() + "\n" +
                        familiar.getTelefono());
            }
            listaFamiliares.setItems(familiaresTexto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pacienteClicked();
    }

    @FXML
    public void muestraFamiliarSeleccionado() {
        int indice = listaFamiliares.getSelectionModel().getSelectedIndex();

        /*FamiliarResponsable familiarSeleccionado = familiares[indice];
        string sMarcoText = familiarSeleccionado.get
        lblMarco.setText()*/
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
                PacienteMedicamento pacienteMedicamento = pacienteMedicamentos[i];
                PacienteMedicamentoTabla pacienteMedicamentoTabla = new PacienteMedicamentoTabla(pacienteMedicamento);
                int dosisRestanteFecha = pacienteMedicamentoTabla.getDosisDisponiblesParaFecha(fechaConsulta);
                pacienteMedicamentoTabla.setDosisDisponibles(dosisRestanteFecha);
                data.add(pacienteMedicamentoTabla);
            }
        }catch (Exception e) {
            System.out.println("No se pudieron cargar los medicamentos");
        }
        return data;
    }

    @FXML
    public void pressButtonVencer() {
        if (listaPacientes.getItems().size() == 0) {
            showAlertDialog(Alert.AlertType.INFORMATION, "No hay pacientes que mostrar.");
        }

        if (dateFrom.getValue() == null) {
            showAlertDialog(Alert.AlertType.INFORMATION, "Favor de especificar la fecha.");
        }

        fechaConsulta = Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        int indice = listaPacientes.getSelectionModel().getSelectedIndex();
        rellenaTablaMedicamentoPaciente(pacientes[indice]);
        pacienteClicked();
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
    public void preparaSurtido() {
        PacienteMedicamentoTabla pacienteMedi = tablaResumenMedicamentos.getSelectionModel().getSelectedItem();
        lblMedicamento.setText(pacienteMedi.getMedicamento());
        lblNombre.setText(listaPacientes.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void surteMedicamento() {
        EnvaseMedicinaModel envaseMedicinaModel = new EnvaseMedicinaModel();
        int cantASurtir = Integer.parseInt(campoSurtir.getText());
        int idMedi = tablaResumenMedicamentos.getSelectionModel().getSelectedItem().getIdMedicamento();
        int idPaci = tablaResumenMedicamentos.getSelectionModel().getSelectedItem().getIdPaciente();
        try {
            EnvaseMedicina[] envaseNuevo = envaseMedicinaModel.selectEnvaseMedicinasPorPacienteMedicamento(idPaci, idMedi);
            envaseNuevo[0].setCantidad(cantASurtir);
            envaseNuevo[0].setFechaSurtimiento(new Date());
            envaseMedicinaModel.addEnvaseMedicina(envaseNuevo[0]);
        } catch (Exception e) {
            System.out.println("NO se pudo agregar el envase");
        }

        try{
            rellenaTablaMedicamentoPaciente(pacienteModel.selectPaciente(idPaci));
        }catch (Exception e){
            System.out.println("Error al cargar paciente  al momento de actualizar tabl");
        }
    }

    @FXML
    public void pressedButtonMarcar() {

    }
}
