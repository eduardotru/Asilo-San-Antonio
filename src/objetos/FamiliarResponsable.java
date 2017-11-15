package objetos;

import db.PacienteModel;

public class FamiliarResponsable {
    private int id = -1;
    private String sNombre;
    private String sRelacion;
    private String sTelefono;
    private int idPaciente = -1;
    private Paciente paciente;

    public FamiliarResponsable(int id, String sNombre, String sRelacion,
                               String sTelefono, int idPaciente){
        this.id = id;
        this.sNombre = sNombre;
        this.sRelacion = sRelacion;
        this.sTelefono = sTelefono;
        this.idPaciente = idPaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id == -1) {
            this.id = id;
        }
    }

    public String getNombre() {
        return this.sNombre;
    }

    public void setNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public String getRelacion() {
        return sRelacion;
    }

    public void setRelacion(String sRelacion) {
        this.sRelacion = sRelacion;
    }

    public String getTelefono() {
        return sTelefono;
    }

    public void setTelefono(String sTelefono) {
        this.sTelefono = sTelefono;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public Paciente getPaciente() {
        if(paciente == null && idPaciente != -1) {
            try {
                PacienteModel pacienteModel = new PacienteModel();
                paciente = pacienteModel.selectPaciente(idPaciente);
            }
            catch(Exception e) {
                System.out.println("Error al obtener un paciente");
            }
        }
        return paciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}