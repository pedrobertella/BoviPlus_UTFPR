package Telas;

import DAO.DAOContas_pagar;
import DAO.DAOCusto_alimento;
import DAO.DAOCusto_geral;
import DAO.DAOCusto_reproducao;
import DAO.DAOCusto_vacina;
import Entidades.Contas_pagar;
import Entidades.Custo_alimento;
import Entidades.Custo_geral;
import Entidades.Custo_reproducao;
import Entidades.Custo_vacina;
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

public class LdiaPagarController implements Initializable {

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
        public int id_contas_pagar;
        public double valor;
        public String data_pagamento;
        public String data_vencimento;
        public double qtd;
        public String descricao;
        public int origem;

        public int getId_contas_pagar() {
            return id_contas_pagar;
        }

        public void setId_contas_pagar(int id_contas_pagar) {
            this.id_contas_pagar = id_contas_pagar;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public String getData_pagamento() {
            return data_pagamento;
        }

        public void setData_pagamento(String data_pagamento) {
            this.data_pagamento = data_pagamento;
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

        public int getOrigem() {
            return origem;
        }

        public void setOrigem(int origem) {
            this.origem = origem;
        }

        public double getTotal() {
            return Math.round(total * 100) / 100;
        }

        public void setTotal(double total) {
            this.total = total;
        }

    }

    private ObservableList<Conta> list;
    private Stage editar;
    private DAOContas_pagar dp;
    private DAOCusto_geral dg;
    private DAOCusto_alimento da;
    private DAOCusto_reproducao dr;
    private DAOCusto_vacina dv;

    public void listar() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        list = FXCollections.observableArrayList();
        dp = new DAOContas_pagar();
        List<Contas_pagar> aa = dp.vencemHoje(LocalDate.now().toString());
        Conta u;
        dg = new DAOCusto_geral();
        dr = new DAOCusto_reproducao();
        dv = new DAOCusto_vacina();
        da = new DAOCusto_alimento();
        Custo_alimento ca;
        Custo_vacina cv;
        Custo_geral cg;
        Custo_reproducao cr;

        if (!aa.isEmpty()) {
            for (Contas_pagar i : aa) {
                u = new Conta();
                u.setData_pagamento(i.getData_pagamento());
                u.setData_vencimento(i.getData_vencimento());
                u.setId_contas_pagar(i.getId_contas_pagar());
                u.setOrigem(i.getOrigem());
                u.setTotal(i.getValor());

                if (u.getOrigem() == 0) {
                    cg = dg.consultaCusto_geral(u.getId_contas_pagar());
                    u.setQtd(cg.getQtd());
                    u.setValor(cg.getValor());
                    u.setDescricao(cg.getDescricao());
                } else if (u.getOrigem() == 1) {
                    cr = dr.consultaCusto(u.getId_contas_pagar());
                    u.setQtd(cr.getQtd());
                    u.setValor(cr.getValor());
                    u.setDescricao("INSEMINAÇÃO: " + cr.getId_gestacao());
                } else if (u.getOrigem() == 2) {
                    ca = da.consultaCusto_alimento(u.getId_contas_pagar());
                    u.setQtd(ca.getQtd());
                    u.setValor(ca.getValor());
                    u.setDescricao("ALIMENTO: " + ca.getId_alimento());
                } else if (u.getOrigem() == 3) {
                    cv = dv.consultaCusto_vacina(u.getId_contas_pagar());
                    u.setQtd(cv.getQtd());
                    u.setValor(cv.getValor());
                    u.setDescricao("VACINAÇÃO: " + cv.getId_vacina());
                }
                list.add(u);
            }

        }
        table.setItems(list);
    }

    @FXML
    public void editar() throws IOException {

        Conta a = table.getSelectionModel().getSelectedItem();
        Main.id_conta_pagar = Integer.toString(a.getId_contas_pagar());
        if (a.getOrigem() == 0) {
            editar = new Stage();
            editar.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("PagarGeral.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento Geral [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
        } else if (a.getOrigem() == 1) {
            editar = new Stage();
            editar.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("PagarReproducao.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento de Reprodução [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
        } else if (a.getOrigem() == 2) {
            editar = new Stage();
            editar.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("PagarAlimentacao.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento de Alimento [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
        } else if (a.getOrigem() == 3) {
            editar = new Stage();
            editar.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("PagarSaude.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento de Saúde [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            id_col.setCellValueFactory(new PropertyValueFactory<>("id_contas_pagar"));
            desc_col.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            qtd_col.setCellValueFactory(new PropertyValueFactory<>("qtd"));
            venci_col.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
            vtot_col.setCellValueFactory(new PropertyValueFactory<>("total"));
            vuni_col.setCellValueFactory(new PropertyValueFactory<>("valor"));
            pagto_col.setCellValueFactory(new PropertyValueFactory<>("data_pagamento"));
            table.setPlaceholder(new Label("Nenhum vencimento para hoje."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            listar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
