package sample;

import db.FamiliarResponsableModel;
import db.SeguroModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class ControllerRegistroSeguro extends ControllerBase {

    @FXML private TextField textNombre;
    @FXML private TextField textPoliza;
    @FXML  private Button btnRegistrar;

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
        // Poner en el label que muestra la fecha la fecha del día de hoy.
        initializeDate(date);
        ruta.setText("Home > Registrar Seguro Médico y de Emergencias");
    }

    /**
     * Acción detonada al presionar el botón "Registrar" en la pantalla de registro de un nuevo seguro.
     * @param event
     * @throws IOException
     */
    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        SeguroModel seguroModel = new SeguroModel();
        try {
            seguroModel.addSeguro(
                    textNombre.getText(), textPoliza.getText()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.close();
    }
}
