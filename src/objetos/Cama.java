package objetos;

public class Cama {
    private int id;
    private int numCama;
    private int idCuarto;
    private int idPaciente;

    public Cama(int id, int numCama, int idCuarto, int idPaciente) {
        this.id = id;
        this.numCama = numCama;
        this.idCuarto = idCuarto;
        this.idPaciente = idPaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(this.id == -1) {
            this.id = id;
        }
    }

    public int getNumCama() {
        return numCama;
    }

    public int getIdCuarto() {
        return idCuarto;
    }

    public int getIdPaciente() {
        return idPaciente;
    }
}
