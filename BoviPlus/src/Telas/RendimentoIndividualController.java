package Telas;

import DAO.DAOAnimal;
import DAO.DAOProducao;
import Entidades.Animal;
import Entidades.Producao;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RendimentoIndividualController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TableView<pc> tabela;

    @FXML
    private TableColumn<pc, String> data_col;

    @FXML
    private TableColumn<pc, Double> litro_col;

    @FXML
    private TextField cod_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private TextField menorg_tx;

    @FXML
    private DatePicker inicial_dp;

    @FXML
    private DatePicker final_dp;

    @FXML
    private TextField mdg_tx;

    @FXML
    private TextField menorn_tx;

    @FXML
    private TextField mdn_tx;

    @FXML
    private TextField nome_tx;

    @FXML
    private TextField tg_tx;

    @FXML
    private TextField tn_tx;

    @FXML
    private TextField maiorg_tx;

    @FXML
    private TextField maiorn_tx;

    @FXML
    private Button abaixo_bt;

    @FXML
    private Button acima_bt;

    @FXML
    private Button tudo_bt;

    public class pc {

        public String data;
        public double litros;

        public pc(String n, double l) {
            data = n;
            litros = l;
        }

        public String getData() {
            return data;
        }

        public void setData(String nome) {
            this.data = nome;
        }

        public double getLitros() {
            return litros;
        }

        public void setLitros(double litros) {
            this.litros = litros;
        }

    }

    List<pc> lgeral;
    List<pc> lperiodo;
    ObservableList<pc> list = FXCollections.observableArrayList();
    Animal vaca;
    DAOAnimal da;
    Producao prod;
    DAOProducao dp;

    @FXML
    public void carrega() {
        lgeral = new ArrayList<>();
        lperiodo = new ArrayList<>();
        list.clear();

        try {
            dp = new DAOProducao();
            buscaAnimal(Integer.parseInt(cod_tx.getText()));
            nome_tx.setText(vaca.getNome());
            List<Producao> a;
            List<Producao> g;
            if (inicial_dp.getValue() == null || final_dp.getValue() == null) {
                a = dp.buscaporanimal(vaca.getId_animal());
            } else if (inicial_dp.getValue().isAfter(final_dp.getValue())) {
                throw new NoSuchMethodException();
            } else {
                a = dp.buscapordataeanimal(vaca.getId_animal(), inicial_dp.getValue().toString(), final_dp.getValue().toString());
            }
            g = dp.buscaporanimal(vaca.getId_animal());
            pc jj;

            for (Producao i : a) {
                jj = new pc(i.getData_producao(), i.getLitragem());
                lperiodo.add(jj);
                list.add(jj);
            }
            for (Producao i : g) {
                jj = new pc(i.getData_producao(), i.getLitragem());
                lgeral.add(jj);
            }
            tabela.setItems(null);
            tabela.setItems(list);
            dadosGerais();
            dadosPeriodo();
            carregar_bt.setDisable(true);
            inicial_dp.setDisable(true);
            final_dp.setDisable(true);
            cod_tx.setDisable(true);
            abaixo_bt.setDisable(false);
            acima_bt.setDisable(false);
            tudo_bt.setDisable(false);
        } catch (NoSuchMethodException ns) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Atenção!");
            dodsad.setHeaderText("A data final não pode ser menor que a data inicial!");
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            inicial_dp.setValue(final_dp.getValue());
        } catch (ArithmeticException ae) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa selecionar um animal para poder carregar!");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        } catch (NoSuchFieldException ie) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("O animal selecionado não é uma vaca! Verifique...");
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
            ko.setHeaderText("Ocorreu um erro ao carregar...");
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

        tudo_bt.setDisable(true);
        abaixo_bt.setDisable(true);
        acima_bt.setDisable(true);
        tg_tx.clear();
        tn_tx.clear();
        maiorg_tx.clear();
        menorg_tx.clear();
        menorn_tx.clear();
        maiorn_tx.clear();
        mdn_tx.clear();
        mdg_tx.clear();
        cod_tx.clear();
        inicial_dp.setValue(null);
        final_dp.setValue(null);
        nome_tx.clear();
        carregar_bt.setDisable(false);
        inicial_dp.setDisable(false);
        final_dp.setDisable(false);
        tabela.setItems(null);
        cod_tx.setDisable(false);
        vaca = null;

    }

    public void dadosGerais() {

        double media, total;
        String a, b;
        media = mediaDiaria(lgeral);
        media = Math.round(media * 100.0) / 100.0;
        total = total(lgeral);
        total = Math.round(total * 100.0) / 100.0;
        a = menor(lgeral);
        b = maior(lgeral);

        tg_tx.setText(Double.toString(total));
        mdg_tx.setText(Double.toString(media));
        maiorg_tx.setText(b);
        menorg_tx.setText(a);
    }

    public void dadosPeriodo() {

        double media, total;
        String a, b;
        media = mediaDiaria(lperiodo);
        media = Math.round(media * 100.0) / 100.0;
        total = total(lperiodo);
        total = Math.round(total * 100.0) / 100.0;
        a = menor(lperiodo);
        b = maior(lperiodo);

        tn_tx.setText(Double.toString(total));
        mdn_tx.setText(Double.toString(media));
        maiorn_tx.setText(b);
        menorn_tx.setText(a);

    }

    private double mediaDiaria(List<pc> lista) {
        double num = 0.0;
        int i = 0;

        for (pc j : lista) {
            num = num + j.litros;
            i++;
        }

        return num / i;
    }

    private double total(List<pc> lista) {
        double num = 0.00;

        for (pc j : lista) {
            num = num + j.litros;
        }

        return num;
    }

    private String menor(List<pc> lista) {
        String data;
        double temp = Double.MAX_VALUE;
        pc aux = null;
        for (pc i : lista) {
            if (i.litros < temp) {
                temp = i.litros;
                aux = i;
            }
        }

        String a = aux.data + ": " + aux.litros + " litros.";
        return a;
    }

    private String maior(List<pc> lista) {
        String data;
        double temp = 0.00;
        pc aux = null;
        for (pc i : lista) {
            if (i.litros > temp) {
                temp = i.litros;
                aux = i;
            }
        }

        String a = aux.data + ": " + aux.litros + " litros.";
        return a;
    }

    private void buscaAnimal(int id) throws ArithmeticException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException, FileNotFoundException {
        if (cod_tx.getText().trim().isEmpty()) {
            throw new ArithmeticException();
        }
        try {
            da = new DAOAnimal();
            vaca = da.consultaAnimal(id);
            if (vaca.getId_tipo() != 1) {
                vaca = null;
                throw new NoSuchFieldException();
            }
        } catch (SQLException j) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Ocorreu um erro ao buscar o animal.");
            ko.setContentText(j.getMessage());
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void acimaMedia() {
        ObservableList<pc> acmedia = FXCollections.observableArrayList();
        double media = mediaDiaria(lperiodo);

        for (pc i : list) {
            if (i.litros > media) {
                acmedia.add(i);
            }
        }

        tabela.setItems(null);
        tabela.setItems(acmedia);
    }

    @FXML
    public void abaixoMedia() {
        ObservableList<pc> abmedia = FXCollections.observableArrayList();
        double media = mediaDiaria(lperiodo);

        for (pc i : list) {
            if (i.litros < media) {
                abmedia.add(i);
            }
        }

        tabela.setItems(null);
        tabela.setItems(abmedia);
    }

    @FXML
    public void tudo() {
        tabela.setItems(null);
        tabela.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Rendimento por Animal [Produção] - BoviPlus");
        }
        data_col.setCellValueFactory(new PropertyValueFactory("data"));
        litro_col.setCellValueFactory(new PropertyValueFactory("litros"));
        tabela.setPlaceholder(new Label("Sem conteúdo para exibir."));
        if (Main.id_animal_prod != "0") {
            cod_tx.setText(Main.id_animal_prod);
            carrega();
            Main.id_animal_prod = "0";
        }

    }

}
