package db;

import objetos.Seguro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeguroModel extends InterfazDB
{
    public void addSeguro(Seguro seguro) throws Exception
    {
        try {
            int id = addSeguro(seguro.getNombre(), seguro.getNumPoliza());
            seguro.setId(id);
        }
        catch(Exception e) {
            throw e;
        }
    }

    public int addSeguro(String nombre, String numPoliza) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un seguro: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Seguro(id, nombre, numPoliza) VALUES (default, ?, ?)");
            prepStatement.setString(1, nombre);
            prepStatement.setString(2, numPoliza);
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

    public Seguro selectSeguro(int id) throws Exception
    {
        Seguro seguro;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener un seguro: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Seguro WHERE id = ?");
            prepStatement.setInt(1, id);
            ResultSet result = prepStatement.executeQuery();
            Seguro[] seguros = crearListaSeguros(result);
            if(seguros.length == 1) {
                seguro = seguros[0];
            }
            else {
                seguro = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return seguro;
    }

    private Seguro[] crearListaSeguros(ResultSet result) throws SQLException
    {
        ArrayList<Seguro> listaSeguros = new ArrayList<Seguro>();
        while(result.next())
        {
            int id = result.getInt("id");
            String nombre = result.getString("nombre");
            String numPoliza = result.getString("numPoliza");
            listaSeguros.add(new Seguro(id, nombre, numPoliza));
        }
        Seguro[] arrSeguros = new Seguro[listaSeguros.size()];
        listaSeguros.toArray(arrSeguros);
        return arrSeguros;
    }
}
