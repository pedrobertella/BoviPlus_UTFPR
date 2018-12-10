package Telas;

import DAO.DAOContas_Receber;
import DAO.DAOEntrega_Leite;
import DAO.DAOProducao;
import Entidades.Contas_Receber;
import Entidades.Entrega_Leite;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ReceberProducaoController implements Initializable {

    public Stage ss;
    private double total;
    private DAOContas_Receber dc;
    private Contas_Receber cr;
    private DAOEntrega_Leite de;
    private Entrega_Leite el;
    private boolean carregado;
    private boolean loaded;
    private DAOProducao dp;
    private boolean alt = false;

    @FXML
    private TextField cod_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private DatePicker inicio_dp;

    @FXML
    private Button ihj_bt;

    @FXML
    private Button i7_bt;

    @FXML
    private Button i15_bt;

    @FXML
    private Button i30_bt;

    @FXML
    private DatePicker fim_dp;

    @FXML
    private Button fhj_bt;

    @FXML
    private Button f7_bt;

    @FXML
    private Button f15_bt;

    @FXML
    private Button f30_bt;

    @FXML
    private Button load_bt;

    @FXML
    private TextField custo_tx;

    @FXML
    private DatePicker venc_dp;

    @FXML
    private Button dias15_bt;

    @FXML
    private Button dias30_bt;

    @FXML
    private Button dias45_bt;

    @FXML
    private DatePicker pag_dp;

    @FXML
    private Button hoje_bt;

    @FXML
    private TextField litros_tx;

    @FXML
    private TextField total_tx;

    @FXML
    private Button consultar_bt;

    @FXML
    private void alteracao() {
        alt = true;
    }

    @FXML
    public void hoje() {
        pag_dp.setValue(LocalDate.now());
    }

    @FXML
    public void dias15() {
        venc_dp.setValue(LocalDate.now().plusDays(15));
    }

    @FXML
    public void dias30() {
        venc_dp.setValue(LocalDate.now().plusDays(30));
    }

    @FXML
    public void dias45() {
        venc_dp.setValue(LocalDate.now().plusDays(45));
    }

    @FXML
    public void ihj() {
        inicio_dp.setValue(LocalDate.now());
    }

    @FXML
    public void fhj() {
        fim_dp.setValue(LocalDate.now());
    }

    @FXML
    public void i15() {
        inicio_dp.setValue(LocalDate.now().minusDays(15));
    }

    @FXML
    public void f15() {
        fim_dp.setValue(LocalDate.now().minusDays(15));
    }

    @FXML
    public void i30() {
        inicio_dp.setValue(LocalDate.now().minusDays(30));
    }

    @FXML
    public void f30() {
        fim_dp.setValue(LocalDate.now().minusDays(30));
    }

    @FXML
    public void i7() {
        inicio_dp.setValue(LocalDate.now().minusDays(7));
    }

    @FXML
    public void f7() {
        fim_dp.setValue(LocalDate.now().minusDays(7));
    }

    @FXML
    public void consultar() throws IOException {
        ss = new Stage();
        ss.setResizable(false);
        Main.window = false;
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaProducao.fxml"));
        Scene scene = new Scene(fxmlDB);
        ss.getIcons().add(new Image("/Imagens/Iconew.png"));
        ss.setScene(scene);
        ss.setTitle("Consulta [Produção] - BoviPlus");
        ss.show();
        Main.window = true;
    }

    @FXML
    public void multiplica() {
        try {
            total = Double.parseDouble(litros_tx.getText()) * Double.parseDouble(custo_tx.getText());
            total = Math.round(total * 100) / 100;
            total_tx.setText(Double.toString(total));
        } catch (Exception e) {

        }
    }

    @FXML
    public void carrega() {
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
        if (cod_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa informar o código para carregar!");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });

        } else {
            try {
                de = new DAOEntrega_Leite();
                dc = new DAOContas_Receber();
                el = de.consultaConta(Integer.parseInt(cod_tx.getText()));
                cr = dc.consultar_contas_receber(Integer.parseInt(cod_tx.getText()));
                if (!cr.isProducao()) {
                    throw new ArithmeticException();
                }
                inicio_dp.setEditable(false);
                fim_dp.setEditable(false);
                load_bt.setDisable(true);
                inicio_dp.getEditor().setDisable(true);
                fim_dp.getEditor().setDisable(true);
                f15_bt.setDisable(true);
                fhj_bt.setDisable(true);
                f7_bt.setDisable(true);
                f30_bt.setDisable(true);
                ihj_bt.setDisable(true);
                i7_bt.setDisable(true);
                i15_bt.setDisable(true);
                i30_bt.setDisable(true);

                inicio_dp.setValue(LocalDate.parse(el.getInicio()));
                fim_dp.setValue(LocalDate.parse(el.getFim()));
                litros_tx.setText(Double.toString(el.getQtd()));
                custo_tx.setText(Double.toString(el.getValor_litro()));
                multiplica();

                venc_dp.setValue(LocalDate.parse(cr.getData_vencimento()));
                if (cr.getData_recebimento() != null) {
                    pag_dp.setValue(LocalDate.parse(cr.getData_recebimento()));
                }
                alt = false;
                loaded = true;
                carregado = true;
            } catch (ArithmeticException a) {
                Main.tocaConfirma();
                Alert ko = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("A conta a receber fornecida não é de Produção, para carregá-la"
                        + " utilize a aba GERAL ao lado.");
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
                ko.setTitle("Oops!");
                ko.setHeaderText("Conta Inválida!");
                ko.setContentText(e.getMessage());
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
            }
        }
    }

    @FXML
    public void salva() {
        if (!loaded) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa carregar produções para salvar. Verifique...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            inicio_dp.requestFocus();
        } else if (custo_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher o custo por litro para salvar. Verifique...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            custo_tx.requestFocus();
        } else if (venc_dp.getValue() == null) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher a data de vencimento para salvar. Verifique...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            venc_dp.requestFocus();
        } else {
            try {
                dc = new DAOContas_Receber();
                de = new DAOEntrega_Leite();
                Double temp = Double.parseDouble(custo_tx.getText());
                if (carregado) {
                    cr.setData_vencimento(venc_dp.getValue().toString());
                    if (pag_dp.getValue() != null) {
                        cr.setData_recebimento(pag_dp.getValue().toString());
                    } else {
                        cr.setData_recebimento(null);
                    }
                    cr.setQtd(Double.parseDouble(litros_tx.getText()));
                    cr.setValor(Double.parseDouble(custo_tx.getText()));
                    el.setValor_litro(Double.parseDouble(custo_tx.getText()));
                    dc.atualizarContas_Receber(cr);
                    de.atualizarEntrega_Leite(el.getId_entrega(), el.getQtd(), el.getInicio(), el.getFim(), el.getValor_litro(), el.getId_conta());
                    Main.tocaSucesso();
                    Alert ko = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Sucesso!");
                    ko.setHeaderText("A conta foi atualizada!");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                    alt = false;
                } else {
                    cr = new Contas_Receber();
                    el = new Entrega_Leite();
                    if (pag_dp.getValue() != null) {
                        cr.setData_recebimento(pag_dp.getValue().toString());
                    }
                    cr.setData_vencimento(venc_dp.getValue().toString());
                    cr.setQtd(Double.parseDouble(litros_tx.getText()));
                    cr.setValor(Double.parseDouble(custo_tx.getText()));
                    cr.setDescricao("PRODUCAO: " + litros_tx.getText() + " - " + fim_dp.getValue().toString());
                    cr.setProducao(true);
                    el.setId_entrega(de.nextID());
                    el.setId_conta(dc.lastID() + 1);
                    el.setFim(fim_dp.getValue().toString());
                    el.setInicio(inicio_dp.getValue().toString());
                    el.setQtd(Double.parseDouble(litros_tx.getText()));
                    el.setValor_litro(Double.parseDouble(custo_tx.getText()));
                    dc.cadastrarContas_receber(cr);
                    de.cadastrar_entrega_leite(el);
                    cod_tx.setText(Integer.toString(dc.lastID()));
                    carregado = true;
                    Main.tocaSucesso();
                    Alert ko = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Sucesso!");
                    ko.setHeaderText("A conta foi registrada!");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                    alt = false;
                }
            } catch (NumberFormatException n) {
                Main.tocaConfirma();
                Alert ko = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("Você precisa digitar um custo valido para salvar. Verifique...");
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
                ko.setHeaderText("Ocorreu um erro ao salvar!");
                ko.setContentText(e.getMessage());
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
            }
        }
    }

    @FXML
    public void loadProd() {

        if (inicio_dp.getValue() == null || fim_dp.getValue() == null) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher ambas as datas para carregar as produções...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            inicio_dp.requestFocus();
        } else if (inicio_dp.getValue().isAfter(fim_dp.getValue())) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("A data inicial não pode ser após a data final...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            inicio_dp.setValue(fim_dp.getValue());
        } else {
            if (inicio_dp.getValue().isAfter(LocalDate.now())) {
                inicio_dp.setValue(LocalDate.now());
            } else if (fim_dp.getValue().isAfter(LocalDate.now())) {
                fim_dp.setValue(LocalDate.now());
            }
            try {
                de = new DAOEntrega_Leite();
                if (de.verificaPeriodo(inicio_dp.getValue().toString(), fim_dp.getValue().toString())) {
                    throw new ArithmeticException();
                }
                dp = new DAOProducao();
                double litros = dp.producaoPeriodo(inicio_dp.getValue().toString(), fim_dp.getValue().toString());
                litros_tx.setText(Double.toString(litros));
                loaded = true;
            } catch (ArithmeticException a) {
                Main.tocaConfirma();
                Alert ko = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("Já foram lançadas contas a receber referentes a este período. Verifique...");
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
                ko.setTitle("Oops!");
                ko.setHeaderText("Ocorreu um erro ao carregar");
                ko.setContentText(e.getMessage());
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
            }

        }

    }

    private void preencheInicio() {
        try {
            de = new DAOEntrega_Leite();
            LocalDate a = LocalDate.parse(de.proximaData());
            a = a.plusDays(1);
            inicio_dp.setValue(a);
        } catch (Exception e) {
            System.out.println("ERRRORR");
        }
    }

    public void limpa() {
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
        total = 0.00;
        loaded = false;
        carregado = false;
        cr = null;
        dc = null;
        de = null;
        el = null;
        cod_tx.clear();
        litros_tx.clear();
        custo_tx.clear();
        total_tx.clear();
        inicio_dp.setValue(null);
        fim_dp.setValue(null);
        venc_dp.setValue(null);
        pag_dp.setValue(null);
        preencheInicio();
        inicio_dp.setEditable(true);
        fim_dp.setEditable(true);
        load_bt.setDisable(false);
        inicio_dp.getEditor().setDisable(false);
        fim_dp.getEditor().setDisable(false);
        f15_bt.setDisable(false);
        fhj_bt.setDisable(false);
        f7_bt.setDisable(false);
        f30_bt.setDisable(false);
        ihj_bt.setDisable(false);
        i7_bt.setDisable(false);
        i15_bt.setDisable(false);
        i30_bt.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Main.id_conta_receber.equals("0")) {
            cod_tx.setText(Main.id_conta_receber);
            carrega();
            Main.id_conta_receber = "0";
        } else {
            preencheInicio();
        }
        if (!Main.window) {
            Platform.runLater(() -> {
                Stage u = (Stage) pag_dp.getScene().getWindow();
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
    

}
