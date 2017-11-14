package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import objetos.Paciente;


public class ControllerDetallesPaciente {
    @FXML
    private TextField campoNombre;

    public void initData(Paciente paciente){
        campoNombre.setText(paciente.getNombre());
    }
}
