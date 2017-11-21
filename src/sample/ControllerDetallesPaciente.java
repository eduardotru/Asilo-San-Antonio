package sample;


import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private int idSeguroSocial;
    private int idSrvEmg;

    @FXML private ListView<String> listaFamiliares;
    private ObservableList<String> olstfamiliares;

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

    @FXML private Button bAgregarFamiliar;
    @FXML private Button bAgregarServicioEmergencia;
    @FXML private Button bAgregarSeguro;

    // Variables auxiliares para el añadimiento y/o reemplazo de familiares responsables, servicio de emergencia
    // y seguro.
    private ArrayList<FamiliarResponsable> familiares;
    private ServicioEmergencia servicio;

    private void cargaFamiliares() {
        olstfamiliares = FXCollections.observableArrayList();

        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
        try {
            FamiliarResponsable[] familiares = familiarResponsableModel.
                    selectFamiliarResponsablesPorPaciente(idPaciente);
            for (FamiliarResponsable familiar: familiares) {
                olstfamiliares.add(familiar.getNombre());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaFamiliares.setItems(olstfamiliares);
    }

    public void initData(Paciente paciente) {
        listaFamiliares.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Rellena los campos con los datos del paciente
        campoNombrePaciente.setText(paciente.getNombre());
        campoFechaNac.setValue(Instant.ofEpochMilli(paciente.getFechaNacimiento().getTime()).atZone((ZoneId.systemDefault())).toLocalDate());
        campoSexo.setText(String.valueOf(paciente.getSexo()));
        campoEstado.setText(paciente.getEstado());
        idPaciente = paciente.getId();

        cargaFamiliares();

        //Obtiene el Servicio de emergencia y rellena los campos con los datos correspondientes
        idSrvEmg = paciente.getIdServicioEmergencia();
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
        idSeguroSocial = paciente.getIdSeguro();
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

    /**
     *  Acción al presionar el botón "Agregar Familiar" en la interfaz.
     *
     *  Se crea una ventana emergente donde el usuario
     *  crea un nuevo familiar. Para procurar un bajo acoplamiento entre este este controlador y el de la ventana
     *  emergente, primero se guarda el último índice creado, y este se vuelve a consultar unav vez que la ventana
     *  emergente se cierra. Si los id's son diferentes, se creo un nuevo familiar y debe agregarse a la lista.
     *  De lo contrario, no se hace nada.
     * @param event
     * @throws IOException
     */
    public void pressButtonAgregarFamiliar(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;


        int prevLastFamiliarId = -1;
        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
        try {
            prevLastFamiliarId = familiarResponsableModel.getLastFamiliarAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarFamiliar.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Familiar");
        stage.setScene(new Scene(root, 580, 500));
        stage.showAndWait();

        int lastFamiliarId = -1;
        try {
            lastFamiliarId = familiarResponsableModel.getLastFamiliarAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastFamiliarId != lastFamiliarId) {
            FamiliarResponsable nuevoFamiliar = null;
            try {
                nuevoFamiliar = familiarResponsableModel.selectFamiliarResponsable(lastFamiliarId);
                nuevoFamiliar.setIdPaciente(idPaciente);
                familiarResponsableModel.updateFamiliarResponsable(nuevoFamiliar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cargaFamiliares();
    }

    /**
     * Acción al presionar el botón "Agregar Servicio" en la interfaz.
     * Al crear un nuevo servicio la función ahora reemplazará el servicio anterior con el creado.
     * @param event
     * @throws IOException
     */
    public void pressButtonAgregarServicio(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());

        int prevLastServicioId = -1;
        ServicioEmergenciaModel servicioEmergenciaModel = new ServicioEmergenciaModel();
        try {
            prevLastServicioId = servicioEmergenciaModel.getLastServicioAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarServicio.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Servicio de Emergencia");
        stage.setScene(new Scene(root, 580, 500));
        stage.showAndWait();

        int lastServicioId = -1;
        try {
            lastServicioId = servicioEmergenciaModel.getLastServicioAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastServicioId != lastServicioId) {
            ServicioEmergencia nuevoServicio = null;
            try {
                nuevoServicio = servicioEmergenciaModel.selectServicioEmergencia(lastServicioId);
                campoTelSrvEmergencia.setText(nuevoServicio.getTelefono());
                campoNombreSrvEmergencia.setText(nuevoServicio.getNombre());
                campoNumSrvEmergencia.setText(nuevoServicio.getDireccion());
                bAgregarServicioEmergencia.setText("Reemplazar");
                idSrvEmg = lastServicioId;
                if (servicio != null) {
                    servicioEmergenciaModel.deleteServicioEmergencia(servicio.getId());
                }
                servicio = nuevoServicio;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Acción al presionar el botón "Agregar Seguro" en la interfaz.
     * Una vez creado un seguro inicial, si se intenta crear otro seguro para el paciente, este reemplaza al anterior.
     * @param event
     * @throws IOException
     */
    public void pressButtonAgregarSeguro(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        System.out.println(event.getSource().toString());

        int prevLastSeguroId = -1;
        SeguroModel seguroModel = new SeguroModel();
        try {
            prevLastSeguroId = seguroModel.getLastSeguroAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        root = FXMLLoader.load(getClass().getResource("agregarSeguro.fxml"));
        stage = new Stage();
        stage.setTitle("Asilo San Antonio - Agregar Seguro Médico");
        stage.setScene(new Scene(root, 580, 500));
        stage.showAndWait();

        int lastSeguroId = -1;
        try {
            lastSeguroId = seguroModel.getLastSeguroAddedId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prevLastSeguroId != lastSeguroId) {
            Seguro nuevoSeguro = null;
            try {
                nuevoSeguro = seguroModel.selectSeguro(lastSeguroId);
                idSeguroSocial = lastSeguroId;
                campoNombreSeguro.setText(nuevoSeguro.getNombre());
                campoNumPoliza.setText(nuevoSeguro.getNumPoliza());
                bAgregarSeguro.setText("Reemplazar");
                if (seguro != null) {
                    seguroModel.deleteSeguro(seguro.getId());
                }
                seguro = nuevoSeguro;
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        //actualizaDatosFamiliar(paciente.getId());
    }

    private void actualizaPaciente(Paciente paciente){
        paciente.setNombre(campoNombrePaciente.getText());
        paciente.setSexo(campoSexo.getText().charAt(0));
        paciente.setEstado(campoEstado.getText());
        paciente.setFechaNacimiento(Date.from(campoFechaNac.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Se ha actualizado el paciente de forma exitosa.");
            alert.showAndWait();

            pacienteModel.updatePaciente(paciente);
            System.out.println("Datos actualizados correctamente");
        }catch (Exception e) {
            System.out.println("No Se pudo actualizar correctamente");
        }
    }

    private void actualizaServicioEm(Paciente paciente) {
        paciente.setNumeroReferencia(campoNumSrvEmergencia.getText());
        try {
            paciente.setIdServicioEmergencia(idSrvEmg);
            pacienteModel.updatePaciente(paciente);
            System.out.println("Informacion del servicio de emergencia ha sido cambiada con éxito");
        }catch (Exception e) {
            System.out.println("No se pudo actualizar la informacion del servicio de emergencias");
        }
    }

    private void actualizaSeguro(Paciente paciente){
        try {
            seguro = seguroModel.selectSeguro(idSeguroSocial);
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

    @FXML
    public void clickFamiliar(ActionEvent event) {
        FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
        String nombreFamiliarSeleccionado = listaFamiliares.getSelectionModel().getSelectedItem();
        try {
            int idFamiliarSeleccionado = familiarResponsableModel.selectIdFamiliarResponsable(nombreFamiliarSeleccionado);
            FamiliarResponsable familiarSeleccionado = familiarResponsableModel.
                    selectFamiliarResponsable(idFamiliarSeleccionado);
            campoNombreFamiliar.setText(familiarSeleccionado.getNombre());
            campoParentesco.setText(familiarSeleccionado.getRelacion());
            campoTelefonoPariente.setText(familiarSeleccionado.getTelefono());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
