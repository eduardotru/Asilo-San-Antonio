package objetos;

public class ServicioEmergencia {
    private int id = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(this.id == -1) {
            this.id = id;
        }
    }
}
