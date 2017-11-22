package sample;

import db.MedicamentoModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.runtime.ECMAException;
import objetos.Medicamento;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.List;


public class ControllerAgregarReceta {
    private Medicamento[] medicamentos;
    private MedicamentoModel medicamentoModel = new MedicamentoModel();

    private int pacienteID;
    public void init(int pacientID){
        setAutoCompletadoMedicamentos();

    }

    @FXML public TextField textNombre;
    @FXML public TextField textMedida;
    @FXML public TextField textDosis;
    @FXML public CheckBox chkTomaMañana;
    @FXML public CheckBox chkTomaMedio;
    @FXML public CheckBox chkTomaTarde;
    @FXML public Button btnRegistrar;

    @FXML
    public void initialize(){

    }

    private void setAutoCompletadoMedicamentos(){
        List<String> nombresMedicamentos = new ArrayList<>();

        try {
                medicamentos = medicamentoModel.selectMedicamentos();
        }catch (Exception e) {
            System.out.println("Error al cargar los nombres de los medicamentos");
        }

        for(int i=0;i<medicamentos.length;i++) {
            nombresMedicamentos.add(medicamentos[i].getNombreGenerico());
            System.out.println(medicamentos[i].getNombreGenerico());
            while(i <medicamentos.length-1 && medicamentos[i+1].getNombreGenerico() == medicamentos[i].getNombreGenerico()) {
                i++;
            }
        }

        TextFields.bindAutoCompletion(textNombre,nombresMedicamentos);
    }

    private void setAutoCompletadoPresentaciones(String nombreMedicamento){
        List<String> medidasDosis = new ArrayList<>();
        try {
            medicamentos = medicamentoModel.selectMedicamentos(nombreMedicamento);
        }catch (Exception e) {
            System.out.println("Error al cargar los nombres de los medicamentos");
        }
        for(int i=0;i<medicamentos.length;i++) {
            medidasDosis.add(medicamentos[i].getMedidaDosis());

        }
        TextFields.bindAutoCompletion(textNombre,medidasDosis);
    }
}
