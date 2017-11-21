package sample;

import db.PacienteMedicamentoModel;
import db.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import objetos.Paciente;


import java.io.IOException;

public class CrontrollerMedicamentosDelDia extends ControllerBase{
    PacienteModel pacienteModel = new PacienteModel();
    PacienteMedicamentoModel pacienteMedicamentoModel = new PacienteMedicamentoModel();

    private Paciente[] pacientes = null;

    @FXML
    private ListView<Pane> listaMedicamentos;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnMenu;
    @FXML
    private TextField campoBusquedaPacientes;

    @FXML
    private void initialize()
    {
        //Obtener la lista de Pacientes
        try {

            pacientes = pacienteModel.selectPacientes();
        }catch (Exception e) {
            System.out.println("Error al cargar la lista de pacienes");
        }

        setAutoCompletado(pacientes,campoBusquedaPacientes);

        cargaTablaMedicamentosDelDia();
    }

    @FXML
    public void pshBtnHome(Event event) throws IOException {
        cargaPantalla(event,"Home.fxml",btnHome);
    }

    @FXML
    public void pshBtnMenu(Event event) throws IOException {
        cargaPantalla(event,"menuMedicinas.fxml",btnHome);
    }

    void cargaTablaMedicamentosDelDia(){

        ObservableList<Pane> listaMedicamento = FXCollections.observableArrayList();
        try {
            for(int i=0; i<pacientes.length;i++) {
                listaMedicamento.add(new Pane());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("tablaMedicamentoDelDia.fxml"));
                Pane newLoadedPane = loader.load();

                ControllerTablaMedicamentosDelDia controller = loader.getController();

                controller.initTable(pacientes[i]);
                listaMedicamentos.setItems(listaMedicamento);
                listaMedicamentos.getItems().get(i).getChildren().add(newLoadedPane);
            }

        }catch (Exception e) {
            System.out.println("Error al cargar los medicamento de varios paciente");
        }
    }
}
