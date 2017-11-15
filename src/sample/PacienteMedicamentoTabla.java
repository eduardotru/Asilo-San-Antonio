package sample;

import db.MedicamentoModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import objetos.Medicamento;
import objetos.PacienteMedicamento;

public class PacienteMedicamentoTabla{
    private final SimpleStringProperty medicamento;
    private final SimpleStringProperty tomaManana;
    private final SimpleStringProperty tomaMedio;
    private final SimpleStringProperty tomaTarde;
    private final SimpleStringProperty via;
    private final SimpleStringProperty dosis;

    PacienteMedicamentoTabla(String medicamento, String tomaManana, String tomaMedio, String tomaTarde, String via,String dosis) {
        super();
        this.medicamento = new SimpleStringProperty(medicamento);
        this.tomaManana = new SimpleStringProperty(tomaManana);
        this.tomaMedio = new SimpleStringProperty(tomaMedio);
        this.tomaTarde = new SimpleStringProperty(tomaTarde);
        this.via = new SimpleStringProperty(via);
        this.dosis = new SimpleStringProperty(dosis);
    }

    PacienteMedicamentoTabla(PacienteMedicamento pacienteMedicamento){
        super();
        Medicamento medicamento = null;
        MedicamentoModel medicamentoModel = new MedicamentoModel();
        try {
            //Se obtiene el objeto medicamento
           medicamento = medicamentoModel.selectMedicamento(pacienteMedicamento.getIdMedicamento());
        }catch (Exception e) {
            System.out.println("No se pudo convertir correctamente el objeto medicamento a medicamentoTabla");
        }
        this.medicamento = new SimpleStringProperty(medicamento.getNombreGenerico());//Obtiene el nombre genérico
        this.tomaManana = new SimpleStringProperty(toString(pacienteMedicamento.isTomaManana()));
        this.tomaMedio = new SimpleStringProperty(toString(pacienteMedicamento.isTomaMedio()));
        this.tomaTarde = new SimpleStringProperty(toString(pacienteMedicamento.isTomaTarde()));
        this.via = new SimpleStringProperty(medicamento.getMedidaDosis());
        this.dosis= new SimpleStringProperty(Integer.toString(medicamento.getDosis()));
    }

    private String toString(boolean bool){
        if(bool)
        {
            return "Si";
        }
        else {
            return "No";
        }

    }

    public String getMedicamento(){
        return medicamento.get();
    }
    public String getTomaManana(){
        return tomaManana.get();
    }
    public String getTomaMedio(){
        return tomaMedio.get();
    }
    public String getTomaTarde(){
        return tomaTarde.get();
    }
    public String getVia(){return via.get();}
    public String getDosis(){return dosis.get();}
}
