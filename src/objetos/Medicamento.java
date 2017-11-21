package objetos;

public class Medicamento {
    private int id = -1;
    private String medidaDosis;
    private String nombreGenerico;

    public Medicamento(int id, String medidaDosis, String nombreGenerico) {
        this.id = id;
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
