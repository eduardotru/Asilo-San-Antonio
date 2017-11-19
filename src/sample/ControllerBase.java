package sample;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class ControllerBase {
    //Carga la ventana la interfaz que se da como parámetros
    //Entradas: El nombre de la pantalla a cargar, el botón que se presionó.
    public void cargaPantalla(Event event, String nombrePantalla, Button button) throws IOException {
        Stage stage = null;
        Parent root = null;
        System.out.println(event.getSource().toString());
        stage =(Stage) button.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(nombrePantalla));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Obtiene la fecha actual y lo escribe en un Label
    //Entrada: El label que será modificado.
    //Salida: Ninguna
    public void initializeDate(Label date) {
        Calendar cal = Calendar.getInstance();
        int Dia = cal.get(Calendar.DAY_OF_MONTH);
        int Mes = cal.get(Calendar.MONTH) + 1;
        int Anio = cal.get(Calendar.YEAR);
        date.setText("Fecha: " + Dia + "/" + Mes + "/" + Anio);
    }
}
