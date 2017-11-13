package db;

import objetos.FamiliarResponsable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FamiliarResponsableModel extends InterfazDB
{
    public int getLastFamiliarAddedId() throws SQLException {
        int familiarId = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener un evento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "SELECT id FROM Asilo.familiarresponsable ORDER BY id DESC");
            ResultSet result = prepStatement.executeQuery();
            if (result.next()) {
                familiarId = result.getInt("id");
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return familiarId;
    }

  /*  public FamiliarResponsable selectFamilar(int id) throws SQLException {
        FamiliarResponsable familiar = null;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener un evento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.familiarresponsable AS f WHERE f.id = ?");
            ResultSet result = prepStatement.executeQuery();
            if (result.next()) {
                int idFamiliar = result.getInt("id");
                String sNombre = result.getString("nombre");
                String sRelacion = result.getString("relacion");
                String sTelefono = result.getString("telefono");
                int idPaciente = result.getInt("idPaciente");
                familiar = new FamiliarResponsable(idFamiliar, sNombre, sRelacion, sTelefono,
                        idPaciente);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return familiar;
    }*/
}
