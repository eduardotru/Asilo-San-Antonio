package sample;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objetos.Paciente;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ControllerBase {



    /**
     * Esta funcion habilita la funcion de autocompletado en el textField de busqueda
     */
    public void setAutoCompletado(Paciente[] pacientes, TextField campoBusquedaPaciente){
        List<String> nombresPacientes = new ArrayList<String>();
        for (int i = 0; i < pacientes.length; i++) {
            nombresPacientes.add(pacientes[i].getNombre());
        }
        TextFields.bindAutoCompletion(campoBusquedaPaciente, nombresPacientes);
    }


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

    public void showAlertDialog(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Asilo San Antonio");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
