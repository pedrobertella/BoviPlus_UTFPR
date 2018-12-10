package Telas;

import DAO.DAOAlimento;
import Entidades.Alimento;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CadastroAlimentoController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TextField nome_tx;

    @FXML
    private ComboBox<Tipo_unid> tipo_combo;

    @FXML
    private ComboBox<Tipo_unid> unid_combo;

    public class Tipo_unid {

        public int id;
        public String nome;

        public Tipo_unid(int id, String nome) {
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

    Alimento a;
    ObservableList<Tipo_unid> listTipo = FXCollections.observableArrayList(), listUnid = FXCollections.observableArrayList();
    boolean carregado = false;

    @FXML
    public void estoque() throws IOException {
        if (carregado) {
            Stage c = new Stage();
            c.setResizable(false);
            FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("CadastroEstoque.fxml"));
            Parent root = fxmlDB.load();
            CadastroEstoqueController cont = fxmlDB.getController();
            Scene scene = new Scene(root);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Lançamento de Estoque [Alimentação] - BoviPlus");
            cont.carregar(a.getId());
            c.show();
        }

    }

    public void initComboBoxes() {
        tipo_combo.setConverter(new StringConverter<Tipo_unid>() {
            @Override
            public String toString(Tipo_unid object) {
                return object.nome;
            }

            @Override
            public Tipo_unid fromString(String string) {
                return tipo_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        unid_combo.setConverter(new StringConverter<Tipo_unid>() {
            @Override
            public String toString(Tipo_unid object) {
                return object.nome;
            }

            @Override
            public Tipo_unid fromString(String string) {
                return unid_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        listTipo.add(new Tipo_unid(1, "Cereal"));
        listTipo.add(new Tipo_unid(2, "Farelo"));
        listTipo.add(new Tipo_unid(3, "Ração"));
        listTipo.add(new Tipo_unid(4, "Tubérculo"));
        listTipo.add(new Tipo_unid(5, "Silagem"));
        listTipo.add(new Tipo_unid(6, "Forragem"));
        listTipo.add(new Tipo_unid(7, "Outros"));

        listUnid.add(new Tipo_unid(1, "KG"));
        listUnid.add(new Tipo_unid(2, "Fardo"));
        listUnid.add(new Tipo_unid(3, "Saca"));
        listUnid.add(new Tipo_unid(4, "Outros"));

        tipo_combo.setItems(listTipo);
        unid_combo.setItems(listUnid);
    }

    private void autoSelectTipo(int id) {
        for (Tipo_unid t : listTipo) {
            if (t.id == id) {
                tipo_combo.getSelectionModel().select(t);
            }
        }
    }

    private void autoSelectUnid(int id) {
        for (Tipo_unid t : listUnid) {
            if (t.id == id) {
                unid_combo.getSelectionModel().select(t);
            }
        }
    }

    public void carregar(int id_alimento) {
        try {
            DAOAlimento d = new DAOAlimento();
            a = d.consultaAlimento(id_alimento);
            nome_tx.setText(a.getNome());
            autoSelectTipo(a.getTipo());
            autoSelectUnid(a.getUnid());
            carregado = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void salvar() {
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
        } else if (tipo_combo.getSelectionModel().getSelectedItem() == null) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa selecionar um tipo...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        } else if (unid_combo.getSelectionModel().getSelectedItem() == null) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa selecionar uma unidade...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        } else {
            if (!carregado) {
                try {
                    DAOAlimento d = new DAOAlimento();
                    Alimento k = new Alimento(d.proximoID(), nome_tx.getText(), tipo_combo.getSelectionModel().getSelectedItem().id, unid_combo.getSelectionModel().getSelectedItem().id);
                    d.cadastrarAlimento(k);
                    a = k;
                    Main.tocaSucesso();
                    Alert ko = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Sucesso!");
                    ko.setHeaderText("O alimento foi Cadastrado!");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                    carregado = true;
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert ko = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("Ocorreu um erro ao salvar!");
                    ko.setContentText(e.getMessage());
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                }

            } else {
                try {
                    DAOAlimento d = new DAOAlimento();
                    a.setNome(nome_tx.getText());
                    a.setTipo(tipo_combo.getSelectionModel().getSelectedItem().id);
                    a.setUnid(unid_combo.getSelectionModel().getSelectedItem().id);
                    d.atualizarAlimento(a);

                    Main.tocaSucesso();
                    Alert ko = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Sucesso!");
                    ko.setHeaderText("O alimento foi atualizado!");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });

                } catch (Exception e) {
                    Main.tocaErro();
                    Alert ko = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("Ocorreu um erro ao atualizar!");
                    ko.setContentText(e.getMessage());
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                }
            }
        }

    }

    @FXML
    public void limpar() {
        nome_tx.clear();
        tipo_combo.getSelectionModel().select(null);
        unid_combo.getSelectionModel().select(null);
        carregado = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBoxes();
    }

}
