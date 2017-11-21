package sample;

import db.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import objetos.Paciente;
import org.controlsfx.control.textfield.TextFields;
import sun.font.Decoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ControllerBusquedaPacientes extends ControllerBase{

    PacienteModel pacienteModel = new PacienteModel();
    private Paciente pacientes[];

    private Paciente pacienteBuscado;

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarPaciente;
    @FXML
    private Button btnMasDetalles;
    @FXML
    private TextField campoBusquedaPaciente;
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
    private Button btnAddPatient;

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

        setAutoCompletado(pacientes,campoBusquedaPaciente);
    }



    @FXML
    public void muestraPacienteSeleccionado(){
        int indice = listaPacientes.getSelectionModel().getSelectedIndex();
        pacienteBuscado =pacientes[indice];
        muestraDatosPaciente(pacienteBuscado);
    }

    @FXML
    //Esta fucion es llamada al
    public void pushedBuscaPaciente(){
        Paciente temp;
        temp = pacienteBuscado;
        //Si el sistema puede realizar la busqueda del paciente en la base de datos se muestra el paciente en el resumen
        //de informacion, si no, el usuario es notificado que no se encontro paciente con una ventana de notificacion
        try {
            pacienteBuscado = pacienteModel.selectPaciente(pacienteModel.selectIdPaciente(campoBusquedaPaciente.getText()));
            muestraDatosPaciente(pacienteBuscado);
        }catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,"Paciente no encontrado").showAndWait();
            //En caso de no encontrar se deshabilita el boton de "Mas detalles"
            if(temp==null) {
                btnMasDetalles.setDisable(true);
            }else {
                pacienteBuscado = temp;
            }
        }

    }

    /**
     * Método para mostrar los datos de un paciente recibido como parametro en la ventana principal de datos
     * @param paciente
     */
    public void muestraDatosPaciente(Paciente paciente){
        lblNombre.setText("Nombre: " + paciente.getNombre());
        lblEdad.setText("Edad: " + Integer.toString(paciente.getEdad()));
        lblEstado.setText("Estado: " + paciente.getEstado());
        lblSexo.setText("Sexo: " + paciente.getSexo());
        lblCuarto.setText("Cuarto: " + paciente.getNumCuarto());

        //Se habilita el botón de más detalles
        btnMasDetalles.setDisable(false);
    }

    /**
     * Se busca el paciente indicado por el usuario dentro del TextField de busqueda al presionar la tecla ENTER.
     * @param e
     */
    @FXML
    public void enterPresionado(KeyEvent e){
        if(e.getCode().toString().equals("ENTER")){
            pushedBuscaPaciente();
        }
    }

    public void pushedBtnHome(Event event) throws IOException {
        cargaPantalla(event,"home.fxml", btnHome);
    }

    /**
     * Este método muestra la ventana de mas detalles sobre el paciente seleccionado, pasando
     * como parámetro la persona seleccionada.
     * @param event
     * @throws IOException
     */
    public void pushedBtnMasDetalles(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("detallespaciente.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        ControllerDetallesPaciente controller = loader.getController();
        controller.initData(pacienteBuscado);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void pressAddPatient(Event event) throws IOException {
        cargaPantalla(event, "registropersona.fxml", btnAddPatient);
    }
}
