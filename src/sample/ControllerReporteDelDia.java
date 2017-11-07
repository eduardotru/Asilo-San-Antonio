package sample;

import db.InterfazDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objetos.Evento;
import java.util.Date;

import java.io.IOException;
import java.util.Calendar;

public class ControllerReporteDelDia {
    @FXML private Button btnHome;
    @FXML private Button btnMenuReporte;
    @FXML private Label date;
    @FXML private Label name;
    @FXML private Label asunto;

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

        /*InterfazDB db = InterfazDB.getInstanciaInterfazDB();
        Evento[] eventos = db.selectEventos(new Date());

        name.setText(eventos[0].getName());
        asunto.setText(eventos[0].getAsunto());*/
    }

    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        if(event.getSource() == btnHome) {
            stage =(Stage) btnHome.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
            System.out.println(event.getSource().toString());
        } else if(event.getSource()==btnMenuReporte){
            stage =(Stage) btnMenuReporte.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
