package db;

import objetos.PacienteMedicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PacienteMedicamentoModel extends InterfazDB {
    public void addPacienteMedicamento(PacienteMedicamento pacienteMedicamento) throws Exception
    {
        try {
            addPacienteMedicamento(pacienteMedicamento.getIdPaciente(), pacienteMedicamento.getIdMedicamento(),
                    pacienteMedicamento.isTomaManana(), pacienteMedicamento.isTomaMedio(),
                    pacienteMedicamento.isTomaTarde(), pacienteMedicamento.getFechaInicio(),
                    pacienteMedicamento.getDuracion());
        }
        catch (Exception e) {
            throw e;
        }
    }

    private String booleanToStr(boolean b) {
        return b ? "S" : "N";
    }

    private boolean strToBoolean(String s) {
        return s.equals("S");
    }

    public void addPacienteMedicamento(int idPaciente, int idMedicamento, boolean tomaManana,
                                       boolean tomaMedio, boolean tomaTarde, java.util.Date fechaInicio,
                                       int duracion) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un medicamento para un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.PacienteMedicamento VALUES (?, ?, ?, ?, ?, ?, ?)");
            prepStatement.setInt(1, idPaciente);
            prepStatement.setInt(2, idMedicamento);
            prepStatement.setString(3, booleanToStr(tomaManana));
            prepStatement.setString(4, booleanToStr(tomaMedio));
            prepStatement.setString(5, booleanToStr(tomaTarde));
            java.sql.Date fecha = new java.sql.Date(fechaInicio.getTime());
            prepStatement.setDate(6, fecha);
            prepStatement.setInt(7, duracion);
            prepStatement.executeUpdate();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
    }

    public PacienteMedicamento selectPacienteMedicamento(int idPaciente,
                                                         int idMedicamento) throws Exception
    {
        PacienteMedicamento pacienteMedicamento;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los medicamentos de un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.PacienteMedicamento WHERE idPaciente = ?");
            prepeparedStatement.setInt(1, idPaciente);
            ResultSet result = prepeparedStatement.executeQuery();
            PacienteMedicamento[] pacienteMedicamentos = crearListaPacienteMedicamentos(result);
            if(pacienteMedicamentos.length == 1) {
                pacienteMedicamento = pacienteMedicamentos[0];
            }
            else {
                pacienteMedicamento = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return pacienteMedicamento;
    }

    public PacienteMedicamento[] selectPacienteMedicamento(int idPaciente) throws Exception
    {
        PacienteMedicamento[] pacienteMedicamentos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los medicamentos de un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.PacienteMedicamento WHERE idPaciente = ?");
            prepeparedStatement.setInt(1, idPaciente);
            ResultSet result = prepeparedStatement.executeQuery();
            pacienteMedicamentos = crearListaPacienteMedicamentos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return pacienteMedicamentos;
    }

    public PacienteMedicamento[] selectPacienteMedicamentos() throws Exception
    {
        PacienteMedicamento[] pacienteMedicamentos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los pacienteMedicamentos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.PacienteMedicamento");
            pacienteMedicamentos = crearListaPacienteMedicamentos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return pacienteMedicamentos;
    }

    private PacienteMedicamento[] crearListaPacienteMedicamentos(ResultSet result) throws SQLException
    {
        ArrayList<PacienteMedicamento> listaPacienteMedicamentos = new ArrayList<PacienteMedicamento>();
        while(result.next())
        {
            int idPaciente = result.getInt("idPaciente");
            int idMedicamento = result.getInt("idMedicamento");
            boolean tomaManana = strToBoolean(result.getString("tomaManana"));
            boolean tomaMedio = strToBoolean(result.getString("tomaMedio"));
            boolean tomaTarde = strToBoolean(result.getString("tomaTarde"));
            java.util.Date fechaInicio = result.getDate("fechaInicio");
            int duracion = result.getInt("duracion");
            listaPacienteMedicamentos.add(new PacienteMedicamento(idPaciente, idMedicamento, tomaManana,
                    tomaMedio, tomaTarde, fechaInicio, duracion));
        }
        PacienteMedicamento[] arrPacienteMedicamentos = new PacienteMedicamento[listaPacienteMedicamentos.size()];
        listaPacienteMedicamentos.toArray(arrPacienteMedicamentos);
        return arrPacienteMedicamentos;
    }

    public void updatePacienteMedicamento(PacienteMedicamento pacienteMedicamento) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un pacienteMedicamento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.PacienteMedicamento " +
                            "SET tomaManana = ?, tomaMedio = ?, tomaTarde = ?, fechaInicio = ?, " +
                            "duracion = ? " +
                            "WHERE idPaciente = ? AND idMedicamento = ?");
            prepStatement.setString(1, booleanToStr(pacienteMedicamento.isTomaManana()));
            prepStatement.setString(2, booleanToStr(pacienteMedicamento.isTomaMedio()));
            prepStatement.setString(3, booleanToStr(pacienteMedicamento.isTomaTarde()));
            java.sql.Date fecha = new java.sql.Date(pacienteMedicamento.getFechaInicio().getTime());
            prepStatement.setDate(4, fecha);
            prepStatement.setInt(5, pacienteMedicamento.getDuracion());
            prepStatement.setInt(6, pacienteMedicamento.getIdPaciente());
            prepStatement.setInt(7, pacienteMedicamento.getIdMedicamento());

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
