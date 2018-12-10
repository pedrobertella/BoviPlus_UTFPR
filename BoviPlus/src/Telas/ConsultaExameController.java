/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import DAO.DAOExame;
import Entidades.Exame;
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

/**
 * FXML Controller class
 *
 * @author new
 */
public class ConsultaExameController implements Initializable {

    DAOExame consulta;
    public Stage cadastro;

    public class Exame1 {

    private int id_exame;
    private String nome;
    private String descricao;
    public String data_exame;
    public  String categoria;
    public int intervalo;
    public int id_animal;

        public Exame1(int id_exame, String nome, String descricao, String data_exame, String categoria, int intervalo, int id_animal) {
            
            this.id_exame = id_exame;
            this.nome = nome;
            this.descricao = descricao;
            this.data_exame = data_exame;
            this.categoria = categoria;
            this.intervalo = intervalo;
            this.id_animal = id_animal;
        }

        public int getId_exame() {
            return id_exame;
        }

        public void setId_doenca(int id_exame) {
            this.id_exame = id_exame;
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

        public String getData_exame() {
            return data_exame;
        }

        public void setData_exame(String data_exame) {
            this.data_exame = data_exame;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public int getIntervalo() {
            return intervalo;
        }

        public void setIntervalo(int intervalo) {
            this.intervalo = intervalo;
        }

        public int getId_animal() {
            return id_animal;
        }

        public void setId_animal(int id_animal) {
            this.id_animal = id_animal;
        }
    }
    
    @FXML
    private AnchorPane panejanela;

    @FXML
    private TableView<Exame1> tabela;

    @FXML
    private TableColumn<Exame1, Integer> tc_cod;

    @FXML
    private TableColumn<Exame1, String> tc_nome;

    @FXML
    private TableColumn<Exame1, String> tc_desc;

    @FXML
    private TableColumn<Exame1, String> tc_data;

    @FXML
    private TableColumn<Exame1, String> tc_categoria;

    @FXML
    private TableColumn<Exame1, Integer> tc_intervalo;

    @FXML
    private TableColumn<Exame1, Integer> tc_cod_animal;
    
    private ObservableList<Exame1> exames = FXCollections.observableArrayList();

    @FXML
    private void preencherTabela(KeyEvent evt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            consulta = new DAOExame();
            List<Exame> af = busca();
            exames = FXCollections.observableArrayList();
            Exame1 b;
            for (Exame m : af) {
                
                b = new Exame1(m.getId_exame(), m.getNome(), m.getDescricao(), m.getData_exame(), m.getCategoria(), m.getIntervalo(), m.getId_animal());
                exames.add(b);
            }     
                          
            
            tabela.setItems(exames);
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
    private List<Exame> busca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAOExame dao = new DAOExame();
        List<Exame> af = dao.getList("SELECT * FROM EXAME");

        return af;
    }

    @FXML
    public void initTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        tc_cod.setCellValueFactory(new PropertyValueFactory("id_exame"));
        tc_nome.setCellValueFactory(new PropertyValueFactory("nome"));
        tc_desc.setCellValueFactory(new PropertyValueFactory("descricao"));
        tc_data.setCellValueFactory(new PropertyValueFactory("data_exame"));
        tc_categoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        tc_intervalo.setCellValueFactory(new PropertyValueFactory("intervalo"));
        tc_cod_animal.setCellValueFactory(new PropertyValueFactory("id_animal"));
        

        List<Exame> af = busca();
        exames = FXCollections.observableArrayList();
        Exame1 b;
        for (Exame m : af) {
                
                b = new Exame1(m.getId_exame(), m.getNome(), m.getDescricao(), m.getData_exame(), m.getCategoria(), m.getIntervalo(), m.getId_animal());
                exames.add(b);
            }
        tabela.setItems(exames);
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
