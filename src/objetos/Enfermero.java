package objetos;

public class Enfermero {
    private int id;
    private String nombre;

    public Enfermero(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public int getId() {
        return id;
    }

    public void guardar() {
        //Guardar el contenido en la base de datos
    }
}
