package Telas;

import DAO.DAOAnimal;
import Entidades.Animal;
import DAO.DAOContas_pagar;
import DAO.DAOCusto_reproducao;
import DAO.DAOReproducao;
import Entidades.Contas_pagar;
import Entidades.Custo_reproducao;
import Entidades.Reproducao;
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

public class PagarReproducaoController implements Initializable {

    @FXML
    private TextField cod_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private TextField insemi_tx;

    @FXML
    private Button buscar_bt;

    @FXML
    private TextField mae_tx;

    @FXML
    private TextField qtd_tx;

    @FXML
    private TextField vuni_tx;

    @FXML
    private TextField vtot_tx;

    @FXML
    private DatePicker venc_dp;

    @FXML
    private Button hojev_bt;

    @FXML
    private Button dias15_bt;

    @FXML
    private Button dias30_bt;

    @FXML
    private Button listar_bt;

    @FXML
    private DatePicker pag_dp;

    @FXML
    private Button hojep_bt;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    private Stage listar;
    private double total;
    private DAOContas_pagar dp;
    private DAOCusto_reproducao dr;
    private Custo_reproducao cr;
    private Contas_pagar cp;
    private boolean carregado = false, loaded = false;
    private DAOReproducao drep;
    private Reproducao rep;
    private boolean alt = false;

    @FXML
    private void alteracao() {
        alt = true;
    }

    @FXML
    public void lista() throws IOException {
        listar = new Stage();
        listar.setResizable(false);
        Main.window = false;
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("AcompanhamentoReproducao.fxml"));
        Scene scene = new Scene(fxmlDB);
        listar.getIcons().add(new Image("/Imagens/Iconew.png"));
        listar.setScene(scene);
        listar.setTitle("Acompanhamento [Reprodução] - BoviPlus");
        listar.show();
        Main.window = true;
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

            dp = new DAOContas_pagar();
            cp = dp.consultaContas_pagar(Integer.parseInt(cod_tx.getText()));
            if (cp.getOrigem() != 1) {
                throw new IOException();
            }
            dr = new DAOCusto_reproducao();
            cr = dr.consultaCusto(cp.getId_contas_pagar());
            insemi_tx.setText(Integer.toString(cr.getId_gestacao()));
            busca();
            vuni_tx.setText(Double.toString(cr.getValor()));
            qtd_tx.setText(Integer.toString(cr.getQtd()));
            venc_dp.setValue(LocalDate.parse(cp.getData_vencimento()));
            if (cp.getData_pagamento() != null) {
                pag_dp.setValue(LocalDate.parse(cp.getData_pagamento()));
            }
            multiplica();
            alt = false;
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
            ko.setHeaderText("A conta a pagar fornecida não é de Reprodução, para carregá-la"
                    + " utilize a aba correta ao lado.");
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
        } else if (!loaded) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa carregar uma inseminação para salvar...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            insemi_tx.requestFocus();
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
                dp = new DAOContas_pagar();
                dr = new DAOCusto_reproducao();
                if (carregado) {
                    cr.setId_gestacao(rep.getId_gestacao());
                    if (pag_dp.getValue() != null) {
                        cp.setData_pagamento(pag_dp.getValue().toString());
                    } else {
                        cp.setData_pagamento(null);
                    }
                    cp.setData_vencimento(venc_dp.getValue().toString());
                    cr.setValor(Double.parseDouble(vuni_tx.getText()));
                    cr.setQtd(Integer.parseInt(qtd_tx.getText()));
                    multiplica();
                    cp.setValor(total);
                    cp.setOrigem(1);

                    dp.atualizarContas_pagar(cp.getId_contas_pagar(), cp.getValor(), cp.getData_vencimento(), cp.getData_pagamento());
                    dr.atualizarCusto_reproducao(cr.getId_gestacao(), cr.getId_contas_pagar(), cr.getQtd(), cr.getValor());

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
                    cp = new Contas_pagar();
                    cr = new Custo_reproducao();
                    cr.setId_gestacao(rep.getId_gestacao());
                    if (pag_dp.getValue() != null) {
                        cp.setData_pagamento(pag_dp.getValue().toString());
                    }
                    cp.setData_vencimento(venc_dp.getValue().toString());
                    cr.setValor(Double.parseDouble(vuni_tx.getText()));
                    cr.setQtd(Integer.parseInt(qtd_tx.getText()));
                    cp.setOrigem(1);
                    cp.setId_contas_pagar(dp.proximoID());
                    cr.setId_contas_pagar(dp.proximoID());
                    multiplica();
                    cp.setValor(total);

                    dp.cadastrarContas_pagar(cp);
                    dr.cadastrarCusto_reproducao(cr);
                    cod_tx.setText(Integer.toString(cp.getId_contas_pagar()));
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
        loaded = false;
        insemi_tx.clear();
        cod_tx.clear();
        qtd_tx.clear();
        vuni_tx.clear();
        vtot_tx.clear();
        mae_tx.clear();
        venc_dp.setValue(null);
        pag_dp.setValue(null);
    }

    @FXML
    public void multiplica() {
        try {
            total = Double.parseDouble(qtd_tx.getText()) * Double.parseDouble(vuni_tx.getText());
            total = Math.round(total * 100) / 100;
            vtot_tx.setText(Double.toString(total));
        } catch (Exception e) {

        }
    }

    @FXML
    public void busca() {
        if (insemi_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Para buscar, informe o código da inseminação...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        } else {
            rep = null;
            try {
                drep = new DAOReproducao();
                rep = drep.consultaReproducao(Integer.parseInt(insemi_tx.getText()));

                DAOAnimal da = new DAOAnimal();
                Animal mae = da.consultaAnimal(rep.getId_mae());
                da = new DAOAnimal();
                Animal pai = da.consultaAnimal(rep.getId_pai());

                mae_tx.setText(mae.getNome() + " - " + pai.getNome());
                loaded = true;
            } catch (Exception e) {
                Main.tocaErro();
                Alert ko = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("Inseminação não encontrada!");
                ko.setContentText(e.getMessage());
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
            }
        }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Main.id_conta_pagar.equals("0")) {
            cod_tx.setText(Main.id_conta_pagar);
            carrega();
            Main.id_conta_pagar = "0";
        } else if (!Main.id_inseminacao.equals("0")) {
            insemi_tx.setText(Main.id_inseminacao);
            busca();
            Main.id_inseminacao = "0";
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
