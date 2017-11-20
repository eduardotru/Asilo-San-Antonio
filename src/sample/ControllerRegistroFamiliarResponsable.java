package sample;

import db.FamiliarResponsableModel;
import db.PacienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class ControllerRegistroFamiliarResponsable extends ControllerBase {

    @FXML private TextField textNombre;
    @FXML private TextField textRelacion;
    @FXML private TextField textTelefono;
    @FXML private Button btnRegistrar;
    @FXML private Label date;
    @FXML private Label ruta;

    private int Dia;
    private int Mes;
    private int Anio;

    @FXML
    /**
     * Entrada: ninguna
     * Salida: ninguna
     */
    private void initialize() {
        initializeDate(date);
        ruta.setText("Home > Registrar Familiar Responsable");
    }

    /**
     * Acción detonada al presionar el botón "Registrar" en la interfaz de usuario. Al finalizar el método se cierra
     * la ventana.
     * @param event
     * @throws IOException
     */
    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        // Save the new Familiar Responsable in the database.
        PacienteModel pacienteModel = new PacienteModel();
        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
        try {
            familiarResponsableModel.addFamiliarResponsable(
                    textNombre.getText(), textRelacion.getText(), textTelefono.getText()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.close();
    }
}
