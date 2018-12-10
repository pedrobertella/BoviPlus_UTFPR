package Telas;

import DAO.DAOMedicamento;
import Entidades.Medicamento;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CadastroMedicamentoController implements Initializable{

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
    private Button listmedicamentos_bt;

    @FXML
    private Label nome_label;

    @FXML
    private TextField nome_tx;

    @FXML
    private Label indicacao_label;

    @FXML
    private TextArea indicacao_tx;

    @FXML
    private Label contraindic_label;

    @FXML
    private TextArea contraindic_tx;

    @FXML
    private ButtonBar barradebotoes;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button excluir_bt;
    

    private int id_med;
    private boolean medicamentoCarregado = false;
    private boolean define = true;

    @FXML
    public void listMedicamentos() throws IOException {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaMedicamento.fxml"));
            Scene scene = new Scene(fxmlDB);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Consulta de Medicamentos [Saúde] - BoviPlus");
            c.show();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

    }
    
    @FXML
    public void salvaMedicamento() {
        if (medicamentoCarregado) {
            try {
                DAOMedicamento dao = new DAOMedicamento();
                Medicamento med = new Medicamento();

                med.setId_med(parseInt(codigo_tx.getText()));
                med.setNome(nome_tx.getText());
                med.setIndicacao(indicacao_tx.getText());
                med.setContra_indc(contraindic_tx.getText());
                
                System.out.println("passando dados pra dao");
                dao.atualizarMedicamento(med.getId_med(), med.getNome(), med.getDescricao(), med.getContra_indc());
                System.out.println("saiu da dao");
                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("O medicamento foi atualizado, com os valores:\nCódigo: " + med.getId_med() + ";\nNome: " + med.getNome() 
                        + ";\nIndicação: " + med.getDescricao() + ";\nContra indicação: " + med.getContra_indc() + ".");                
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                limpaTela();
            } catch (Exception e) {
                Main.tocaErro();
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Falha!");
                alert2.setHeaderText("O medicamento não pode ser atualizado!");
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                limpaTela();
            }
        } else {
            try {
                DAOMedicamento dao = new DAOMedicamento();
                Medicamento med = new Medicamento();
                if (codigo_tx.getText().equals("")) {
                    med.setId_med(dao.proximoID());
                    
                } else {
                    med.setId_med(parseInt(codigo_tx.getText()));
                }
                
                med.setNome(nome_tx.getText());
                med.setIndicacao(indicacao_tx.getText());
                med.setContra_indc(contraindic_tx.getText());
                 
                dao.cadastrarMedicamento(med);
                System.out.println("SAIU DAO");  
                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("O medicamento foi cadastrado, seu código é: " + med.getId_med() + "!");
                limpaTela();
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });                
                limpaTela();
            } catch (Exception e) {
                Main.tocaErro();
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Falha!");
                alert2.setHeaderText("O medicamento não pode ser cadastrado!");
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });                
                limpaTela();
            }
        }        
    }

    @FXML
    public void carregaMedicamento() {
        if (codigo_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para carregar digite o código do medicamento!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
                limpaTela();
        } else {
            try {
                DAOMedicamento dao = new DAOMedicamento();
                Medicamento medicamento = new Medicamento();
                id_med = Integer.parseInt(codigo_tx.getText());

                medicamento = dao.consultaMedicamento(id_med);

                nome_tx.setText(medicamento.getNome());
                indicacao_tx.setText(medicamento.getDescricao());
                contraindic_tx.setText(medicamento.getContra_indc());
                
                if (id_med == 0) {
                    throw new IllegalAccessException();
                }
                System.out.println("carregou medicamento");
                medicamentoCarregado = true;
            } catch (Exception e) {
                System.out.println("nao carregou medicamento");
                limpaTela();
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O medicamento não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                limpaTela();
            }

        }
    }

    @FXML
    public void limpaTela() {
        codigo_tx.setText("");
        nome_tx.setText("");
        indicacao_tx.setText("");
        contraindic_tx.setText("");
        medicamentoCarregado = false;
    }
    
    @FXML
    public void apagaMedicamento() {
        if (medicamentoCarregado) {
            Main.tocaConfirma();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção.");
            alert.setHeaderText("Você tem certeza que deseja apagar o medicamento " + nome_tx.getText() + "?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        DAOMedicamento dao = new DAOMedicamento();
                        dao.deletarMedicamento(Integer.parseInt(codigo_tx.getText()));
                        Main.tocaSucesso();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView aa = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert2.setGraphic(aa);
                        ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert2.setTitle("Sucesso!");
                        alert2.setHeaderText("O medicamento foi apagado!");
                        alert2.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                        limpaTela();
                    } catch (Exception e) {
                        Main.tocaErro();
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        ImageView ae = new ImageView(new Image(new File("error.png").toURI().toString()));
                        alert1.setGraphic(ae);
                        ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert1.setTitle("Ocorreu um erro!");
                        alert1.setHeaderText("Não foi possível apagar o medicamento, tente novamente!");
                        alert1.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                    limpaTela();
                    }
                } else {
                    limpaTela();
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Cadastro de Medicamento [Saúde] - BoviPlus");
        }
    }

}
