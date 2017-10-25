package objetos;

import java.util.Date;

public class Paciente {
    private int id;
    private String nombre;
    private String estado;
    private char sexo;
    private Date fechaNacimiento;
    public Paciente(int id, String nombre, String estado, char sexo, Date fecha)
    {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.sexo = sexo;
        this.fechaNacimiento = fecha;
    }

    public String getNombre()
    {
        return this.nombre;
    }
}
