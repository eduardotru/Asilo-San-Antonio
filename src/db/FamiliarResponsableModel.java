package db;

import objetos.FamiliarResponsable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public void addFamiliarResponsable(FamiliarResponsable familiarResponsable) throws Exception
    {
        try {
            int id = addFamiliarResponsable(familiarResponsable.getNombre(), familiarResponsable.getRelacion(),
                    familiarResponsable.getTelefono(), familiarResponsable.getIdPaciente());
            familiarResponsable.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addFamiliarResponsable(String nombre, String relacion, String telefono,
                                      int idPaciente) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un familiarResponsable: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.FamiliarResponsable(id, nombre, " +
                            "relacion, telefono, idPaciente) " +
                            "VALUES (default, ?, ?, ?, ?)");
            prepStatement.setString(1, nombre);
            prepStatement.setString(2, relacion);
            prepStatement.setString(3, telefono);
            prepStatement.setInt(4, idPaciente);
            prepStatement.executeUpdate();

            ResultSet result = prepStatement.getGeneratedKeys();

            if(result.next()) {
                id = result.getInt(1);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return id;
    }

    public FamiliarResponsable selectFamiliarResponsable(int id) throws Exception
    {
        FamiliarResponsable familiarResponsable;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los familiarResponsables: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.FamiliarResponsable WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            FamiliarResponsable[] familiarResponsables = crearListaFamiliarResponsables(result);
            if(familiarResponsables.length == 1) {
                familiarResponsable = familiarResponsables[0];
            }
            else {
                familiarResponsable = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return familiarResponsable;
    }

    public FamiliarResponsable selectFamiliarResponsablePorPacienteId(int id) throws Exception
    {
        FamiliarResponsable familiarResponsable;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los familiarResponsables: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.FamiliarResponsable WHERE idPaciente = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            FamiliarResponsable[] familiarResponsables = crearListaFamiliarResponsables(result);
            if(familiarResponsables.length == 1) {
                familiarResponsable = familiarResponsables[0];
            }
            else {
                familiarResponsable = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return familiarResponsable;
    }

    public int selectIdFamiliarResponsable(String nombre) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los familiarResponsables: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT id FROM Asilo.FamiliarResponsable WHERE nombre = ?");
            preparedStatement.setString(1, nombre);

            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return id;
    }

    public FamiliarResponsable[] selectFamiliarResponsablesPorPaciente(int idPaciente) throws Exception
    {
        FamiliarResponsable[] familiarResponsables;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los familiarResponsables: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.FamiliarResponsable WHERE idPaciente = ?");
            prepStatement.setInt(1, idPaciente);
            prepStatement.executeQuery();
            ResultSet result = prepStatement.getResultSet();
            familiarResponsables = crearListaFamiliarResponsables(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return familiarResponsables;
    }

    public FamiliarResponsable[] selectFamiliarResponsables() throws Exception
    {
        FamiliarResponsable[] familiarResponsables;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los familiarResponsables: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.FamiliarResponsable");
            familiarResponsables = crearListaFamiliarResponsables(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return familiarResponsables;
    }

    private FamiliarResponsable[] crearListaFamiliarResponsables(ResultSet result) throws SQLException
    {
        ArrayList<FamiliarResponsable> listaFamiliarResponsables = new ArrayList<FamiliarResponsable>();
        while(result.next())
        {
            int id = result.getInt("id");
            String nombre = result.getString("nombre");
            String relacion = result.getString("relacion");
            String telefono = result.getString("telefono");
            int idPaciente = result.getInt("idPaciente");
            listaFamiliarResponsables.add(new FamiliarResponsable(id, nombre, relacion, telefono, idPaciente));
        }
        FamiliarResponsable[] arrFamiliarResponsables = new FamiliarResponsable[listaFamiliarResponsables.size()];
        listaFamiliarResponsables.toArray(arrFamiliarResponsables);
        return arrFamiliarResponsables;
    }

    public void updateFamiliarResponsable(FamiliarResponsable familiarResponsable) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un familiarResponsable: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.FamiliarResponsable " +
                            "SET nombre = ?, telefono = ?, relacion = ? " +
                            "WHERE id = ?");
            prepStatement.setString(1, familiarResponsable.getNombre());
            prepStatement.setString(2, familiarResponsable.getTelefono());
            prepStatement.setString(3, familiarResponsable.getRelacion());
            prepStatement.setInt(4, familiarResponsable.getId());

            prepStatement.executeUpdate();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
    }
}
