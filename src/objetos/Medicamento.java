package objetos;

public class Medicamento {
    private int id = -1;
    private int dosis;
    private String medidaDosis;
    private String nombreGenerico;

    public Medicamento(int id, int dosis, String medidaDosis, String nombreGenerico) {
        this.id = id;
        this.dosis = dosis;
        this.medidaDosis = medidaDosis;
        this.nombreGenerico = nombreGenerico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id == -1) {
            this.id = id;
        }
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getMedidaDosis() {
        return medidaDosis;
    }

    public void setMedidaDosis(String medidaDosis) {
        this.medidaDosis = medidaDosis;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }
}
