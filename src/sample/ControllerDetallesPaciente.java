package sample;


import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.naming.Name;
import java.awt.*;
import java.io.IOException;
import java.security.spec.ECField;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class ControllerDetallesPaciente extends  ControllerBase{

    //Instancias de los modelos para poder trabajar con la base de datos
    private ServicioEmergenciaModel servicioEmergenciaModel = new ServicioEmergenciaModel();
    private FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
    private PacienteModel pacienteModel = new PacienteModel();
    private SeguroModel seguroModel = new SeguroModel();
    private PadecimientoModel padecimientoModel = new PadecimientoModel();

    private ServicioEmergencia servicioEmergencia;

    private Seguro seguro;
    private int idPaciente;


    @FXML
    private Button btnHome;
    @FXML
    private Button btnPacientes;
    @FXML
    private TextField campoNombrePaciente;
    @FXML
    private TextField campoSexo;
    @FXML
    private DatePicker campoFechaNac;
    @FXML
    private TextField campoEstado;
    @FXML
    private TextField campoNombreSrvEmergencia;
    @FXML
    private TextField campoNumSrvEmergencia;
    @FXML
    private TextField campoTelSrvEmergencia;
    @FXML
    private TextField campoNombreSeguro;
    @FXML
    private TextField campoNumPoliza;
    @FXML
    private TextField campoNombreFamiliar;
    @FXML
    private TextField campoParentesco;
    @FXML
    private TextField campoTelefonoPariente;
    @FXML
    private ListView<Pane> listaTabla;
    @FXML
    private ListView<String> listaPadecimientos;

    public void initData(Paciente paciente) {
        //Rellena los campos con los datos del paciente
        campoNombrePaciente.setText(paciente.getNombre());
        campoFechaNac.setValue(Instant.ofEpochMilli(paciente.getFechaNacimiento().getTime()).atZone((ZoneId.systemDefault())).toLocalDate());
        campoSexo.setText(String.valueOf(paciente.getSexo()));
        campoEstado.setText(paciente.getEstado());
        idPaciente = paciente.getId();

        //Obtiene el Servicio de emergencia y rellena los campos con los datos correspondientes
        int idSrvEmg = paciente.getIdServicioEmergencia();
        try {
            System.out.println("Buscando servicio con id: " + idSrvEmg);
            //Se obtiene el objeto servicio de emergencia
            servicioEmergencia = servicioEmergenciaModel.selectServicioEmergencia(idSrvEmg);

            campoNombreSrvEmergencia.setText(servicioEmergencia.getNombre()); //Se rellena el nombre en el campo
            campoNumSrvEmergencia.setText(paciente.getNumeroReferencia()); //Se rellena el no. Servicio en el campo
            campoTelSrvEmergencia.setText(servicioEmergencia.getTelefono());//Se rellena el telefono del sevicio en el campo
        } catch (Exception e) {
            System.out.println("Error al obtener el Servicio de emergecia con id: " + idSrvEmg);
        }

        //Obtiene el Objeto Seguro de gastos médicos del paciente y vacia los datos en los campos correspondientes
        int idSeguroSocial = paciente.getIdSeguro();
        try {
            //Se obtiene el objeto seguro con base al id de seguro del paciente
            seguro = seguroModel.selectSeguro(idSeguroSocial);

            campoNombreSeguro.setText(seguro.getNombre()); //Rellena el nombre del seguro
            campoNumPoliza.setText(seguro.getNumPoliza()); //Rellena el numero de poliza

        } catch (Exception e) {
            System.out.println("Error al obtener el seguro con id: " + idSeguroSocial);
        }

        //Cargar los datos del familiar responsable
        FamiliarResponsable familiarResponsable = null;
        try {
            //Se obtiene el objeto del Familiar
            familiarResponsable = familiarResponsableModel.selectFamiliarResponsablePorPacienteId(paciente.getId());
            campoNombreFamiliar.setText(familiarResponsable.getNombre());
            campoParentesco.setText(familiarResponsable.getRelacion());
            campoTelefonoPariente.setText(familiarResponsable.getTelefono());

        } catch (Exception e){
            System.out.println("Error al cargar los datos del familiar responsable");
        }


        //Cargar la tabla de medicamentos
        ObservableList<Pane> list = FXCollections.observableArrayList();
        list.add(new Pane());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("tablaMedicamento.fxml"));
            Pane newLoadedPane = loader.load();
            ControllerTablaMedicamentos controller = loader.getController();

            controller.initTable(paciente.getId());

            listaTabla.setItems(list);
            listaTabla.getItems().get(0).getChildren().add(newLoadedPane);
        }catch (Exception e)
        {
            System.out.println("Error cargar tabla");
        }

        //Cargar los padecimientos del paciente
        ObservableList<String> lista = FXCollections.observableArrayList();
        Padecimiento[] padecimientos = null;
        try {
            padecimientos = padecimientoModel.selectPadecimientosPorPaciente(paciente.getId());
            for (int i = 0; i < padecimientos.length; i++){
                lista.add(padecimientos[i].getPadecimiento());
            }
            listaPadecimientos.setItems(lista);
        }catch (Exception e){
            System.out.println("Error al cargar padecimientos");
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

    @FXML
    public void pushedActualizar(){
        Paciente paciente = null;
        try {
            paciente = pacienteModel.selectPaciente(idPaciente);
        }catch (Exception e) {
            System.out.println("Hubo error al actualizar el paciente");
        }


        //Metodos para actualizar
        actualizaPaciente(paciente);
        actualizaServicioEm(paciente);
        actualizaSeguro(paciente);
        actualizaDatosFamiliar(paciente.getId());
    }

    private void actualizaPaciente(Paciente paciente){
        paciente.setNombre(campoNombrePaciente.getText());
        paciente.setSexo(campoSexo.getText().charAt(0));
        paciente.setEstado(campoEstado.getText());
        paciente.setFechaNacimiento(Date.from(campoFechaNac.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        try {
            pacienteModel.updatePaciente(paciente);
            System.out.println("Datos actualizados correctamente");
        }catch (Exception e) {
            System.out.println("No Se pudo actualizar correctamente");
        }
    }

    private void actualizaServicioEm(Paciente paciente) {
        paciente.setNumeroReferencia(campoNumSrvEmergencia.getText());
        try {
            paciente.setIdServicioEmergencia(servicioEmergenciaModel.selectIdServicioEmergencia(campoNombreSrvEmergencia.getText()));
            pacienteModel.updatePaciente(paciente);
            System.out.println("Informacion del servicio de emergencia ha sido cambiada con éxito");
        }catch (Exception e) {
            System.out.println("No se pudo actualizar la informacion del servicio de emergencias");
        }
    }

    private void actualizaSeguro(Paciente paciente){
        try {
            seguro = seguroModel.selectSeguro(paciente.getIdSeguro());
            seguro.setNumPoliza(campoNumPoliza.getText());
            seguro.setNombre(campoNombreSeguro.getText());
            //seguroModel.updateSeguro(seguro);
            //System.out.println("Datos del seguro actualizados con éxito");
        }catch (Exception e){
            System.out.println("No se pudo actualizar la informacion del seguro");
        }
    }

    private void actualizaDatosFamiliar(Integer id) {
        FamiliarResponsable familiarResponsable = null;
        try{
            //Se obtiene el objeto del familiar correspondiente
            familiarResponsable=familiarResponsableModel.selectFamiliarResponsablePorPacienteId(id);
            //Se obtienen los nuevos datos de los campos de la interfaz
            familiarResponsable.setNombre(campoNombreFamiliar.getText());
            familiarResponsable.setRelacion(campoParentesco.getText());
            familiarResponsable.setTelefono(campoTelefonoPariente.getText());
            //Se realiza la actualizacion en la base de datos
            familiarResponsableModel.updateFamiliarResponsable(familiarResponsable);
        }catch (Exception e){
            System.out.println("No se pudo actualizar la informacion del familiar");
        }
    }
}
