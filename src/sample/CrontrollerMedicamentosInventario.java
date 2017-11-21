package sample;

import db.PacienteMedicamentoModel;
import db.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import objetos.Paciente;
import objetos.PacienteMedicamento;
import objetos.PacienteMedicamentoTabla;

import java.io.IOException;
import java.util.List;

public class CrontrollerMedicamentosInventario extends ControllerBase{
    PacienteModel pacienteModel = new PacienteModel();
    PacienteMedicamentoModel pacienteMedicamentoModel = new PacienteMedicamentoModel();

    private Paciente[] pacientes = null;

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

    @FXML
    private void initTable(int idPaciente){
        columnaNombG.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("medicamento"));
        columnaDosisDisp.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla,String>("dosis"));
        tablaResumenMedicamentos.setItems(getMedicamentos(idPaciente));
    }

    @FXML
    private void initialize()
    {
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
            }
        }catch (Exception e) {
            System.out.println("No se pudieron cargar los medicamentos");
        }
        return data;
    }
}
