package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class InterfazDB {
    protected static Connection c = null;
    private static String username = "root";
    private static String passwd = "admin";
    private static String url = "jdbc:mysql://localhost:3306/Asilo";


    // ------------------------------------------------------------------------
    // Crear y Cerrar conexión
    protected static void crearConexion() throws Exception {
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

    protected static void cerrarConexion()
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

}