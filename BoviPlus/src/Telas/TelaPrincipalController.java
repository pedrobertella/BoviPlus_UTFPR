package Telas;

import DAO.DAOAlim_Replica;
import DAO.DAOAlimentacao;
import DAO.DAOEstoque;
import DAO.DAOGasto_diario;
import Entidades.Alim_Replica;
import Entidades.Alimentacao;
import Entidades.Estoque;
import Entidades.Evento;
import Entidades.Gasto_diario;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TelaPrincipalController implements Initializable {

    public static Stage Sobrestage = new Stage();
    public static Stage BDConect = new Stage();

     @FXML
    private BorderPane mainpane;

    @FXML
    private Pane toppane;

    @FXML
    private MenuBar barrademenu;

    @FXML
    private Menu menu_boviplus;

    @FXML
    private MenuItem mitem_inicio;

    @FXML
    private MenuItem mitem_produtor;

    @FXML
    private MenuItem mitem_usuario;

    @FXML
    private MenuItem mitem_bd;

    @FXML
    private MenuItem preferencias;

    @FXML
    private MenuItem mitem_sair;

    @FXML
    private MenuItem mitem_fechar;

    @FXML
    private Menu menu_animal;

    @FXML
    private MenuItem mitem_cadastro_animal;

    @FXML
    private MenuItem mitem_consulta_animal;

    @FXML
    private Menu menu_saude;

    @FXML
    private MenuItem mitem_regvacina;

    @FXML
    private MenuItem mitem_regdoença;

    @FXML
    private MenuItem mitem_cadastrodoenca;

    @FXML
    private MenuItem mitem_cadastroexame;

    @FXML
    private Menu menu_financeiro;

    @FXML
    private MenuItem mitem_contasreceber;

    @FXML
    private MenuItem mitem_contaspagar;

    @FXML
    private MenuItem mitem_analitico_financeiro;

    @FXML
    private Menu menu_producao;

    @FXML
    private MenuItem mitem_registrosimplificado;

    @FXML
    private MenuItem mitem_cadastro_producao;

    @FXML
    private MenuItem mitem_cadrapido;

    @FXML
    private MenuItem mitem_consulta_producao;

    @FXML
    private MenuItem mitem_rendimento;

    @FXML
    private MenuItem mitem_rendimento_grupo;

    @FXML
    private MenuItem mitem_analitico_producao;

    @FXML
    private Menu menu_reproducao;

    @FXML
    private MenuItem mitem_registro_inseminacao;

    @FXML
    private MenuItem mitem_acompanhamento_reproducao;

    @FXML
    private Menu menu_alimentacao;

    @FXML
    private MenuItem mitem_cadastro_alimentos;

    @FXML
    private MenuItem mitem_analitico_alimentacao;

    @FXML
    private MenuItem mitem_consulta_alimentacao;

    @FXML
    private Menu menu_ajuda;

    @FXML
    private MenuItem mitem_manual;

    @FXML
    private MenuItem mitem_sobre;

    @FXML
    private AnchorPane centerpane;

    @FXML
    private AnchorPane leftpane;

    @FXML
    private ImageView vaca_img;

    @FXML
    private ImageView prod_img;

    @FXML
    private ImageView insem_img;

    @FXML
    private ImageView alim_img;

    @FXML
    private ImageView conta_img;

    @FXML
    private Label user_lb;

    @FXML
    public void manualBoviplus() throws IOException {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        String fileDir = workingDir + "/Manual/ManualBoviPlus.html";
        File htmlFile = new File(fileDir);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    protected void Fechar() {
        Main.tocaConfirma();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
        alert.setGraphic(imageView);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
        alert.setTitle("Atenção!");
        alert.setHeaderText("Você tem certeza que deseja encerrar o BoviPlus?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                Platform.exit();
            } else {

            }
        });
    }

    @FXML
    public void analiticoFinanceiro() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "AnaliticoFinanceiro.fxml";
        TabPane aa = FXMLLoader.load(getClass().getResource("AnaliticoFinanceiro.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void contasPagar() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ContasPagar.fxml";
        TabPane aa = FXMLLoader.load(getClass().getResource("ContasPagar.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void cadastroVacina() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroMedicacao.fxml";
        TabPane aa = FXMLLoader.load(getClass().getResource("CadastroMedicacao.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void racas() throws IOException {
        Stage racasss = new Stage();
        racasss.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("Racas.fxml"));
        Scene scene = new Scene(fxmlDB);
        racasss.getIcons().add(new Image("/Imagens/Iconew.png"));
        racasss.setScene(scene);
        racasss.setTitle("Raças [Animais] - BoviPlus");
        racasss.show();
    }

    @FXML
    public void contasReceber() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ContasReceber.fxml";
        TabPane aa = FXMLLoader.load(getClass().getResource("ContasReceber.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    protected void fazerLogoff() {
        Main.tocaConfirma();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
        alert.setGraphic(imageView);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
        alert.setTitle("Atenção!");
        alert.setHeaderText("Você tem certeza que deseja encerrar sua sessão do BoviPlus?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                try {
                    Parent fxmlTelaPrincipal = FXMLLoader.load(getClass().getResource("Bovi+.fxml"));
                    Scene TelaPrincipalScene = new Scene(fxmlTelaPrincipal);
                    Main.stage.setScene(TelaPrincipalScene);
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

            }
        });
    }

    @FXML
    public void producaoRapido() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ProducaoRapido.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("ProducaoRapido.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void analiticoProducao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "AnaliticoProducao.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("AnaliticoProducao.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void rendimentoAnimal() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "RendimentoIndividual.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("RendimentoIndividual.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void rendimentoGrupo() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "RendimentoGrupo.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("RendimentoGrupo.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    public void consultaProducao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ConsultaProducao.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("ConsultaProducao.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    protected void consultaAnimal() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ConsultaAnimal.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("ConsultaAnimal.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    protected void cadastroProducao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroProducao.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("CadastroProducao.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    protected void registroSimplificado() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ProducaoSimplificado.fxml";
        AnchorPane aa = FXMLLoader.load(getClass().getResource("ProducaoSimplificado.fxml"));
        mainpane.setCenter(aa);
    }

    @FXML
    protected void mostrarBDConect() throws IOException {

        BDConect.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("BDConect.fxml"));
        Scene scene = new Scene(fxmlDB);
        BDConect.getIcons().add(new Image("/Imagens/dbicon.png"));
        BDConect.setScene(scene);
        BDConect.setTitle("Banco de Dados [Sistema] - BoviPlus");
        BDConect.show();
    }

    protected static void fecharBDConect() {
        BDConect.close();
    }

    @FXML
    protected void mostrarSobre() throws IOException {

        Sobrestage.setResizable(false);
        Parent fxmlSobre = FXMLLoader.load(getClass().getResource("Sobre.fxml"));
        Scene scene = new Scene(fxmlSobre);
        Sobrestage.getIcons().add(new Image("/Imagens/Iconew.png"));
        Sobrestage.setScene(scene);
        Sobrestage.setTitle("Sobre [Sistema] - BoviPlus");
        Sobrestage.show();

    }

    @FXML
    protected void cadastroProdutor() throws IOException {
        Stage a = new Stage();
        a.setResizable(false);
        Parent fxmlSobre = FXMLLoader.load(getClass().getResource("CadastroProdutor.fxml"));
        Scene scene = new Scene(fxmlSobre);
        a.getIcons().add(new Image("/Imagens/Iconew.png"));
        a.setScene(scene);
        a.setTitle("Produtor [Sistema] - BoviPlus");
        a.show();

    }

    protected static void fecharSobre() {
        Sobrestage.close();
    }

    @FXML
    public void preferenciasSistema() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "PreferenciasSistema.fxml";
        AnchorPane aca = FXMLLoader.load(getClass().getResource("PreferenciasSistema.fxml"));
        mainpane.setCenter(aca);
    }

    @FXML
    protected void cadastroAnimal() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroAnimal.fxml";
        AnchorPane aca = FXMLLoader.load(getClass().getResource("CadastroAnimal.fxml"));
        mainpane.setCenter(aca);
    }

    @FXML
    protected void cadastroUsuario() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ListagemUsuario.fxml";
        AnchorPane au = FXMLLoader.load(getClass().getResource("ListagemUsuario.fxml"));
        mainpane.setCenter(au);
    }

    @FXML
    protected void telaInicial() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "TelaInicio.fxml";
        AnchorPane ai = FXMLLoader.load(getClass().getResource("TelaInicio.fxml"));
        mainpane.setCenter(ai);
    }

    @FXML
    protected void voltar() throws IOException {
        String temp = Main.telaAtual;
        Main.telaAtual = Main.telaAnterior;
        if (Main.telaAnterior.equals("ContasPagar.fxml") || Main.telaAnterior.equals("ContasReceber.fxml") || Main.telaAnterior.equals("AnaliticoFinanceiro.fxml") || Main.telaAnterior.equals("CadastroMedicacao.fxml")) {
            TabPane aaa = FXMLLoader.load(getClass().getResource(Main.telaAnterior));
            Main.telaAnterior = temp;
            mainpane.setCenter(aaa);
        } else {
            AnchorPane aaa = FXMLLoader.load(getClass().getResource(Main.telaAnterior));
            Main.telaAnterior = temp;
            mainpane.setCenter(aaa);
        }

    }

    @FXML
    protected void registroDoenca() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroEnfermidade.fxml";
        AnchorPane ad = FXMLLoader.load(getClass().getResource("CadastroEnfermidade.fxml"));
        mainpane.setCenter(ad);
    }

    @FXML
    protected void registroInseminacao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "RegistroInseminacao.fxml";
        AnchorPane ad = FXMLLoader.load(getClass().getResource("RegistroInseminacao.fxml"));
        mainpane.setCenter(ad);
    }

    @FXML
    protected void cadastroAlimento() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "Alimentos.fxml";
        AnchorPane aca = FXMLLoader.load(getClass().getResource("Alimentos.fxml"));
        mainpane.setCenter(aca);
    }

    @FXML
    protected void consultaAlimentacao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "ConsultaAlimentacao.fxml";
        AnchorPane aconal = FXMLLoader.load(getClass().getResource("ConsultaAlimentacao.fxml"));
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void acompanhamentoReproducao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "AcompanhamentoReproducao.fxml";
        FXMLLoader aa = new FXMLLoader(getClass().getResource("AcompanhamentoReproducao.fxml"));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void analiticoAlimentacao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "AnaliticoAlimentacao.fxml";
        FXMLLoader aa = new FXMLLoader(getClass().getResource("AnaliticoAlimentacao.fxml"));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void cadastroEstoque() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroEstoque.fxml";
        FXMLLoader aa = new FXMLLoader(getClass().getResource("CadastroEstoque.fxml"));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void cadastroDoencas() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroDoenca.fxml";
        FXMLLoader aa = new FXMLLoader(getClass().getResource("CadastroDoenca.fxml"));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void cadastroExame() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroExame.fxml";
        FXMLLoader aa = new FXMLLoader(getClass().getResource("CadastroExame.fxml"));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }
    
    @FXML
    protected void cadastroEnfermidade() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = "CadastroEnfermidade.fxml";
        FXMLLoader aa = new FXMLLoader(getClass().getResource("CadastroEnfermidade.fxml"));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        new Thread() {
            @Override
            public void run() {
                verificaAlimentacoes();;
            }
        }.start();
        Platform.runLater(() -> {
            Main.stage.setOnCloseRequest(b -> {
                Main.tocaConfirma();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
                alert.setGraphic(imageView);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert.setTitle("Atenção!");
                alert.setHeaderText("Você tem certeza que deseja encerrar o BoviPlus?");
                alert.setContentText("Alterações não salvas serão perdidas!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        Platform.exit();
                    } else {
                        b.consume();
                    }
                });
            });
        });
        user_lb.setText(Main.getUser().getNome());
        String str = Main.getUser().getPermissoes();
        if (str.charAt(0) == '0') {
            mitem_usuario.setDisable(true);
            preferencias.setDisable(true);
            mitem_produtor.setDisable(true);
        }
        if (str.charAt(1) == '0') {
            menu_animal.setDisable(true);
            vaca_img.setDisable(true);
        }
        if (str.charAt(2) == '0') {
            menu_saude.setDisable(true);
        }
        if (str.charAt(3) == '0') {
            menu_financeiro.setDisable(true);
            conta_img.setDisable(true);
        }
        if (str.charAt(4) == '0') {
            menu_producao.setDisable(true);
            prod_img.setDisable(true);
        }
        if (str.charAt(5) == '0') {
            menu_reproducao.setDisable(true);
            insem_img.setDisable(true);
        }
        if (str.charAt(6) == '0') {
            menu_alimentacao.setDisable(true);
            alim_img.setDisable(true);
        }

        try {
            Main.telaAnterior = "TelaInicio.fxml";
            Main.telaAtual = "TelaInicio.fxml";
            telaInicial();
        } catch (IOException ex) {
            System.out.println("Não entrou");
        }

        prod_img.setOnMouseClicked(e -> {
            if (e.isShiftDown()) {
                try {
                    Stage a = new Stage();
                    a.setResizable(false);
                    Main.window = false;
                    Parent fxmlSobre = FXMLLoader.load(getClass().getResource(Main.pref.a2));
                    Scene scene = new Scene(fxmlSobre);
                    a.getIcons().add(new Image("/Imagens/Iconew.png"));
                    a.setTitle("Produção - BoviPlus");
                    a.setScene(scene);
                    a.show();
                    Main.window = true;
                } catch (IOException u) {
                    System.out.println(u.getMessage());
                }

            } else {
                try {
                    atalhoProducao();
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        insem_img.setOnMouseClicked(e -> {
            if (e.isShiftDown()) {
                try {
                    Stage a = new Stage();
                    a.setResizable(false);
                    Main.window = false;
                    Parent fxmlSobre = FXMLLoader.load(getClass().getResource(Main.pref.a3));
                    Scene scene = new Scene(fxmlSobre);
                    a.getIcons().add(new Image("/Imagens/Iconew.png"));
                    a.setTitle("Reprodução - BoviPlus");
                    a.setScene(scene);
                    a.show();
                    Main.window = true;
                } catch (IOException u) {
                    System.out.println(u.getMessage());
                }

            } else {
                try {
                    atalhoReproducao();
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        vaca_img.setOnMouseClicked(e -> {
            if (e.isShiftDown()) {
                try {
                    Stage a = new Stage();
                    a.setResizable(false);
                    Main.window = false;
                    Parent fxmlSobre = FXMLLoader.load(getClass().getResource(Main.pref.a1));
                    Scene scene = new Scene(fxmlSobre);
                    a.getIcons().add(new Image("/Imagens/Iconew.png"));
                    a.setTitle("Animal - BoviPlus");
                    a.setScene(scene);
                    a.show();
                    Main.window = true;
                } catch (IOException u) {
                    System.out.println(u.getMessage());
                }

            } else {
                try {
                    atalhoAnimal();
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        alim_img.setOnMouseClicked(e -> {
            if (e.isShiftDown()) {
                try {
                    Stage a = new Stage();
                    a.setResizable(false);
                    Main.window = false;
                    Parent fxmlSobre = FXMLLoader.load(getClass().getResource(Main.pref.a4));
                    Scene scene = new Scene(fxmlSobre);
                    a.getIcons().add(new Image("/Imagens/Iconew.png"));
                    a.setTitle("Alimentação - BoviPlus");
                    a.setScene(scene);
                    a.show();
                    Main.window = true;
                } catch (IOException u) {
                    System.out.println(u.getMessage());
                }

            } else {
                try {
                    atalhoAlimentacao();
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        conta_img.setOnMouseClicked(e -> {
            if (e.isShiftDown()) {
                try {
                    Stage a = new Stage();
                    a.setResizable(false);
                    Main.window = false;
                    Parent fxmlSobre = FXMLLoader.load(getClass().getResource(Main.pref.a5));
                    Scene scene = new Scene(fxmlSobre);
                    a.getIcons().add(new Image("/Imagens/Iconew.png"));
                    a.setTitle("Financeiro - BoviPlus");
                    a.setScene(scene);
                    a.show();
                    Main.window = true;
                } catch (IOException u) {
                    System.out.println(u.getMessage());
                }

            } else {
                try {
                    atalhoFinanceiro();
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    protected void atalhoAnimal() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = Main.pref.a1;
        FXMLLoader aa = new FXMLLoader(getClass().getResource(Main.pref.a1));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void atalhoProducao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = Main.pref.a2;
        FXMLLoader aa = new FXMLLoader(getClass().getResource(Main.pref.a2));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void atalhoReproducao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = Main.pref.a3;
        FXMLLoader aa = new FXMLLoader(getClass().getResource(Main.pref.a3));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void atalhoAlimentacao() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = Main.pref.a4;
        FXMLLoader aa = new FXMLLoader(getClass().getResource(Main.pref.a4));
        AnchorPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    @FXML
    protected void atalhoFinanceiro() throws IOException {
        Main.telaAnterior = Main.telaAtual;
        Main.telaAtual = Main.pref.a5;
        FXMLLoader aa = new FXMLLoader(getClass().getResource(Main.pref.a5));
        TabPane aconal = aa.load();
        mainpane.setCenter(aconal);
    }

    private int descontaEstoque(int alimento, double qtd, String date) { // 0 = faltou estoque, 1 = OK, -1 = excessão
        try {
            DAOEstoque de = new DAOEstoque();
            if (de.somaPorAlimento(alimento) < qtd) { //verifica se tem estoque suficiente
                return 0;
            }
            List<Estoque> est = de.listarPorAlimento(alimento);
            double valor = 0, temp = 0;
            if (est.get(0).quant >= qtd) {
                valor = est.get(0).valor_uni * qtd;
                est.get(0).quant = est.get(0).quant - qtd;
            } else {
                for (int i = 0; i < est.size(); i++) {
                    if (est.get(i).quant < qtd) {
                        valor = valor + (est.get(i).quant * est.get(i).valor_uni);
                        temp = qtd - est.get(i).quant;
                        est.get(i).quant = 0;
                        qtd = temp;
                        if (qtd == 0) {
                            break;
                        }
                    } else {
                        valor = valor + (est.get(i).getValor_uni() * qtd);
                        est.get(i).quant = est.get(i).quant - qtd;
                        qtd = 0;
                        break;
                    }
                }
            }

            for (Estoque e : est) {
                de.atualizarEstoque(e);
            }

            for (Estoque e : est) {
                if (e.quant == 0) {
                    de.deletarMovimento(e.getId_movimento());
                }
            }
            geraGasto(alimento, valor, date);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return 1;
    }

    public void geraGasto(int alimento, double valor, String data) {
        try {
            DAOGasto_diario dg = new DAOGasto_diario();
            Gasto_diario gd = dg.consultaGasto(alimento, data);
            if (gd == null) {
                dg.cadastrarGasto(new Gasto_diario(alimento, valor, data));
            } else {
                gd.valor = gd.valor + valor;
                dg.atualizarGasto(gd);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void verificaAlimentacoes() {
        if (Main.pref.autoalimentacao) {
            try {
                DAOAlimentacao da = new DAOAlimentacao();
                DAOEstoque de = new DAOEstoque();
                DAOAlim_Replica dd = new DAOAlim_Replica();
                LocalDate a = LocalDate.parse(da.ultimaData());
                if (!a.equals(LocalDate.now())) {
                    List<Alim_Replica> list = dd.listarTodos();
                    long dif = DAYS.between(a, LocalDate.now());
                    Alimentacao u;
                    while (dif > 0) {                        
                        for (Alim_Replica l : list) {
                            u = new Alimentacao();
                            u.setData_alimentacao(a.plusDays(dif).toString());
                            u.setId_alimento(l.alim);
                            u.setId_animal(l.animal);
                            u.setQtd(l.quant);
                            if (u.getQtd() > de.somaPorAlimento(u.getId_alimento())) {
                                Main.outros_eventos.add(new Evento("ATENÇÃO\nFalta de estoque ao realizar\nalimentações automáticas!\nAlimentação Automática Desativada", "outros", LocalDate.now()));
                                Main.pref.autoalimentacao = false;
                            } else {
                                da.cadastrarAlimentacao_individual(u);
                                descontaEstoque(u.getId_alimento(), u.getQtd(), u.getData_alimentacao());
                            }
                        }
                        dif--;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
