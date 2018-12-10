package Telas;

import ConexaoBD.Conexao;
import DAO.DAOAnimal;
import DAO.DAOProducao;
import Entidades.Animal;
import Entidades.Producao;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConsultaProducaoController implements Initializable {

    @FXML
    private TableView<pc> tabela;

    @FXML
    private TableColumn<pc, Integer> cod_col;

    @FXML
    private TableColumn<pc, String> vaca_col;

    @FXML
    private TableColumn<pc, Double> litro_col;

    @FXML
    private DatePicker inicial_dp;

    @FXML
    private DatePicker final_dp;

    @FXML
    private Button hojeinicial_bt;

    @FXML
    private Button hojefinal_bt;

    @FXML
    private Button meses6_bt;

    @FXML
    private TextField total_tx;

    @FXML
    private Button buscar_bt;

    @FXML
    private Button dias30_bt;

    @FXML
    private Button meses3_bt;

    @FXML
    private Button ano1_bt;

    ObservableList<pc> list = FXCollections.observableArrayList();
    Animal atual;
    DAOAnimal da;
    Producao prod;
    DAOProducao dp;
    double total = 0;

    public class pc {

        public int cod;
        public String nome;
        public double litros;

        public pc(int c, String n, double l) {
            cod = c;
            nome = n;
            litros = l;
        }

        public int getCod() {
            return cod;
        }

        public void setCod(int cod) {
            this.cod = cod;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getLitros() {
            return litros;
        }

        public void setLitros(double litros) {
            this.litros = litros;
        }

    }

    @FXML
    public void hojeInicial() {
        inicial_dp.setValue(LocalDate.now());
    }

    @FXML
    public void hojeFinal() {
        final_dp.setValue(LocalDate.now());
    }

    @FXML
    public void buscar() {
        total = 0;
        if (inicial_dp.getValue().isAfter(final_dp.getValue())) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Atenção!");
            dodsad.setHeaderText("A data final não pode ser menor que a data inicial!");
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            inicial_dp.setValue(final_dp.getValue());
        } else {
            try {
                list.clear();
                Conexao kk = new Conexao();
                kk.abrirConexao();
                String sql = "SELECT p.id_vaca, a.nome, SUM(p.litragem) from producao p inner join animal a on p.id_vaca = a.id_animal where p.data_producao between '" + inicial_dp.getValue().toString() + "' and '" + final_dp.getValue().toString() + "' group by p.id_vaca, a.nome;";
                Statement kh = kk.getConnection().createStatement();
                ResultSet rs = kh.executeQuery(sql);
                while (rs.next()) {
                    pc a = new pc(rs.getInt("id_vaca"), rs.getString("nome"), rs.getDouble("sum"));
                    list.add(a);
                }
                tabela.setItems(null);
                tabela.setItems(list);
                for (pc k : list) {
                    total = total + k.litros;
                }
                total = Math.round(total * 100.0) / 100.0;
                total_tx.setText(Double.toString(total));
            } catch (Exception e) {
                Main.tocaErro();
                Alert ko = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("Ocorreu um erro ao buscar.");
                ko.setContentText(e.getMessage());
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }

    }

    @FXML
    public void dias30() {
        inicial_dp.setValue(LocalDate.now().minusDays(30));
        final_dp.setValue(LocalDate.now());
        buscar();
    }

    @FXML
    public void mes3() {
        inicial_dp.setValue(LocalDate.now().minusMonths(3));
        final_dp.setValue(LocalDate.now());
        buscar();
    }

    @FXML
    public void mes6() {
        inicial_dp.setValue(LocalDate.now().minusMonths(6));
        final_dp.setValue(LocalDate.now());
        buscar();
    }

    @FXML
    public void ano() {
        inicial_dp.setValue(LocalDate.now().minusYears(1));
        final_dp.setValue(LocalDate.now());
        buscar();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Consulta [Produção] - BoviPlus");
        }
        cod_col.setCellValueFactory(new PropertyValueFactory("cod"));
        vaca_col.setCellValueFactory(new PropertyValueFactory("nome"));
        litro_col.setCellValueFactory(new PropertyValueFactory("litros"));
        tabela.setPlaceholder(new Label("Sem registros."));
    }

}
