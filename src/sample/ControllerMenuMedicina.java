package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ControllerMenuMedicina extends ControllerBase {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnMedDia;
    @FXML
    private Button btnMedicamentosInventario;
    @FXML
    private Label date;

    public void pshBotonMedicamentosDelDia(Event event) throws IOException{
        cargaPantalla(event,"medicamentosDelDia.fxml",btnMedDia);
    }

    public void pshBtnHome(Event event) throws IOException{
        cargaPantalla(event,"Home.fxml",btnHome);
    }

    public void pshBotonMedicamentosInventario(Event event) throws  IOException{
        cargaPantalla(event,"medicamentosInventario.fxml",btnMedicamentosInventario);
    }

}
