package objetos;

import db.SeguroModel;
import db.ServicioEmergenciaModel;

import java.util.Date;

public class Paciente {
    private int id = -1;
    private String nombre;
    private String estado;
    private char sexo;
    private Date fechaNacimiento;
    private int numCuarto;
    private int numCama;
    private int idSeguro = -1;
    private Seguro seguro;
    private int idServicioEmergencia = -1;
    private ServicioEmergencia servicioEmergencia;

    public Paciente(int id, String nombre, String estado, char sexo,
                    Date fecha, int numCuarto, int numCama, int idSeguro, int idServicioEmergencia)
    {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.sexo = sexo;
        this.fechaNacimiento = fecha;
        this.numCuarto = numCuarto;
        this.numCama = numCama;
        this.idSeguro = idSeguro;
        this.idServicioEmergencia = idServicioEmergencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(this.id == -1) {
            this.id = id;
        }
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getNumCuarto() {
        return numCuarto;
    }

    public void setNumCuarto(int numCuarto) {
        this.numCuarto = numCuarto;
    }

    public int getNumCama() {
        return numCama;
    }

    public void setNumCama(int numCama) {
        this.numCama = numCama;
    }

    public int getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public Seguro getSeguro() {
        if(seguro == null && idSeguro != -1) {
            try {
                SeguroModel seguroModel = new SeguroModel();
                seguro = seguroModel.selectSeguro(idSeguro);
            }
            catch (Exception e) {
                System.out.println("Error al obtener el seguro: " + idSeguro);
            }
        }
        return seguro;
    }

    public int getIdServicioEmergencia() {
        return idServicioEmergencia;
    }
}
