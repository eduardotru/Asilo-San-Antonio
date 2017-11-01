package objetos;

import java.util.Date;

public class Evento {
    private int id;
    private String asunto;
    private String descripcion;
    private boolean estaHospitalito;
    private boolean avisoFamiliar;
    private boolean requirioConsulta;
    private Date fecha;
    private int idPaciente;
    private int idEnfermero;
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
        this.idEnfermero = idEnfermero;
    }

    public String getName() {
        return Integer.toString(this.id);
    }

    public String getAsunto() {
        return this.asunto;
    }
}
