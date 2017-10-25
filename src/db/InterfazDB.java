package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import objetos.*;

public class InterfazDB {
    private static Connection c = null;
    private static String username = "root";
    private static String passwd = "admin";
    private static String url = "jdbc:mysql://localhost:3306/Asilo";

    private static void crearConexion() throws Exception {
        if(c == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                c = DriverManager.getConnection(url, username, passwd);
                System.out.println("Conexion con Base de Datos establecida.");
            }
            catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                throw e;
            }
        }
    }

    public void addPaciente(String nombre, java.sql.Date fechaNacimiento,
                                    char sexo, String estado, int idSeguro,
                                    int idServicioEmergencia)
            throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Paciente VALUES (default, ?, ?, ?, ?, ?, ?)");
            prepStatement.setString(1, nombre);
            prepStatement.setDate(2, fechaNacimiento);
            prepStatement.setString(3, Character.toString(sexo));
            prepStatement.setString(4, estado);
            prepStatement.setInt(5, idSeguro);
            prepStatement.setInt(6, idServicioEmergencia);

            prepStatement.executeUpdate();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
    }

    public void addEventualidad(String asunto, String descripcion,
                            char estaHospitalito, char avisoFamiliar, char requirioConsulta,
                            java.sql.Date fecha, int idPaciente, int idEnfermero)
            throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un evento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Evento VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            prepStatement.setString(1, asunto);
            prepStatement.setString(2, descripcion);
            prepStatement.setString(4, Character.toString(estaHospitalito));
            prepStatement.setString(5, Character.toString(avisoFamiliar));
            prepStatement.setString(6, Character.toString(requirioConsulta));
            prepStatement.setDate(7, fecha);
            prepStatement.setInt(8, idPaciente);
            prepStatement.setInt(9, idEnfermero);

            prepStatement.executeUpdate();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
    }

    public Paciente[] selectPacientes() throws Exception
    {
        Paciente[] pacientes;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement  statement = c.createStatement();
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
            listaPacientes.add(new Paciente(id, nombre, estado, sexo, fechaNacimiento));
        }
        Paciente[] arrPacientes = new Paciente[listaPacientes.size()];
        listaPacientes.toArray(arrPacientes);
        return arrPacientes;
    }

    public Evento[] selectEventos(Date desde, Date hasta) throws SQLException
    {
        Evento[] eventos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los eventos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Evento AS e WHERE e.fecha BETWEEN ? AND ?");
            prepStatement.setDate(1, new java.sql.Date(desde.getTime()));
            prepStatement.setDate(2, new java.sql.Date(hasta.getTime()));
            ResultSet result = prepStatement.executeQuery();
            eventos =  crearListaEventos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return eventos;
    }

    private Evento[] crearListaEventos(ResultSet result) throws SQLException
    {
        ArrayList<Evento> listaEventos = new ArrayList<Evento>();
        while(result.next())
        {
            int id = result.getInt("id");
            String asunto = result.getString("asunto");
            String descripcion = result.getString("descripcion");
            boolean estaHospitalito = result.getString("estaHospitalito").equals("S");
            boolean avisoFamiliar = result.getString("avisoFamiliar").equals("S");
            boolean requirioConsulta = result.getString("requirioConsulta").equals("S");
            Date fecha = result.getDate("fecha");
            int idPaciente = result.getInt("idPaciente");
            int idEnfermero = result.getInt("idEnfermero");
            listaEventos.add(new Evento(id, asunto, descripcion, estaHospitalito,
                    avisoFamiliar, requirioConsulta, fecha, idPaciente, idEnfermero));
        }
        Evento[] arrEvento = new Evento[listaEventos.size()];
        listaEventos.toArray(arrEvento);
        return arrEvento;
    }

    private static void cerrarConexion()
    {
        try {
            if (c != null) {
                c.close();
                System.out.println("Conexión con la Base de Datos cerrada");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexion.");
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World");
        InterfazDB intdb = new InterfazDB();
        try {
            intdb.addPaciente("Juan Perez", new java.sql.Date(1942, 12, 11), 'M', "Vivo", 1, 1);
        }
        catch (Exception e) {
            System.err.println("Prueba fallada." + e.getClass().getName() + ": " + e.getMessage());
        }
    }

}