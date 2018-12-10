/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import DAO.DAODoenca;
import Entidades.Doenca;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author new
 */
public class CadastroDoencaController implements Initializable {
int aux=1, aux2;
        @FXML
    private Label titulo_tela;

    @FXML
    private ImageView icone_img;

    @FXML
    private Label codigo_label;

    @FXML
    private TextField codigo_tx;

    @FXML
    private Button carregar_bt;
            
    @FXML
    private Button listdoencas_bt;

    @FXML
    private Label descricao_label;

    @FXML
    private TextField descricao_tx;

    @FXML
    private Label nome_label;

    @FXML
    private TextField nome_tx;

    @FXML
    private ButtonBar barradebotoes;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button excluir_bt;
       
    @FXML
    public void listDoencas() throws IOException {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaDoenca.fxml"));
            Scene scene = new Scene(fxmlDB);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Consulta de Doenças [Saúde] - BoviPlus");
            c.show();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

    }
    
    
    @FXML
    public void atualizaDoenca() {
        try {
            DAODoenca dao = new DAODoenca();

            dao.atualizarDoenca(Integer.parseInt(codigo_tx.getText()), nome_tx.getText(), descricao_tx.getText() );
            Main.tocaSucesso();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Sucesso!");
            alert2.setHeaderText("A doença foi atualizada!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Falha!");
            alert2.setHeaderText("A doença não pode ser cadastrada!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }
    
    @FXML
    public void cadastraDoenca() {
        try {
            DAODoenca dao = new DAODoenca();
            Doenca doe = new Doenca();
            
            if(codigo_tx.getText().trim().isEmpty()){
             doe.setId_doenca(dao.proximoID());
             aux2=doe.getId_doenca();
            } else{
                aux2=Integer.parseInt(codigo_tx.getText());
            }

            dao.cadastrar_doenca(aux2, nome_tx.getText(), descricao_tx.getText() );
            Main.tocaSucesso();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Sucesso!");
            alert2.setHeaderText("A doença foi cadastrada seu código é " + aux2 + "!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Falha!");
            alert2.setHeaderText("A doença não pode ser cadastrada!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void carregaDoenca() {

        if (codigo_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para carregar digite o código da doença!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                DAODoenca doenca_dao = new DAODoenca();
                Doenca doenca = new Doenca();

                doenca = doenca_dao.consultar_doenca(Integer.parseInt(codigo_tx.getText()));

                codigo_tx.setText(Integer.toString(doenca.getId_doenca()));
                nome_tx.setText(doenca.getNome());
                descricao_tx.setText(doenca.getDescricao());
            } catch (Exception e) {
                limpaTela();
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("A doença não foi encontrada");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }

        }
    }

    @FXML
    public void limpaTela() {
        codigo_tx.setText("");
        nome_tx.setText("");
        descricao_tx.setText("");
    }
    
     @FXML
    public void apagaDoenca() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (aux == 1) {
            Main.tocaConfirma();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção.");
            alert.setHeaderText("Você tem certeza que deseja apagar a doença " + nome_tx.getText() + "?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {

                        DAODoenca doenca = new DAODoenca();
                        doenca.deletarDoenca(Integer.parseInt(codigo_tx.getText()));
                        Main.tocaSucesso();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imageiew = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert.setGraphic(imageiew);
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert2.setTitle("Sucesso!");
                        alert2.setHeaderText("A doença foi apagada!");
                        alert2.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                    } catch (Exception e) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        ImageView imageVew = new ImageView(new Image(new File("error.png").toURI().toString()));
                        alert.setGraphic(imageVew);
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert1.setTitle("Ocorreu um erro!");
                        alert1.setHeaderText("Não foi possível apagar a doença, tente novamente!");
                        alert1.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                    }
                } else {
                    codigo_tx.setText("");
                    nome_tx.setText("");
                    descricao_tx.setText("");
                    aux = 0;
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Cadastro de Doenças [Saúde] - BoviPlus");
        }
    }

}

