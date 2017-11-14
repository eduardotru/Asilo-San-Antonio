package sample;

import db.FamiliarResponsableModel;
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

public class ControllerRegistroFamiliarResponsable {

    @FXML
    public TextField textNombre;

    @FXML
    public TextField textRelacion;

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
        ruta.setText("Home > Registrar Familiar Responsable");
    }

    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());
        stage = (Stage) btnRegistrar.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // Save the new Familiar Responsable in the database.
        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
        try {
            familiarResponsableModel.addFamiliarResponsable(
                    textNombre.getText(), textRelacion.getText(), textTelefono.getText(),
                    -1
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.close();
    }
}
