package Telas;

import ConexaoBD.Conexao;
import DAO.DAOContas_Receber;
import Entidades.Contas_Receber;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConsultaReceberController implements Initializable {

    @FXML
    private AnchorPane consulta_pane;

    @FXML
    private DatePicker inicial_dp;

    @FXML
    private DatePicker final_dp;

    @FXML
    private RadioButton venc_rbt;

    @FXML
    private ToggleGroup data_tipo;

    @FXML
    private RadioButton pag_rbt;

    @FXML
    private TextField minval_tx;

    @FXML
    private TextField maxval_tx;

    @FXML
    private CheckBox pagar_cbox;

    @FXML
    private CheckBox prod_cbox;

    @FXML
    private Button buscar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private TableView<Conta> tabela;

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
    private Button editar_bt;

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
            return Math.round(total * 100) / 100;
        }

        public void setTotal(double total) {
            this.total = total;
        }

    }

    public Stage editar;
    private ObservableList<Conta> list;
    private DAOContas_Receber dc;

    public void editar() throws IOException {

        Conta a = tabela.getSelectionModel().getSelectedItem();
        Main.id_conta_receber = Integer.toString(a.id_contas_receber);
        if (a.isProducao()) {
            editar = new Stage();
            editar.setResizable(false);
            Main.window = false;
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ReceberProducao.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento de Produção [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
            Main.window = true;
        } else {
            editar = new Stage();
            editar.setResizable(false);
            Main.window = false;
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ReceberGeral.fxml"));
            Scene scene = new Scene(fxmlDB);
            editar.getIcons().add(new Image("/Imagens/Iconew.png"));
            editar.setTitle("Lançamento Geral [Financeiro] - BoviPlus");
            editar.setScene(scene);
            editar.show();
            Main.window = true;
        }

    }

    @FXML
    public void geraRelatorio() {
        try {
            Conexao c = new Conexao();
            c.abrirConexao();
            Statement stm = c.getConnection().createStatement();
            ResultSet rs = stm.executeQuery("select * from produtor");
            rs.next();
            String nome = rs.getString("nome");
            String cpf = rs.getString("cpf");
            Document doc = new Document();
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(Main.stage);
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            doc.setPageSize(PageSize.A4);
            com.itextpdf.text.Image im = com.itextpdf.text.Image.getInstance(getClass().getResource("/Imagens/LogoBig.png").toExternalForm());
            double soma = 0;;
            im.scaleToFit(200, 87);
            doc.add(im);
            doc.add(new Paragraph("Produtor: " + nome + "\nCPF: " + cpf + "\n"));
            doc.add(new Paragraph("______________________________________________________________________________\n\n"));
            doc.add(new Paragraph("LISTAGEM DE CONTAS A RECEBER\n"));
            doc.add(new Paragraph(LocalDate.now().toString()));
            doc.add(new Paragraph("______________________________________________________________________________\n\n"));
            for (Conta a : list) {
                doc.add(new Paragraph(a.id_contas_receber + " - R$ " + a.total + " - " + a.data_vencimento + " - " + a.data_recebimento + "\n"));
                soma = soma + a.total;
            }
            doc.add(new Paragraph("\nTotal: R$ "+ soma + "\n"));
            doc.add(new Paragraph("______________________________________________________________________________\n\n"));
            doc.add(new Paragraph("Relatório gerado pelo BoviPlus."));
            doc.close();
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Não foi possivel gerar o relatório!");
            alert2.setContentText(e.getMessage());
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void excluir() {
        try {
            if (tabela.getSelectionModel().getSelectedItem() == null) {
                throw new ArithmeticException();
            }
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Deseja continuar?");
            ko.setHeaderText("Você tem certeza que deseja apagar a conta selecionada?");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    try {

                        DAOContas_Receber dd = new DAOContas_Receber();
                        dd.deletarContas_Receber(tabela.getSelectionModel().getSelectedItem().id_contas_receber);

                        Main.tocaSucesso();
                        Alert as = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imagaeView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Sucesso!");
                        as.setHeaderText("A conta foi apagada.");
                        as.showAndWait().ifPresent(ss -> {
                            if (ss == ButtonType.OK) {
                            }
                        });
                        listar();
                    } catch (Exception e) {
                        Main.tocaErro();
                        Alert as = new Alert(Alert.AlertType.ERROR);
                        ImageView imagaeView = new ImageView(new Image(new File("error.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Atenção!");
                        as.setHeaderText("Ocorreu um erro ao apagar!");
                        as.setContentText(e.getMessage());
                        as.showAndWait().ifPresent(ss -> {
                            if (rr == ButtonType.OK) {
                            }
                        });
                    }

                } else {

                }

            });

        } catch (ArithmeticException e) {

        }
    }

    public void listar() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        try {
            if (inicial_dp.getValue() != null && final_dp.getValue() != null) {
                if (inicial_dp.getValue().isAfter(final_dp.getValue())) {
                    Main.tocaConfirma();
                    Alert ko = new Alert(Alert.AlertType.WARNING);
                    ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("A data inicial não pode ser após a data final...");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                    final_dp.setValue(inicial_dp.getValue());
                }
            }
            list = FXCollections.observableArrayList();
            dc = new DAOContas_Receber();
            List<Contas_Receber> aa = dc.pesquisaCustom(montaSQL());
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
            tabela.setItems(null);
            tabela.setItems(list);
            editar_bt.setDisable(false);
        } catch (Exception e) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Oops!");
            ko.setHeaderText("Ocorreu um erro ao carregar");
            ko.setContentText(e.getMessage());
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        }

    }

    @FXML
    public void limpa() {
        tabela.setItems(null);
        editar_bt.setDisable(true);
        final_dp.setValue(null);
        inicial_dp.setValue(null);
        minval_tx.clear();
        maxval_tx.clear();
        venc_rbt.setSelected(true);
        pagar_cbox.setSelected(false);
        prod_cbox.setSelected(false);

    }

    private String montaSQL() {
        String s = "select * from contas_receber where id_contas_receber > 0";

        if (venc_rbt.isSelected()) {
            if (inicial_dp.getValue() != null) {
                s = strcat(s, " and data_vencimento >= '" + inicial_dp.getValue().toString() + "'");
            }
            if (final_dp.getValue() != null) {
                s = strcat(s, " and data_vencimento <= '" + final_dp.getValue().toString() + "'");
            }
        } else if (pag_rbt.isSelected()) {
            if (inicial_dp.getValue() != null) {
                s = strcat(s, " and data_recebimento >= '" + inicial_dp.getValue().toString() + "'");
            }
            if (final_dp.getValue() != null) {
                s = strcat(s, " and data_recebimento <= '" + final_dp.getValue().toString() + "'");
            }
        }

        if (!minval_tx.getText().trim().isEmpty()) {
            s = strcat(s, " and valor*qtd >=" + Double.parseDouble(minval_tx.getText()));
        }
        if (!maxval_tx.getText().trim().isEmpty()) {
            s = strcat(s, " and valor*qtd <=" + Double.parseDouble(maxval_tx.getText()));
        }

        if (pagar_cbox.isSelected()) {
            s = strcat(s, " and data_recebimento is not null");
        }

        if (prod_cbox.isSelected()) {
            s = strcat(s, " and producao = true");
        }

        return strcat(s, ";");
    }

    private String strcat(String a, String b) {
        return a + b;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        editar();
                    } catch (IOException ex) {
                        System.out.println("ERRO");
                    }
                }
            }
        });
        id_col.setCellValueFactory(new PropertyValueFactory<>("id_contas_receber"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        pagto_col.setCellValueFactory(new PropertyValueFactory<>("data_recebimento"));
        qtd_col.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        venci_col.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
        vtot_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        vuni_col.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tabela.setPlaceholder(new Label("Nenhum conta a receber encontrada com os filtros selecionados."));

        if (!Main.dataConsulta.equals("0")) {
            inicial_dp.setValue(LocalDate.parse(Main.dataConsulta));
            final_dp.setValue(LocalDate.parse(Main.dataConsulta));
            try {
                listar();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            Main.dataConsulta = "0";
        }

    }

}
