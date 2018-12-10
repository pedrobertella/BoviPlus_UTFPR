package Telas;

import DAO.DAOAnimal;
import DAO.DAOExame;
import Entidades.Animal;
import Entidades.Exame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CadastroExameController implements Initializable {

    boolean define;
    private boolean exameCarregado = false;
    int aux2 = 0;

    @FXML
    private TextField codigo_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private TextField nome_tx;

    @FXML
    private TextField descricao_tx;

    @FXML
    private DatePicker data_pk;

    @FXML
    private Label titulo_tela;

    @FXML
    private ImageView icone_img;

    @FXML
    private Label codigo_label;

    @FXML
    private Label descricao_label;

    @FXML
    private Label nome_label;

    @FXML
    private Label data_label;

    @FXML
    private Label categoria_label;

    @FXML
    private Label intervalo_label;

    @FXML
    private Button datahoje_bt;

    @FXML
    private Button ontem_bt;

    @FXML
    private RadioButton esporadico_rbt;

    @FXML
    private ToggleGroup categoria;

    @FXML
    private RadioButton periodico_rbt;

    @FXML
    private TextField intervalo_tx;

    @FXML
    private Label codigo_animal_label;

    @FXML
    private Label tipo_label;

    @FXML
    private RadioButton individual_rbt;

    @FXML
    private ToggleGroup tipo;

    @FXML
    private RadioButton grupo_rbt;

    @FXML
    private TextField codigo_animal_tx;

    @FXML
    private Button carregar_animal_bt;

    @FXML
    private ButtonBar barradebotoes;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button excluir_bt;

    @FXML
    private Label nome_lb;  
    
    @FXML
    private Button listexames_bt;
    
    @FXML
    public void listExames() throws IOException {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaExame.fxml"));
            Scene scene = new Scene(fxmlDB);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Consulta de Exames [Saúde] - BoviPlus");
            c.show();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

    }

    @FXML
    public void habilitaCampos() {
        if (periodico_rbt.isSelected()) {
            define = false;
            intervalo_label.setDisable(define);
            intervalo_tx.setDisable(define);

        } else if (esporadico_rbt.isSelected()) {
            define = true;
            intervalo_label.setDisable(define);
            intervalo_tx.setDisable(define);
        }
    }

    @FXML
    public void selecionaAnimal() throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {

        try {
            DAOAnimal da = new DAOAnimal();
            int id_animal = Integer.parseInt(codigo_animal_tx.getText());
            Animal a = da.consultaAnimal(id_animal);
            nome_lb.setText(a.getNome());
            if (id_animal == 0) {
                throw new IllegalAccessException();
            }
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Não encontrado");
            alert1.setHeaderText("O Animal não foi encontrado");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            limpaTela();
        }
    }

    @FXML
    public void carregaExame() {
        if (codigo_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para carregar digite o código do exame!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            limpaTela();
        } else {
            try {
                DAOExame dao = new DAOExame();
                Exame exame = new Exame();

                exame = dao.consultar_exame(Integer.parseInt(codigo_tx.getText()));

                nome_tx.setText(exame.getNome());
                descricao_tx.setText(exame.getDescricao());
                intervalo_tx.setText(Integer.toString(exame.getIntervalo()));
                data_pk.setValue(LocalDate.parse(exame.getData_exame()));
                codigo_animal_tx.setText(Integer.toString(exame.getId_animal()));
                DAOAnimal daoanimal = new DAOAnimal();
                Animal animal = new Animal();
                
                animal = daoanimal.consultaAnimal(Integer.parseInt(codigo_animal_tx.getText()));
                nome_lb.setText(animal.getNome());
                System.out.println(LocalDate.parse(exame.getData_exame()));
                if (exame.getCategoria().equals("E")) {
                    categoria.selectToggle(esporadico_rbt);

                } else if (exame.getCategoria().equals("P")) {
                    intervalo_tx.setDisable(false);
                    intervalo_label.setDisable(false);
                    categoria.selectToggle(periodico_rbt);
                    intervalo_tx.setText(Integer.toString(exame.getIntervalo()));
                }

                if (Integer.parseInt(codigo_tx.getText()) == 0) {
                    throw new IllegalAccessException();
                }
                System.out.println("carregou exame");
                exameCarregado = true;
            } catch (Exception e) {
                System.out.println("nao carregou exame");
                limpaTela();
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O exame não foi encontrado");
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
        descricao_tx.setText("");
        data_pk.setValue(null);
        categoria.selectToggle(null);
        tipo.selectToggle(null);
        codigo_animal_tx.setText("");
        intervalo_tx.setText("");
        exameCarregado = false;
        nome_lb.setText("");
    }

    @FXML
    public void hojeDataCad() {
        data_pk.setValue(LocalDate.now());
    }

    @FXML
    public void salvaExame() {
        if (exameCarregado == true) {
            try {
                System.out.println("ENTROUCARREGADO");
                DAOExame dao = new DAOExame();
                Exame exame = new Exame();
                if (codigo_tx.getText().equals("")) {
                    System.out.println("ENTROU");
                    exame.setId_exame(dao.proximoID());

                } else {
                    exame.setId_exame(Integer.parseInt(codigo_tx.getText()));
                    System.out.println("ENTROUCODIGO");
                }

                exame.setNome(nome_tx.getText());
                exame.setDescricao(descricao_tx.getText());
                exame.setId_animal(Integer.parseInt(codigo_animal_tx.getText()));
                exame.setData_exame(data_pk.getValue().toString());
                if (periodico_rbt.isSelected()) {
                    System.out.println("entrouP");
                    exame.setCategoria("P");
                    exame.setIntervalo(Integer.parseInt(intervalo_tx.getText()));
                } else if (esporadico_rbt.isSelected()) {
                    System.out.println("entrouE");
                    exame.setCategoria("E");
                    exame.setIntervalo(0);
                }

                dao.atualizarExame(exame);

                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("O exame foi atualizado!");
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
                alert2.setHeaderText("O exame não pode ser atualizado"
                        + "!");
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                limpaTela();
            }
        } else {
            try {
                DAOExame dao = new DAOExame();
                Exame exame = new Exame();
                if (codigo_tx.getText().equals("")) {
                    System.out.println("ENTROU");
                    exame.setId_exame(dao.proximoID());

                } else {
                    exame.setId_exame(Integer.parseInt(codigo_tx.getText()));
                }

                exame.setNome(nome_tx.getText());
                exame.setDescricao(descricao_tx.getText());
                exame.setId_animal(Integer.parseInt(codigo_animal_tx.getText()));
                exame.setData_exame(data_pk.getValue().toString());
                if (periodico_rbt.isSelected()) {
                    exame.setCategoria("P");
                    exame.setIntervalo(Integer.parseInt(intervalo_tx.getText()));
                } else if (esporadico_rbt.isSelected()) {
                    exame.setCategoria("E");
                }

                dao.cadastrar_exame(exame);
                System.out.println("SAIU DAO");
                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("O exame foi cadastrado, seu código é: " + exame.getId_exame() + "!");
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
                alert2.setHeaderText("O exame não pode ser cadastrado!");
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
    public void apagaExame() {
        if (exameCarregado) {
            Main.tocaConfirma();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção.");
            alert.setHeaderText("Você tem certeza que deseja apagar o exame " + nome_tx.getText() + "?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        DAOExame dao = new DAOExame();
                        dao.deletarExame(Integer.parseInt(codigo_tx.getText()));
                        Main.tocaSucesso();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView aa = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert2.setGraphic(aa);
                        ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert2.setTitle("Sucesso!");
                        alert2.setHeaderText("O exame foi apagado!");
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
                        alert1.setHeaderText("Não foi possível apagar o exame, tente novamente!");
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

    @FXML
    public void ontemDataCad() {
        data_pk.setValue(LocalDate.now().minusDays(1));
    }

    @FXML
    private void alteracao() {
        define = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Cadastro de Exame [Saúde] - BoviPlus");
        }
    }
}
