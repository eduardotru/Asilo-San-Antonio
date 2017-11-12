package db;

import objetos.Enfermero;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnfermeroModel extends InterfazDB
{
    public void addEnfermero(Enfermero enfermero) throws Exception
    {
        try {
            int id = addEnfermero(enfermero.getNombre());
            enfermero.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addEnfermero(String nombre) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un enfermero: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Enfermero VALUES (default, ?)");
            prepStatement.setString(1, nombre);
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

    public Enfermero selectEnfermero(int id) throws Exception
    {
        Enfermero enfermero;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los enfermeros: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Enfermero WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            Enfermero[] enfermeros = crearListaEnfermeros(result);
            if(enfermeros.length == 1) {
                enfermero = enfermeros[0];
            }
            else {
                enfermero = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return enfermero;
    }

    public int selectIdEnfermero(String nombre) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los enfermeros: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT id FROM Asilo.Enfermero WHERE nombre = ?");
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

    public Enfermero[] selectEnfermeros() throws Exception
    {
        Enfermero[] enfermeros;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los enfermeros: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.Enfermero");
            enfermeros = crearListaEnfermeros(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return enfermeros;
    }

    private Enfermero[] crearListaEnfermeros(ResultSet result) throws SQLException
    {
        ArrayList<Enfermero> listaEnfermeros = new ArrayList<Enfermero>();
        while(result.next())
        {
            int id = result.getInt("id");
            String nombre = result.getString("nombre");
            listaEnfermeros.add(new Enfermero(id, nombre));
        }
        Enfermero[] arrEnfermeros = new Enfermero[listaEnfermeros.size()];
        listaEnfermeros.toArray(arrEnfermeros);
        return arrEnfermeros;
    }

    public void updateEnfermero(Enfermero enfermero) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un enfermero: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.Enfermero " +
                        "SET nombre = ? WHERE id = ?");
            prepStatement.setString(1, enfermero.getNombre());
            prepStatement.setInt(2, enfermero.getId());

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
