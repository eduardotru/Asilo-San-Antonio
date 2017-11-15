package db;

import objetos.ServicioEmergencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServicioEmergenciaModel extends InterfazDB 
{
    public void addServicioEmergencia(ServicioEmergencia servicioEmergencia) throws Exception
    {
        try {
            int id = addServicioEmergencia(servicioEmergencia.getNombre(), servicioEmergencia.getTelefono(),
                    servicioEmergencia.getDireccion());
            servicioEmergencia.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addServicioEmergencia(String nombre, String telefono, String direccion) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un servicioEmergencia: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.ServicioEmergencia(id, nombre, telefono, direccion) " +
                            "VALUES (default, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1, nombre);
            prepStatement.setString(2, telefono);
            prepStatement.setString(3, direccion);
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

    public ServicioEmergencia selectServicioEmergencia(int id) throws Exception
    {
        ServicioEmergencia servicioEmergencia;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los servicioEmergencias: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.ServicioEmergencia WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            ServicioEmergencia[] servicioEmergencias = crearListaServicioEmergencias(result);
            if(servicioEmergencias.length == 1) {
                servicioEmergencia = servicioEmergencias[0];
            }
            else {
                servicioEmergencia = null;
            }
        }
        catch (Exception e) {
            System.out.println("No se encontro elemento con id: " + id);
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return servicioEmergencia;
    }

    public int selectIdServicioEmergencia(String nombre) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener el servicioEmergencia: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT id FROM Asilo.ServicioEmergencia WHERE nombre = ?");
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

    public ServicioEmergencia[] selectServicioEmergencias() throws Exception
    {
        ServicioEmergencia[] servicioEmergencias;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los servicioEmergencias: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.ServicioEmergencia");
            servicioEmergencias = crearListaServicioEmergencias(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return servicioEmergencias;
    }

    private ServicioEmergencia[] crearListaServicioEmergencias(ResultSet result) throws SQLException
    {
        ArrayList<ServicioEmergencia> listaServicioEmergencias = new ArrayList<ServicioEmergencia>();
        while(result.next())
        {
            int id = result.getInt("id");
            String nombre = result.getString("nombre");
            String telefono = result.getString("telefono");
            String direccion = result.getString("direccion");
            listaServicioEmergencias.add(new ServicioEmergencia(id, nombre, telefono, direccion));
        }
        ServicioEmergencia[] arrServicioEmergencias = new ServicioEmergencia[listaServicioEmergencias.size()];
        listaServicioEmergencias.toArray(arrServicioEmergencias);
        return arrServicioEmergencias;
    }

    public void updateServicioEmergencia(ServicioEmergencia servicioEmergencia) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un servicioEmergencia: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.ServicioEmergencia " +
                            "SET nombre = ?, telefono = ?, direccion = ? " +
                            "WHERE id = ?");
            prepStatement.setString(1, servicioEmergencia.getNombre());
            prepStatement.setString(2, servicioEmergencia.getTelefono());
            prepStatement.setString(3, servicioEmergencia.getDireccion());
            prepStatement.setInt(4, servicioEmergencia.getId());

            prepStatement.executeUpdate();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
    }

    public int getLastServicioAddedId() throws SQLException {
        int servicioId = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e) {
            System.err.println("Error al obtener un evento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "SELECT id FROM Asilo.servicioemergencia ORDER BY id DESC");
            ResultSet result = prepStatement.executeQuery();
            if (result.next()) {
                servicioId = result.getInt("id");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            cerrarConexion();
        }

        return servicioId;
    }

    public void deleteServicioEmergencia(int id) throws SQLException
    {
        try {
            InterfazDB.crearConexion();
        }
        catch(Exception e) {
            System.err.println("Error al borrar un Servicio de Emergencia: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(
                    "DELETE FROM Asilo.ServicioEmergencia WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.err.println("Error al eliminar el Servicio de emergencia: " + id);
            throw e;
        }
        finally {
            cerrarConexion();
        }
    }
}
