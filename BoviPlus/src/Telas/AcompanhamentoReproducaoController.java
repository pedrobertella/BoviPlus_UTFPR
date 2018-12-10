package Telas;

import ConexaoBD.Conexao;
import DAO.DAOAnimal;
import DAO.DAOReproducao;
import Entidades.Animal;
import Entidades.Reproducao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AcompanhamentoReproducaoController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TableView<Reprod> tabela;

    @FXML
    private TableColumn<Reprod, Integer> cod_col;

    @FXML
    private TableColumn<Reprod, String> vaca_col;

    @FXML
    private TableColumn<Reprod, String> boi_col;

    @FXML
    private TableColumn<Reprod, String> datai_col;

    @FXML
    private TableColumn<Reprod, String> datap_col;

    @FXML
    private TextField campo1_tx;

    @FXML
    private DatePicker campo2_dp;

    @FXML
    private RadioButton previsao_rbt;

    @FXML
    private ToggleGroup datat;

    @FXML
    private RadioButton inseminacao_rbt;

    @FXML
    private RadioButton mae_rbt;

    @FXML
    private ToggleGroup outrot;

    @FXML
    private RadioButton pai_rbt;

    @FXML
    private RadioButton codigo_rbt;

    @FXML
    private Button todos_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button selecionar_bt;

    @FXML
    private ProgressBar barra_pb;

    private static Animal mae;
    private static Animal pai;
    private ObservableList<Reprod> tablecontent;
    private Stage cadastro;

    public static class Reprod {

        public final SimpleIntegerProperty id;
        public final SimpleStringProperty nome_mae;
        public final SimpleStringProperty nome_pai;
        public final SimpleStringProperty previsao;
        public final SimpleStringProperty data;

        public Reprod(int id, int mae1, int pai1, String prev, String insem) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
            this.id = new SimpleIntegerProperty(id);
            selecionaMae(mae1);
            this.nome_mae = new SimpleStringProperty(mae.getNome());
            selecionaPai(pai1);
            this.nome_pai = new SimpleStringProperty(pai.getNome());
            this.previsao = new SimpleStringProperty(prev);
            this.data = new SimpleStringProperty(insem);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public SimpleStringProperty nome_maeProperty() {
            return nome_mae;
        }

        public SimpleStringProperty nome_paiProperty() {
            return nome_pai;
        }

        public SimpleStringProperty previsaoProperty() {
            return previsao;
        }

        public SimpleStringProperty dataProperty() {
            return data;
        }

        public SimpleIntegerProperty getId() {
            return id;
        }

        public SimpleStringProperty getNome_mae() {
            return nome_mae;
        }

        public SimpleStringProperty getNome_pai() {
            return nome_pai;
        }

        public SimpleStringProperty getPrevisao() {
            return previsao;
        }

        public SimpleStringProperty getData() {
            return data;
        }
    }

    private static void selecionaMae(int id_mae) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAOAnimal da = new DAOAnimal();
        mae = da.consultaAnimal(id_mae);
    }

    private static void selecionaPai(int id_pai) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAOAnimal da = new DAOAnimal();
        pai = da.consultaAnimal(id_pai);
    }

    @FXML
    public void selecionar() throws IOException {
        Reprod a = tabela.getSelectionModel().getSelectedItem();
        Main.id_inseminacao = a.id.getValue().toString();
        cadastro = new Stage();
        Main.window = false;
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("RegistroInseminacao.fxml"));
        Scene scene = new Scene(fxmlDB);
        cadastro.getIcons().add(new Image("/Imagens/Iconew.png"));
        cadastro.setScene(scene);
        cadastro.setResizable(false);
        cadastro.setTitle("Registro de Inseminação [Reprodução] - BoviPlus");
        cadastro.show();
        Main.window = true;
    }

    @FXML
    protected void listarTodos() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {

        barra_pb.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        Thread a = new Thread() {
            @Override
            public void run() {
                try {
                    tablecontent = FXCollections.observableArrayList();
                    DAOReproducao dr = new DAOReproducao();
                    List<Reproducao> l = dr.listaTodos();
                    List<Reprod> o = new ArrayList<>();
                    for (Reproducao k : l) {
                        Reprod f = new Reprod(k.getId_gestacao(), k.getId_mae(), k.getId_pai(), k.getPrevisao(), k.getData_inseminacao());
                        tablecontent.add(f);
                    }

                    tabela.setItems(null);
                    tabela.setItems(tablecontent);
                    Platform.runLater(() -> {
                        barra_pb.setProgress(100);
                    });
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    alert2.setGraphic(imageView);
                    ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert2.setTitle("Atenção!");
                    alert2.setHeaderText("Não foi possível carregar os dados");
                    alert2.setContentText(e.getMessage());
                    alert2.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    Platform.runLater(() -> {
                        barra_pb.setProgress(0);
                    });
                }
            }
        };
        a.start();
    }

    @FXML
    protected void limparTabela() {
        tabela.setItems(null);
        barra_pb.setProgress(0);
    }

    private String strcat(String a, String b) {
        return a + b;
    }

    private String montaSQL2() {
        String sql = "SELECT r.id_gestacao, r.id_mae, r.id_pai, r.data_inseminacao, r.previsao FROM REPRODUCAO r ";
        String query = campo2_dp.getValue().toString();
        if (previsao_rbt.isSelected()) {
            sql = strcat(sql, "WHERE previsao = '" + query + "';");
        } else {
            sql = strcat(sql, "WHERE data_inseminacao ='" + query + "';");
        }

        return sql;
    }

    private String montaSQL1() {
        String sql = "SELECT r.id_gestacao, r.id_mae, r.id_pai, r.data_inseminacao, r.previsao FROM REPRODUCAO r ";
        String query = campo1_tx.getText();
        if (mae_rbt.isSelected()) {
            sql = strcat(sql, "inner join animal a on  r.id_mae = a.id_animal WHERE a.nome ILIKE '%" + query + "%';");
        } else if (pai_rbt.isSelected()) {
            sql = strcat(sql, "inner join animal a on  r.id_pai = a.id_animal WHERE a.nome ILIKE '%" + query + "%';");
        } else {
            sql = strcat(sql, "WHERE id_gestacao =" + query + ";");
        }

        return sql;
    }

    private List<Reproducao> listaBanco(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Conexao conexao = new Conexao();
        conexao.abrirConexao();
        List<Reproducao> lista = new ArrayList<>();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Reproducao r;
        while (rs.next()) {
            r = new Reproducao();
            r.setId_gestacao(rs.getInt("id_gestacao"));
            r.setId_mae(rs.getInt("id_mae"));
            r.setId_pai(rs.getInt("id_pai"));
            r.setPrevisao(rs.getString("previsao"));
            r.setData_inseminacao(rs.getString("data_inseminacao"));
            lista.add(r);
        }
        return lista;
    }

    @FXML
    protected void pesquisaData() {
        try {
            tablecontent = FXCollections.observableArrayList();

            List<Reproducao> l = listaBanco(montaSQL2());
            List<Reprod> o = new ArrayList<>();
            for (Reproducao k : l) {
                Reprod f = new Reprod(k.getId_gestacao(), k.getId_mae(), k.getId_pai(), k.getPrevisao(), k.getData_inseminacao());
                tablecontent.add(f);
            }

            tabela.setItems(null);
            tabela.setItems(tablecontent);

        } catch (Exception e) {
            Main.tocaErro();
        }
    }

    @FXML
    protected void pesquisa() {
        try {
            tablecontent = FXCollections.observableArrayList();

            List<Reproducao> l = listaBanco(montaSQL1());
            List<Reprod> o = new ArrayList<>();
            for (Reproducao k : l) {
                Reprod f = new Reprod(k.getId_gestacao(), k.getId_mae(), k.getId_pai(), k.getPrevisao(), k.getData_inseminacao());
                tablecontent.add(f);
            }

            tabela.setItems(null);
            tabela.setItems(tablecontent);

        } catch (Exception e) {
            Main.tocaErro();
            Platform.runLater(() -> {
                barra_pb.setProgress(0);
            });
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        selecionar();
                    } catch (IOException ex) {
                        System.out.println("ERRO");
                    }
                }
            }
        });
        if (Main.window) {
            Main.stage.setTitle("Acompanhamento [Reprodução] - BoviPlus");
        }

        try {
            cod_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            vaca_col.setCellValueFactory(new PropertyValueFactory<>("nome_mae"));
            boi_col.setCellValueFactory(new PropertyValueFactory<>("nome_pai"));
            datap_col.setCellValueFactory(new PropertyValueFactory<>("previsao"));
            datai_col.setCellValueFactory(new PropertyValueFactory<>("data"));
            listarTodos();
        } catch (Exception ex) {
        }
        if(!Main.dataConsulta.equals("0")){
            campo2_dp.setValue(LocalDate.parse(Main.dataConsulta));
            previsao_rbt.setSelected(true);
            pesquisaData();
            Main.dataConsulta = "0";
        }
    }

    @FXML
    public void mudaPesquisa() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        campo1_tx.setText("");
        listarTodos();
    }

}
