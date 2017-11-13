package objetos;

import java.util.Date;

public class PacienteMedicamento {
    private int idPaciente = -1;
    private Paciente paciente;
    private int idMedicamento = -1;
    private Medicamento medicamento;
    private boolean tomaManana;
    private boolean tomaMedio;
    private boolean tomaTarde;
    private java.util.Date fechaInicio;
    private int duracion;

    public PacienteMedicamento(int idPaciente, int idMedicamento, boolean tomaManana,
                               boolean tomaMedio, boolean tomaTarde,
                               java.util.Date fechaInicio, int duracion) {
        this.idPaciente = idPaciente;
        this.idMedicamento = idMedicamento;
        this.tomaManana = tomaManana;
        this.tomaMedio = tomaMedio;
        this.tomaTarde = tomaTarde;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        if(idPaciente == -1) {
            this.idPaciente = idPaciente;
        }
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        if(idMedicamento == -1) {
            this.idMedicamento = idMedicamento;
        }
    }

    public boolean isTomaManana() {
        return tomaManana;
    }

    public void setTomaManana(boolean tomaManana) {
        this.tomaManana = tomaManana;
    }

    public boolean isTomaMedio() {
        return tomaMedio;
    }

    public void setTomaMedio(boolean tomaMedio) {
        this.tomaMedio = tomaMedio;
    }

    public boolean isTomaTarde() {
        return tomaTarde;
    }

    public void setTomaTarde(boolean tomaTarde) {
        this.tomaTarde = tomaTarde;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
