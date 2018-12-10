package Telas;

import DAO.DAOAnimal;
import DAO.DAOCusto_reproducao;
import DAO.DAORaca;
import DAO.DAOReproducao;
import Entidades.Animal;
import Entidades.Custo_reproducao;
import Entidades.Raca;
import Entidades.Reproducao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegistroInseminacaoController implements Initializable {

    @FXML
    private TextField codigo_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private Button financeiro_bt;

    @FXML
    private DatePicker cadastro_data;

    @FXML
    private TextField maeid_tx1;

    @FXML
    private TextField maenome_tx;

    @FXML
    private TextField paiid_tx;

    @FXML
    private TextField pai_tx;

    @FXML
    private DatePicker previsao_data;

    @FXML
    private Button datahoje_bt;

    @FXML
    private Button ontem_bt;

    @FXML
    private Button buscamae_bt;

    @FXML
    private Button buscapai_bt;

    @FXML
    private Button calcular_bt;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button excluir_bt;

    @FXML
    private AnchorPane paneprincipal;

    private int carregado = 0;
    private Animal mae;
    private Animal pai;
    private Stage fi;
    private boolean alt = false;

    public void setPai(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        paiid_tx.setText(Integer.toString(id));
        buscaPai();
        alt = true;
    }

    public void setMae(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        maeid_tx1.setText(Integer.toString(id));
        buscaMae();
        alt = true;
    }

    @FXML
    private void alteracao() {
        alt = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Registro de Inseminação [Reprodução] - BoviPlus");
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
        if (!Main.id_inseminacao.equals("0")) {
            try {
                codigo_tx.setText(Main.id_inseminacao);
                carregar();
                Main.id_inseminacao = "0";

            } catch (Exception ex) {
                System.out.println("Não Carregou");
            }
        } else {
            codigo_tx.requestFocus();
        }
    }

    @FXML
    public void financ() throws IOException {
        try {
            DAOCusto_reproducao d = new DAOCusto_reproducao();
            Custo_reproducao c = d.consultaCusto_reproducao(Integer.parseInt(codigo_tx.getText()));
            Main.id_conta_pagar = Integer.toString(c.getId_contas_pagar());
            fi = new Stage();
            fi.setResizable(false);
            Main.window = false;
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("PagarReproducao.fxml"));
            Scene scene = new Scene(fxmlDB);
            fi.getIcons().add(new Image("/Imagens/Iconew.png"));
            fi.setScene(scene);
            fi.setTitle("Lançamento de Reprodução [Financeiro] - BoviPlus");
            fi.show();
            Main.window = true;
        } catch (SQLException sa) {
            Main.id_inseminacao = codigo_tx.getText();
            fi = new Stage();
            fi.setResizable(false);
            Main.window = false;
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("PagarReproducao.fxml"));
            Scene scene = new Scene(fxmlDB);
            fi.getIcons().add(new Image("/Imagens/Iconew.png"));
            fi.setScene(scene);
            fi.setTitle("Lançamento de Reprodução [Financeiro] - BoviPlus");
            fi.show();
            Main.window = true;
        } catch (Exception k) {
            System.out.println(k.getMessage());
        }
    }

    @FXML
    public void infoTela() {
        Main.tocaConfirma();
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
        alert2.setGraphic(imageView);
        ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
        alert2.setTitle("Ajuda");
        alert2.setHeaderText("Registro de Inseminação");
        alert2.setContentText("Para cadastrar preencha os campos e pressione SALVAR, o código será informado. Para atualizar digite o código e pressione CARREGAR, faça as alterações e pressione SALVAR.");
        alert2.showAndWait().ifPresent(rj -> {
            if (rj == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });

    }

    @FXML
    public void salvar() {
        if (carregado == 1) {
            atualiza();
        } else {
            salva();
        }
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
        ee.ric = this;
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
        ee.ric = this;
        Main.window = true;
    }

    private void salva() {
        try {
            DAOReproducao dr = new DAOReproducao();

            if (dr.verificaInseminacaoVaca(Integer.parseInt(maeid_tx1.getText()), cadastro_data.getValue())) {
                throw new ArithmeticException();
            }

            Reproducao r = new Reproducao();
            if (codigo_tx.getText().equals("")) {
                r.setId_gestacao(dr.proximoID());
            } else {
                r.setId_gestacao(Integer.parseInt(codigo_tx.getText()));
            }
            r.setData_inseminacao(cadastro_data.getValue().toString());
            buscaMae();
            if (paiid_tx.getText().trim().isEmpty() || paiid_tx.getText() == null) {
                paiid_tx.setText("0");
            }
            buscaPai();
            r.setId_mae(mae.getId_animal());
            r.setId_pai(pai.getId_animal());
            r.setPrevisao(previsao_data.getValue().toString());
            dr.cadastrarReproducao(r);

            Main.tocaSucesso();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Sucesso!");
            alert2.setHeaderText("A inseminação foi registrada, seu código é " + r.getId_gestacao() + ".");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            codigo_tx.setText(Integer.toString(r.getId_gestacao()));
            carregado = 1;
            financeiro_bt.setDisable(false);
            alt = false;
        } catch (ArithmeticException ae) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("A vaca selecionada já está em gestação ou ainda não está apta para uma nova reprodução."
                    + "\nPara continuar, exclua o registro anterior, ou altere a data de inserminação.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (Exception u) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("A inseminação não pode ser registrada.");
            alert2.setContentText(u.getMessage());
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    private void atualiza() {
        try {
            DAOReproducao dr = new DAOReproducao();

            dr.atualizarReproducao(Integer.parseInt(codigo_tx.getText()), mae.getId_animal(), pai.getId_animal(), previsao_data.getValue().toString(), cadastro_data.getValue().toString());

            Main.tocaSucesso();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Sucesso!");
            alert2.setHeaderText("A inseminação foi atualizada!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            financeiro_bt.setDisable(false);
            alt = false;

        } catch (Exception e) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("A inseminação não pode ser atualizada.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void excluir() {
        if (carregado == 1) {
            Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert3.setGraphic(imageView);
            ((Stage) alert3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert3.setTitle("Atenção!");
            alert3.setHeaderText("Você tem certeza que deseja excluir a inseminação da vaca " + mae.getNome() + "?");
            alert3.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    try {

                        DAOReproducao r = new DAOReproducao();
                        r.deletarReproducao(Integer.parseInt(codigo_tx.getText()));
                        Alert alert15 = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imageVew = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        alert15.setGraphic(imageVew);
                        ((Stage) alert15.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        Main.tocaSucesso();
                        alert15.setTitle("Sucesso!");
                        alert15.setHeaderText("A inseminação foi apagada!");
                        alert15.showAndWait().ifPresent(kk -> {
                            if (kk == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                        limpar();
                    } catch (Exception ex) {
                        Main.tocaErro();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        ImageView imageViw = new ImageView(new Image(new File("error.png").toURI().toString()));
                        alert.setGraphic(imageViw);
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert.setTitle("Atenção!");
                        alert.setHeaderText("Não foi possível apagar, tente novamente...");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait().ifPresent(rs -> {
                            if (rs == ButtonType.OK) {
                                System.out.println("Pressed OK.");
                            }
                        });
                    }

                } else {
                }
            });
        } else {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Nenhum animal carregado!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    @FXML
    public void carregar() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
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
            DAOReproducao dr = new DAOReproducao();
            Reproducao r = dr.consultaReproducao(Integer.parseInt(codigo_tx.getText()));
            selecionaMae(r.getId_mae());
            selecionaPai(r.getId_pai());
            cadastro_data.setValue(LocalDate.parse(r.getData_inseminacao()));
            previsao_data.setValue(LocalDate.parse(r.getPrevisao()));
            carregado = 1;
            financeiro_bt.setDisable(false);
            alt = false;
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Inseminação não encontrada!!! Verifique.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void inutil() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        try {
            calcularPrevisao();
        } catch (Exception j) {

        }
    }

    @FXML
    public void limpar() {
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
        cadastro_data.setValue(null);
        maeid_tx1.clear();
        paiid_tx.clear();
        maenome_tx.setText("");
        pai_tx.setText("");
        mae = null;
        pai = null;
        previsao_data.setValue(null);
        financeiro_bt.setDisable(true);
    }

    @FXML
    public void calcularPrevisao() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        if (mae != null) {
            DAORaca dr1 = new DAORaca();
            Raca r = dr1.consultar_raca(mae.getId_raca());
            try {
                previsao_data.setValue(cadastro_data.getValue().plusDays(r.getTempo_gestacao()));
            } catch (Exception i) {

            }
        } else {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Não foi carregado nenhuma mãe! Verifique...");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    private void selecionaMae(int id_mae) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAOAnimal da = new DAOAnimal();
        mae = da.consultaAnimal(id_mae);
        maenome_tx.setText(mae.getNome());
        maeid_tx1.setText(Integer.toString(mae.getId_animal()));
    }

    private void selecionaPai(int id_pai) throws SQLException, FileNotFoundException, InstantiationException, ClassNotFoundException, IllegalAccessException {
        DAOAnimal da = new DAOAnimal();
        pai = da.consultaAnimal(id_pai);
        pai_tx.setText(pai.getNome());
        paiid_tx.setText(Integer.toString(pai.getId_animal()));
    }

    @FXML
    public void buscaPai() throws ClassNotFoundException, IllegalAccessException, InstantiationException, FileNotFoundException {
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
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                DAOAnimal da = new DAOAnimal();
                pai = da.consultaAnimal(Integer.parseInt(paiid_tx.getText()));
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
                alert1.setHeaderText("O pai não pode ser do tipo VACA, BEZERRO ou NOVILHA!!");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
            }
        }
    }

    @FXML
    public void buscaMae() throws ClassNotFoundException, IllegalAccessException, FileNotFoundException, InstantiationException {
        if (maeid_tx1.getText().equals("")) {
            Main.tocaErro();
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
                DAOAnimal da = new DAOAnimal();
                mae = da.consultaAnimal(Integer.parseInt(maeid_tx1.getText()));
                if (mae.getId_tipo() != 1 && mae.getId_tipo() != 0) {
                    if (mae.getId_tipo() == 4) {
                        Main.tocaConfirma();
                        Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION);
                        ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
                        alert5.setGraphic(imageView);
                        ((Stage) alert5.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        alert5.setTitle("Atenção!");
                        alert5.setHeaderText("A mãe é uma novilha, deseja mudar seu tipo para vaca?");
                        alert5.showAndWait().ifPresent(rj -> {
                            if (rj == ButtonType.OK) {
                                try {
                                    da.atualizarAnimal(mae.getId_animal(), mae.getNome(), mae.getNascimento(), mae.getPeso(), mae.getDescricao(), mae.getId_mae(), mae.getId_pai(), 1, mae.getId_raca(), mae.isLactacao(), 0, mae.getId_animal());
                                } catch (Exception e) {
                                    Main.tocaErro();
                                    Alert alert6 = new Alert(Alert.AlertType.ERROR);
                                    ImageView imageiew = new ImageView(new Image(new File("error.png").toURI().toString()));
                                    alert6.setGraphic(imageiew);
                                    ((Stage) alert6.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                                    alert6.setTitle("Erro!");
                                    alert6.setHeaderText("Não foi possível atualizar! Tente novamente.");
                                    alert6.showAndWait().ifPresent(j -> {
                                        if (j == ButtonType.OK) {
                                            System.out.println("Pressed OK.");
                                        }
                                    });
                                }
                            } else {
                                throw new ArithmeticException();
                            }
                        });
                    } else {
                        throw new ArithmeticException();
                    }
                }
                maenome_tx.setText(mae.getNome());
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
    public void hojeDataCad() {
        cadastro_data.setValue(LocalDate.now());
    }

    @FXML
    public void ontemDataCad() {
        cadastro_data.setValue(LocalDate.now().minusDays(1));
    }

}
