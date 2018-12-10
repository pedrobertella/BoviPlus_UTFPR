package Telas;

import DAO.DAOAlim_Replica;
import DAO.DAOAlimento;
import DAO.DAOAnimal;
import Entidades.Alim_Replica;
import Entidades.Alimento;
import Entidades.Animal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroAlimentacaoController implements Initializable {

    private Alimento almto;
    private Animal animal;
    private int cod_alimento, cod_animal, cod_lote;
    private boolean alt = false;
    private boolean carregado = false;

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private Label titulo_tela;

    @FXML
    private ImageView icone_img;

    @FXML
    private Label cod_alimento_label;

    @FXML
    private Label cod_animal_label;

    @FXML
    private Label qtd_label;

    @FXML
    private TextField id_alim_tx;

    @FXML
    private TextField cod_alimento_tx;

    @FXML
    private Button buscaalmto_btn;

    @FXML
    private TextField id_anim_tx;

    @FXML
    private TextField cod_animal_tx;

    @FXML
    private Button buscaanimal_btn;

    @FXML
    private TextField qtd_tx;

    @FXML
    private Button limpar_btn;

    @FXML
    private Button salvar_btn;

    @FXML
    public void alteracao() {
        alt = true;
    }

    private Alim_Replica a;

    public void carregar(Alim_Replica x) {
        try {
            a = x;
            id_alim_tx.setText(Integer.toString(a.alim));
            buscaAlimento();
            id_anim_tx.setText(Integer.toString(a.animal));
            buscaAnimal();
            qtd_tx.setText(Double.toString(a.quant));
            carregado = true;
            id_alim_tx.setDisable(true);
            id_anim_tx.setDisable(true);
            limpar_btn.setDisable(true);
            qtd_tx.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void cadastrar() {
        if (animal == null) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção");
            alert1.setHeaderText("Carregue um animal para continuar...");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else if (almto == null) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção");
            alert1.setHeaderText("Carregue um alimento para continuar...");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else if (qtd_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção");
            alert1.setHeaderText("Informe a quantidade");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            qtd_tx.requestFocus();
        } else {
            if (carregado) {
                try {
                    DAOAlim_Replica d = new DAOAlim_Replica();
                    a.setQuant(Double.parseDouble(qtd_tx.getText()));
                    d.atualizarAlim(a);
                    Main.tocaSucesso();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Sucesso.");
                    alert1.setHeaderText("Alimentação atualizada com sucesso.");
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    ((Stage) id_alim_tx.getScene().getWindow()).close();
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Atenção");
                    alert1.setHeaderText("Não foi possível salvar!");
                    alert1.setContentText(e.getMessage());
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                }
            } else {
                try {
                    DAOAlim_Replica d = new DAOAlim_Replica();
                    a = new Alim_Replica(almto.getId(), animal.getId_animal(), Double.parseDouble(qtd_tx.getText()));
                    d.cadastro(a);
                    Main.tocaSucesso();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Sucesso.");
                    alert1.setHeaderText("Alimentação registrada com sucesso.");
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    ((Stage) id_alim_tx.getScene().getWindow()).close();
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Atenção");
                    alert1.setHeaderText("Não foi possível salvar!");
                    alert1.setContentText(e.getMessage());
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                }
            }

        }
    }

    @FXML
    public void listarAnimal() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("ConsultaAnimal.fxml"));
        Parent root = fxmlDB.load();
        Scene scene = new Scene(root);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Consulta [Animal] - BoviPlus");
        c.show();
        Main.window = true;
    }

    @FXML
    public void listarAlimentos() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("Alimentos.fxml"));
        Parent root = fxmlDB.load();
        Scene scene = new Scene(root);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Alimentos [Alimentação] - BoviPlus");
        c.show();
        Main.window = true;
    }

    @FXML
    private void buscaAlimento() throws ClassNotFoundException, IllegalAccessException, FileNotFoundException, InstantiationException {
        if (id_alim_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do alimento!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                int id_alimento = Integer.parseInt(id_alim_tx.getText());
                DAOAlimento alimento_dao = new DAOAlimento();
                almto = alimento_dao.consultaAlimento(id_alimento);
                cod_alimento = Integer.parseInt(id_alim_tx.getText());
                cod_alimento_tx.setText(almto.getNome());
            } catch (SQLException e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O alimento não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }

    @FXML
    private void buscaAnimal() throws ClassNotFoundException, IllegalAccessException, FileNotFoundException, InstantiationException {
        if (id_anim_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                int id_animal = Integer.parseInt(id_anim_tx.getText());
                DAOAnimal animal_dao = new DAOAnimal();
                animal = animal_dao.consultaAnimal(id_animal);
                cod_animal = Integer.parseInt(id_anim_tx.getText());
                cod_animal_tx.setText(animal.getNome());
            } catch (SQLException e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O animal não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }

    @FXML
    private void limparTela() {
        try {
            if (alt) {
                Main.tocaConfirma();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Existem alterações não salvas!");
                alert1.setHeaderText("Existem alterações que não foram salvas, deseja limpar assim mesmo?");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {

                    } else {
                        throw new ArithmeticException();
                    }
                });
            }
        } catch (ArithmeticException a) {
            return;
        }
        id_alim_tx.setDisable(true);
        id_anim_tx.setDisable(true);
        alt = false;
        carregado = false;
        cod_alimento_tx.clear();
        id_alim_tx.clear();
        id_anim_tx.clear();
        cod_animal_tx.clear();
        qtd_tx.clear();
        almto = null;
        animal = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Stage u = (Stage) paneprincipal.getScene().getWindow();
            u.setOnCloseRequest(b -> {
                if (alt) {
                    Main.tocaConfirma();
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Existem alterações não salvas!");
                    alert1.setHeaderText("Existem alterações que não foram salvas, deseja sair assim mesmo?");
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {

                        } else {
                            b.consume();
                        }
                    });
                }
            });
        });

    }
}
