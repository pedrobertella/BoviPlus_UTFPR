package Telas;

import ConexaoBD.Conexao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BDConectController implements Initializable {

    Tooltip s;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtIP;

    @FXML
    private TextField txtSenha;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnPronto;

    @FXML
    private Button btnTCnx;

    @FXML
    private ImageView status;

    private Scene scene;
    
    Image img;

    @FXML
    protected void SalvarConexao() throws IOException {

        if (txtNome.getText() == null || txtNome.getText().trim().isEmpty()) {
            Main.tocaErro();
            Alert alert = new Alert(AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção!");
            alert.setHeaderText("Preencha o campo NOME para salvar");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else if (txtIP.getText() == null || txtIP.getText().trim().isEmpty()) {
            Main.tocaErro();
            Alert alert = new Alert(AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção!");
            alert.setHeaderText("Preencha o campo IP para salvar");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else if (txtSenha.getText() == null || txtSenha.getText().trim().isEmpty()) {
            Main.tocaErro();
            Alert alert = new Alert(AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção!");
            alert.setHeaderText("Preencha o campo SENHA para salvar");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            FileWriter file = new FileWriter("configDB.dbc");
            try (PrintWriter printer = new PrintWriter(file)) {
                printer.printf("%s\n", txtNome.getText());
                printer.printf("%s\n", txtIP.getText());
                printer.printf("%s\n", txtSenha.getText());
                Main.tocaSucesso();
                Alert alert = new Alert(AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert.setGraphic(imageView);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert.setTitle("Configuração salva!");
                alert.setHeaderText("Configuração salva!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            } catch (Exception e) {
                Main.tocaErro();
                Alert alert = new Alert(AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert.setGraphic(imageView);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert.setTitle("ERRO!");
                alert.setHeaderText("Não foi possivel salvar a configuração do banco!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }

    @FXML
    protected int TesteConexao() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            Conexao conexao = new Conexao();
            conexao.abrirConexao();
            conexao.stm = conexao.connection.createStatement();
            String sql = "SELECT * FROM usuario";
            conexao.rs = conexao.stm.executeQuery(sql);
            Main.tocaSucesso();
            img  = new Image(getClass().getResource("/Imagens/ok.png").toExternalForm());
            status.setImage(img);
            return 0;
        } catch (Exception e) {
            Main.tocaErro();
            img  = new Image(getClass().getResource("/Imagens/error.png").toExternalForm());
            status.setImage(img);
            return 1;
        }

    }

    @FXML
    protected void Pronto() throws IOException, SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        SalvarConexao();
        if (TesteConexao() == 0) {
            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("configDB.dbc");
        Scanner sc;
        try {
            sc = new Scanner(file);
            txtNome.setText(sc.nextLine());
            txtIP.setText(sc.nextLine());
            txtSenha.setText(sc.nextLine());
        } catch (Exception ex) {

        }
        try {
            TesteConexao();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
        s = new Tooltip();
        s.setText("Digite o IP do servidor BoviPlus");
        txtIP.setTooltip(s);
    }
}
