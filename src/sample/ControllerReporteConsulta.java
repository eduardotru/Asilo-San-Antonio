package sample;

import db.EventoModel;
import db.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objetos.Evento;
import objetos.EventoTabla;
import objetos.Paciente;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class ControllerReporteConsulta extends  ControllerBase{

    private EventoModel eventoModel = new EventoModel();

    @FXML private Button btnHome;
    @FXML private Button btnMenuReporte;
    @FXML private Label date;
    @FXML private Label name;
    @FXML private Label asunto;
    @FXML private TableView<EventoTabla> tableEventoDelDia;
    @FXML private TableColumn<EventoTabla,String> columnaPaciente;
    @FXML private TableColumn<EventoTabla,String> columnaEvento;
    @FXML private Button btnConsultar;
    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;
    @FXML private ComboBox cbPaciente;
    @FXML private TableColumn<EventoTabla,String> columnaEnfermero;
    @FXML private TableColumn<EventoTabla,String> columnaFecha;

    @FXML
    private TextField campoPaciente;
    @FXML
    private TextField campoAsunto;
    @FXML
    private TextField campoDescripcion;
    @FXML
    private TextField campoNotificado;
    @FXML
    private TextField campoHospitalito;
    @FXML
    private TextField campoConsulta;
    @FXML
    private TextField campoEnfermero;

    private int Dia;
    private int Mes;
    private int Anio;

    @FXML
    private void initialize() {
        initializeDate(date);
        rellenaTabla();

        PacienteModel pacienteModel = new PacienteModel();

        Paciente[] pacientes;
        try {
            pacientes = pacienteModel.selectPacientes();
            for (int i = 0; i < pacientes.length; i++) {
                System.out.println(pacientes[i].getNombre());
                cbPaciente.getItems().add(pacientes[i].getNombre());
            }

        } catch (Exception e) {
            System.out.println(e.getClass()+e.getMessage());
        }
    }

    private boolean rellenaTabla(){
        Evento[] eventos;
        eventos = null;
        try {
            if (cbPaciente.getValue() == null && dateFrom.getValue() == null  && dateTo.getValue() == null) {
                    eventos = eventoModel.selectAllEventos();
            }
            else if (cbPaciente.getValue() != null && (dateFrom.getValue() == null ||
                        dateTo.getValue() == null)) {
                PacienteModel pacienteModel = new PacienteModel();
                int pacienteId = 0;
                try {
                    pacienteId = pacienteModel.selectIdPaciente((String) cbPaciente.getValue());
                } catch (Exception e) {
                    return false;
                }
                try {
                    eventos = eventoModel.selectEventosPorPaciente(pacienteId);
                } catch (SQLException e) {
                    return false;
                }
            } else if (cbPaciente.getValue() == null && (dateFrom.getValue() != null &&
                        dateTo.getValue() != null)) {
                if (dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() >
                        dateTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond()) {
                    showAlertDialog(Alert.AlertType.INFORMATION,
                            "La fecha de inicio debe ser menor a la fecha de término");
                    return false;
                }
                try {
                    eventos = eventoModel.selectEventos(Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(dateTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } catch (SQLException e) {
                    return false;
                }
            } else if (cbPaciente.getValue() != null && dateFrom.getValue() != null &&
                    dateTo.getValue() != null) {
                if (dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond() >
                        dateTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond()) {
                    showAlertDialog(Alert.AlertType.INFORMATION,
                            "La fecha de inicio debe ser menor a la fecha de término");

                    return false;
                }
                try {
                    PacienteModel pacienteModel = new PacienteModel();
                    eventos = eventoModel.selectEventosPorFechaYPaciente(Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(dateTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            pacienteModel.selectIdPaciente((String) cbPaciente.getValue()));
                } catch (SQLException e) {
                    return false;
                }
            }
            initTable(eventos);
            return true;
        }catch (Exception e) {
            initTable(eventos);
            return false;
        }
    }

    private void initTable(Evento[] eventos){
        columnaPaciente.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("paciente"));
        columnaEvento.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("asunto"));
        columnaEnfermero.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("enfermero"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("fecha"));

        tableEventoDelDia.setItems(getEvento(eventos));
    }

    private ObservableList<EventoTabla> getEvento(Evento[] eventos){
        ObservableList<EventoTabla> listaEventos = FXCollections.observableArrayList();
        for(int i=0; i<eventos.length;i++) {
            listaEventos.add(new EventoTabla(eventos[i]));
        }
        return  listaEventos;
    }

    public void  pressButton(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        if(event.getSource() == btnHome) {
            stage =(Stage) btnHome.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
            System.out.println(event.getSource().toString());
        } else if(event.getSource()==btnMenuReporte){
            stage =(Stage) btnMenuReporte.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void  filaClickeada(){
        EventoTabla eventoSeleccionado = tableEventoDelDia.getSelectionModel().selectedItemProperty().get();
        campoAsunto.setText(eventoSeleccionado.getAsunto());
        campoPaciente.setText(eventoSeleccionado.getPaciente());
        campoDescripcion.setText(eventoSeleccionado.getDescripcion());
        campoEnfermero.setText(eventoSeleccionado.getEnfermero());
        campoNotificado.setText(eventoSeleccionado.getAvisoFamiliar());
        campoHospitalito.setText(eventoSeleccionado.getEstaHospitalito());
        campoConsulta.setText(eventoSeleccionado.getRequirioConsulta());
    }

    public void pressButtonSearch(ActionEvent event) throws IOException {
        boolean rellenoTabla = rellenaTabla();
        if (rellenoTabla) {
            showAlertDialog(Alert.AlertType.INFORMATION, "Eventos cargados de forma exitosa.");
        } else {
            showAlertDialog(Alert.AlertType.INFORMATION,
                    "Hubo un error al cargar los eventos con los parámetros que elegiste. Intenta de nuevo.");
        }
    }
}
