package Telas;

import DAO.DAOAnimal;
import DAO.DAORaca;
import Entidades.Animal;
import Entidades.Raca;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CadastroAnimalController implements Initializable {

    private int id_animal_antigo;
    private int carregado = 0;
    private Animal mae, pai;
    private boolean alt = false;

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TextField codigo_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private TextField nome_tx;

    @FXML
    private TextField peso_tx;

    @FXML
    private DatePicker data_picker;

    @FXML
    private Button hoje_bt;

    @FXML
    private TextField paiid_tx;

    @FXML
    private TextField pai_tx;

    @FXML
    private Button buscarpai_tx;

    @FXML
    private TextField maeid_tx;

    @FXML
    private TextField mae_tx;

    @FXML
    private Button buscarmae_tx;

    @FXML
    private TextArea descricao_tx;

    @FXML
    private RadioButton vaca_rbt;

    @FXML
    private ToggleGroup tipo;

    @FXML
    private RadioButton boi_rbt;

    @FXML
    private RadioButton bezerro_rbt;

    @FXML
    private RadioButton novilha_rbt;

    @FXML
    private ComboBox<Racinha> raca_combo;

    @FXML
    private CheckBox lac_cbox;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button excluir_bt;

    @FXML
    private Button listpai_bt;

    @FXML
    private Button listmae_bt;

    private Raca raca1;
    private ObservableList<Racinha> listraca;

    public class Racinha {

        public int id;
        public String nome;

        public Racinha(int id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }
    }

    public void initCBox() {
        raca_combo.setConverter(new StringConverter<Racinha>() {
            @Override
            public String toString(Racinha object) {
                return object.nome;
            }

            @Override
            public Racinha fromString(String string) {
                return raca_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });
        listraca = FXCollections.observableArrayList();
        try {
            DAORaca dd = new DAORaca();
            List<Raca> add = dd.listarTudo();
            for (Raca ik : add) {
                listraca.add(new Racinha(ik.getId_raca(), ik.getNome()));
            }
            raca_combo.setItems(listraca);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void autoSelectRaca(int id) {
        for (Racinha t : listraca) {
            if (t.id == id) {
                raca_combo.getSelectionModel().select(t);
            }
        }
    }

    @FXML
    private void alteracao() {
        alt = true;
    }

    @FXML
    public void hojeData() {
        data_picker.setValue(LocalDate.now());
    }

    @FXML
    public void salvaAnimal() {
        if (nome_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Informe um nome para salvar...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            nome_tx.requestFocus();
        } else {
            int tipo1;
            if (vaca_rbt.isSelected()) {
                tipo1 = 1;
            } else if (boi_rbt.isSelected()) {
                tipo1 = 2;
            } else if (bezerro_rbt.isSelected()) {
                tipo1 = 3;
            } else {
                tipo1 = 4;
            }

            if (carregado == 1) {
                try {
                    DAOAnimal d = new DAOAnimal();
                    Animal temp = d.consultaAnimal(id_animal_antigo);
                    if (temp.isLactacao() != lac_cbox.isSelected()) {
                        d.altHistorico(temp, lac_cbox.isSelected(), LocalDate.now());
                        if (lac_cbox.isSelected()) {
                            d.addOrdem(temp.getId_animal());
                        } else {
                            d.delOrdem(temp.getId_animal());
                        }

                    }
                    d.atualizarAnimal(id_animal_antigo, nome_tx.getText(), data_picker.getValue().toString(), Double.parseDouble(peso_tx.getText()), descricao_tx.getText(), mae.getId_animal(), pai.getId_animal(), tipo1, raca_combo.getSelectionModel().getSelectedItem().id, lac_cbox.isSelected(), 0, Integer.parseInt(codigo_tx.getText()));
                    Main.tocaSucesso();
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    alert2.setGraphic(imageView);
                    ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert2.setTitle("Sucesso!");
                    alert2.setHeaderText("O Animal foi atualizado!");
                    alert2.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                        }
                    });
                    alt = false;
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    alert2.setGraphic(imageView);
                    ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert2.setTitle("Falha!");
                    alert2.setHeaderText("O Animal não pode ser atualizado!");
                    alert2.setContentText(e.getMessage());
                    alert2.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                        }
                    });
                }
            } else {
                try {
                    DAOAnimal b = new DAOAnimal();
                    Animal novo = new Animal();
                    if (codigo_tx.getText().equals("")) {
                        novo.setId_animal(b.proximoID());
                    } else {
                        novo.setId_animal(Integer.parseInt(codigo_tx.getText()));
                    }
                    novo.setNome(nome_tx.getText());
                    novo.setDescricao(descricao_tx.getText());
                    if (!peso_tx.getText().trim().isEmpty()) {
                        novo.setPeso(Double.parseDouble(peso_tx.getText()));
                    }
                    if (data_picker.getValue() != null) {
                        novo.setNascimento(data_picker.getValue().toString());
                    }
                    novo.setId_tipo(tipo1);
                    novo.setId_raca(raca_combo.getSelectionModel().getSelectedItem().id);
                    if (maeid_tx.getText().equals("")) {
                        maeid_tx.setText("0");
                    }
                    if (paiid_tx.getText().equals("")) {
                        paiid_tx.setText("0");
                    }
                    buscaMae();
                    buscaPai();
                    novo.setId_mae(mae.getId_animal());
                    novo.setId_pai(pai.getId_animal());
                    novo.setLactacao(lac_cbox.isSelected());
                    novo.setId_lote(0);
                    b.cadastrarAnimal(novo);
                    if (lac_cbox.isSelected()) {
                        b.addHistorico(novo, true, LocalDate.now());
                        b.addOrdem(novo.getId_animal());
                    }
                    codigo_tx.setText(Integer.toString(novo.getId_animal()));
                    alt = false;
                    carregaAnimal();
                    Main.tocaSucesso();
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    Image image = new Image(new File("ok.png").toURI().toString());
                    ImageView imageView = new ImageView(image);
                    alert2.setTitle("Sucesso!");
                    alert2.setGraphic(imageView);
                    alert2.setHeaderText("O Animal foi cadastrado, seu Código é: " + novo.getId_animal() + ".");
                    ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert2.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                        }
                    });

                } catch (Exception e) {
                    Main.tocaErro();
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    alert2.setGraphic(imageView);
                    ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert2.setTitle("Falha!");
                    alert2.setHeaderText("O Animal não pode ser cadastrado!");
                    alert2.setContentText(e.getMessage());
                    alert2.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                        }
                    });
                }
            }
        }

    }

    @FXML
    public void apagaAnimal() {
        if (carregado == 1) {
            Main.tocaConfirma();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção.");
            alert.setHeaderText("Você tem certeza que deseja apagar o animal " + nome_tx.getText() + "?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        DAOAnimal user = new DAOAnimal();
                        user.deletarAnimal(Integer.parseInt(codigo_tx.getText()));
                        Main.tocaSucesso();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView aa = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert2.setGraphic(aa);
                        ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert2.setTitle("Sucesso!");
                        alert2.setHeaderText("O Animal foi apagado!");
                        alert2.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
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
                        alert1.setHeaderText("Não foi possível apagar o animal, tente novamente!");
                        alert1.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                            }
                        });
                    }
                } else {
                    limpaTela();
                }
            });
        }
    }

    public void setPai(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        paiid_tx.setText(Integer.toString(id));
        buscaPai();
        alt = true;
    }

    public void setMae(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        maeid_tx.setText(Integer.toString(id));
        buscaMae();
        alt = true;
    }

    @FXML
    public void listPai() throws IOException {
        Stage a = new Stage();
        Main.consultaAnimal = 2;
        a.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("ConsultaAnimal.fxml"));
        Parent root = fxmlDB.load();
        ConsultaAnimalController ee = fxmlDB.getController();
        Scene scene = new Scene(root);
        a.getIcons().add(new Image("/Imagens/Iconew.png"));
        a.setScene(scene);
        a.setTitle("Consulta [Animal] - BoviPlus");
        a.show();
        ee.cac = this;
        Main.window = true;
    }

    @FXML
    public void listMae() throws IOException {
        Stage a = new Stage();
        Main.consultaAnimal = 1;
        a.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("ConsultaAnimal.fxml"));
        Parent root = fxmlDB.load();
        ConsultaAnimalController ee = fxmlDB.getController();
        Scene scene = new Scene(root);
        a.getIcons().add(new Image("/Imagens/Iconew.png"));
        a.setScene(scene);
        a.setTitle("Consulta [Animal] - BoviPlus");
        a.show();
        ee.cac = this;
        Main.window = true;
    }

    @FXML
    public void desabilitaLactacao() {
        lac_cbox.setSelected(false);
        lac_cbox.setDisable(true);
    }

    @FXML
    public void habilitaLactacao() {
        lac_cbox.setDisable(false);
    }

    @FXML
    public void limpaTela() {
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
        alt = false;
        carregado = 0;
        codigo_tx.setText("");
        nome_tx.setText("");
        peso_tx.setText("");
        lac_cbox.setDisable(false);
        lac_cbox.setSelected(true);
        data_picker.setValue(null);
        vaca_rbt.setSelected(true);
        autoSelectRaca(1);
        pai_tx.setText("");
        mae_tx.setText("");
        paiid_tx.clear();
        maeid_tx.clear();
        mae = null;
        pai = null;
        descricao_tx.setText("");
    }

    @FXML
    public void carregaAnimal() {
        try {
            if (alt) {
                Main.tocaConfirma();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Existem alterações não salvas!");
                alert1.setHeaderText("Existem alterações que não foram salvas, deseja carregar assim mesmo?");
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
        if (codigo_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para carregar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                }
            });
        } else {
            try {
                DAOAnimal da = new DAOAnimal();
                int id_animal = Integer.parseInt(codigo_tx.getText());
                Animal a = da.consultaAnimal(id_animal);
                id_animal_antigo = Integer.parseInt(codigo_tx.getText());
                nome_tx.setText(a.getNome());
                peso_tx.setText(Double.toString(a.getPeso()));
                data_picker.setValue(LocalDate.parse(a.getNascimento()));
                if (a.getId_tipo() == 2) {
                    tipo.selectToggle(boi_rbt);
                    lac_cbox.setDisable(true);
                } else if (a.getId_tipo() == 3) {
                    tipo.selectToggle(bezerro_rbt);
                    lac_cbox.setDisable(true);
                } else if (a.getId_tipo() == 4) {
                    tipo.selectToggle(novilha_rbt);
                    lac_cbox.setDisable(true);
                } else {
                    tipo.selectToggle(vaca_rbt);
                    lac_cbox.setDisable(false);
                }
                lac_cbox.setSelected(a.isLactacao());
                autoSelectRaca(a.getId_raca());
                selecionaPai(a.getId_pai());
                selecionaMae(a.getId_mae());
                descricao_tx.setText(a.getDescricao());
                carregado = 1;
                alt = false;
                if (id_animal == 0) {
                    throw new IllegalAccessException();
                }
            } catch (Exception e) {
                Main.tocaErro();
                limpaTela();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O Animal não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
            }

        }
    }

    private void selecionaMae(int id_mae) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {

        DAOAnimal da = new DAOAnimal();
        mae = da.consultaAnimal(id_mae);
        maeid_tx.setText(Integer.toString(id_mae));
        mae_tx.setText(mae.getNome());
    }

    private void selecionaPai(int id_pai) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {

        DAOAnimal da = new DAOAnimal();
        pai = da.consultaAnimal(id_pai);
        paiid_tx.setText(Integer.toString(id_pai));
        pai_tx.setText(pai.getNome());
    }

    private void buscaRaca(int id_raca) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAORaca da = new DAORaca();
        raca1 = da.consultar_raca(id_raca);
    }

    @FXML
    private void buscaMae() throws ClassNotFoundException, IllegalAccessException, FileNotFoundException, InstantiationException {
        if (maeid_tx.getText().equals("")) {
            Main.tocaErro();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                }
            });
        } else {
            try {
                int id_animal = Integer.parseInt(maeid_tx.getText());
                DAOAnimal da = new DAOAnimal();
                mae = da.consultaAnimal(id_animal);
                if (mae.getId_tipo() != 1 && mae.getId_tipo() != 0) {
                    throw new ArithmeticException();
                }
                mae_tx.setText(mae.getNome());
            } catch (SQLException e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O Animal não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
            } catch (ArithmeticException kk) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Tipo incompatível");
                alert1.setHeaderText("A mãe não pode ser do tipo BOI, BEZERRO ou NOVILHA!");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
            }
        }
    }

    @FXML
    private void buscaPai() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (paiid_tx.getText().equals("")) {
            Main.tocaErro();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                }
            });
        } else {
            try {
                int id_animal = Integer.parseInt(paiid_tx.getText());
                DAOAnimal da = new DAOAnimal();
                pai = da.consultaAnimal(id_animal);
                if (pai.getId_tipo() != 2 && pai.getId_tipo() != 0) {
                    throw new ArithmeticException();
                }
                pai_tx.setText(pai.getNome());
            } catch (SQLException e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O Animal não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
            } catch (ArithmeticException kk) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Tipo incompatível");
                alert1.setHeaderText("O pai não pode ser do tipo VACA, BEZERRO ou NOVILHA!");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCBox();
        autoSelectRaca(1);
        if (Main.window) {
            Main.stage.setTitle("Cadastro [Animal] - BoviPlus");
        } else {
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

        if (!Main.id_animal.equals("0")) {
            codigo_tx.setText(Main.id_animal);
            carregaAnimal();
            Main.id_animal = "0";
        }

    }

}
