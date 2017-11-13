package objetos;

public class FamiliarResponsable {
    private int id = -1;
    private String sNombre;
    private String sRelacion;
    private String sTelefono;
    private int idPaciente;

    public FamiliarResponsable(int id, String sNombre, String sRelacion,
                               String sTelefono, int idPaciente){
        this.id = id;
        this.sNombre = sNombre;
        this.sRelacion = sRelacion;
        this.sTelefono = sTelefono;
        this.idPaciente = idPaciente;
    }

    public String getName() {
        return this.sNombre;
    }
}
