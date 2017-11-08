package sample;

import db.EnfermeroModel;
import db.InterfazDB;
import db.PacienteModel;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
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
            stage =(Stage) btnRegistrar.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
