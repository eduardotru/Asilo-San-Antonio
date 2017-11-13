package db;

import objetos.Padecimiento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PadecimientoModel extends InterfazDB
{
    public void addPadecimiento(Padecimiento padecimiento) throws Exception
    {
        try {
            int id = addPadecimiento(padecimiento.getIdPaciente(), padecimiento.getPadecimiento());
            padecimiento.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addPadecimiento(int idPaciente, String padecimiento) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un padecimiento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Padecimiento VALUES (default, ?, ?)");
            prepStatement.setInt(1, idPaciente);
            prepStatement.setString(2, padecimiento);
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

    public Padecimiento selectPadecimiento(int id) throws Exception
    {
        Padecimiento padecimiento;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los padecimientos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Padecimiento WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            Padecimiento[] padecimientos = crearListaPadecimientos(result);
            if(padecimientos.length == 1) {
                padecimiento = padecimientos[0];
            }
            else {
                padecimiento = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return padecimiento;
    }

    public Padecimiento[] selectPadecimientosPorPaciente(int idPaciente) throws Exception
    {
        Padecimiento[] padecimientos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los padecimientos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Padecimiento WHERE idPaciente = ?");
            prepStatement.setInt(1, idPaciente);
            prepStatement.executeQuery();

            ResultSet result = prepStatement.getResultSet();
            padecimientos = crearListaPadecimientos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return padecimientos;
    }

    public Padecimiento[] selectPadecimientos() throws Exception
    {
        Padecimiento[] padecimientos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los padecimientos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.Padecimiento");
            padecimientos = crearListaPadecimientos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return padecimientos;
    }

    private Padecimiento[] crearListaPadecimientos(ResultSet result) throws SQLException
    {
        ArrayList<Padecimiento> listaPadecimientos = new ArrayList<Padecimiento>();
        while(result.next())
        {
            int id = result.getInt("id");
            int idPaciente = result.getInt("idPaciente");
            String padecimiento = result.getString("padecimiento");
            listaPadecimientos.add(new Padecimiento(id, idPaciente, padecimiento));
        }
        Padecimiento[] arrPadecimientos = new Padecimiento[listaPadecimientos.size()];
        listaPadecimientos.toArray(arrPadecimientos);
        return arrPadecimientos;
    }

    public void updatePadecimiento(Padecimiento padecimiento) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un padecimiento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.Padecimiento " +
                            "SET padecimiento = ? WHERE id = ?");
            prepStatement.setString(1, padecimiento.getPadecimiento());
            prepStatement.setInt(2, padecimiento.getId());

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
