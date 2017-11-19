package sample;


import com.sun.org.apache.xpath.internal.operations.String;
import db.PacienteMedicamentoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objetos.PacienteMedicamento;
import objetos.PacienteMedicamentoTabla;

public class ControllerTablaMedicamentos {
    @FXML
    private TableView<PacienteMedicamentoTabla> tableView;
    @FXML
    private TableColumn<PacienteMedicamentoTabla,String> tableColumn;
    @FXML
    private TableColumn<PacienteMedicamentoTabla,String> tableColumn2;
    @FXML
    private TableColumn<PacienteMedicamentoTabla,String> tableColumn3;
    @FXML
    private TableColumn<PacienteMedicamentoTabla,String> tableColumn4;

    @FXML
    private void initialize() {
        System.out.println("GG");
        initTable(1);
    }

    public void initTable(int idPaciente) {
        tableColumn.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla, String>("medicamento"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla, String>("tomaManana"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla, String>("tomaMedio"));
        tableColumn4.setCellValueFactory(new PropertyValueFactory<PacienteMedicamentoTabla, String>("tomaTarde"));

        tableView.setItems(getMedicamentos(idPaciente));

    }

    private ObservableList<PacienteMedicamentoTabla> getMedicamentos(int idPaciente){
        ObservableList<PacienteMedicamentoTabla> data = FXCollections.observableArrayList();
        PacienteMedicamento pacienteMedicamentos[];
        PacienteMedicamentoModel model = new PacienteMedicamentoModel();
        try {
            pacienteMedicamentos = model.selectPacienteMedicamento(idPaciente);
            for(int i=0; i<pacienteMedicamentos.length; i++) {
                data.add(new PacienteMedicamentoTabla(pacienteMedicamentos[i]));
            }
        }catch (Exception e) {
            System.out.println("No se pudieron cargar los medicamentos");
        }
        return data;
    }
}
