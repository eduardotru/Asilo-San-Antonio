package db;

import objetos.Cuarto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CuartoModel extends InterfazDB
{
    public void addCuarto(Cuarto cuarto) throws Exception
    {
        try {
            int id = addCuarto(cuarto.getNumCuarto());
            cuarto.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addCuarto(int numCuarto) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un cuarto: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Cuarto VALUES (default, ?)");
            prepStatement.setInt(1, numCuarto);
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

    public Cuarto selectCuarto(int id) throws Exception
    {
        Cuarto cuarto;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener las cuartos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Cuarto WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            Cuarto[] cuartos = crearListaCuartos(result);
            if(cuartos.length == 1) {
                cuarto = cuartos[0];
            }
            else {
                cuarto = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return cuarto;
    }

    public Cuarto[] selectCuartos() throws Exception
    {
        Cuarto[] cuartos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los cuartos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.Cuarto");
            cuartos = crearListaCuartos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return cuartos;
    }

    private Cuarto[] crearListaCuartos(ResultSet result) throws SQLException
    {
        ArrayList<Cuarto> listaCuartos = new ArrayList<Cuarto>();
        while(result.next())
        {
            int id = result.getInt("id");
            int numCuarto = result.getInt("numCuarto");
            listaCuartos.add(new Cuarto(id, numCuarto));
        }
        Cuarto[] arrCuartos = new Cuarto[listaCuartos.size()];
        listaCuartos.toArray(arrCuartos);
        return arrCuartos;
    }

    public void updateCuarto(Cuarto cuarto) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar una cuarto: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE numCuarto = ? FROM Asilo.Cuarto WHERE id = ?");
            prepStatement.setInt(1, cuarto.getNumCuarto());
            prepStatement.setInt(2, cuarto.getId());

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
