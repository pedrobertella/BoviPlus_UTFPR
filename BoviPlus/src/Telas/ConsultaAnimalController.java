package Telas;

import ConexaoBD.Conexao;
import DAO.DAOAnimal;
import DAO.DAORaca;
import DAO.DAOTipo;
import Entidades.Animal;
import Entidades.Raca;
import Entidades.Tipo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConsultaAnimalController implements Initializable {

    DAOAnimal consulta;
    public Stage cadastro;

    public ConsultaAnimalController() {
        try {
            consulta = new DAOAnimal();
        } catch (Exception e) {

        }
    }

    public class Animal1 {

        private int id_animal;
        private String nome;
        private String nascimento;
        private double peso;
        private String descricao;
        private int id_mae;
        private int id_pai;
        private String tipo;
        private String raca;
        private boolean lactacao;

        public Animal1(int id_animal, String nome, String nascimento, double peso, String descricao, int id_mae, int id_pai, String tipo, String raca, boolean lactacao) {
            this.id_animal = id_animal;
            this.nome = nome;
            this.nascimento = nascimento;
            this.peso = peso;
            this.descricao = descricao;
            this.id_mae = id_mae;
            this.id_pai = id_pai;
            this.tipo = tipo;
            this.raca = raca;
            this.lactacao = lactacao;
        }

        public int getId_animal() {
            return id_animal;
        }

        public String getNome() {
            return nome;
        }

        public String getNascimento() {
            return nascimento;
        }

        public double getPeso() {
            return peso;
        }

        public String getDescricao() {
            return descricao;
        }

        public int getId_mae() {
            return id_mae;
        }

        public int getId_pai() {
            return id_pai;
        }

        public String getTipo() {
            return tipo;
        }

        public String getRaca() {
            return raca;
        }

        public boolean isLactacao() {
            return lactacao;
        }

    }

    @FXML
    private AnchorPane panejanela;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button todos_bt;

    @FXML
    private Button alterar_bt;

    @FXML
    private TextField tx_pesquisa;

    @FXML
    private RadioButton nome_rbt;

    @FXML
    private ToggleGroup pesquisatipo;

    @FXML
    private RadioButton descricao_rbt;

    @FXML
    private RadioButton vaca_rbt;

    @FXML
    private ToggleGroup tipoanimal;

    @FXML
    private RadioButton boi_rbt;

    @FXML
    private RadioButton bezerro_rbt;

    @FXML
    private RadioButton novilha_rbt;

    @FXML
    private TableView<Animal1> tabela;

    @FXML
    private TableColumn<Animal1, Integer> tc_cod;

    @FXML
    private TableColumn<Animal1, String> tc_nome;

    @FXML
    private TableColumn<Animal1, String> tc_tipo;

    @FXML
    private TableColumn<Animal1, String> tc_nasc;

    @FXML
    private TableColumn<Animal1, Double> tc_peso;

    @FXML
    private TableColumn<Animal1, String> tc_raca;

    @FXML
    private TableColumn<Animal1, String> tc_desc;

    private ObservableList<Animal1> animais = FXCollections.observableArrayList();

    private static Tipo tipo;
    private static Raca raca;
    public CadastroAnimalController cac;
    public RegistroInseminacaoController ric;

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

            im.scaleToFit(200, 87);
            doc.add(im);
            doc.add(new Paragraph("Produtor: " + nome + "\nCPF: " + cpf + "\n"));
            doc.add(new Paragraph("______________________________________________________________________________\n\n"));
            doc.add(new Paragraph("LISTAGEM DE ANIMAIS\n"));
            doc.add(new Paragraph(LocalDate.now().toString()));
            doc.add(new Paragraph("______________________________________________________________________________\n\n"));
            Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnSim = new ButtonType("Faixa Etária");
            ButtonType btnNao = new ButtonType("Seleção Atual");
            ImageView iageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            dialogoExe.setGraphic(iageView);
            ((Stage) dialogoExe.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dialogoExe.setTitle("Relatório de Animais");
            dialogoExe.setHeaderText("Informe qual o relatório a ser emitido.");
            dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
            dialogoExe.showAndWait().ifPresent(b -> {
                if (b == btnSim) {
                    try {
                        DAOAnimal dd = new DAOAnimal();
                        List<Animal> kkk = dd.getList("select * from animal where id_animal > 0;");
                        kkk.sort((Animal m1, Animal m2) -> {
                            if (LocalDate.parse(m1.getNascimento()).equals(LocalDate.parse(m2.getNascimento()))) {
                                return 0;
                            } else if (LocalDate.parse(m1.getNascimento()).isAfter(LocalDate.parse(m2.getNascimento()))) {
                                return 1;
                            } else {
                                return -1;
                            }
                        });
                        String tipo, raca;
                        List<Animal1> anii = new ArrayList<>();
                        for (Animal u : kkk) {
                            if (u.getId_tipo() == 2) {
                                tipo = "Boi";
                            } else if (u.getId_tipo() == 3) {
                                tipo = "Bezerro";
                            } else if (u.getId_tipo() == 4) {
                                tipo = "Novilha";
                            } else {
                                tipo = "Vaca";
                            }

                            switch (u.getId_raca()) {
                                case 1:
                                    raca = "Jersey";
                                    break;
                                case 2:
                                    raca = "Holandesa";
                                    break;
                                case 3:
                                    raca = "Pardo-Suiço";
                                    break;
                                case 4:
                                    raca = "Girolando";
                                    break;
                                case 5:
                                    raca = "Jersey-Holanda";
                                    break;
                                default:
                                    raca = "Jersey";
                                    break;
                            }
                            anii.add(new Animal1(u.getId_animal(), u.getNome(), u.getNascimento(), u.getPeso(), u.getDescricao(), u.getId_mae(), u.getId_pai(), tipo, raca, u.isLactacao()));
                        }
                        for (Animal1 a : anii) {
                            try {
                                doc.add(new Paragraph(a.id_animal + " - " + a.nome + " - " + a.tipo + " - " + a.raca + " - " + a.nascimento + " - " + a.peso + "\n"));
                            } catch (DocumentException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (b == btnNao) {
                    for (Animal1 a : animais) {
                        try {
                            doc.add(new Paragraph(a.id_animal + " - " + a.nome + " - " + a.tipo + " - " + a.raca + " - " + a.nascimento + " - " + a.peso + "\n"));
                        } catch (DocumentException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            });

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
    private void preencherTabela(KeyEvent evt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            List<Animal> af = busca();
            animais = FXCollections.observableArrayList();
            Animal1 b;
            String tipo, raca;
            for (Animal a : af) {
                if (a.getId_tipo() == 2) {
                    tipo = "Boi";
                } else if (a.getId_tipo() == 3) {
                    tipo = "Bezerro";
                } else if (a.getId_tipo() == 4) {
                    tipo = "Novilha";
                } else {
                    tipo = "Vaca";
                }

                switch (a.getId_raca()) {
                    case 1:
                        raca = "Jersey";
                        break;
                    case 2:
                        raca = "Holandesa";
                        break;
                    case 3:
                        raca = "Pardo-Suiço";
                        break;
                    case 4:
                        raca = "Girolando";
                        break;
                    case 5:
                        raca = "Jersey-Holanda";
                        break;
                    default:
                        raca = "Jersey";
                        break;
                }
                b = new Animal1(a.getId_animal(), a.getNome(), a.getNascimento(), a.getPeso(), a.getDescricao(), a.getId_mae(), a.getId_pai(), tipo, raca, a.isLactacao());
                animais.add(b);
            }
            tabela.setItems(animais);
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

    private String prepararStatement() {
        String sql = "SELECT * FROM ANIMAL WHERE ";

        if (nome_rbt.isSelected()) {
            sql = strcat(sql, "NOME ILIKE '%" + tx_pesquisa.getText() + "%' AND ");
        } else {
            sql = strcat(sql, "DESCRICAO ILIKE '%" + tx_pesquisa.getText() + "%' AND ");
        }

        if (vaca_rbt.isSelected()) {
            sql = strcat(sql, "id_tipo = 1 AND ");
        } else if (boi_rbt.isSelected()) {
            sql = strcat(sql, "id_tipo = 2 AND ");
        } else if (bezerro_rbt.isSelected()) {
            sql = strcat(sql, "id_tipo = 3 AND ");
        } else if (novilha_rbt.isSelected()) {
            sql = strcat(sql, "id_tipo = 4 AND ");
        }

        sql = strcat(sql, "id_animal > 0;");
        return sql;
    }

    private String strcat(String a, String b) {
        return a + b;
    }

    private static void selecionaTipo(int id_tipo) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAOTipo da = new DAOTipo();
        tipo = da.consultar_tipo(id_tipo);
    }

    private static void selecionaRaca(int id_raca) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAORaca da = new DAORaca();
        raca = da.consultar_raca(id_raca);
    }

    private List<Animal> busca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAOAnimal dao = new DAOAnimal();
        List<Animal> af = dao.getList(prepararStatement());

        return af;
    }

    @FXML
    public void initTable() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        tc_cod.setCellValueFactory(new PropertyValueFactory("id_animal"));
        tc_nome.setCellValueFactory(new PropertyValueFactory("nome"));
        tc_nasc.setCellValueFactory(new PropertyValueFactory("nascimento"));
        tc_peso.setCellValueFactory(new PropertyValueFactory("peso"));
        tc_desc.setCellValueFactory(new PropertyValueFactory("descricao"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        tc_raca.setCellValueFactory(new PropertyValueFactory("raca"));

        List<Animal> af = busca();
        animais = FXCollections.observableArrayList();
        Animal1 b;
        String tipo;
        for (Animal a : af) {
            if (a.getId_tipo() == 2) {
                tipo = "Boi";
            } else if (a.getId_tipo() == 3) {
                tipo = "Bezerro";
            } else if (a.getId_tipo() == 4) {
                tipo = "Novilha";
            } else {
                tipo = "Vaca";
            }
            selecionaRaca(a.getId_raca());
            b = new Animal1(a.getId_animal(), a.getNome(), a.getNascimento(), a.getPeso(), a.getDescricao(), a.getId_mae(), a.getId_pai(), tipo, raca.getNome(), a.isLactacao());
            animais.add(b);
        }
        tabela.setItems(animais);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        alterar();
                    } catch (IOException ex) {
                        System.out.println("ERRO");
                    }
                }
            }
        });
        if (Main.window) {
            Main.stage.setTitle("Consulta [Animal] - BoviPlus");
        } else {
            Platform.runLater(() -> {
                Stage u = (Stage) tabela.getScene().getWindow();
                u.setOnCloseRequest(b -> {
                    try {
                        if (cac != null) {
                            Animal1 j = tabela.getSelectionModel().getSelectedItem();
                            if (j.getTipo().equals("Vaca")) {
                                cac.setMae(j.getId_animal());
                            } else if (j.getTipo().equals("Boi")) {
                                cac.setPai(j.getId_animal());
                            }
                        } else if (ric != null) {
                            Animal1 j = tabela.getSelectionModel().getSelectedItem();
                            if (j.getTipo().equals("Vaca")) {
                                ric.setMae(j.getId_animal());
                            } else if (j.getTipo().equals("Boi")) {
                                ric.setPai(j.getId_animal());
                            }
                        }

                    } catch (Exception e) {

                    }

                });
            });
        }
        pesquisatipo.selectToggle(nome_rbt);
        if (Main.consultaAnimal == 1) {
            vaca_rbt.setSelected(true);
            Main.consultaAnimal = 0;
        } else if (Main.consultaAnimal == 2) {
            boi_rbt.setSelected(true);
            Main.consultaAnimal = 0;
        }

        try {
            initTable();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    public void listaTodos() {
        try {
            tx_pesquisa.clear();
            initTable();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void limpaTabela() {
        nome_rbt.setSelected(true);
        tx_pesquisa.setText(null);
        tipoanimal.selectToggle(null);
        tabela.setItems(null);
    }

    @FXML
    public void alterar() throws IOException {
        try {
            Animal1 bb = tabela.getSelectionModel().getSelectedItem();
            Main.id_animal = Integer.toString(bb.getId_animal());
            cadastro = new Stage();
            Main.window = false;
            cadastro.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("CadastroAnimal.fxml"));
            Scene scene = new Scene(fxmlDB);
            cadastro.getIcons().add(new Image("/Imagens/Iconew.png"));
            cadastro.setScene(scene);
            cadastro.setTitle("Cadastro [Animal] - BoviPlus");
            cadastro.show();
            Main.window = true;
        } catch (Exception u) {

        }

    }

    @FXML
    public void mudaPesquisa() {
        tx_pesquisa.setText("");
        listaTodos();
    }

}
