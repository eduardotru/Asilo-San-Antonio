package db;

import objetos.Evento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class EventoModel extends InterfazDB {
    public void addEventualidad(Evento evento) throws Exception
    {
        try {
            char estaHospitalito = (evento.isEstaHospitalito() ? 'S' : 'N');
            char avisoFamiliar = (evento.isAvisoFamiliar() ? 'S' : 'N');
            char requirioConsulta = (evento.isRequirioConsulta() ? 'S' : 'N');
            java.sql.Date fecha = new java.sql.Date(evento.getFecha().getTime());
            int id = addEventualidad(evento.getAsunto(), evento.getDescripcion(),
                    estaHospitalito, avisoFamiliar, requirioConsulta, fecha,
                    evento.getIdPaciente(), evento.getIdEnfermero());
            evento.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addEventualidad(String asunto, String descripcion,
                               char estaHospitalito, char avisoFamiliar, char requirioConsulta,
                               java.sql.Date fecha, int idPaciente, int idEnfermero)
            throws Exception
    {
        int id = -1;
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

    public Evento selectEvento(int id) throws SQLException
    {
        Evento evento;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener un evento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Evento AS e WHERE e.id = ?");
            prepStatement.setInt(1, id);
            ResultSet result = prepStatement.executeQuery();
            Evento[] eventos =  crearListaEventos(result);
            if(eventos.length == 1) {
                evento = eventos[0];
            }
            else {
                evento = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return evento;
    }

    public Evento[] selectEventosPorPaciente(int idPaciente) throws SQLException
    {
        Evento[] eventos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los eventos de un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Evento AS e WHERE e.idPaciente = ?");
            prepStatement.setInt(1, idPaciente);
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

    public void updateEvento(Evento evento) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un paciente: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE asunto = ?, descripcion = ?, estaHospitalito = ?, " +
                            "avisoFamiliar = ?, requirioConsulta = ?, fecha = ?, " +
                            "idPaciente = ?, idEnfermero = ? " +
                            "FROM Asilo.Evento WHERE id = ?");
            char estaHospitalito = (evento.isEstaHospitalito() ? 'S' : 'N');
            char avisoFamiliar = (evento.isAvisoFamiliar() ? 'S' : 'N');
            char requirioConsulta = (evento.isRequirioConsulta() ? 'S' : 'N');
            java.sql.Date fecha = new java.sql.Date(evento.getFecha().getTime());
            prepStatement.setString(1, evento.getAsunto());
            prepStatement.setString(2, evento.getDescripcion());
            prepStatement.setString(3, Character.toString(estaHospitalito));
            prepStatement.setString(4, Character.toString(avisoFamiliar));
            prepStatement.setString(5, Character.toString(requirioConsulta));
            prepStatement.setDate(6, fecha);
            prepStatement.setInt(7, evento.getIdPaciente());
            prepStatement.setInt(8, evento.getIdEnfermero());
            prepStatement.setInt(9, evento.getId());

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
