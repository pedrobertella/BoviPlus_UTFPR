package Telas;

import DAO.DAODoenca;
import Entidades.Doenca;
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

public class ConsultaDoencaController implements Initializable {

    DAODoenca consulta;
    public Stage cadastro;

    public class Doenca1 {

    private int id_doenca;
    private String nome;
    private String descricao;

        public Doenca1(int id_doenca, String nome, String descricao) {
            
            this.id_doenca = id_doenca;
            this.nome = nome;
            this.descricao = descricao;
        }

        public int getId_doenca() {
            return id_doenca;
        }

        public void setId_doenca(int id_doenca) {
            this.id_doenca = id_doenca;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    @FXML
    private AnchorPane panejanela;

    @FXML
    private TableView<Doenca1> tabela;

    @FXML
    private TableColumn<Doenca1, Integer> tc_cod;

    @FXML
    private TableColumn<Doenca1, String> tc_nome;

    @FXML
    private TableColumn<Doenca1, String> tc_desc;


    private ObservableList<Doenca1> doencas = FXCollections.observableArrayList();

    @FXML
    private void preencherTabela(KeyEvent evt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            consulta = new DAODoenca();
            List<Doenca> af = busca();
            doencas = FXCollections.observableArrayList();
            Doenca1 b;
            for (Doenca m : af) {
                
                b = new Doenca1(m.getId_doenca(), m.getNome(), m.getDescricao());
                doencas.add(b);
            }     
                          
            
            tabela.setItems(doencas);
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
    private List<Doenca> busca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAODoenca dao = new DAODoenca();
        List<Doenca> af = dao.getList("SELECT * FROM DOENCA");

        return af;
    }

    @FXML
    public void initTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        tc_cod.setCellValueFactory(new PropertyValueFactory("id_doenca"));
        tc_nome.setCellValueFactory(new PropertyValueFactory("nome"));
        tc_desc.setCellValueFactory(new PropertyValueFactory("descricao"));

        List<Doenca> af = busca();
        doencas = FXCollections.observableArrayList();
        Doenca1 b;
        for (Doenca m : af) {
                
                b = new Doenca1(m.getId_doenca(), m.getNome(), m.getDescricao());
                doencas.add(b);
            }
        tabela.setItems(doencas);
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
