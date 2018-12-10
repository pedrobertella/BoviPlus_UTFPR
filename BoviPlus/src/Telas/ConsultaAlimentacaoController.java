package Telas;

import java.util.List;

import Entidades.Estoque;
import Entidades.Gasto_diario;
import Entidades.Alimentacao;
import DAO.DAOEstoque;
import DAO.DAOGasto_diario;
import DAO.DAOAlim_Replica;
import DAO.DAOAlimentacao;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConsultaAlimentacaoController implements Initializable {

    @FXML
    private TableView<Alim> tabela;

    @FXML
    private TableColumn<Alim, String> alim_col;

    @FXML
    private TableColumn<Alim, Integer> id_col;

    @FXML
    private TableColumn<Alim, String> anim_col;

    @FXML
    private TableColumn<Alim, Double> qtd_col;

    @FXML
    private TextField ultima_tx;

    @FXML
    private Button add_bt;

    @FXML
    private Button del_bt;

    @FXML
    private Button edit_bt;

    @FXML
    private Button att_bt;

    @FXML
    private Button registra_bt;

    @FXML
    private DatePicker data_dp;

    @FXML
    private Button pref_bt;

    @FXML
    private TextField status_tx;

    @FXML
    private Button hist_bt;

    public class Alim {

        public String ali, animal;
        public Double qtd;
        public int id, al;

        public Alim(int al, String ali, int id, String date, Double qtd) {
            this.al = al;
            this.ali = ali;
            this.id = id;
            this.animal = date;
            this.qtd = qtd;
        }

        public int getAl() {
            return al;
        }

        public String getAli() {
            return ali;
        }

        public String getAnimal() {
            return animal;
        }

        public int getId() {
            return id;
        }

        public Double getQtd() {
            return qtd;
        }

    }
    ObservableList<Alim> list;

    @FXML
    public void adicionar() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("CadastroAlimentacao.fxml"));
        Scene scene = new Scene(fxmlDB);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Cadastro de Alimentação [Alimentação] - BoviPlus");
        c.show();
    }

    @FXML
    public void pref() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("PreferenciasSistema.fxml"));
        Scene scene = new Scene(fxmlDB);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Preferências [Sistema] - BoviPlus");
        c.show();
    }

    @FXML
    public void historico() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("HistoricoAlimentacao.fxml"));
        Scene scene = new Scene(fxmlDB);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Histórico de Alimentações [Alimentação] - BoviPlus");
        c.show();
    }

    private void initStatusPref() {
        if (Main.pref.autoalimentacao) {
            status_tx.setText("Ativado");
            registra_bt.setDisable(true);
            data_dp.setDisable(true);
        } else {
            status_tx.setText("Desativado");
            registra_bt.setDisable(false);
            data_dp.setDisable(false);
        }
    }

    private void initData() {
        try {
            DAOAlimentacao d = new DAOAlimentacao();
            if (d.ultimaData() == null) {
                ultima_tx.setText(LocalDate.MIN.toString());
            } else {
                ultima_tx.setText(d.ultimaData());
            }
        } catch (Exception e) {
            ultima_tx.setText(LocalDate.MIN.toString());
            System.out.println(e.getMessage());
        }
    }

    private void initTable() {
        try {
            DAOAlim_Replica dar = new DAOAlim_Replica();
            List<Alim_Replica> d = dar.listarTodos();
            list = FXCollections.observableArrayList();
            Alim a;
            for (Alim_Replica i : d) {
                a = new Alim(i.alim, getNomeAlim(i.alim), i.animal, getNomeAnimal(i.animal), i.getQuant());
                list.add(a);
            }
            tabela.setItems(null);
            tabela.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getNomeAnimal(int id) {
        try {
            DAOAnimal da = new DAOAnimal();
            Animal animal = da.consultaAnimal(id);
            return animal.getNome();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String getNomeAlim(int id) {
        try {
            DAOAlimento ddd = new DAOAlimento();
            Alimento u = ddd.consultaAlimento(id);
            return u.getNome();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @FXML
    public void atualizar() {
        initData();
        initStatusPref();
        initTable();
    }

    @FXML
    public void editar() {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("CadastroAlimentacao.fxml"));
            Parent root = fxmlDB.load();
            CadastroAlimentacaoController cont = fxmlDB.getController();
            Scene scene = new Scene(root);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Cadastro de Alimentação [Alimentação] - BoviPlus");
            if (tabela.getSelectionModel().getSelectedItem() == null) {
                throw new ArithmeticException();
            }
            Alim_Replica ad = new Alim_Replica(tabela.getSelectionModel().getSelectedItem().al, tabela.getSelectionModel().getSelectedItem().id, tabela.getSelectionModel().getSelectedItem().qtd);
            cont.carregar(ad);
            c.show();
        } catch (Exception e) {

        }
    }

    @FXML
    public void excluir() {
        try {
            if (tabela.getSelectionModel().getSelectedItem() == null) {
                throw new ArithmeticException();
            }
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Deseja continuar?");
            ko.setHeaderText("Você tem certeza que deseja excluir a alimentação selecionada?");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    try {
                        DAOAlim_Replica da = new DAOAlim_Replica();
                        Alim_Replica ad = new Alim_Replica(tabela.getSelectionModel().getSelectedItem().al, tabela.getSelectionModel().getSelectedItem().id, tabela.getSelectionModel().getSelectedItem().qtd);
                        da.deletarAlim(ad);
                        Main.tocaSucesso();
                        Alert as = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imagaeView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Sucesso!");
                        as.setHeaderText("O registro foi excluído.");
                        as.showAndWait().ifPresent(ss -> {
                            if (ss == ButtonType.OK) {
                            }
                        });
                        atualizar();
                    } catch (Exception e) {
                        Main.tocaErro();
                        Alert as = new Alert(Alert.AlertType.ERROR);
                        ImageView imagaeView = new ImageView(new Image(new File("error.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Atenção!");
                        as.setHeaderText("Ocorreu um erro ao excluir!");
                        as.setContentText(e.getMessage());
                        as.showAndWait().ifPresent(ss -> {
                            if (rr == ButtonType.OK) {
                            }
                        });
                    }

                } else {

                }

            });

        } catch (ArithmeticException e) {

        }
    }

    private int descontaEstoque(int alimento, double qtd, String data) { // 0 = faltou estoque, 1 = OK, -1 = excessão
        try {
            DAOEstoque de = new DAOEstoque();
            if (de.somaPorAlimento(alimento) < qtd) { //verifica se tem estoque suficiente
                return 0;
            }
            List<Estoque> est = de.listarPorAlimento(alimento);
            double valor = 0, temp = 0;
            if (est.get(0).quant >= qtd) {
                valor = est.get(0).valor_uni * qtd;
                est.get(0).quant = est.get(0).quant - qtd;
            } else {
                for (int i = 0; i < est.size(); i++) {
                    if (est.get(i).quant < qtd) {
                        valor = valor + (est.get(i).quant * est.get(i).valor_uni);
                        temp = qtd - est.get(i).quant;
                        est.get(i).quant = 0;
                        qtd = temp;
                        if (qtd == 0) {
                            break;
                        }
                    } else {
                        valor = valor + (est.get(i).getValor_uni() * qtd);
                        est.get(i).quant = est.get(i).quant - qtd;
                        qtd = 0;
                        break;
                    }
                }
            }

            for (Estoque e : est) {
                de.atualizarEstoque(e);
            }

            for (Estoque e : est) {
                if (e.quant == 0) {
                    de.deletarMovimento(e.getId_movimento());
                }
            }
            geraGasto(alimento, valor, data.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return 1;
    }

    public void geraGasto(int alimento, double valor, String data) {
        try {
            DAOGasto_diario dg = new DAOGasto_diario();
            Gasto_diario gd = dg.consultaGasto(alimento, data);
            if (gd == null) {
                dg.cadastrarGasto(new Gasto_diario(alimento, valor, data));
            } else {
                gd.valor = gd.valor + valor;
                dg.atualizarGasto(gd);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void cadastrarAlimentacao(Alimentacao a) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        DAOAlimentacao add = new DAOAlimentacao();
        add.cadastrarAlimentacao_individual(a);
        int result = descontaEstoque(a.getId_alimento(), a.getQtd(), a.getData_alimentacao());
        if (result == 1) {
            System.out.println("deu certo");
        } else if (result == 0) {
            System.out.println("faltou");
        } else {
            System.out.println("erro");
        }
    }

    @FXML
    public void registraManual() {
        if (list.isEmpty() || list == null) {
            Main.tocaErro();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Sem alimentações para registrar!");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                }
            });
        } else if (data_dp.getValue() == null) {
            Main.tocaConfirma();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Para registrar, preencha a data.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                }
            });
        } else if (LocalDate.parse(ultima_tx.getText()).isEqual(data_dp.getValue()) || LocalDate.parse(ultima_tx.getText()).isAfter(data_dp.getValue())) {
            Main.tocaConfirma();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert2.setGraphic(imageView);
            ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Verifique a data.");
            alert2.setContentText("Para registrar, a data selecionada deve ser maior do que a data da última alimentação registrada.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                }
            });
        } else {
            try {
                String date = data_dp.getValue().toString();
                Alimentacao b;
                for (Alim i : list) {
                    System.out.println("u");
                    b = new Alimentacao();
                    b.setData_alimentacao(date);
                    b.setId_alimento(i.al);
                    b.setId_animal(i.id);
                    b.setQtd(i.qtd);
                    cadastrarAlimentacao(b);
                }
                Main.tocaSucesso();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                alert2.setGraphic(imageView);
                ((Stage) alert2.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("As alimentações foram cadastradas!");
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });
                atualizar();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.window) {
            Main.stage.setTitle("Alimentações [Alimentação] - BoviPlus");
        }
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    editar();
                }
            }
        });
        initStatusPref();
        initData();
        initTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        alim_col.setCellValueFactory(new PropertyValueFactory<>("ali"));
        anim_col.setCellValueFactory(new PropertyValueFactory<>("animal"));
        qtd_col.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tabela.setPlaceholder(new Label("Nenhuma alimentação!"));
    }
}
