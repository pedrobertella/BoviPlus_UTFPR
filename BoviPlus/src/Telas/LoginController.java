package Telas;

import ConexaoBD.Conexao;
import DAO.DAOUsuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    public static Stage BDConect = new Stage();

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField nomeDeUsuario;

    @FXML
    private PasswordField senhaLogin;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnSair;

    @FXML
    private CheckBox ck_salvarsenha;

    @FXML
    protected void Sair() {
        Platform.exit();
    }

    protected void mostrarBDConect() throws IOException {

        BDConect.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("BDConect.fxml"));
        Scene scene = new Scene(fxmlDB);
        BDConect.getIcons().add(new Image("/Imagens/dbicon.png"));
        BDConect.setScene(scene);
        BDConect.setTitle("Configuração do Banco de Dados - BoviPlus");
        BDConect.show();
    }

    @FXML
    protected void validarLogin() throws IOException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Conexao conexao = new Conexao();
            conexao.abrirConexao();
            conexao.stm = conexao.connection.createStatement();
            String sql = "SELECT id_user FROM usuario where nome = '" + nomeDeUsuario.getText() + "' and senha = '" + senhaLogin.getText() + "';";
            conexao.rs = conexao.stm.executeQuery(sql);

            if (conexao.rs.next()) {
                
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Iniciando...");
                al.setHeaderText("Iniciando BoviPlus...");
                al.show();
                Main.carregaConfig();
                Main.tocaStart();
                DAOUsuario du = new DAOUsuario();
                Main.setUser(du.consultaUsuario(conexao.rs.getInt("id_user")));
                System.out.println("Login Efetuado!");
                salvarUsuario();

                Parent fxmlTelaPrincipal = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
                Scene TelaPrincipalScene = new Scene(fxmlTelaPrincipal);
                al.close();
                Main.stage.setScene(TelaPrincipalScene);

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                alert.setGraphic(imageView);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert.setTitle("Atenção!");
                alert.setHeaderText("Usuário ou Senha inválidos!!!");
                alert.setContentText("Verifique o usuário ou senha digitados...");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Erro no banco de dados!");
            alert.setHeaderText("Não foi possível conectar ao banco de dados.");
            alert.setContentText("Por favor configure o acesso ao banco de dados na tela a seguir...");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            mostrarBDConect();
        }
    }

    public void salvarUsuario() throws IOException {
        FileWriter file1 = new FileWriter("lastUser.dbc");
        try {
            PrintWriter printer = new PrintWriter(file1);
            printer.printf("%s\n", nomeDeUsuario.getText());
            if (ck_salvarsenha.isSelected()) {
                printer.printf("%s\n", senhaLogin.getText());
            }
            printer.close();
        } catch (Exception e) {
            System.out.println("Não foi possivel salvar o usuário.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.stage.setTitle("Entrar - BoviPlus");
        try {
            File file = new File("lastUser.dbc");
            Scanner sc = new Scanner(file);
            String nome = sc.nextLine();

            nomeDeUsuario.setText(nome);
            if (sc.hasNext()) {
                String senha = sc.nextLine();
                senhaLogin.setText(senha);
                ck_salvarsenha.setSelected(true);
            }
        } catch (Exception e) {

        }
    }

    @FXML
    public void pulaCampo() {
        senhaLogin.requestFocus();
    }

}
