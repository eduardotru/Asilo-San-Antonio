package sample;

import db.FamiliarResponsableModel;
import db.PacienteModel;
import db.SeguroModel;
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
import objetos.Seguro;
import objetos.ServicioEmergencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    TextField txtCuarto;

    @FXML
    TextField txtCama;

    @FXML
    Button bAgregarFamiliar;

    @FXML
    Button bAgregarServicioEmergencia;

    @FXML
    Button bAgregarSeguro;

    @FXML
    TextArea txtFamiliares;

    @FXML
    TextArea txtServicios;

    @FXML
    TextArea txtSeguros;

    @FXML
    TextArea txtPadecimientos;

    @FXML
    public Button btnRegistrar;

    @FXML
    private TextField txtNumeroReferencia;

    @FXML
    public Label date;

    @FXML
    public Label ruta;

    private int Dia;
    private int Mes;
    private int Anio;

    private ArrayList<FamiliarResponsable> familiares;
    private ServicioEmergencia servicio;
    private Seguro seguro;

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
                familiares.add(nuevoFamiliar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void  pressButtonAgregarServicio(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());

        int prevLastServicioId = -1;
        ServicioEmergenciaModel servicioEmergenciaModel = new ServicioEmergenciaModel();
        try {
            prevLastServicioId = servicioEmergenciaModel.getLastServicioAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarServicio.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Servicio de Emergencia");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();

        int lastServicioId = -1;
        try {
            lastServicioId = servicioEmergenciaModel.getLastServicioAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastServicioId != lastServicioId) {
            ServicioEmergencia nuevoServicio = null;
            try {
                nuevoServicio = servicioEmergenciaModel.selectServicioEmergencia(lastServicioId);
                txtServicios.setText(txtServicios.getText() + "\n" + nuevoServicio.getNombre());
                bAgregarFamiliar.setText("Reemplazar Familiar");
                if (servicio != null) {
                    servicioEmergenciaModel.deleteServicioEmergencia(servicio.getId());
                }
                servicio = nuevoServicio;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void  pressButtonAgregarSeguro(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());

        int prevLastSeguroId = -1;
        SeguroModel seguroModel = new SeguroModel();
        try {
            prevLastSeguroId = seguroModel.getLastSeguroAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarSeguro.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Seguro Médico");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();

        int lastSeguroId = -1;
        try {
            lastSeguroId = seguroModel.getLastSeguroAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastSeguroId != lastSeguroId) {
            Seguro nuevoSeguro = null;
            try {
                nuevoSeguro = seguroModel.selectSeguro(lastSeguroId);
                txtSeguros.setText(txtSeguros.getText() + "\n" + nuevoSeguro.getNombre());
                bAgregarSeguro.setText("Reemplazar Seguro");
                if (seguro != null) {
                    seguroModel.deleteSeguro(seguro.getId());
                }
                seguro = nuevoSeguro;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void  pressButtonRegister(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (txtNombre.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Favor de especificar un nombre para el paciente.");
            alert.showAndWait();
            return;
        }

        if (dateFechaNacimiento.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Favor de especificar la fecha de nacimiento.");
            alert.showAndWait();
            return;
        }

        if (cbSexo.getValue() == "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Favor de especificar el sexo.");
            alert.showAndWait();
            return;
        }

        if (cbEstado.getValue() == "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Favor de especificar el estado.");
            alert.showAndWait();
            return;
        }

        PacienteModel pacienteModel = new PacienteModel();
        String nombre = txtNombre.getText();
        java.sql.Date fechaNacimiento = java.sql.Date.valueOf(dateFechaNacimiento.getValue());
        char sexo = ((String)cbSexo.getValue()).charAt(0);
        String estado = (String)cbEstado.getValue();
        int numCuarto = Integer.parseInt(txtCuarto.getText());
        int numCama = Integer.parseInt(txtCama.getText());
        int idSeguro = seguro.getId();
        int idServicioEmergencia = servicio.getId();
        String numeroReferencia = txtNumeroReferencia.getText();

        try {
            int nuevoPacienteId = pacienteModel.addPaciente(nombre, fechaNacimiento,
                sexo, estado, numCuarto, numCama, idSeguro, idServicioEmergencia, numeroReferencia);

            FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
            for (FamiliarResponsable familiar: familiares) {
                familiar.setIdPaciente(nuevoPacienteId);
                familiarResponsableModel.updateFamiliarResponsable(familiar);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Se ha guardado el nuevo paciente de forma exitosa.");
            alert.showAndWait();
            return;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un error al guardar al nuevo paciente. Favor de intentarlo más tarde.");
            alert.showAndWait();
            e.printStackTrace();
            return;
        }
    }
}
