package db;

import objetos.Cama;
import objetos.Cama;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CamaModel extends InterfazDB
{
    public void addCama(Cama cama) throws Exception
    {
        try {
            int id = addCama(cama.getNumCama(), cama.getIdCuarto(), cama.getIdPaciente());
            cama.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addCama(int numCama, int idCuarto, int idPaciente) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un cama: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Cama VALUES (default, ?, ?, ?)");
            prepStatement.setInt(1, numCama);
            prepStatement.setInt(2, idPaciente);
            prepStatement.setInt(3, idCuarto);
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

    public Cama selectCama(int id) throws Exception
    {
        Cama cama;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener las camas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Cama WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            Cama[] camas = crearListaCamas(result);
            if(camas.length == 1) {
                cama = camas[0];
            }
            else {
                cama = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return cama;
    }

    public Cama[] selectCamas() throws Exception
    {
        Cama[] camas;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los camas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.Cama");
            camas = crearListaCamas(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return camas;
    }

    private Cama[] crearListaCamas(ResultSet result) throws SQLException
    {
        ArrayList<Cama> listaCamas = new ArrayList<Cama>();
        while(result.next())
        {
            int id = result.getInt("id");
            int numCama = result.getInt("numCama");
            int idPaciente = result.getInt("idPaciente");
            int idCuarto = result.getInt("idCuarto");
            listaCamas.add(new Cama(id, numCama, idCuarto, idPaciente));
        }
        Cama[] arrCamas = new Cama[listaCamas.size()];
        listaCamas.toArray(arrCamas);
        return arrCamas;
    }

    public void updateCama(Cama cama) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar una cama: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE numCama = ?, idPaciente = ?, idCuarto = ? FROM Asilo.Cama WHERE id = ?");
            prepStatement.setInt(1, cama.getNumCama());
            prepStatement.setInt(2, cama.getIdPaciente());
            prepStatement.setInt(3, cama.getIdCuarto());
            prepStatement.setInt(4, cama.getId());

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
