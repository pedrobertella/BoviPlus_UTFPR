package Telas;

import DAO.DAOVacina;
import Entidades.Vacina;
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

public class ConsultaVacinaController implements Initializable {

    DAOVacina consulta;
    public Stage cadastro;

    public class Vacina1 {

    private int id_vacina;
    private String nome;
    private String indicacao;
    private String contra_indc;
    private String periodica;
    private String campanha;
    private String mes;
    private String cat_animais;

        public Vacina1(int id_vacina, String nome, String indicacao, String contra_indc, String periodica, String campanha, String mes, String cat_animais) {
            
            this.id_vacina = id_vacina;
            this.nome = nome;
            this.indicacao = indicacao;
            this.contra_indc = contra_indc;
            this.periodica = periodica;
            this.campanha = campanha;
            this.mes = mes;
            this.cat_animais = cat_animais;
        }

        public int getId_vacina() {
            return id_vacina;
        }

        public void setId_vacina(int idVacina) {
            this.id_vacina = idVacina;
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

        public String getPeriodica() {
            return periodica;
        }

        public void setPeriodica(String periodica) {
            this.periodica = periodica;
        }

        public String getCampanha() {
            return campanha;
        }

        public void setCampanha(String campanha) {
            this.campanha = campanha;
        }

        public String getMes() {
            return mes;
        }

        public void setMes(String mes) {
            this.mes = mes;
        }

        public String getCat_animais() {
            return cat_animais;
        }

        public void setCat_animais(String cat_animais) {
            this.cat_animais = cat_animais;
        }

    }

    @FXML
    private AnchorPane panejanela;

    @FXML
    private TableView<Vacina1> tabela;

    @FXML
    private TableColumn<Vacina1, Integer> tc_cod;

    @FXML
    private TableColumn<Vacina1, String> tc_nome;

    @FXML
    private TableColumn<Vacina1, String> tc_indic;

    @FXML
    private TableColumn<Vacina1, String> tc_contra_indc;

    @FXML
    private TableColumn<Vacina1, String> tc_tipo;

    @FXML
    private TableColumn<Vacina1, String> tc_camp;

    @FXML
    private TableColumn<Vacina1, String> tc_mes;

    @FXML
    private TableColumn<Vacina1, String> tc_cat_animais;

    private ObservableList<Vacina1> vacinas = FXCollections.observableArrayList();

    @FXML
    private void preencherTabela(KeyEvent evt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            consulta = new DAOVacina();
            List<Vacina> af = busca();
            vacinas = FXCollections.observableArrayList();
            Vacina1 b;
            String tipo;
            for (Vacina v : af) {
                if (v.getPeriodica()) {
                    tipo = "Periódica";
                } else {
                    tipo = "Extemporânea";                    
                }
                
                b = new Vacina1(v.getId_vacina(), v.getNome(), v.getIndicacao(), v.getContra_indc(), tipo, v.getCampanha(), v.getMes(), v.getCat_animais());
                vacinas.add(b);
            }     
                          
            
            tabela.setItems(vacinas);
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
    private List<Vacina> busca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAOVacina dao = new DAOVacina();
        List<Vacina> af = dao.getList("SELECT * FROM VACINA");

        return af;
    }

    @FXML
    public void initTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        tc_cod.setCellValueFactory(new PropertyValueFactory("id_vacina"));
        tc_nome.setCellValueFactory(new PropertyValueFactory("nome"));
        tc_indic.setCellValueFactory(new PropertyValueFactory("indicacao"));
        tc_contra_indc.setCellValueFactory(new PropertyValueFactory("contra_indc"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory("periodica"));
        tc_camp.setCellValueFactory(new PropertyValueFactory("campanha"));
        tc_mes.setCellValueFactory(new PropertyValueFactory("mes"));
        tc_cat_animais.setCellValueFactory(new PropertyValueFactory("cat_animais"));

        List<Vacina> af = busca();
        vacinas = FXCollections.observableArrayList();
        Vacina1 b;
        String tipo;
        for (Vacina v : af) {
                if (v.getPeriodica()) {
                    tipo = "Periódica";
                } else {
                    tipo = "Extemporânea";                    
                }
                
                b = new Vacina1(v.getId_vacina(), v.getNome(), v.getIndicacao(), v.getContra_indc(), tipo, v.getCampanha(), v.getMes(), v.getCat_animais());
                vacinas.add(b);
            }
        tabela.setItems(vacinas);
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
