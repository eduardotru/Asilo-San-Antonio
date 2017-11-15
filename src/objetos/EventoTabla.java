package objetos;

import db.EnfermeroModel;
import db.PacienteModel;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.ExecutionException;


public class EventoTabla {

    private PacienteModel pacienteModel = new PacienteModel();
    private EnfermeroModel enfermeroModel = new EnfermeroModel();


    private final SimpleStringProperty asunto;
    private final SimpleStringProperty descripcion;
    private final SimpleStringProperty estaHospitalito;
    private final SimpleStringProperty avisoFamiliar;
    private final SimpleStringProperty requirioConsulta;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty paciente;
    private final SimpleStringProperty enfermero;

    public EventoTabla() {
        super();
        this.asunto = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.estaHospitalito = new SimpleStringProperty("");
        this.avisoFamiliar = new SimpleStringProperty("");
        this.requirioConsulta = new SimpleStringProperty("");
        this.fecha = new SimpleStringProperty("");
        this.paciente= new SimpleStringProperty("");
        this.enfermero = new SimpleStringProperty("");
    }

    public EventoTabla(Evento evento) {
        super();
        SimpleStringProperty pacienteTemp = null;
        SimpleStringProperty enfermeroTemp = null;
        this.asunto = new SimpleStringProperty(evento.getAsunto());
        this.descripcion = new SimpleStringProperty(evento.getDescripcion());
        this.estaHospitalito = new SimpleStringProperty(toString(evento.isEstaHospitalito()));
        this.avisoFamiliar = new SimpleStringProperty(toString(evento.isAvisoFamiliar()));
        this.requirioConsulta = new SimpleStringProperty(toString(evento.isRequirioConsulta()));
        this.fecha = new SimpleStringProperty(evento.getFecha().toString());
        try {
            pacienteTemp = new SimpleStringProperty(pacienteModel.selectPaciente(evento.getIdPaciente()).getNombre());
            enfermeroTemp = new SimpleStringProperty(enfermeroModel.selectEnfermero(evento.getIdEnfermero()).getNombre());
        }catch(Exception e){
            System.out.println("No se pudo cargar datos paciente y enfermero");
        }
        this.paciente = pacienteTemp;
        this.enfermero = enfermeroTemp;

    }


    private String toString(boolean b)
    {
        if(b == true){
            return "Si";
        }else {
            return "No";
        }
    }

    public String getAsunto() {
        return asunto.get();
    }

    public String getPaciente() {
        return paciente.get();
    }

    public String getEnfermero() {
        return enfermero.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public String getAvisoFamiliar() {
        return avisoFamiliar.get();
    }

    public String getEstaHospitalito() {
        return estaHospitalito.get();
    }

    public String getRequirioConsulta() {
        return requirioConsulta.get();
    }
}
