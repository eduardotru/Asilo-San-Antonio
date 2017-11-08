package objetos;

public class Seguro {
    private int id = -1;
    private String nombre;
    private String numPoliza;

    public Seguro(int id, String nombre, String numPoliza) {
        this.id = id;
        this.nombre = nombre;
        this.numPoliza = numPoliza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        // Solo se puede modificar si no tiene id
        if(this.id == -1) {
            this.id = id;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumPoliza() {
        return numPoliza;
    }
}
