package sample;

import db.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objetos.Paciente;
import sun.font.Decoration;

import java.awt.event.ActionEvent;
import java.util.Observable;

public class ControllerBusquedaPacientes {

    PacienteModel pacienteModel = new PacienteModel();
    private Paciente pacientes[];


    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAgregarPaciente;
    @FXML
    private TextField campoMedicamento;
    @FXML
    private ListView<String> listaPacientes;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblEdad;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblSexo;
    @FXML
    private Label lblCuarto;

    @FXML
    public void initialize() throws Exception {
        ObservableList<String> list = FXCollections.observableArrayList();

        //El usuario solo podra seleccionar un solo elemento de la lista a la vez
        listaPacientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        pacientes = pacienteModel.selectPacientes();
        try {
            pacientes = pacienteModel.selectPacientes();
            for (int i = 0; i < pacientes.length; i++) {
                System.out.println(pacientes[i].getNombre());
                list.add(pacientes[i].getNombre());
            }

        } catch (Exception e) {
            System.out.println(e.getClass()+e.getMessage());
        }
        listaPacientes.setItems(list);
    }

    @FXML
    public void muestraPaciente(){
        int indice = listaPacientes.getSelectionModel().getSelectedIndex();
        lblNombre.setText("Nombre: " + pacientes[indice].getNombre());
        lblEdad.setText("Edad: " + Integer.toString(pacientes[indice].getEdad()));
        lblEstado.setText("Estado: " + pacientes[indice].getEstado());
        lblSexo.setText("Sexo: " + pacientes[indice].getSexo());
        lblCuarto.setText("Cuarto: " + pacientes[indice].getNumCuarto());
    }
}
