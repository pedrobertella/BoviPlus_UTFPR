package Telas;

import DAO.DAOMedicamento;
import Entidades.Medicamento;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConsultaMedicamentoController implements Initializable {

    DAOMedicamento consulta;
    public Stage cadastro;

    public class Medicamento1 {

    private int id_med;
    private String nome;
    private String indicacao;
    private String contra_indc;

        public Medicamento1(int id_med, String nome, String indicacao, String contra_indc) {
            
            this.id_med = id_med;
            this.nome = nome;
            this.indicacao = indicacao;
            this.contra_indc = contra_indc;
        }

        public int getId_med() {
            return id_med;
        }

        public void setId_med(int id_med) {
            this.id_med = id_med;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getIndicacao() {
            return indicacao;
        }

        public void setIndicacao(String indicacao) {
            this.indicacao = indicacao;
        }

        public String getContra_indc() {
            return contra_indc;
        }

        public void setContra_indc(String contra_indc) {
            this.contra_indc = contra_indc;
        }

    }

    @FXML
    private AnchorPane panejanela;

    @FXML
    private TableView<Medicamento1> tabela;

    @FXML
    private TableColumn<Medicamento1, Integer> tc_cod;

    @FXML
    private TableColumn<Medicamento1, String> tc_nome;

    @FXML
    private TableColumn<Medicamento1, String> tc_indic;

    @FXML
    private TableColumn<Medicamento1, String> tc_contra_indc;

    private ObservableList<Medicamento1> medicamentos = FXCollections.observableArrayList();

    @FXML
    private void preencherTabela(KeyEvent evt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            consulta = new DAOMedicamento();
            List<Medicamento> af = busca();
            medicamentos = FXCollections.observableArrayList();
            Medicamento1 b;
            String tipo;
            for (Medicamento m : af) {
                
                b = new Medicamento1(m.getId_med(), m.getNome(), m.getDescricao(), m.getContra_indc());
                medicamentos.add(b);
            }     
                          
            
            tabela.setItems(medicamentos);
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Falha!");
            alert2.setHeaderText("Não foi possivel pesquisar!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }
    private List<Medicamento> busca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAOMedicamento dao = new DAOMedicamento();
        List<Medicamento> af = dao.getList("SELECT * FROM MEDICAMENTO");

        return af;
    }

    @FXML
    public void initTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        tc_cod.setCellValueFactory(new PropertyValueFactory("id_med"));
        tc_nome.setCellValueFactory(new PropertyValueFactory("nome"));
        tc_indic.setCellValueFactory(new PropertyValueFactory("indicacao"));
        tc_contra_indc.setCellValueFactory(new PropertyValueFactory("contra_indc"));

        List<Medicamento> af = busca();
        medicamentos = FXCollections.observableArrayList();
        Medicamento1 b;
        for (Medicamento m : af) {
                
                b = new Medicamento1(m.getId_med(), m.getNome(), m.getDescricao(), m.getContra_indc());
                medicamentos.add(b);
            }
        tabela.setItems(medicamentos);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initTable();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
    

}
