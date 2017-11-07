package objetos;

import java.util.Date;

public class Paciente {
    private int id;
    private String nombre;
    private String estado;
    private char sexo;
    private Date fechaNacimiento;
    private int idCama;
    private Cama cama;
    private int idSeguro;
    private Seguro seguro;
    private int idServicioEmergencia;
    private ServicioEmergencia servicioEmergencia;

    public Paciente(int id, String nombre, String estado, char sexo, Date fecha)
    {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.sexo = sexo;
        this.fechaNacimiento = fecha;
    }

    public int getId() {
        return id;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public String getEstado() {
        return estado;
    }

    public char getSexo() {
        return sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getIdCama() {
        return idCama;
    }

    public int getIdSeguro() {
        return idSeguro;
    }

    public int getIdServicioEmergencia() {
        return idServicioEmergencia;
    }

    public void guardar() {
        //Guardar el contenido en la base de datos
    }
}
