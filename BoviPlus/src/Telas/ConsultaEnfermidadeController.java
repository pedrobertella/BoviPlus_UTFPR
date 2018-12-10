package Telas;

import DAO.DAOEnfermidade;
import Entidades.Enfermidade;
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

public class ConsultaEnfermidadeController implements Initializable {

    DAOEnfermidade consulta;
    public Stage cadastro;

    public class Enfermidade1 {

    private int id_animal;
    private int id_doenca;
    public int tempo_carencia;
    public  int tempo_tratamento;
    public String data_enfermidade;

        public Enfermidade1(int id_animal, int id_doenca, int tempo_carencia, int tempo_tratamento, String data_enfermidade) {
            this.id_animal = id_animal;
            this.id_doenca = id_doenca;
            this.tempo_carencia = tempo_carencia;
            this.tempo_tratamento = tempo_tratamento;
            this.data_enfermidade = data_enfermidade;
        }

        public int getId_animal() {
            return id_animal;
        }

        public void setId_animal(int id_animal) {
            this.id_animal = id_animal;
        }
        
        public int getId_doenca() {
            return id_doenca;
        }

        public void setId_doenca(int id_doenca) {
            this.id_doenca = id_doenca;
        }

        public int getTempo_carencia() {
            return tempo_carencia;
        }

        public void setTempo_carencia(int tempo_carencia) {
            this.tempo_carencia = tempo_carencia;
        }

        public int getTempo_tratamento() {
            return tempo_tratamento;
        }

        public void setTempo_tratamento(int tempo_tratamento) {
            this.tempo_tratamento = tempo_tratamento;
        }

        public String getData_enfermidade() {
            return data_enfermidade;
        }

        public void setData_enfermidade(String data_enfermidade) {
            this.data_enfermidade = data_enfermidade;
        }

    }
    
    @FXML
    private AnchorPane panejanela;

    @FXML
    private TableView<Enfermidade1> tabela;

    @FXML
    private TableColumn<Enfermidade1, Integer> tc_cod_animal;

    @FXML
    private TableColumn<Enfermidade1, Integer> tc_cod_doenca;

    @FXML
    private TableColumn<Enfermidade1, Integer> tc_temp_car;

    @FXML
    private TableColumn<Enfermidade1, Integer> tc_temp_trat;

    @FXML
    private TableColumn<Enfermidade1, String> tc_data;

    
    
    private ObservableList<Enfermidade1> enfermidades = FXCollections.observableArrayList();

    @FXML
    private void preencherTabela(KeyEvent evt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            consulta = new DAOEnfermidade();
            List<Enfermidade> af = busca();
            enfermidades = FXCollections.observableArrayList();
            Enfermidade1 b;
            for (Enfermidade e : af) {
                
                b = new Enfermidade1(e.getId_animal(), e.getId_doenca(), e.getTempo_carencia(), e.getTempo_tratamento(), e.getData_enfermidade());
                enfermidades.add(b);
            }     
                          
            
            tabela.setItems(enfermidades);
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
    private List<Enfermidade> busca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAOEnfermidade dao = new DAOEnfermidade();
        List<Enfermidade> af = dao.getList("SELECT * FROM ENFERMIDADE");

        return af;
    }

    @FXML
    public void initTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        tc_cod_animal.setCellValueFactory(new PropertyValueFactory("id_animal"));
        tc_cod_doenca.setCellValueFactory(new PropertyValueFactory("id_doenca"));
        tc_temp_car.setCellValueFactory(new PropertyValueFactory("tempo_carencia"));
        tc_temp_trat.setCellValueFactory(new PropertyValueFactory("tempo_tratamento"));
        tc_data.setCellValueFactory(new PropertyValueFactory("data_enfermidade"));
        

        List<Enfermidade> af = busca();
        enfermidades = FXCollections.observableArrayList();
        Enfermidade1 b;
        for (Enfermidade e : af) {
                
                b = new Enfermidade1(e.getId_animal(), e.getId_doenca(), e.getTempo_carencia(), e.getTempo_tratamento(), e.getData_enfermidade());
                enfermidades.add(b);
            }
        tabela.setItems(enfermidades);
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
