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

    private void rellenaTabla(){
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
                }
                try {
                    eventos = eventoModel.selectEventosPorPaciente(pacienteId);
                } catch (SQLException e) {
                }
            } else if (cbPaciente.getValue() == null && (dateFrom.getValue() != null &&
                        dateTo.getValue() != null)) {
                try {
                    eventos = eventoModel.selectEventos(Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(dateTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } catch (SQLException e) {
                }
            }
            System.out.println("Eventos cargados!");
        }catch (Exception e) {
            System.out.println("No se pudieron cargar los eventos del dia");
        }
        initTable(eventos);
    }

    private void initTable(Evento[] eventos){
        columnaPaciente.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("paciente"));
        columnaEvento.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("asunto"));
        columnaEnfermero.setCellValueFactory(new PropertyValueFactory<EventoTabla,String>("enfermero"));
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
        EventoTabla eventoSeleccionado=  tableEventoDelDia.getSelectionModel().selectedItemProperty().get();
        System.out.println(eventoSeleccionado.getAsunto());
    }

    public void pressButtonSearch(ActionEvent event) throws IOException {
        rellenaTabla();
    }
}
