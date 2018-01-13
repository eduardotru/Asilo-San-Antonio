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

public class ControllerMenuReportes extends ControllerBase{

    @FXML private Button btnHome;
    @FXML private Button btnReporte;
    @FXML private Button btnHistorialRps;
    @FXML private Label date;

    @FXML
    private void initialize() {
        initializeDate(date);
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
        } else if(event.getSource()==btnHistorialRps) {
            stage = (Stage) btnHistorialRps.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("reporteconsulta.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
