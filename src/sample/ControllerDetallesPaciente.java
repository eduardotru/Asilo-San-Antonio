package sample;


import db.PacienteModel;
import db.ServicioEmergenciaModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objetos.Paciente;
import objetos.ServicioEmergencia;

import javax.naming.Name;
import java.awt.*;
import java.io.IOException;


public class ControllerDetallesPaciente extends  ControllerBase{

    private ServicioEmergenciaModel servicioEmergenciaModel = new ServicioEmergenciaModel();
    private ServicioEmergencia servicioEmergencia;

    @FXML
    private Button btnHome;
    @FXML
    private Button btnPacientes;
    @FXML
    private TextField campoNombrePaciente;
    @FXML
    private TextField campoSexo;
    @FXML
    private TextField campoFechaNac;
    @FXML
    private TextField campoEstado;
    @FXML
    private TextField campoNombreSrvEmergencia;
    @FXML
    private TextField campoNumSrvEmergencia;
    @FXML
    private TextField campoTelSrvEmergencia;

    public void initData(Paciente paciente){
        //Rellena los campos con los datos del paciente
        campoNombrePaciente.setText(paciente.getNombre());
        campoFechaNac.setText(paciente.getFechaNacimiento().toString());
        campoSexo.setText(String.valueOf(paciente.getSexo()));
        campoEstado.setText(paciente.getEstado());

        //Obtiene el Servicio de emergencia y rellena los campos con los datos correspondientes
        int idSrvEmg = paciente.getIdServicioEmergencia();
        try {
            System.out.println("Buscando servicio con id: " + idSrvEmg);
            //Se obtiene el objeto servicio de emergencia
            servicioEmergencia = servicioEmergenciaModel.selectServicioEmergencia(idSrvEmg);

            campoNombreSrvEmergencia.setText(servicioEmergencia.getNombre()); //Se rellena el nombre en el campo
            campoNumSrvEmergencia.setText(paciente.getNumeroReferencia()); //Se rellena el no. Servicio en el campo
            campoTelSrvEmergencia.setText(servicioEmergencia.getTelefono());//Se rellena el telefono del sevicio en el campo
        }catch (Exception e) {
            System.out.println("Error al obtener el Servicio de emergecia con id: " + idSrvEmg);
        }

    }

    @FXML
    public void pushedBtnHome(Event event) throws IOException{
        cargaPantalla(event,"home.fxml",btnHome);
    }

    @FXML
    public void pushedBtnBuscaP(Event event) throws  IOException{
        cargaPantalla(event,"busquedapacientes.fxml",btnPacientes);
    }


}
