package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class ControllerMenuReportes {
    @FXML private Button btnHome;
    @FXML private Button btnReporte;
    @FXML private Button btnHistorialRps;
    @FXML private Label date;
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
    }
    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        if(event.getSource() == btnHome) {
            stage =(Stage) btnHome.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
            System.out.println(event.getSource().toString());
        } else if(event.getSource()==btnReporte){
            stage =(Stage) btnReporte.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("reportedeldia.fxml"));
        } else if(event.getSource()==btnReporte) {
            stage = (Stage) btnReporte.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("historialdereportes"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
