package objetos;

import java.util.Date;
import db.InterfazDB;

public class Evento {
    private int id;
    private String asunto;
    private String descripcion;
    private boolean estaHospitalito;
    private boolean avisoFamiliar;
    private boolean requirioConsulta;
    private Date fecha;
    private int idPaciente;
    private Paciente paciente;
    private int idEnfermero;
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

    public String getName() {
        return Integer.toString(this.id);
    }

    public String getAsunto() {
        return this.asunto;
    }

    public Paciente getPaciente() {
        if(paciente == null) {
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
        if(enfermero == null) {
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

    public void guardar() {
        //Guardar el contenido en la base de datos
    }

}
