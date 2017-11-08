package objetos;

import db.CamaModel;

public class Cuarto {
    private int id;
    private int numCuarto;

    public Cuarto(int id, int numCuarto) {
        this.id = id;
        this.numCuarto = numCuarto;
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
}
