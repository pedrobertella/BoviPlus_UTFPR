package Telas;

import DAO.DAOVacina;
import Entidades.Vacina;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CadastroVacinaController implements Initializable {

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
    private Button listvacinas_bt;

    @FXML
    private Label nome_label;

    @FXML
    private TextField nome_tx;

    @FXML
    private RadioButton periodica_rdbt;

    @FXML
    private ToggleGroup tipo;

    @FXML
    private RadioButton extemp_rdbt;

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

    @FXML
    private Label camp_lbl;

    @FXML
    private Label mes_lbl;

    @FXML
    private Label cat_ali_lbl;

    @FXML
    private TextArea campanha_tx;

    @FXML
    private TextArea cat_ani_tx;

    @FXML
    private ComboBox<Mes> mes_combo;

    private int id_vacina;
    private boolean vacinaCarregada = false;
    private boolean define = true;

    private ObservableList<Mes> listmes;

    public class Mes {

        public int id;
        public String nome;

        public Mes(int id, String nome) {
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
        mes_combo.setConverter(new StringConverter<Mes>() {
            @Override
            public String toString(Mes object) {
                return object.nome;
            }

            @Override
            public Mes fromString(String string) {
                return mes_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });
        listmes = FXCollections.observableArrayList();
        try {
            List<Mes> meses = new ArrayList();
            meses.add(new Mes(1, "Janeiro"));
            meses.add(new Mes(2, "Fevereiro"));
            meses.add(new Mes(3, "Março"));
            meses.add(new Mes(4, "Abril"));
            meses.add(new Mes(5, "Maio"));
            meses.add(new Mes(6, "Junho"));
            meses.add(new Mes(7, "Julho"));
            meses.add(new Mes(8, "Agosto"));
            meses.add(new Mes(9, "Setembro"));
            meses.add(new Mes(10, "Outubro"));
            meses.add(new Mes(11, "Novembro"));
            meses.add(new Mes(12, "Dezembro"));

            for (int i = 0; i < 12; i++) {
                listmes.add(meses.get(i));
            }
            mes_combo.setItems(listmes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void autoSelectMes(int id) {
        for (Mes t : listmes) {
            if (t.id == id) {
                mes_combo.getSelectionModel().select(t);
            }
        }
    }

    @FXML
    public void listVacinas() throws IOException {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaVacina.fxml"));
            Scene scene = new Scene(fxmlDB);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Consulta de Vacinas [Saúde] - BoviPlus");
            c.show();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

    }

    @FXML
    public void habilitaCampos() {
        if (periodica_rdbt.isSelected()) {
            define = false;
        } else {
            define = true;
        }
        camp_lbl.setDisable(define);
        campanha_tx.setDisable(define);
        cat_ali_lbl.setDisable(define);
        cat_ani_tx.setDisable(define);
        mes_lbl.setDisable(define);
        mes_combo.setDisable(define);
    }

    @FXML
    public void salvaVacina() {
        if (vacinaCarregada) {
            try {
                DAOVacina dao = new DAOVacina();
                Vacina vac = new Vacina();

                vac.setId_vacina(parseInt(codigo_tx.getText()));
                vac.setNome(nome_tx.getText());
                vac.setIndicacao(indicacao_tx.getText());
                vac.setContra_indc(contraindic_tx.getText());
                vac.setPeriodica(!define);
                vac.setCampanha(campanha_tx.getText());
                vac.setMes(mes_combo.getSelectionModel().getSelectedItem().nome);
                vac.setCat_animais(cat_ani_tx.getText());

                //System.out.println("passando dados pra dao");
                dao.atualizarVacina(vac.getId_vacina(), vac.getNome(), vac.getIndicacao(), vac.getContra_indc(), vac.getPeriodica(), vac.getCampanha(), vac.getMes(), vac.getCat_animais());
                //System.out.println("saiu da dao");
                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("A vacina foi atualizada, com os valores:\nCódigo: " + vac.getId_vacina() + ";\nNome: " + vac.getNome()
                        + ";\nIndicação: " + vac.getIndicacao() + ";\nContra indicação: " + vac.getContra_indc() + "...");
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
                alert2.setHeaderText("A vacina não pode ser atualizada"
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
                DAOVacina dao = new DAOVacina();
                Vacina vac = new Vacina();
                if (codigo_tx.getText().trim().isEmpty()) {
                    vac.setId_vacina(dao.proximoID());

                } else {
                    vac.setId_vacina(parseInt(codigo_tx.getText()));
                }

                vac.setNome(nome_tx.getText());
                vac.setIndicacao(indicacao_tx.getText());
                vac.setContra_indc(contraindic_tx.getText());
                vac.setPeriodica(!define);
                System.out.println("define");
                System.out.println(define);
                if (define == false) {
                    vac.setCampanha(campanha_tx.getText());
                    vac.setMes(mes_combo.getSelectionModel().getSelectedItem().nome);
                    vac.setCat_animais(cat_ani_tx.getText());
                    System.out.println("if define");
                }

                dao.cadastrarVacina(vac);
                System.out.println("SAIU DAO");
                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("A vacina foi cadastrada, seu código é: " + vac.getId_vacina() + "!");
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
                alert2.setHeaderText("A vacina não pode ser cadastrada!");
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
    public void carregaVacina() {
        if (codigo_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para carregar digite o código da vacina!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            limpaTela();
        } else {
            try {
                DAOVacina dao = new DAOVacina();
                Vacina vacina = new Vacina();
                id_vacina = Integer.parseInt(codigo_tx.getText());

                vacina = dao.consultaVacina(id_vacina);

                nome_tx.setText(vacina.getNome());
                indicacao_tx.setText(vacina.getIndicacao());
                contraindic_tx.setText(vacina.getContra_indc());
                if (vacina.getPeriodica() == false) {
                    tipo.selectToggle(extemp_rdbt);
                    campanha_tx.setText("");
                    autoSelectMes(1);
                    cat_ani_tx.setText("");
                    camp_lbl.setDisable(true);
                    campanha_tx.setDisable(true);
                    cat_ali_lbl.setDisable(true);
                    cat_ani_tx.setDisable(true);
                    mes_lbl.setDisable(true);
                    mes_combo.setDisable(true);
                } else {
                    camp_lbl.setDisable(false);
                    campanha_tx.setDisable(false);
                    cat_ali_lbl.setDisable(false);
                    cat_ani_tx.setDisable(false);
                    mes_lbl.setDisable(false);
                    mes_combo.setDisable(false);
                    tipo.selectToggle(periodica_rdbt);
                    campanha_tx.setText(vacina.getCampanha());
                    autoSelectMes(vacina.getId_vacina());
                    cat_ani_tx.setText(vacina.getCat_animais());
                }

                if (id_vacina == 0) {
                    throw new IllegalAccessException();
                }
                System.out.println("carregou vacina");
                vacinaCarregada = true;
            } catch (Exception e) {
                System.out.println("nao carregou vacina");
                limpaTela();
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("A vacina não foi encontrada");
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
        tipo.selectToggle(extemp_rdbt);
        habilitaCampos();
        campanha_tx.setText("");
        autoSelectMes(1);
        cat_ani_tx.setText("");
        vacinaCarregada = false;
    }

    @FXML
    public void apagaVacina() {
        if (vacinaCarregada) {
            Main.tocaConfirma();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Atenção.");
            alert.setHeaderText("Você tem certeza que deseja apagar a vacina " + nome_tx.getText() + "?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        DAOVacina dao = new DAOVacina();
                        dao.deletarVacina(Integer.parseInt(codigo_tx.getText()));
                        Main.tocaSucesso();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView aa = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert2.setGraphic(aa);
                        ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert2.setTitle("Sucesso!");
                        alert2.setHeaderText("A vacina foi apagada!");
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
                        alert1.setHeaderText("Não foi possível apagar a vacina, tente novamente!");
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

        initCBox();
        autoSelectMes(1);
        if (Main.window) {
            Main.stage.setTitle("Cadastro de Vacina [Saúde] - BoviPlus");
        }
    }

}
