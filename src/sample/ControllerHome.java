package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Calendar;

public class ControllerHome extends ControllerBase {
    @FXML
    private Label date;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnEventualidad;
    @FXML
    private Button btnMenuPacientes;
    @FXML
    private Button btnMenuMedicinas;

    private int Dia;
    private int Mes;
    private int Anio;

    @FXML
    private void initialize() {
        initializeDate(date);
    }

    public void  pressButton(ActionEvent event) throws IOException{
        Stage stage = null;
        Parent root = null;
        if(event.getSource() == btnReporte) {
            System.out.println(event.getSource().toString());
            stage =(Stage) btnReporte.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } else if (event.getSource() == btnEventualidad) {
            System.out.println(event.getSource().toString());
            stage =(Stage) btnEventualidad.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("registroeventualidad.fxml"));
        } else if (event.getSource()== btnMenuPacientes) {
            System.out.println(event.getSource().toString());
            stage =(Stage) btnEventualidad.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("busquedapacientes.fxml"));
        } else if (event.getSource()== btnMenuMedicinas) {
            System.out.println(event.getSource().toString());
            stage =(Stage) btnEventualidad.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("menuMedicinas.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
