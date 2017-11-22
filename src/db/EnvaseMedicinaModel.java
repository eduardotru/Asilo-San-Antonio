package db;

import objetos.EnvaseMedicina;
import objetos.PacienteMedicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;

public class EnvaseMedicinaModel extends InterfazDB
{
    public void addEnvaseMedicina(EnvaseMedicina envaseMedicina) throws Exception
    {
        try {
            int id = addEnvaseMedicina(envaseMedicina.getNombreComercial(),
                    envaseMedicina.getFechaSurtimiento(), envaseMedicina.getPresentacion(),
                    envaseMedicina.getCantidad(), envaseMedicina.getIdMedicamento(),
                    envaseMedicina.getIdPaciente());
            envaseMedicina.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addEnvaseMedicina(String nombreComercial, java.util.Date fechaSurtimiento,
                                 String presentacion, int cantidad, int idMedicamento,
                                 int idPaciente) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un envaseMedicina: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.EnvaseMedicina(id, nombreComercial, fechaSurtimiento, " +
                            "presentacion, cantidad, idMedicamento, idPaciente) " +
                            "VALUES (default, ?, ?, ?, ?, ?, ?)");
            prepStatement.setString(1, nombreComercial);
            java.sql.Date fecha = new java.sql.Date(fechaSurtimiento.getTime());
            prepStatement.setDate(2, fecha);
            prepStatement.setString(3, presentacion);
            prepStatement.setInt(4, cantidad);
            prepStatement.setInt(5, idMedicamento);
            prepStatement.setInt(6, idPaciente);
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

    public EnvaseMedicina selectEnvaseMedicina(int id) throws Exception
    {
        EnvaseMedicina envaseMedicina;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los envaseMedicinas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.EnvaseMedicina WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            EnvaseMedicina[] envaseMedicinas = crearListaEnvaseMedicinas(result);
            if(envaseMedicinas.length == 1) {
                envaseMedicina = envaseMedicinas[0];
            }
            else {
                envaseMedicina = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return envaseMedicina;
    }

    public int selectEnvaseMedicinaPorPaciente(int idPaciente) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los envaseMedicinas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT id FROM Asilo.EnvaseMedicina WHERE idPaciente = ?");
            preparedStatement.setInt(1, idPaciente);

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

    public EnvaseMedicina selectEnvaseMedicina(int idPaciente, int idMedicamento) throws Exception
    {
        EnvaseMedicina envaseMedicina;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los envaseMedicinas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.EnvaseMedicina WHERE idPaciente = ? AND idMedicamento = ?");
            prepeparedStatement.setInt(1, idPaciente);
            prepeparedStatement.setInt(2, idMedicamento);
            ResultSet result = prepeparedStatement.executeQuery();
            EnvaseMedicina[] envaseMedicinas = crearListaEnvaseMedicinas(result);
            if(envaseMedicinas.length == 1) {
                envaseMedicina = envaseMedicinas[0];
            }
            else {
                envaseMedicina = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return envaseMedicina;
    }

    public EnvaseMedicina[] selectEnvaseMedicinas() throws Exception
    {
        EnvaseMedicina[] envaseMedicinas;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los envaseMedicinas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.EnvaseMedicina");
            envaseMedicinas = crearListaEnvaseMedicinas(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return envaseMedicinas;
    }

    public EnvaseMedicina[] selectEnvaseMedicinasPorPaciente(int idPaciente) throws Exception
    {
        EnvaseMedicina[] envaseMedicinas;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los envaseMedicinas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();

            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.EnvaseMedicina as e WHERE e.idPaciente = ? ");
            preparedStatement.setInt(1, idPaciente);
            ResultSet result = preparedStatement.executeQuery();
            envaseMedicinas = crearListaEnvaseMedicinas(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return envaseMedicinas;
    }

    public EnvaseMedicina[] selectEnvaseMedicinasPorPacienteMedicamento(int idPaciente, int idMedicina) throws Exception
    {
        EnvaseMedicina[] envaseMedicinas;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los envaseMedicinas: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();

            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.EnvaseMedicina as e WHERE e.idPaciente = ?  AND  e.idMedicamento = ?");
            preparedStatement.setInt(1, idPaciente);
            preparedStatement.setInt(2,idMedicina);
            ResultSet result = preparedStatement.executeQuery();
            envaseMedicinas = crearListaEnvaseMedicinas(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return envaseMedicinas;
    }

    public EnvaseMedicina[] selectEnvasesPorTerminarEntre(Date fechaInicio, Date fechaFin) throws SQLException
    {
        EnvaseMedicina[] envaseMedicinas;
        try {
           envaseMedicinas = selectEnvaseMedicinas();
        }
        catch (Exception e) {
            System.out.println("Error al obtener los envases de medicina por vencer");
            return null;
        }
        ArrayList<EnvaseMedicina> listaEnvases = new ArrayList<EnvaseMedicina>();
        for (EnvaseMedicina envase: envaseMedicinas) {
            if(envaseVenceEntre(envase, fechaInicio, fechaFin)) {
                listaEnvases.add(envase);
            }
        }
        envaseMedicinas = new EnvaseMedicina[listaEnvases.size()];
        listaEnvases.toArray(envaseMedicinas);
        return envaseMedicinas;
    }

    public EnvaseMedicina[] selectEnvasesPorTerminarEntreParaPaciente(Date fechaInicio, Date fechaFin,
                                                                      int idPaciente) throws SQLException
    {
        EnvaseMedicina[] envaseMedicinas;
        try {
            envaseMedicinas = selectEnvaseMedicinasPorPaciente(idPaciente);
        }
        catch (Exception e) {
            System.out.println("Error al obtener los envases de medicina por vencer");
            return null;
        }
        ArrayList<EnvaseMedicina> listaEnvases = new ArrayList<EnvaseMedicina>();
        for (EnvaseMedicina envase: envaseMedicinas) {
            if(envaseVenceEntre(envase, fechaInicio, fechaFin)) {
                listaEnvases.add(envase);
            }
        }
        envaseMedicinas = new EnvaseMedicina[listaEnvases.size()];
        listaEnvases.toArray(envaseMedicinas);
        return envaseMedicinas;
    }

    private boolean envaseVenceEntre(EnvaseMedicina envase, Date fechaInicio, Date fechaFin) {
        PacienteMedicamentoModel pacienteMedicamentoModel = new PacienteMedicamentoModel();
        PacienteMedicamento pacienteMedicamento;
        try {
            pacienteMedicamento = pacienteMedicamentoModel.selectPacienteMedicamento(
                    envase.getIdPaciente(), envase.getIdMedicamento());
        }
        catch (Exception e) {
            System.out.println("Error al obtener un pacienteMedicamento");
            return false;
        }
        int dosisDiarias = 0;
        if(pacienteMedicamento.isTomaManana()) {
            dosisDiarias++;
        }
        if(pacienteMedicamento.isTomaMedio()) {
            dosisDiarias++;
        }
        if(pacienteMedicamento.isTomaTarde()) {
            dosisDiarias++;
        }
        int diasDisponibles = envase.getCantidad()/(pacienteMedicamento.getDosis()*dosisDiarias);
        if(diasDisponibles >= pacienteMedicamento.getDuracion()) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(envase.getFechaSurtimiento());
        calendar.add(Calendar.DAY_OF_MONTH, diasDisponibles);
        if(calendar.after(fechaInicio) && calendar.before(fechaFin)) {
            int diferenciaDias = calendar.get(Calendar.DATE) - fechaInicio.getDate();
            envase.setDiasDisponibles(diferenciaDias);
            int dosisDisponibles = envase.getCantidad() - dosisDiarias*(diasDisponibles-diferenciaDias);
            envase.setDosisDisponibles(dosisDisponibles);
            return true;
        }
        else {
            return false;
        }
    }

    private EnvaseMedicina[] crearListaEnvaseMedicinas(ResultSet result) throws SQLException
    {
        ArrayList<EnvaseMedicina> listaEnvaseMedicinas = new ArrayList<EnvaseMedicina>();
        while(result.next())
        {
            int id = result.getInt("id");
            String nombreComercial = result.getString("nombreComercial");
            java.util.Date fechaSurtimiento = result.getDate("fechaSurtimiento");
            String presentacion = result.getString("presentacion");
            int cantidad = result.getInt("cantidad");
            int idMedicamento = result.getInt("idMedicamento");
            int idPaciente = result.getInt("idPaciente");
            listaEnvaseMedicinas.add(new EnvaseMedicina(id, nombreComercial, fechaSurtimiento,
                    presentacion, cantidad, idMedicamento, idPaciente));
        }
        EnvaseMedicina[] arrEnvaseMedicinas = new EnvaseMedicina[listaEnvaseMedicinas.size()];
        listaEnvaseMedicinas.toArray(arrEnvaseMedicinas);
        return arrEnvaseMedicinas;
    }

    public void updateEnvaseMedicina(EnvaseMedicina envaseMedicina) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un envaseMedicina: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.EnvaseMedicina " +
                            "SET nombreComercial = ?, fechaSurtimiento = ?, " +
                            "presentacion = ?, cantidad = ?, " +
                            "idMedicamento = ?, idPaciente = ? " +
                            "WHERE id = ?");
            prepStatement.setString(1, envaseMedicina.getNombreComercial());
            java.sql.Date fecha = new java.sql.Date(envaseMedicina.getFechaSurtimiento().getTime());
            prepStatement.setDate(2, fecha);
            prepStatement.setString(3, envaseMedicina.getPresentacion());
            prepStatement.setInt(4, envaseMedicina.getCantidad());
            prepStatement.setInt(5, envaseMedicina.getIdMedicamento());
            prepStatement.setInt(6, envaseMedicina.getIdPaciente());
            prepStatement.setInt(7, envaseMedicina.getId());

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
