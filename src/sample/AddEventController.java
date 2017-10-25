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

public class AddEventController {
    @FXML
    public Label date;

    @FXML
    public Button btnRegistrar;

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
        ruta.setText("Home > Registrar Eventualidad");
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
