package sample;

import db.EventoModel;
import db.InterfazDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.Evento;
import objetos.EventoTabla;

import javax.annotation.Generated;
import java.time.Instant;
import java.util.Date;

import java.io.IOException;
import java.util.Calendar;

public class ControllerReporteDelDia extends  ControllerBase{

    private EventoModel eventoModel = new EventoModel();

    @FXML private Button btnHome;
    @FXML private Button btnMenuReporte;
    @FXML private Label date;
    @FXML private Label name;
    @FXML private Label asunto;
    @FXML private TableView<EventoTabla> tableEventoDelDia;
    @FXML private TableColumn<EventoTabla,String> columnaPaciente;
    @FXML private TableColumn<EventoTabla,String> columnaEvento;
    @FXML private TableColumn<EventoTabla,String> columnaEnfermero;
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
    }

    private void rellenaTabla(){
        Evento[] eventos;
        eventos = null;
        try{
                eventos = eventoModel.selectEventosDelDia(Date.from(Instant.now()));
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
        EventoTabla eventoSeleccionado = tableEventoDelDia.getSelectionModel().selectedItemProperty().get();
        campoAsunto.setText(eventoSeleccionado.getAsunto());
        campoPaciente.setText(eventoSeleccionado.getPaciente());
        campoDescripcion.setText(eventoSeleccionado.getDescripcion());
        campoEnfermero.setText(eventoSeleccionado.getEnfermero());
        campoNotificado.setText(eventoSeleccionado.getAvisoFamiliar());
        campoHospitalito.setText(eventoSeleccionado.getEstaHospitalito());
        campoConsulta.setText(eventoSeleccionado.getRequirioConsulta());
    }
}
