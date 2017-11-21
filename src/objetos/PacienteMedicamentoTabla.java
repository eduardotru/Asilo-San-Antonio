package objetos;

import db.EnvaseMedicinaModel;
import db.MedicamentoModel;
import db.PacienteMedicamentoModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import objetos.Medicamento;
import objetos.PacienteMedicamento;

public class PacienteMedicamentoTabla{

    //Los atributos clase deben de estar en un tipo de variable especial llamado Single Property para que los datos puedan
    //ser utilizados por los TableView
    private final SimpleStringProperty medicamento;
    private final SimpleStringProperty tomaManana;
    private final SimpleStringProperty tomaMedio;
    private final SimpleStringProperty tomaTarde;
    private final SimpleStringProperty via;
    private final SimpleStringProperty dosis;
    private final SimpleStringProperty dosisDisponibles;

    //Constructor con parámetros del la clase
    public PacienteMedicamentoTabla(String medicamento, String tomaManana, String tomaMedio, String tomaTarde, String via,String dosis,String dosisDisp) {
        super();
        this.medicamento = new SimpleStringProperty(medicamento);
        this.tomaManana = new SimpleStringProperty(tomaManana);
        this.tomaMedio = new SimpleStringProperty(tomaMedio);
        this.tomaTarde = new SimpleStringProperty(tomaTarde);
        this.via = new SimpleStringProperty(via);
        this.dosis = new SimpleStringProperty(dosis);
        this.dosisDisponibles = new SimpleStringProperty(dosisDisp);
    }

    //Constructor por default de la clase
    public PacienteMedicamentoTabla() {
        super();
        this.medicamento = new SimpleStringProperty() ;
        this.tomaManana = new SimpleStringProperty();
        this.tomaMedio = new SimpleStringProperty();
        this.tomaTarde = new SimpleStringProperty();
        this.via = new SimpleStringProperty();
        this.dosis = new SimpleStringProperty();
        this.dosisDisponibles = new SimpleStringProperty();
    }

    //Este contructor convierte directamente un objeto de la clase Paciente medicamento a un objeto compatible
    //para los Table View de las interfaces
    //Entrada: Objeto de tipo Paciente Medicamento.
    public PacienteMedicamentoTabla(PacienteMedicamento pacienteMedicamento){
        super();
        Medicamento medicamento = null;
        PacienteMedicamentoModel pacienteMedicamentoModel = new PacienteMedicamentoModel();
        EnvaseMedicinaModel envaseMedicinaModel = new EnvaseMedicinaModel();
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
        this.dosis= new SimpleStringProperty(Integer.toString(pacienteMedicamento.dosis));
        this.dosisDisponibles = new SimpleStringProperty("Prueba");
    }

    //Método para convertir los atributos booleanos de la clase Paciente medicamento a tipo String
    private String toString(boolean bool){
        if(bool)
        {
            return "Si";
        }
        else {
            return "No";
        }

    }

    //Gets de los atributos de la clase
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
    public String getDosisDisponibles() {
        return dosisDisponibles.get();
    }
}
