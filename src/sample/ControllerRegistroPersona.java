package sample;

import db.FamiliarResponsableModel;
import db.ServicioEmergenciaModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objetos.FamiliarResponsable;
import objetos.Paciente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

public class ControllerRegistroPersona {
    @FXML
    TextField txtNombre;

    @FXML
    DatePicker  dateFechaNacimiento;

    @FXML
    ComboBox cbSexo;

    @FXML
    ComboBox cbEstado;

    @FXML
    ComboBox cbCuarto;

    @FXML
    ComboBox cbCama;

    @FXML
    Button bAgregarFamiliar;

    @FXML
    Button bAgregarServicioEmergencia;

    @FXML
    Button bAgregarSeguro;

    @FXML
    TextArea txtFamiliares;

    @FXML
    TextArea txtPadecimientos;

    @FXML
    public Button btnRegistrar;

    @FXML
    public Label date;

    @FXML
    public Label ruta;

    private int Dia;
    private int Mes;
    private int Anio;

    @FXML
    private void initialize() {
        Calendar cal = Calendar.getInstance();
        Dia = cal.get(Calendar.DAY_OF_MONTH);
        Mes = cal.get(Calendar.MONTH) + 1;
        Anio = cal.get(Calendar.YEAR);
        date.setText("Fecha: " + Dia + "/" + Mes + "/" + Anio);
        ruta.setText("Home > Registrar Persona");

        // TODO(erickzul): Make estados table in database and retreive these values from it.
        cbEstado.getItems().add("Estable");
        cbEstado.getItems().add("Hospitalito");
        cbEstado.getItems().add("Internado");
        cbEstado.getItems().add("Baja");

        cbSexo.getItems().add("Hombre");
        cbSexo.getItems().add("Mujer");
    }

    public void  pressButtonAgregarFamiliar(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());

        int prevLastFamiliarId = -1;
        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
        try {
            prevLastFamiliarId = familiarResponsableModel.getLastFamiliarAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarFamiliar.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Familiar");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();

        int lastFamiliarId = -1;
        try {
            lastFamiliarId = familiarResponsableModel.getLastFamiliarAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastFamiliarId != lastFamiliarId) {
            FamiliarResponsable nuevoFamiliar = null;
            try {
                nuevoFamiliar = familiarResponsableModel.selectFamiliarResponsable(lastFamiliarId);
                txtFamiliares.setText(txtFamiliares.getText() + "\n" + nuevoFamiliar.getNombre());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarFamiliarClosed() {

    }

   /* public void  pressButtonAgregarServicio(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());

        int prevLastEmergencyId = -1;
        ServicioEmergenciaModel servicioEmergenciaModel = new ServicioEmergenciaModel();
        try {
            prevLastEmergencyId = servicioEmergenciaModel.getLastEmergencyServiceId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarFamiliar.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Familiar");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();

        int lastFamiliarId = -1;
        try {
            lastFamiliarId = familiarResponsableModel.getLastFamiliarAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastFamiliarId != lastFamiliarId) {
            FamiliarResponsable nuevoFamiliar = null;
            try {
                nuevoFamiliar = familiarResponsableModel.selectFamilar(lastFamiliarId);
                txtFamiliares.setText(txtFamiliares.getText() + "\n" + nuevoFamiliar.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 */
    public void  pressButtonAgregarSeguro(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());
    }

    public void  pressButtonRegister(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());
    }
}
