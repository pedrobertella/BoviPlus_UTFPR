package Telas;

import ConexaoBD.Conexao;
import DAO.DAOUsuario;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroUsuarioController implements Initializable {

    private int aux = 0;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private Rectangle fundotitulo;

    @FXML
    private Label titulo;

    @FXML
    private ImageView img_icone;

    @FXML
    private TextField tx_codigo;

    @FXML
    private Button bt_carregar;

    @FXML
    private TextField tx_nome;

    @FXML
    private TextField tx_senha;

    @FXML
    private Button bt_salvar;

    @FXML
    private Button bt_excluir;

    @FXML
    private CheckBox user_cbox;

    @FXML
    private CheckBox animal_cbox;

    @FXML
    private CheckBox saude_cbox;

    @FXML
    private CheckBox financ_cbox;

    @FXML
    private CheckBox prod_cbox;

    @FXML
    private CheckBox reprod_cbox;

    @FXML
    private CheckBox alim_cbox;
    
    @FXML
    public void apagaUsuario() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (aux == 1) {
            Main.tocaConfirma();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção.");
            alert.setHeaderText("Você tem certeza que deseja apagar o usuário " + tx_nome.getText() + "?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {

                        DAOUsuario user = new DAOUsuario();
                        user.deletarUsuario(Integer.parseInt(tx_codigo.getText()));
                        Main.tocaSucesso();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imageViw = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert2.setGraphic(imageViw);
                        ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert2.setTitle("Sucesso!");
                        alert2.setHeaderText("O usuário foi apagado!");
                        alert2.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                        limpaTela();
                    } catch (Exception e) {
                        Main.tocaErro();
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        ImageView imageVie = new ImageView(new Image(new File("error.png").toURI().toString()));
                        alert1.setGraphic(imageVie);
                        ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert1.setTitle("Ocorreu um erro!");
                        alert1.setHeaderText("Não foi possível apagar o usuário, tente novamente!");
                        alert1.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                    }
                } else {
                    tx_codigo.setText("");
                    tx_nome.setText("");
                    tx_senha.setText("");
                    aux = 0;
                }
            });
        }
    }

    @FXML
    public void limpaTela() {
       ((Stage)tx_codigo.getScene().getWindow()).close();
    }

    @FXML
    public void salvaUsuario() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        try {
            DAOUsuario aa = new DAOUsuario();
            if (aa.existeOutro(tx_nome.getText()) && aux != 1) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageVie = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageVie);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Atenção");
                alert1.setHeaderText("Já existe um usuário com este nome! Verifique...");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (aux == 1) {
            updateUsuario();
            tx_codigo.setText("");
            tx_nome.setText("");
            tx_senha.setText("");
            aux = 0;
            user_cbox.setSelected(false);
            animal_cbox.setSelected(false);
            saude_cbox.setSelected(false);
            financ_cbox.setSelected(false);
            prod_cbox.setSelected(false);
            reprod_cbox.setSelected(false);
            alim_cbox.setSelected(false);
            Main.tocaSucesso();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            Image image = new Image(new File("ok.png").toURI().toString());
            ImageView imageView = new ImageView(image);
            alert2.setTitle("Sucesso!");
            alert2.setGraphic(imageView);
            alert2.setHeaderText("Todas as alterações foram salvas");
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                char[] p = new char[7];
                if (user_cbox.isSelected()) {
                    p[0] = '1';
                } else {
                    p[0] = '0';
                }

                if (animal_cbox.isSelected()) {
                    p[1] = '1';
                } else {
                    p[1] = '0';
                }

                if (saude_cbox.isSelected()) {
                    p[2] = '1';
                } else {
                    p[2] = '0';
                }

                if (financ_cbox.isSelected()) {
                    p[3] = '1';
                } else {
                    p[3] = '0';
                }

                if (prod_cbox.isSelected()) {
                    p[4] = '1';
                } else {
                    p[4] = '0';
                }

                if (reprod_cbox.isSelected()) {
                    p[5] = '1';
                } else {
                    p[5] = '0';
                }

                if (alim_cbox.isSelected()) {
                    p[6] = '1';
                } else {
                    p[6] = '0';
                }
                Conexao conexao = new Conexao();
                conexao.abrirConexao();
                conexao.stm = conexao.connection.createStatement();
                if (tx_senha.getText().equals("")) {
                    Main.tocaConfirma();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                    alert.setGraphic(imageView);
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert.setTitle("Atenção!");
                    alert.setHeaderText("A senha não pode ser vazia!");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                } else {
                    String sql = "INSERT INTO usuario (nome, senha, permissoes) values ('" + tx_nome.getText() + "', '" + tx_senha.getText() + "', '" + String.valueOf(p) + "');";
                    conexao.stm.execute(sql);
                    tx_codigo.setText("");
                    tx_nome.setText("");
                    tx_senha.setText("");
                    user_cbox.setSelected(false);
                    animal_cbox.setSelected(false);
                    saude_cbox.setSelected(false);
                    financ_cbox.setSelected(false);
                    prod_cbox.setSelected(false);
                    reprod_cbox.setSelected(false);
                    alim_cbox.setSelected(false);
                    Main.tocaSucesso();
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    Image image = new Image(new File("ok.png").toURI().toString());
                    ImageView imageView = new ImageView(image);
                    alert2.setTitle("Sucesso!");
                    alert2.setGraphic(imageView);
                    alert2.setHeaderText("Usuário cadastrado");
                    ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert2.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                }

            } catch (Exception e) {
                Main.tocaErro();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert.setGraphic(imageView);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert.setTitle("Ocorreu um erro!");
                alert.setHeaderText("Não foi possível inserir usuário, tente novamente!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }

        }
    }

    @FXML
    public void carregaUsuario() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        user_cbox.setSelected(false);
        animal_cbox.setSelected(false);
        saude_cbox.setSelected(false);
        financ_cbox.setSelected(false);
        prod_cbox.setSelected(false);
        reprod_cbox.setSelected(false);
        alim_cbox.setSelected(false);
        bt_excluir.setDisable(false);
        tx_nome.setEditable(true);
        user_cbox.setDisable(false);
        Conexao conexao = new Conexao();
        conexao.abrirConexao();
        conexao.stm = conexao.connection.createStatement();
        aux = 1;
        try {
            String sql = "SELECT nome, senha, permissoes FROM usuario where id_user = " + Integer.parseInt(tx_codigo.getText()) + ";";
            conexao.rs = conexao.stm.executeQuery(sql);
            if (conexao.rs.next()) {
                String nome = conexao.rs.getString("nome");
                tx_nome.setText(nome);
                tx_senha.setText(conexao.rs.getString("senha"));
                tx_nome.requestFocus();
                String perm = conexao.rs.getString("permissoes");
                System.out.println(perm);
                if (perm.charAt(0) == '1') {
                    user_cbox.setSelected(true);
                }
                if (perm.charAt(1) == '1') {
                    animal_cbox.setSelected(true);
                }
                if (perm.charAt(2) == '1') {
                    saude_cbox.setSelected(true);
                }
                if (perm.charAt(3) == '1') {
                    financ_cbox.setSelected(true);
                }
                if (perm.charAt(4) == '1') {
                    prod_cbox.setSelected(true);
                }
                if (perm.charAt(5) == '1') {
                    reprod_cbox.setSelected(true);
                }
                if (perm.charAt(6) == '1') {
                    alim_cbox.setSelected(true);
                }
                if (nome.equals("ADMIN")) {
                    bt_excluir.setDisable(true);
                    tx_nome.setEditable(false);
                    user_cbox.setDisable(true);
                }
            } else {
                System.out.println("Falha!");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção!");
                alert.setHeaderText("Usuário não encontrado!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção!");
            alert.setHeaderText("Não foi possível consultar o usuário!");
            alert.setContentText(e.getMessage());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    public void updateUsuario() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        char[] p = new char[7];
        if (user_cbox.isSelected()) {
            p[0] = '1';
        } else {
            p[0] = '0';
        }

        if (animal_cbox.isSelected()) {
            p[1] = '1';
        } else {
            p[1] = '0';
        }

        if (saude_cbox.isSelected()) {
            p[2] = '1';
        } else {
            p[2] = '0';
        }

        if (financ_cbox.isSelected()) {
            p[3] = '1';
        } else {
            p[3] = '0';
        }

        if (prod_cbox.isSelected()) {
            p[4] = '1';
        } else {
            p[4] = '0';
        }

        if (reprod_cbox.isSelected()) {
            p[5] = '1';
        } else {
            p[5] = '0';
        }

        if (alim_cbox.isSelected()) {
            p[6] = '1';
        } else {
            p[6] = '0';
        }

        Conexao conexao = new Conexao();
        conexao.abrirConexao();
        conexao.stm = conexao.connection.createStatement();
        String sql = "UPDATE usuario SET nome = '" + tx_nome.getText() + "', senha = '" + tx_senha.getText() + "', permissoes = '" + String.valueOf(p) + "' WHERE id_user = " + Integer.parseInt(tx_codigo.getText()) + ";";
        conexao.stm.execute(sql);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.window) {
            Main.stage.setTitle("Cadastro de Usuários [Sistema] - BoviPlus");
        }
        if (!Main.id_user.equals("0")) {
            tx_codigo.setText(Main.id_user);
            try {
                carregaUsuario();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            Main.id_user = "0";

        }
    }
}
