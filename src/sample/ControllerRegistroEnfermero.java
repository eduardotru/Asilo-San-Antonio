package sample;

import db.EnfermeroModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ControllerRegistroEnfermero extends ControllerBase {

    @FXML TextField textNombre;

    @FXML
    public void pressButton(javafx.event.ActionEvent event) {
        EnfermeroModel enfermeroModel = new EnfermeroModel();
        try {
            enfermeroModel.addEnfermero(textNombre.getText());
            showAlertDialog(Alert.AlertType.INFORMATION, "Se ha agregado el enfermero exitosamente.");
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlertDialog(Alert.AlertType.ERROR, "Ha habido un problema al guardar el enfermero. Intenta de nuevo.");
            e.printStackTrace();
        }
    }
}
