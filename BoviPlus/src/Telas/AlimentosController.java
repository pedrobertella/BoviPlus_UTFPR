package Telas;

import ConexaoBD.Conexao;
import DAO.DAOAlimento;
import DAO.DAOEstoque;
import Entidades.Alimento;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AlimentosController implements Initializable {

    @FXML
    private Button cad_bt;

    @FXML
    private TextField nome_tx;

    @FXML
    private TableView<Alim> tabela;

    @FXML
    private TableColumn<Alim, Integer> id_col;

    @FXML
    private TableColumn<Alim, String> nome_col;

    @FXML
    private TableColumn<Alim, String> tipo_col;

    @FXML
    private TableColumn<Alim, String> unid_col;

    @FXML
    private TableColumn<Alim, Double> est_col;

    @FXML
    private Button buscar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private RadioButton todos_rbt;

    @FXML
    private ToggleGroup tipo;

    @FXML
    private RadioButton cereal_rbt;

    @FXML
    private RadioButton farelo_rbt;

    @FXML
    private RadioButton racao_rbt;

    @FXML
    private RadioButton tuberculo_rbt;

    @FXML
    private RadioButton silagem_rbt;

    @FXML
    private RadioButton forragem_rbt;

    @FXML
    private RadioButton outros_rbt;

    public class Alim {

        public int id;
        public String nome;
        public String tipo;
        public String unid;
        public double estoque;

        ;

        public Alim(int id, String nome, String tipo, String unid, double estoque) {
            this.id = id;
            this.nome = nome;
            this.tipo = tipo;
            this.unid = unid;
            this.estoque = estoque;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getTipo() {
            return tipo;
        }

        public String getUnid() {
            return unid;
        }

        public double getEstoque() {
            return estoque;
        }

    }

    ObservableList<Alim> list;
    public Stage c;

    @FXML
    public void cadastro() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("CadastroAlimento.fxml"));
        Scene scene = new Scene(fxmlDB);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Cadastro de Alimento [Alimentação] - BoviPlus");
        c.show();
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
            doc.add(new Paragraph("LISTAGEM DE ALIMENTOS\n"));
            doc.add(new Paragraph(LocalDate.now().toString()));
            doc.add(new Paragraph("______________________________________________________________________________\n\n"));
            for (Alim a : list) {
                doc.add(new Paragraph(a.id + " - " + a.nome + " - " + a.tipo + " - " + a.unid + " - ESTOQUE: " + a.estoque +"\n"));
            }
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

    private String montaSQL() {
        String s = "select * from alimento where id_alimento > 0";

        if (!nome_tx.getText().trim().isEmpty()) {
            s = strcat(s, " and nome ilike '%" + nome_tx.getText() + "%'");
        }

        if (cereal_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 1");
        } else if (farelo_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 2");
        } else if (racao_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 3");
        } else if (tuberculo_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 4");
        } else if (silagem_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 5");
        } else if (forragem_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 6");
        } else if (outros_rbt.isSelected()) {
            s = strcat(s, " and id_tipo_alimento = 7");
        }

        return strcat(s, ";");
    }

    private String strcat(String a, String b) {
        return a + b;
    }

    private String selectTipo(int id) {
        if (id == 1) {
            return "Cereal";
        } else if (id == 2) {
            return "Farelo";
        } else if (id == 3) {
            return "Ração";
        } else if (id == 4) {
            return "Tubérculo";
        } else if (id == 5) {
            return "Silagem";
        } else if (id == 6) {
            return "Forragem";
        } else if (id == 7) {
            return "Outros";
        }
        return null;
    }

    private String selectUnid(int id) {
        if (id == 1) {
            return "KG";
        } else if (id == 2) {
            return "Fardo";
        } else if (id == 3) {
            return "Saca";
        } else if (id == 4) {
            return "Outros";
        }
        return null;
    }

    @FXML
    public void buscar() {
        try {
            DAOAlimento da = new DAOAlimento();
            List<Alimento> lista = da.pesquisaCustom(montaSQL());
            list = FXCollections.observableArrayList();
            DAOEstoque de = new DAOEstoque();
            Alim b;
            for (Alimento a : lista) {
                b = new Alim(a.getId(), a.getNome(), selectTipo(a.getTipo()), selectUnid(a.getUnid()), de.somaPorAlimento(a.getId()));
                list.add(b);
            }
            tabela.setItems(null);
            tabela.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            ko.setHeaderText("Você tem certeza que deseja excluir o alimento selecionado?");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    try {
                        DAOAlimento da = new DAOAlimento();
                        da.deletarAlimento(tabela.getSelectionModel().getSelectedItem().id);
                        Main.tocaSucesso();
                        Alert as = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imagaeView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Sucesso!");
                        as.setHeaderText("O alimento foi apagado");
                        as.showAndWait().ifPresent(ss -> {
                            if (ss == ButtonType.OK) {
                            }
                        });
                        buscar();
                    } catch (Exception e) {
                        Main.tocaErro();
                        Alert as = new Alert(Alert.AlertType.ERROR);
                        ImageView imagaeView = new ImageView(new Image(new File("error.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Atenção!");
                        as.setHeaderText("Ocorreu um erro ao excluir!");
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

    @FXML
    public void editar() {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("CadastroAlimento.fxml"));
            Parent root = fxmlDB.load();
            CadastroAlimentoController cont = fxmlDB.getController();
            Scene scene = new Scene(root);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Cadastro de Alimento [Alimentação] - BoviPlus");
            if (tabela.getSelectionModel().getSelectedItem() == null) {
                throw new ArithmeticException();
            }
            cont.carregar(tabela.getSelectionModel().getSelectedItem().id);
            c.show();

        } catch (Exception e) {

        }
    }

    @FXML
    public void estoque() throws IOException {
        if (tabela.getSelectionModel().getSelectedItem() != null) {
            c = new Stage();
            c.setResizable(false);
            FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("CadastroEstoque.fxml"));
            Parent root = fxmlDB.load();
            CadastroEstoqueController cont = fxmlDB.getController();
            Scene scene = new Scene(root);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Lançamento de Estoque [Alimentação] - BoviPlus");
            cont.carregar(tabela.getSelectionModel().getSelectedItem().id);
            c.show();
        }

    }

    @FXML
    public void limpar() {
        tabela.setItems(null);
        nome_tx.clear();
        todos_rbt.setSelected(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Alimentos [Alimentação] - BoviPlus");
        }
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    editar();
                }
            }
        });
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome_col.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipo_col.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        unid_col.setCellValueFactory(new PropertyValueFactory<>("unid"));
        est_col.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        tabela.setPlaceholder(new Label("Nenhum alimento encontrado com os filtros selecionados."));
        buscar();
    }

}
