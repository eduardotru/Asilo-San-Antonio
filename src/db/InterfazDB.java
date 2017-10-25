package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class InterfazDB {
    private static Connection c = null;
    private static String username = "root";
    private static String passwd = "admin";
    private static String url = "jdbc:mysql://localhost:3306/asilo";

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

    private static void cerrarConexion() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexion.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        InterfazDB intdb = new InterfazDB();
        try {
            intdb.addPaciente("Juan Perez", new java.sql.Date(1942, 12, 11), 'M', "Vivo", 0, 0);
        }
        catch (Exception e) {
            System.err.println("Prueba fallada.");
        }
    }

}