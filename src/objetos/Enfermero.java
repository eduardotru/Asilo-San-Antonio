package objetos;

public class Enfermero {
    private int id = -1;
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

    public void setId(int id) {
        if(this.id == -1) {
            this.id = id;
        }
    }
}
