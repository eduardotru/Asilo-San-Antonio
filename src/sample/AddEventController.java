package sample;

import db.EnfermeroModel;
import db.EventoModel;
import db.InterfazDB;
import db.PacienteModel;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import objetos.*;

public class AddEventController {
    @FXML
    public Label date;

    @FXML
    public Button btnRegistrar;

    @FXML
    public Label ruta;

    @FXML
    public ComboBox nombrePaciente;

    @FXML
    public ComboBox nombreEnfermero;

    @FXML
    public TextField textAsunto;

    @FXML
    public CheckBox bHospitalito;

    @FXML
    public CheckBox bFamiliar;

    @FXML
    public CheckBox bConsulta;

    @FXML
    public DatePicker fechaEventualidad;

    @FXML
    public TextArea textComentarios;

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
        ruta.setText("Home > Registrar Eventualidad");

        PacienteModel pacienteModel = new PacienteModel();

        Paciente[] pacientes;
        try {
            pacientes = pacienteModel.selectPacientes();
            for (int i = 0; i < pacientes.length; i++) {
                System.out.println(pacientes[i].getNombre());
                nombrePaciente.getItems().add(pacientes[i].getNombre());
            }

        } catch (Exception e) {
            System.out.println(e.getClass()+e.getMessage());
        }

        EnfermeroModel enfermeroModel = new EnfermeroModel();
        Enfermero[] enfermeros;
        try {
            enfermeros = enfermeroModel.selectEnfermeros();
            System.out.println(enfermeros.length);
            for (int i = 0; i < enfermeros.length; i++) {
                System.out.println(enfermeros[i].getNombre());
                nombreEnfermero.getItems().add(enfermeros[i].getNombre());
            }
        } catch (Exception e) {
            System.out.println(e.getClass()+e.getMessage());
        }
    }

    public void  pressButton(ActionEvent event) throws IOException{
        Stage stage = null;
        Parent root = null;
        if(event.getSource() == btnRegistrar) {
            System.out.println(event.getSource().toString());

            EnfermeroModel enfermeroModel = new EnfermeroModel();
            PacienteModel pacienteModel = new PacienteModel();
            int enfermeroId = 0, pacienteId = 0;
            try {
                enfermeroId = enfermeroModel.selectIdEnfermero(nombreEnfermero.getValue().toString());
                pacienteId = pacienteModel.selectIdPaciente(nombrePaciente.getValue().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LocalDate dateEventualidad = fechaEventualidad.getValue();

            String sAsunto = textAsunto.getText();
            String sDescripcion = textComentarios.getText();

            boolean estaHospitalito = bHospitalito.isSelected();
            boolean avisoFamiliar = bFamiliar.isSelected();
            boolean consulta = bConsulta.isSelected();

            Evento eventoNuevo = new Evento(0, sAsunto, sDescripcion,
                    estaHospitalito, avisoFamiliar, consulta,
                    Date.from(dateEventualidad.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    pacienteId, enfermeroId);

            EventoModel eventoModel = new EventoModel();
            try {
                eventoModel.addEventualidad(eventoNuevo);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Asilo San Antonio");
                alert.setHeaderText(null);
                alert.setContentText("Tuvimos problemas al intentar procesar tu nuevo evento. Intenta más tarde.");
                alert.showAndWait();
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("La eventualidad ha sido registrada de manera exitosa.");
            alert.showAndWait();

            stage =(Stage) btnRegistrar.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
