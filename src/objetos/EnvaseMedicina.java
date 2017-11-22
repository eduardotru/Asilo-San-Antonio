package objetos;

import java.util.Date;
import db.MedicamentoModel;
import db.PacienteModel;

public class EnvaseMedicina {
    private int id = -1;
    private String nombreComercial;
    private java.util.Date fechaSurtimiento;
    private String presentacion;
    private int cantidad;
    private int idMedicamento = -1;
    private Medicamento medicamento;
    private int idPaciente = -1;
    private Paciente paciente;
    private int dosisDisponibles = 0;
    private int diasDisponibles = 0;
    private boolean avisoFamiliar = false;

    public EnvaseMedicina(int id, String nombreComercial, java.util.Date fechaSurtimiento,
                          String presentacion, int cantidad, int idMedicamento, int idPaciente, boolean avisoFamiliar)
    {
        this.id = id;
        this.nombreComercial = nombreComercial;
        this.fechaSurtimiento = fechaSurtimiento;
        this.presentacion = presentacion;
        this.cantidad = cantidad;
        this.idMedicamento = idMedicamento;
        this.idPaciente = idPaciente;
        this.avisoFamiliar = avisoFamiliar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id == -1) {
            this.id = id;
        }
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public java.util.Date getFechaSurtimiento() {
        return fechaSurtimiento;
    }

    public void setFechaSurtimiento(Date fechaSurtimiento) {
        this.fechaSurtimiento = fechaSurtimiento;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Medicamento getMedicamento() {
        if(medicamento == null && idMedicamento != -1) {
            MedicamentoModel medicamentoModel = new MedicamentoModel();
            try {
                this.medicamento = medicamentoModel.selectMedicamento(this.idMedicamento);
            }
            catch (Exception e) {
                System.out.println("Error al obtener el medicamento.");
            }
        }
        return this.medicamento;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Paciente getPaciente() {
        if(paciente == null && idPaciente != -1) {
            PacienteModel pacienteModel = new PacienteModel();
            try {
                paciente = pacienteModel.selectPaciente(this.idPaciente);
            }
            catch (Exception e) {
                System.out.println("Error al obtener el paciente.");
            }
        }
        return paciente;
    }

    public int getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(int diasDisponibles) {
        this.diasDisponibles = diasDisponibles;
    }

    public int getDosisDisponibles() {
        return dosisDisponibles;
    }

    public void setDosisDisponibles(int dosisDisponibles) {
        this.dosisDisponibles = dosisDisponibles;
    }

    public boolean isAvisoFamiliar() {
        return avisoFamiliar;
    }

    public void setAvisoFamiliar(boolean avisoFamiliar) {
        this.avisoFamiliar = avisoFamiliar;
    }
}
