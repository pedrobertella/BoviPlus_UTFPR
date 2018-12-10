package Telas;

import DAO.DAOContas_Receber;
import Entidades.Contas_Receber;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LdiaReceberController implements Initializable {

    @FXML
    private AnchorPane pane1;

    @FXML
    private TableView<Conta> table;

    @FXML
    private TableColumn<Conta, Integer> id_col;

    @FXML
    private TableColumn<Conta, Double> qtd_col;

    @FXML
    private TableColumn<Conta, Double> vuni_col;

    @FXML
    private TableColumn<Conta, Double> vtot_col;

    @FXML
    private TableColumn<Conta, String> venci_col;

    @FXML
    private TableColumn<Conta, String> pagto_col;

    @FXML
    private TableColumn<Conta, String> desc_col;

    @FXML
    private Button edit_bt;

    public class Conta {

        public double total;
        public int id_contas_receber;
        public double valor;
        public String data_recebimento;
        public String data_vencimento;
        public double qtd;
        public String descricao;
        public boolean producao;

        public int getId_contas_receber() {
            return id_contas_receber;
        }

        public void setId_contas_receber(int id_contas_receber) {
            this.id_contas_receber = id_contas_receber;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public String getData_recebimento() {
            return data_recebimento;
        }

        public void setData_recebimento(String data_recebimento) {
            this.data_recebimento = data_recebimento;
        }

        public String getData_vencimento() {
            return data_vencimento;
        }

        public void setData_vencimento(String data_vencimento) {
            this.data_vencimento = data_vencimento;
        }

        public double getQtd() {
            return qtd;
        }

        public void setQtd(double qtd) {
            this.qtd = qtd;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public boolean isProducao() {
            return producao;
        }

        public void setProducao(boolean producao) {
            this.producao = producao;
        }

        public double getTotal() {
            return Math.round(total*100)/100;
        }

        public void setTotal(double total) {
            this.total = total;
        }

    }

    private ObservableList<Conta> list;
    private Stage editar;
    private DAOContas_Receber dc;

    public void listar() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        list = FXCollections.observableArrayList();
        dc = new DAOContas_Receber();
        List<Contas_Receber> aa = dc.vencemHoje(LocalDate.now().toString());
        Conta u;
        if (!aa.isEmpty()) {
            for (Contas_Receber i : aa) {
                u = new Conta();
                u.setData_recebimento(i.getData_recebimento());
                u.setData_vencimento(i.getData_vencimento());
                u.setDescricao(i.getDescricao());
                u.setId_contas_receber(i.getId_contas_receber());
                u.setProducao(i.isProducao());
                u.setQtd(i.getQtd());
                u.setValor(i.getValor());
                u.setTotal(i.getQtd() * i.getValor());
                list.add(u);
            }

        }
        table.setItems(list);
    }

    @FXML
    public void editar() throws IOException {

        Conta a = table.getSelectionModel().getSelectedItem();
        Main.id_conta_receber = Integer.toString(a.id_contas_receber);
        if (a.isProducao()) {
            editar = new Stage();
            editar.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ReceberProducao.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento de Produção [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
        } else {
            editar = new Stage();
            editar.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ReceberGeral.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento Geral [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_col.setCellValueFactory(new PropertyValueFactory<>("id_contas_receber"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        pagto_col.setCellValueFactory(new PropertyValueFactory<>("data_recebimento"));
        qtd_col.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        venci_col.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
        vtot_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        vuni_col.setCellValueFactory(new PropertyValueFactory<>("valor"));
        table.setPlaceholder(new Label("Nenhum vencimento para hoje."));

        try {
            listar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
