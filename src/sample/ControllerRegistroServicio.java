package sample;

import db.SeguroModel;
import db.ServicioEmergenciaModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class ControllerRegistroServicio {

    @FXML
    public TextField textNombre;

    @FXML
    public TextField textDireccion;

    @FXML
    public TextField textTelefono;

    @FXML
    public Button btnRegistrar;

    @FXML
    public Label date;

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
        ruta.setText("Home > Registrar Seguro Médico y de Emergencias");
    }

    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = null;

        System.out.println(event.getSource().toString());

        ServicioEmergenciaModel servicioEmergenciaModel = new ServicioEmergenciaModel();
        try {
            servicioEmergenciaModel.addServicioEmergencia(
                    textNombre.getText(), textTelefono.getText(), textDireccion.getText()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.close();
    }
}
