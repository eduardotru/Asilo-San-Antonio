package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import db.EnvaseMedicinaModel;
import db.MedicamentoModel;
import db.PacienteMedicamentoModel;
import db.PacienteModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.ECMAException;
import objetos.Medicamento;
import objetos.PacienteMedicamento;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ControllerAgregarReceta {
    private Medicamento[] medicamentos;
    private MedicamentoModel medicamentoModel = new MedicamentoModel();
    private PacienteMedicamentoModel pacienteMedicamentoModel = new PacienteMedicamentoModel();
    private EnvaseMedicinaModel envaseMedicinaModel = new EnvaseMedicinaModel();


    private int pacienteID;
    public void init(int pacientID){
        setAutoCompletadoMedicamentos();
        pacienteID=pacientID;
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
            boolean duplicado = false;
            for (int j = 0; j < nombresMedicamentos.size(); j++) {

                if(medicamentos[i].getNombreGenerico().equals(nombresMedicamentos.get(j)))
                    duplicado = true;
            }
            if(!duplicado)  nombresMedicamentos.add(medicamentos[i].getNombreGenerico());
        }

        TextFields.bindAutoCompletion(textNombre,nombresMedicamentos);
    }

    @FXML
    private void setAutoCompletadoPresentaciones(){
        String nombreMedicamento = textNombre.getText();
        int idMed=0;
        if(!textNombre.getText().equals("")) {
            List<String> medidasDosis = new ArrayList<>();
            try {
                medicamentos = medicamentoModel.selectMedicamentos(nombreMedicamento);
            } catch (Exception e) {
                System.out.println("Error al cargar los nombres de los medicamentos");
            }
            if(medicamentos != null){
               for (int i = 0; i < medicamentos.length; i++) {
                   medidasDosis.add(medicamentos[i].getMedidaDosis());
                   System.out.println(medicamentos[i].getMedidaDosis());
                }
            }
            TextFields.bindAutoCompletion(textMedida, medidasDosis);
        }
    }

    @FXML
    private void registraReceta(ActionEvent event) throws IOException{
        System.out.println(pacienteID);
        if(!textNombre.getText().equals("")&&!textDosis.getText().equals("")&&!textMedida.getText().equals("")&&(chkTomaMañana.isSelected()||chkTomaMedio.isSelected()||chkTomaTarde.isSelected())) {
            System.out.println("Se puede guardar");
            Medicamento medicamentoGuardar = null;
            if (medicamentos != null) {
                try {
                    medicamentoGuardar = medicamentoModel.selectMedicamento(textNombre.getText(),textMedida.getText());
                }catch (Exception e){

                }
                if(medicamentoGuardar!=null){

                    System.out.println("Accediendo a medicamento existente");
                    try {
                        pacienteMedicamentoModel.addPacienteMedicamento(pacienteID, medicamentoGuardar.getId(), Integer.parseInt(textDosis.getText()),
                                chkTomaMañana.isSelected(), chkTomaMedio.isSelected(), chkTomaTarde.isSelected(), new Date(), 14);
                        agregaEnvase(pacienteID,medicamentoGuardar.getId(),medicamentoGuardar.getNombreGenerico());
                    }catch (Exception e) {
                        System.out.println("Error al agregar receta");
                    }
                }else{
                    int nuevoMedicamentoID =0;
                    System.out.println("Agregar medicamento completamente nuevo");
                    try{
                        nuevoMedicamentoID = medicamentoModel.addMedicamento(textMedida.getText(),textNombre.getText());
                        medicamentoGuardar = new Medicamento(nuevoMedicamentoID,textDosis.getText(),textNombre.getText());
                        pacienteMedicamentoModel.addPacienteMedicamento(pacienteID, nuevoMedicamentoID, Integer.parseInt(textDosis.getText()),
                                chkTomaMañana.isSelected(), chkTomaMedio.isSelected(), chkTomaTarde.isSelected(), new Date(), 14);
                        agregaEnvase(pacienteID,medicamentoGuardar.getId(),medicamentoGuardar.getNombreGenerico());
                    }catch (Exception e) {
                        System.out.println("Error al agregar medicamento nuevo");
                    }
                }
            }else {
                int nuevoMedicamentoID =0;
                System.out.println("Agregar medicamento completamente nuevo");
                try{
                    nuevoMedicamentoID = medicamentoModel.addMedicamento(textMedida.getText(),textNombre.getText());
                    medicamentoGuardar = new Medicamento(nuevoMedicamentoID,textDosis.getText(),textNombre.getText());
                    pacienteMedicamentoModel.addPacienteMedicamento(pacienteID, nuevoMedicamentoID, Integer.parseInt(textDosis.getText()),
                            chkTomaMañana.isSelected(), chkTomaMedio.isSelected(), chkTomaTarde.isSelected(), new Date(), 14);
                    agregaEnvase(pacienteID,medicamentoGuardar.getId(),medicamentoGuardar.getNombreGenerico());
                }catch (Exception e) {
                    System.out.println("Error al agregar medicamento nuevo");
                }
            }

            //Cierre de ventana
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();

        }
        else{
            System.out.println("No se puede guardar");
        }
    }

    public void agregaEnvase(int idPac,int idMedi,String nombreMedi){
        try{
            envaseMedicinaModel.addEnvaseMedicina(nombreMedi,new Date(),"presentacionBase",0,idMedi,idPac);
        }catch (Exception e){
            System.out.println("Error al agregar el envase");
        }
    }
}
