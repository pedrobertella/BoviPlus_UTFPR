package Telas;

import ConexaoBD.Conexao;
import java.io.File;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PreferenciasSistemaController implements Initializable {

    @FXML
    private TextField diasex_tx;

    @FXML
    private TextField dianasc_tx;

    @FXML
    private Button salvar_bt;

    @FXML
    private TextField cpagar_tx;

    @FXML
    private TextField creceber_tx;

    @FXML
    private TextField vacina_tx;

    @FXML
    private TextField exame_tx;

    @FXML
    private TextField estoque_tx;

    @FXML
    private ComboBox<tela> anmal_combo;

    @FXML
    private ComboBox<tela> prod_combo;

    @FXML
    private ComboBox<tela> reprod_combo;

    @FXML
    private ComboBox<tela> alim_combo;

    @FXML
    private ComboBox<tela> financ_combo;

    @FXML
    private CheckBox sons_cbox;

    @FXML
    private CheckBox alim_cbox;

    public class tela {

        public String nome, file;

        public tela(String nome, String file) {
            this.nome = nome;
            this.file = file;
        }

        public String getNome() {
            return nome;
        }

        public String getFile() {
            return file;
        }

    }

    ObservableList<tela> listA = FXCollections.observableArrayList();
    ObservableList<tela> listB = FXCollections.observableArrayList();
    ObservableList<tela> listC = FXCollections.observableArrayList();
    ObservableList<tela> listD = FXCollections.observableArrayList();
    ObservableList<tela> listE = FXCollections.observableArrayList();

    private void attConfig() {
        try {
            Main.pref.diasec = Integer.parseInt(diasex_tx.getText());
            Main.pref.dianasc = Integer.parseInt(dianasc_tx.getText());
            Main.pref.cpagar = Integer.parseInt(cpagar_tx.getText());
            Main.pref.creceber = Integer.parseInt(creceber_tx.getText());
            Main.pref.diaexame = Integer.parseInt(exame_tx.getText());
            Main.pref.diavacina = Integer.parseInt(vacina_tx.getText());
            Main.pref.diasest = Integer.parseInt(estoque_tx.getText());

            Main.pref.a1 = anmal_combo.getSelectionModel().getSelectedItem().file;
            Main.pref.a2 = prod_combo.getSelectionModel().getSelectedItem().file;
            Main.pref.a3 = reprod_combo.getSelectionModel().getSelectedItem().file;
            Main.pref.a4 = alim_combo.getSelectionModel().getSelectedItem().file;
            Main.pref.a5 = financ_combo.getSelectionModel().getSelectedItem().file;

            Main.pref.mudo = !sons_cbox.isSelected();
            Main.pref.autoalimentacao = alim_cbox.isSelected();

            Conexao c = new Conexao();
            c.abrirConexao();

            String sql = "UPDATE PREFSIS SET ATALHO1 = '" + Main.pref.a1 + "', atalho2 = '" + Main.pref.a2 + "', prevestoque = "+Main.pref.diasest+", atalho3 = '" + Main.pref.a3 + "', atalho4 = '" + Main.pref.a4 + "', atalho5 = '" + Main.pref.a5 + "', aviso_nascimento = " + Main.pref.dianasc + ", aviso_secagem = " + Main.pref.diasec + ", cpagar = " + Main.pref.cpagar + ", creceber = " + Main.pref.creceber + " , aviso_exame = " + Main.pref.diaexame + ", aviso_vacina = " + Main.pref.diavacina + ", mudo = " + Main.pref.mudo + ", autoalimentacao = " + Main.pref.autoalimentacao + " where id = 1; ";
            Statement stm = c.getConnection().createStatement();
            stm.execute(sql);

            Main.tocaSucesso();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Sucesso!");
            alert2.setHeaderText("Suas preferências foram salvas!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

        } catch (NumberFormatException n) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Digite apenas números nos campos de aviso!");
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
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Ocorreu um erro ao salvar suas preferências...");
            alert2.setContentText(e.getMessage());
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void initComboBoxes() {

        anmal_combo.setConverter(new StringConverter<tela>() {
            @Override
            public String toString(tela object) {
                return object.nome;
            }

            @Override
            public tela fromString(String string) {
                return anmal_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        prod_combo.setConverter(new StringConverter<tela>() {
            @Override
            public String toString(tela object) {
                return object.nome;
            }

            @Override
            public tela fromString(String string) {
                return prod_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        reprod_combo.setConverter(new StringConverter<tela>() {
            @Override
            public String toString(tela object) {
                return object.nome;
            }

            @Override
            public tela fromString(String string) {
                return reprod_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        alim_combo.setConverter(new StringConverter<tela>() {
            @Override
            public String toString(tela object) {
                return object.nome;
            }

            @Override
            public tela fromString(String string) {
                return alim_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        financ_combo.setConverter(new StringConverter<tela>() {
            @Override
            public String toString(tela object) {
                return object.nome;
            }

            @Override
            public tela fromString(String string) {
                return financ_combo.getItems().stream().filter(ap
                        -> ap.nome.equals(string)).findFirst().orElse(null);
            }

        });

        listA.add(new tela("Cadastro", "CadastroAnimal.fxml"));
        listA.add(new tela("Consulta", "ConsultaAnimal.fxml"));

        listB.add(new tela("Registro Simplificado", "ProducaoSimplificado.fxml"));
        listB.add(new tela("Registro em Lote", "CadastroProducao.fxml"));
        listB.add(new tela("Registro Rápido", "ProducaoRapido.fxml"));
        listB.add(new tela("Consulta", "ConsultaProducao.fxml"));
        listB.add(new tela("Rendimento por Animal", "RendimentoIndividual.fxml"));
        listB.add(new tela("Rendimento por Grupo", "RendimentoGrupo.fxml"));
        listB.add(new tela("Analítico", "AnaliticoProducao.fxml"));

        listC.add(new tela("Registro", "RegistroInseminacao.fxml"));
        listC.add(new tela("Acompanhamento", "AcompanhamentoReproducao.fxml"));

        listD.add(new tela("Alimentos", "Alimentos.fxml"));
        listD.add(new tela("Alimentações", "ConsultaAlimentacao.fxml"));
        listD.add(new tela("Previsão de Estoque", "AnaliticoAlimentacao.fxml"));

        listE.add(new tela("Contas a Pagar", "ContasPagar.fxml"));
        listE.add(new tela("Contas a Receber", "ContasReceber.fxml"));
        listE.add(new tela("Analítico", "AnaliticoFinanceiro.fxml"));

        anmal_combo.setItems(listA);
        prod_combo.setItems(listB);
        reprod_combo.setItems(listC);
        alim_combo.setItems(listD);
        financ_combo.setItems(listE);
    }

    @FXML
    public void salvar() {
        attConfig();
    }

    private void autoSelectA(String file) {
        for (tela t : listA) {
            if (t.file.equals(file)) {
                anmal_combo.getSelectionModel().select(t);
            }
        }
    }

    private void autoSelectB(String file) {
        for (tela t : listB) {
            if (t.file.equals(file)) {
                prod_combo.getSelectionModel().select(t);
            }
        }
    }

    private void autoSelectC(String file) {
        for (tela t : listC) {
            if (t.file.equals(file)) {
                reprod_combo.getSelectionModel().select(t);
            }
        }
    }

    private void autoSelectD(String file) {
        for (tela t : listD) {
            if (t.file.equals(file)) {
                alim_combo.getSelectionModel().select(t);
            }
        }
    }

    private void autoSelectE(String file) {
        for (tela t : listE) {
            if (t.file.equals(file)) {
                financ_combo.getSelectionModel().select(t);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Preferências [Sistema] - BoviPlus");
        }
        initComboBoxes();
        try {
            diasex_tx.setText(Integer.toString(Main.pref.diasec));
            dianasc_tx.setText(Integer.toString(Main.pref.dianasc));
            creceber_tx.setText(Integer.toString(Main.pref.creceber));
            cpagar_tx.setText(Integer.toString(Main.pref.cpagar));
            exame_tx.setText(Integer.toString(Main.pref.diaexame));
            vacina_tx.setText(Integer.toString(Main.pref.diavacina));
            estoque_tx.setText(Integer.toString(Main.pref.diasest));

            autoSelectA(Main.pref.a1);
            autoSelectB(Main.pref.a2);
            autoSelectC(Main.pref.a3);
            autoSelectD(Main.pref.a4);
            autoSelectE(Main.pref.a5);

            sons_cbox.setSelected(!Main.pref.mudo);
            alim_cbox.setSelected(Main.pref.autoalimentacao);
        } catch (Exception e) {
            System.out.println("EXCEPTION");
        }

    }

}
