package objetos;

public class Cuarto {
    private int id;
    private int numCuarto;
    private Cama[] arrCamas;

    public Cuarto(int id, int numCuarto, Cama[] arrCamas) {
        this.id = id;
        this.numCuarto = numCuarto;
        this.arrCamas = arrCamas;
    }

    public int getId() {
        return id;
    }

    public int getNumCuarto() {
        return numCuarto;
    }

    public Cama getCama(int i) {
        if(i >= arrCamas.length || i < 0) {
            return null;
        }
        return arrCamas[i];
    }

    public void guardar() {
        //Guardar el contenido en la base de datos
    }
}
