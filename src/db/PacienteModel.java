package db;

import objetos.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class PacienteModel extends InterfazDB
{
    public void addPaciente(Paciente paciente) throws Exception
    {
        try {
            java.sql.Date fechaNacimiento = new java.sql.Date(
                    paciente.getFechaNacimiento().getTime());
            int id = addPaciente(paciente.getNombre(), fechaNacimiento,
                    paciente.getSexo(), paciente.getEstado(), paciente.getNumCuarto(),
                    paciente.getNumCama(), paciente.getIdSeguro(),
                    paciente.getIdServicioEmergencia(), paciente.getNumeroReferencia());
            paciente.setId(id);
        }
        catch(Exception e) {
            throw e;
        }
    }

    public int addPaciente(String nombre, java.sql.Date fechaNacimiento,
                           char sexo, String estado, int numCuarto, int numCama,
                           int idSeguro, int idServicioEmergencia, String numeroReferencia)
            throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Paciente(id, nombre, fechaNacimiento, " +
                            "sexo, estado, numCuarto, numCama, idSeguro, " +
                            "idServicioEmergencia, numeroReferencia)" +
                            "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1, nombre);
            prepStatement.setDate(2, fechaNacimiento);
            prepStatement.setString(3, Character.toString(sexo));
            prepStatement.setString(4, estado);
            prepStatement.setInt(5, numCuarto);
            prepStatement.setInt(6, numCama);
            prepStatement.setInt(7, idSeguro);
            prepStatement.setInt(8, idServicioEmergencia);
            prepStatement.setString(9, numeroReferencia);

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

    public Paciente selectPaciente(int id) throws Exception
    {
        Paciente paciente;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Paciente WHERE id = ?");
            prepStatement.setInt(1, id);
            ResultSet result = prepStatement.executeQuery();
            Paciente[] pacientes = crearListaPacientes(result);
            if(pacientes.length == 1) {
                paciente = pacientes[0];
            }
            else {
                paciente = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return paciente;
    }

    public int selectIdPaciente(String nombre) throws Exception
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
                    "SELECT id FROM Asilo.Paciente WHERE nombre = ?");
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

    public Paciente[] selectPacientes() throws Exception
    {
        Paciente[] pacientes;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los pacientes: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.Paciente");
            pacientes = crearListaPacientes(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return pacientes;
    }

    public Paciente[] buscarPacientesPorNombre(String nombre) throws SQLException
    {
        Paciente[] arrPaciente;
        nombre = "%"+nombre+"%";
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los enfermeros: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Paciente WHERE nombre LIKE ?");
            preparedStatement.setString(1, nombre);

            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            arrPaciente = crearListaPacientes(resultSet);

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return arrPaciente;
    }

    private Paciente[] crearListaPacientes(ResultSet result) throws SQLException
    {
        ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
        while(result.next())
        {
            int id = result.getInt("id");
            String nombre = result.getString("nombre");
            char sexo = result.getString("sexo").charAt(0);
            String estado = result.getString("estado");
            Date fechaNacimiento = result.getDate("fechaNacimiento");
            int numCuarto = result.getInt("numCuarto");
            int numCama = result.getInt("numCama");
            int idSeguro = result.getInt("idSeguro");
            int idServicioEmergencia = result.getInt("idServicioEmergencia");
            String numeroReferencia = result.getString("numeroReferencia");
            listaPacientes.add(new Paciente(id, nombre, estado, sexo,
                    fechaNacimiento, numCuarto, numCama, idSeguro,
                    idServicioEmergencia, numeroReferencia));
        }
        Paciente[] arrPacientes = new Paciente[listaPacientes.size()];
        listaPacientes.toArray(arrPacientes);
        return arrPacientes;
    }

    public void updatePaciente(Paciente paciente) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.Paciente " +
                            "SET nombre = ?, fechaNacimiento = ?, " +
                            "sexo = ?, estado = ?, numCama = ?, numCuarto = ?," +
                            "idSeguro = ?, idServicioEmergencia = ?, numeroReferencia = ?" +
                            "WHERE id = ?");
            java.sql.Date fechaNac = new java.sql.Date(paciente.getFechaNacimiento().getTime());
            prepStatement.setString(1, paciente.getNombre());
            prepStatement.setDate(2, fechaNac);
            prepStatement.setString(3, Character.toString(paciente.getSexo()));
            prepStatement.setString(4, paciente.getEstado());
            prepStatement.setInt(5, paciente.getNumCama());
            prepStatement.setInt(6, paciente.getNumCuarto());
            prepStatement.setInt(7, paciente.getIdSeguro());
            prepStatement.setInt(8, paciente.getIdServicioEmergencia());
            prepStatement.setString(9, paciente.getNumeroReferencia());
            prepStatement.setInt(10, paciente.getId());

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
