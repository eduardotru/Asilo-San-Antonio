package objetos;

import java.util.Date;
import db.InterfazDB;

public class Evento {
    private int id = -1;
    private String asunto;
    private String descripcion;
    private boolean estaHospitalito;
    private boolean avisoFamiliar;
    private boolean requirioConsulta;
    private Date fecha;
    private int idPaciente = -1;
    private Paciente paciente;
    private int idEnfermero = -1;
    private Enfermero enfermero;
    public Evento(int id, String asunto, String descripcion, boolean estaHospitalito, boolean avisoFamiliar,
                  boolean requirioConsulta, Date fecha, int idPaciente, int idEnfermero)
    {
        this.id = id;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estaHospitalito = estaHospitalito;
        this.avisoFamiliar = avisoFamiliar;
        this.requirioConsulta = requirioConsulta;
        this.fecha = fecha;
        this.idPaciente = idPaciente;
        this.paciente = null;
        this.idEnfermero = idEnfermero;
        this.enfermero = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(this.id == -1) {
            this.id = id;
        }
    }

    public String getAsunto() {
        return this.asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isAvisoFamiliar() {
        return avisoFamiliar;
    }

    public boolean isEstaHospitalito() {
        return estaHospitalito;
    }

    public boolean isRequirioConsulta() {
        return requirioConsulta;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public int getIdEnfermero() {
        return idEnfermero;
    }

    public Paciente getPaciente() {
        if(paciente == null && idPaciente != -1) {
            InterfazDB interfazDB = InterfazDB.getInstanciaInterfazDB();
            try {
                paciente = interfazDB.selectPaciente(idPaciente);
            }
            catch(Exception e) {
                System.out.println("Error al obtener el paciente: " + idPaciente);
            }
        }
        return paciente;
    }

    public Enfermero getEnfermero() {
        if(enfermero == null && idEnfermero != -1) {
            InterfazDB interfazDB = InterfazDB.getInstanciaInterfazDB();
            try {
                enfermero = interfazDB.selectEnfermero(idEnfermero);
            }
            catch(Exception e) {
                System.out.println("Error al obtener el enfermero: " + idEnfermero);
            }
        }
        return enfermero;
    }
}
