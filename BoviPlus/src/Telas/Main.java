package Telas;

import ConexaoBD.Conexao;
import Entidades.Evento;
import Entidades.Pref;
import Entidades.Usuario;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;
    private static Scene LoginScene;
    public static String telaAnterior;
    public static String telaAtual;
    public static String id_inseminacao = "0";
    public static String id_animal = "0";
    public static String id_animal_prod = "0";
    private static Usuario user;
    public static Pref pref = null;
    public static String id_conta_receber = "0";
    public static String id_conta_pagar = "0";
    public static int id_alimento = 0;
    public static double tempdouble = 0.00;
    public static int consultaAnimal = 0;
    public static boolean window = true;
    public static String id_user = "0";
    public static String dataConsulta = "0";
    public static String id_vacina = "0";
    public static List<Evento> outros_eventos = new ArrayList<>();

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        Main.user = user;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(false);
        Image i = new Image("/Imagens/Iconew.png");
        primaryStage.getIcons().add(i);
        Parent fxmlLogin = FXMLLoader.load(getClass().getResource("Bovi+.fxml"));
        LoginScene = new Scene(fxmlLogin);

        primaryStage.setScene(LoginScene);
        primaryStage.show();

    }

    public static void tocaConfirma() {
        try {
            if (!pref.mudo) {
                String musicFile = "confirma.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void tocaStart() {
        try {
            if (!pref.mudo) {
                String musicFile = "start.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tocaErro() {
        try {
            if (!pref.mudo) {
                String musicFile = "erro.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tocaSucesso() {
        try {
            if (!pref.mudo) {
                String musicFile = "sucesso.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void carregaConfig() {
        try {
            Conexao c = new Conexao();
            c.abrirConexao();
            String sql = "select * from prefsis;";
            Statement stm = c.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                pref = new Pref(rs.getInt("aviso_secagem"), rs.getInt("aviso_nascimento"), rs.getString("atalho1"), rs.getString("atalho2"), rs.getString("atalho3"), rs.getString("atalho4"), rs.getString("atalho5"), rs.getInt("cpagar"), rs.getInt("creceber"), rs.getInt("aviso_vacina"), rs.getInt("aviso_exame"), rs.getBoolean("mudo"), rs.getBoolean("autoalimentacao"), rs.getInt("prevestoque"));
            } else {
                throw new ArithmeticException();
            }

        } catch (Exception e) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Ocorreu um erro ao iniciar as preferências do BoviPlus...");
            ko.setContentText(e.getMessage());
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

}
