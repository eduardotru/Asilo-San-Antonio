package db;

import objetos.Medicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedicamentoModel extends InterfazDB
{
    public void addMedicamento(Medicamento medicamento) throws Exception
    {
        try {
            int id = addMedicamento(medicamento.getDosis(), medicamento.getMedidaDosis(),
                    medicamento.getNombreGenerico());
            medicamento.setId(id);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int addMedicamento(int dosis, String medidaDosis, String nombreGenerico) throws Exception
    {
        int id = -1;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al agregar un medicamento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "INSERT INTO Asilo.Medicamento VALUES (default, ?, ?, ?)");
            prepStatement.setInt(1, dosis);
            prepStatement.setString(2, medidaDosis);
            prepStatement.setString(3, nombreGenerico);
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

    public Medicamento selectMedicamento(int id) throws Exception
    {
        Medicamento medicamento;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los medicamentos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement  prepeparedStatement = c.prepareStatement(
                    "SELECT * FROM Asilo.Medicamento WHERE id = ?");
            prepeparedStatement.setInt(1, id);
            ResultSet result = prepeparedStatement.executeQuery();
            Medicamento[] medicamentos = crearListaMedicamentos(result);
            if(medicamentos.length == 1) {
                medicamento = medicamentos[0];
            }
            else {
                medicamento = null;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return medicamento;
    }

    public Medicamento[] selectMedicamentos() throws Exception
    {
        Medicamento[] medicamentos;
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al obtener los medicamentos: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Asilo.Medicamento");
            medicamentos = crearListaMedicamentos(result);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            cerrarConexion();
        }
        return medicamentos;
    }

    private Medicamento[] crearListaMedicamentos(ResultSet result) throws SQLException
    {
        ArrayList<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
        while(result.next())
        {
            int id = result.getInt("id");
            int dosis = result.getInt("dosis");
            String medidaDosis = result.getString("medidaDosis");
            String nombreGenerico = result.getString("nombreGenerico");
            listaMedicamentos.add(new Medicamento(id, dosis, medidaDosis, nombreGenerico));
        }
        Medicamento[] arrMedicamentos = new Medicamento[listaMedicamentos.size()];
        listaMedicamentos.toArray(arrMedicamentos);
        return arrMedicamentos;
    }

    public void updateMedicamento(Medicamento medicamento) throws Exception
    {
        try {
            InterfazDB.crearConexion();
        } catch (Exception e){
            System.err.println("Error al modificar un medicamento: "
                    + e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            PreparedStatement prepStatement = c.prepareStatement(
                    "UPDATE Asilo.Medicamento " +
                        "SET dosis = ?, medidaDosis = ?, nombreGenerico = ? " +
                        "WHERE id = ?");
            prepStatement.setInt(1, medicamento.getDosis());
            prepStatement.setString(2, medicamento.getMedidaDosis());
            prepStatement.setString(3, medicamento.getNombreGenerico());
            prepStatement.setInt(4, medicamento.getId());

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
