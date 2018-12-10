package Telas;

import ConexaoBD.Conexao;
import DAO.DAOProducao;
import Entidades.Producao;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

public class CadastroProducaoController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TableView<pc> tabela;

    @FXML
    private TableColumn<pc, Integer> cod_col;

    @FXML
    private TableColumn<pc, String> vaca_col;

    @FXML
    private TableColumn<pc, Double> litro_col;

    @FXML
    private DatePicker data_dp;

    @FXML
    private Button hoje_bt;

    @FXML
    private Button carrega_bt;

    @FXML
    private Button salva_bt;

    @FXML
    private Button limpa_bt;

    @FXML
    private TextField total_tx;

    ObservableList<pc> list;
    double total = 0;
    boolean ignorar = true;
    DAOProducao dp;
    Conexao c;
    Statement stm;
    ResultSet rs;
    String sql;

    public class pc {

        public int cod;
        public String nome;
        public double litros;

        public pc(int c, String n, double l) {
            cod = c;
            nome = n;
            litros = l;
        }

        public int getCod() {
            return cod;
        }

        public void setCod(int cod) {
            this.cod = cod;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getLitros() {
            return litros;
        }

        public void setLitros(double litros) {
            this.litros = litros;
        }

    }

    @FXML
    public void carregarVaca() {
        try {
            list = FXCollections.observableArrayList();
            sql = "select id_animal, nome from animal where id_tipo = 1 and lactacao = true;";
            stm = c.getConnection().createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                pc z = new pc(rs.getInt("id_animal"), rs.getString("nome"), 0);
                list.add(z);
            }
            data_dp.setDisable(false);
            hoje_bt.setDisable(false);
            salva_bt.setDisable(false);
            total_tx.setDisable(false);
            carrega_bt.setDisable(true);
            tabela.setItems(null);
            tabela.setItems(list);
        } catch (Exception e) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Ocorreu um erro ao carregar os animais.");
            ko.setContentText(e.getMessage());
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void limpa() {
        tabela.setItems(null);
        carrega_bt.setDisable(false);
        data_dp.setValue(null);
        total_tx.clear();
        total = 0;
        list = null;
        data_dp.setDisable(true);
        hoje_bt.setDisable(true);
        salva_bt.setDisable(true);
        total_tx.setDisable(true);
    }

    @FXML
    public void salvar() {
        try {
            if (ignorar) {
                for (pc e : list) {
                    if (e.litros == 0) {
                        throw new IOException();
                    }
                }
            }
            if (data_dp.getValue() == null) {
                throw new ArithmeticException();
            }
            if (!dp.consultaProducaoDia(data_dp.getValue().toString())) {
                throw new Exception();
            }

            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            Main.tocaConfirma();
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Mesclar produção?");
            alert1.setHeaderText("Já existe uma produção registrada com esta data, deseja mesclar os registros?");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Mesclando!");
                    al.setHeaderText("Mesclando as produções. Por favor aguarde...");
                    al.show();

                    Producao k;
                    for (pc i : list) {
                        try {
                            k = dp.consultaProducao(i.cod, data_dp.getValue().toString());
                            k.setLitragem(i.litros + k.getLitragem());
                            dp.atualizarProducao(k.getId_vaca(), k.getData_producao(), k.getLitragem());
                        } catch (Exception ss) {
                            k = new Producao();
                            k.setId_vaca(i.cod);
                            k.setData_producao(data_dp.getValue().toString());
                            k.setLitragem(i.litros);
                            try {
                                dp.cadastrarProducao(k);
                            } catch (Exception ea) {
                                Main.tocaErro();
                                Alert ko = new Alert(Alert.AlertType.ERROR);
                                ImageView imageiew = new ImageView(new Image(new File("error.png").toURI().toString()));
                                ko.setGraphic(imageiew);
                                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                                ko.setTitle("Atenção!");
                                ko.setHeaderText("Ocorreu um erro ao salvar a produção.");
                                ko.setContentText(ea.getMessage());
                                ko.showAndWait().ifPresent(rr -> {
                                    if (rr == ButtonType.OK) {
                                        System.out.println("Pressed OK.");
                                    }
                                });
                            }
                        }
                    }
                    limpa();
                    ignorar = true;
                    al.close();
                    Main.tocaSucesso();
                    Alert al3 = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageaew = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    al3.setGraphic(imageaew);
                    ((Stage) al3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    al3.setTitle("Pronto");
                    al3.setHeaderText("O registro foi finalizado com sucesso!");
                    al3.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.CLOSE) {

                        }
                    });
                } else {

                }
            });

        } catch (IOException io) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Existem animais com a litragem zerada, deseja prosseguir?");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    ignorar = false;
                    salvar();
                } else {
                    System.out.println("CANCELADO");
                }
            });
        } catch (ArithmeticException ar) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("A data não foi preenchida. Deseja salvar com a data atual?");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    hojeDP();
                    salvar();
                } else {
                    System.out.println("CANCELADO");
                }
            });
        } catch (Exception e) {
            for (pc i : list) {
                Producao p = new Producao();
                p.setId_vaca(i.cod);
                p.setData_producao(data_dp.getValue().toString());
                p.setLitragem(i.litros);
                try {
                    dp.cadastrarProducao(p);
                } catch (Exception ex) {
                    Main.tocaErro();
                    Alert ko = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("Ocorreu um erro ao salvar a produção.");
                    ko.setContentText(ex.getMessage());
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                }
            }
            limpa();
            ignorar = true;
            Main.tocaSucesso();
            Alert al3 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            al3.setGraphic(imageView);
            ((Stage) al3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            al3.setTitle("Pronto");
            al3.setHeaderText("O registro foi finalizado com sucesso!");
            al3.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.CLOSE) {

                }
            });
        }

    }

    @FXML
    public void hojeDP() {
        data_dp.setValue(LocalDate.now());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Registro em Lote [Produção] - BoviPlus");
        }
        data_dp.setDisable(true);
        hoje_bt.setDisable(true);
        salva_bt.setDisable(true);
        total_tx.setDisable(true);
        try {
            c = new Conexao();
            c.abrirConexao();
            dp = new DAOProducao();
        } catch (Exception k) {
            System.out.println("DEU UM BAITA RUIM");
        }
        tabela.setEditable(true);
        cod_col.setCellValueFactory(new PropertyValueFactory("cod"));
        vaca_col.setCellValueFactory(new PropertyValueFactory("nome"));
        litro_col.setCellFactory(TextFieldTableCell.<pc, Double>forTableColumn(new DoubleStringConverter()));
        litro_col.setCellValueFactory(new PropertyValueFactory("litro"));
    }

    private int retornaIndiceAnimalnaLista(int id) {
        int j;

        for (j = 0; j < list.size(); j++) {
            if (id == list.get(j).cod) {
                break;
            }
        }
        return j;
    }

    @FXML
    void ediTabela(TableColumn.CellEditEvent<pc, Double> event) {
        try {
            double tt = event.getNewValue();
            int id = event.getTableView().getSelectionModel().getSelectedItem().cod;
            int indice = retornaIndiceAnimalnaLista(id);
            if (list.get(indice).litros != 0) {
                total = total - list.get(indice).litros;
            }
            total = total + tt;
            total_tx.setText(Double.toString(total));
            list.get(indice).litros = tt;
            tabela.requestFocus();
            tabela.getSelectionModel().select(tabela.getSelectionModel().getSelectedIndex() + 1, litro_col);
        } catch (Exception a) {

        }

    }

}
