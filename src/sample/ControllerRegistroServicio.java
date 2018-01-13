package sample;

import db.SeguroModel;
import db.ServicioEmergenciaModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class ControllerRegistroServicio extends ControllerBase{

    @FXML private TextField textNombre;
    @FXML private TextField textDireccion;
    @FXML private TextField textTelefono;
    @FXML private Button btnRegistrar;

    @FXML
    public Label date;
    @FXML
    public Label ruta;

    private int Dia;
    private int Mes;
    private int Anio;

    @FXML
    /**
     * Entrada: ninguna
     * Salida: ninguna (void)
     */
    private void initialize() {
        initializeDate(date);
        ruta.setText("Home > Registrar Seguro Médico y de Emergencias");
    }

    /**
     * Acción detonada al presionar el botón "Registrar" en la interfaz de usuario.
     * @param event
     * @throws IOException
     */
    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();;

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
