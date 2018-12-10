package Telas;

import DAO.DAOContas_Receber;
import Entidades.Contas_Receber;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ReceberGeralController implements Initializable {

    @FXML
    private TextField cod_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private TextField qtd_tx;

    @FXML
    private TextField vuni_tx;

    @FXML
    private TextField vtot_tx;

    @FXML
    private DatePicker venc_dp;

    @FXML
    private DatePicker pag_dp;

    @FXML
    private TextField desc_tx;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button dias15;

    @FXML
    private Button dias30;

    @FXML
    private Button dias45;

    @FXML
    private Button hoje;

    @FXML
    private Button limpar_bt;

    private boolean carregado = false;
    private double total;
    private DAOContas_Receber dc;
    private Contas_Receber cr;
    private boolean alt = false;

    @FXML
    private void alteracao() {
        alt = true;
    }

    @FXML
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
        carregado = false;
        cr = null;
        cod_tx.clear();
        qtd_tx.clear();
        vuni_tx.clear();
        vtot_tx.clear();
        desc_tx.clear();
        venc_dp.setValue(null);
        pag_dp.setValue(null);
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
    public void salva() {
        if (qtd_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher a quantidade para salvar. Verifique...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            qtd_tx.requestFocus();
        } else if (vuni_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher o valor unitário para salvar. Verifique...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            vuni_tx.requestFocus();
        } else if (desc_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher a descrição para salvar. Verifique...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            desc_tx.requestFocus();
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
                if (carregado) {
                    cr.setDescricao(desc_tx.getText());
                    if (pag_dp.getValue() != null) {
                        cr.setData_recebimento(pag_dp.getValue().toString());
                    } else {
                        cr.setData_recebimento(null);
                    }
                    cr.setData_vencimento(venc_dp.getValue().toString());
                    cr.setValor(Double.parseDouble(vuni_tx.getText()));
                    cr.setQtd(Double.parseDouble(qtd_tx.getText()));
                    dc.atualizarContas_Receber(cr);
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
                    cr.setDescricao(desc_tx.getText());
                    if (pag_dp.getValue() != null) {
                        cr.setData_recebimento(pag_dp.getValue().toString());
                    }
                    cr.setData_vencimento(venc_dp.getValue().toString());
                    cr.setValor(Double.parseDouble(vuni_tx.getText()));
                    cr.setQtd(Double.parseDouble(qtd_tx.getText()));
                    cr.setProducao(false);
                    dc.cadastrarContas_receber(cr);
                    cod_tx.setText(Integer.toString(dc.lastID()));
                    cr.setId_contas_receber(dc.lastID());
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
        try {
            if (cod_tx.getText().trim().isEmpty()) {
                throw new ArithmeticException();
            }

            dc = new DAOContas_Receber();
            cr = dc.consultar_contas_receber(Integer.parseInt(cod_tx.getText()));
            if (cr.isProducao()) {
                throw new IOException();
            }

            desc_tx.setText(cr.getDescricao());
            vuni_tx.setText(Double.toString(cr.getValor()));
            qtd_tx.setText(Double.toString(cr.getQtd()));
            venc_dp.setValue(LocalDate.parse(cr.getData_vencimento()));
            if (cr.getData_recebimento() != null) {
                pag_dp.setValue(LocalDate.parse(cr.getData_recebimento()));
            }
            multiplica();

            carregado = true;
        } catch (ArithmeticException aa) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Para carregar, informe o código.");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        } catch (IOException i) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("A conta a receber fornecida é de Produção, para carregá-la"
                    + " utilize a aba PRODUÇÃO ao lado.");
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
            ko.setHeaderText("Conta inválida!");
            ko.setContentText(e.getMessage());
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        }
    }

    @FXML
    public void multiplica() {
        try {
            total = Double.parseDouble(qtd_tx.getText()) * Double.parseDouble(vuni_tx.getText());
            vtot_tx.setText(Double.toString(total));
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Main.id_conta_receber.equals("0")) {
            cod_tx.setText(Main.id_conta_receber);
            carrega();
            Main.id_conta_receber = "0";
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
