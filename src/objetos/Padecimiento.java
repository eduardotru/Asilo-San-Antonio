package objetos;

public class Padecimiento
{
    private int id = -1;
    private int idPaciente = -1;
    private String padecimiento;

    public Padecimiento(int id, int idPaciente, String padecimiento) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.padecimiento = padecimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id == -1) {
            this.id = id;
        }
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }
}
