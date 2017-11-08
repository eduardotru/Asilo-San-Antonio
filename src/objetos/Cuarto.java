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

    public void setId(int id) {
        if(this.id == -1) {
            this.id = id;
        }
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

    public int getCamasLength() {
        return arrCamas.length;
    }
}
