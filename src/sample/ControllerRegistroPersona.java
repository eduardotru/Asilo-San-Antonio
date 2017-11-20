package sample;

import db.FamiliarResponsableModel;
import db.PacienteModel;
import db.SeguroModel;
import db.ServicioEmergenciaModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objetos.FamiliarResponsable;
import objetos.Paciente;
import objetos.Seguro;
import objetos.ServicioEmergencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class ControllerRegistroPersona  extends ControllerBase {
    @FXML private TextField txtNombre;
    @FXML private DatePicker  dateFechaNacimiento;
    @FXML private ComboBox cbSexo;
    @FXML private ComboBox cbEstado;
    @FXML private TextField txtCuarto;
    @FXML private TextField txtCama;
    @FXML private Button bAgregarFamiliar;
    @FXML private Button bAgregarServicioEmergencia;
    @FXML private Button bAgregarSeguro;
    @FXML private TextArea txtFamiliares;
    @FXML private TextArea txtServicios;
    @FXML private TextArea txtSeguros;
    @FXML private TextArea txtPadecimientos;
    @FXML private TextField txtNumeroReferencia;
    @FXML public Button btnRegistrar;

    @FXML
    public Label date;
    @FXML
    public Label ruta;
    @FXML
    public ScrollPane scrollPane;

    private int Dia;
    private int Mes;
    private int Anio;
    
    private ArrayList<FamiliarResponsable> familiares;
    private ServicioEmergencia servicio;
    private Seguro seguro;

    @FXML
    /** Inicializa los labels de la parte superior de la pantalla, añade los elementos a ser utilizados
     *  en los ComboBox de la interfaz y prepara la lista de familiares a llenarse para el paciente.
     *
     *  Entrada: Ninguna (void)
     *  Salida: Ninguna (void)
     */
    private void initialize() {
        initializeDate(date);
        ruta.setText("Home > Registrar Persona");

        //
        cbEstado.getItems().add("Estable");
        cbEstado.getItems().add("Hospitalito");
        cbEstado.getItems().add("Internado");
        cbEstado.getItems().add("Baja");

        cbSexo.getItems().add("Hombre");
        cbSexo.getItems().add("Mujer");

        familiares = new ArrayList<FamiliarResponsable>();

        scrollPane.setVvalue(-1000);
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
    public void  pressButtonAgregarFamiliar(ActionEvent event) throws IOException {
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
                txtFamiliares.setText(txtFamiliares.getText() + "\n" + nuevoFamiliar.getNombre());
                familiares.add(nuevoFamiliar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Acción al presionar el botón "Agregar Servicio" en la interfaz.
     * Al crear un nuevo servicio la función ahora reemplazará el servicio anterior con el creado.
     * @param event
     * @throws IOException
     */
    public void  pressButtonAgregarServicio(ActionEvent event) throws IOException {
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
                txtServicios.setText(nuevoServicio.getNombre());
                bAgregarServicioEmergencia.setText("Reemplazar Servicio");
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
    public void  pressButtonAgregarSeguro(ActionEvent event) throws IOException {
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
                txtSeguros.setText(nuevoSeguro.getNombre());
                bAgregarSeguro.setText("Reemplazar Seguro");
                if (seguro != null) {
                    seguroModel.deleteSeguro(seguro.getId());
                }
                seguro = nuevoSeguro;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifica que los campos obligatorios de la forma de registro están llenados, y manda un cuadro
     * de dialogo de advertencia al usuario en caso de que falta alguno.
     * @return valor booleano. Indica si los valores obligatorios de la forma de registro están presentes
     * y llenados por el usuario.
     */
    private boolean validacionesFormaRegistro() {
        String elementoRestante = "";

        if (txtNombre.getText().length() <= 0) {
            elementoRestante = "un nombre para el paciente";
        } else if (dateFechaNacimiento.getValue() == null) {
            elementoRestante = "la fecha de nacimiento";
        } else if (cbSexo.getValue() == null) {
            elementoRestante= "el sexo";
        } else if (cbEstado.getValue() == null) {
            elementoRestante = "el estado";
        } else if (txtNumeroReferencia.getText().length() <= 0) {
            elementoRestante = "el número de referencia";
        } else if (seguro == null) {
            elementoRestante= "el seguro";
        } else if (servicio == null) {
            elementoRestante = "el servicio de emergencias";
        }

        if (elementoRestante != "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Favor de especificar " + elementoRestante + ".");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Función auxiliar que maneja la tarea específica de guardar el nuevo paciente en la base de datos.
     * @param pacienteModel
     * @param nombre
     * @param fechaNacimiento
     * @param sexo
     * @param estado
     * @param numCuarto
     * @param numCama
     * @param idSeguro
     * @param idServicioEmergencia
     * @param numeroReferencia
     * @return valor booleano. Regresa verdadero si la transacción en SQL fue exitosa. De lo contrario, regresa
     * un valor falso.
     */
    private boolean registrarPaciente(PacienteModel pacienteModel,
                                   String nombre, java.sql.Date fechaNacimiento, char sexo, String estado,
                                   int numCuarto, int numCama,
                                   int idSeguro, int idServicioEmergencia, String numeroReferencia) {
        try {
            int nuevoPacienteId = pacienteModel.addPaciente(nombre, fechaNacimiento,
                    sexo, estado, numCuarto, numCama, idSeguro, idServicioEmergencia, numeroReferencia);

            FamiliarResponsableModel familiarResponsableModel = new FamiliarResponsableModel();
            for (FamiliarResponsable familiar: familiares) {
                familiar.setIdPaciente(nuevoPacienteId);
                familiarResponsableModel.updateFamiliarResponsable(familiar);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Acción al presionar el botón "Registrar" en la interfaz de usuario.
     * @param event
     * @throws IOException
     */
    public void  pressButtonRegister(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        // Se asegura que los campos obligatorios de la forma de registro estén llenados.
        if (validacionesFormaRegistro() == false) {
            return;
        }

        // Se obtiene la información de la forma de registro.
        PacienteModel pacienteModel = new PacienteModel();
        String nombre = txtNombre.getText();
        java.sql.Date fechaNacimiento = java.sql.Date.valueOf(dateFechaNacimiento.getValue());
        char sexo = ((String)cbSexo.getValue()).charAt(0);
        String estado = (String)cbEstado.getValue();
        int numCuarto = 0;
        if (txtCuarto.getText().length() > 0) {
            numCuarto = Integer.parseInt(txtCuarto.getText());
        }
        int numCama = 0;
        if (txtCama.getText().length() > 0) {
            numCama = Integer.parseInt(txtCama.getText());
        }
        int idSeguro = seguro.getId();
        int idServicioEmergencia = servicio.getId();
        String numeroReferencia = txtNumeroReferencia.getText();

        boolean registroExitoso =
                registrarPaciente(pacienteModel, nombre, fechaNacimiento, sexo, estado, numCuarto, numCama,
                idSeguro, idServicioEmergencia, numeroReferencia);

        if (registroExitoso) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Se ha guardado el nuevo paciente de forma exitosa.");
            alert.showAndWait();

            cargaPantalla(event, "busquedapacientes.fxml", btnRegistrar);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asilo San Antonio");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un error al guardar al nuevo paciente. Favor de intentarlo más tarde.");
            alert.showAndWait();
        }
    }
}
