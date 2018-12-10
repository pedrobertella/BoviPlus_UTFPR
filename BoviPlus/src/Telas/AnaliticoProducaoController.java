package Telas;

import DAO.DAOAnimal;
import DAO.DAOProducao;
import Entidades.Animal;
import Entidades.Producao;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AnaliticoProducaoController implements Initializable {

    @FXML
    private ComboBox<Mes> mes_combo;

    @FXML
    private ComboBox<Integer> ano_combo;

    @FXML
    private RadioButton todos_rbt;

    @FXML
    private ToggleGroup animalgroup;

    @FXML
    private RadioButton indiv_rbt;

    @FXML
    private TextField cod_tx;
    
    @FXML
    private TextField media_tx;

    @FXML
    private TextField nome_tx;

    @FXML
    private TextField peso_tx;

    @FXML
    private TextField nasc_tx;

    @FXML
    private TextField raca_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private LineChart<String, Double> grafico;

    @FXML
    private CategoryAxis cat_ax;

    @FXML
    private NumberAxis num_ax;

    private Animal animal;

    private String selecionaRaca(int id_raca) {
        switch (id_raca) {
            case 1:
                return "Jersey";

            case 2:
                return "Holandesa";

            case 3:
                return "Pardo Suiço";

            case 4:
                return "Girolando";

            case 5:
                return "Jersey-Holanda";

            default:
                return "Jersey";

        }
    }

    private LocalDate d1, d2;

    @FXML
    public void carregar() {
        try {
            if (d1 == null && d2 == null) {
                if (ano_combo.getValue() != null && mes_combo.getValue() != null) {
                    d1 = LocalDate.of(ano_combo.getValue(), mes_combo.getValue().num, 1);
                    d2 = LocalDate.of(ano_combo.getValue(), mes_combo.getValue().num, d1.lengthOfMonth());
                } else {
                    throw new ArithmeticException();
                }
            }
            String s = null, nome = null;
            if (indiv_rbt.isSelected()) {
                if (animal == null) {
                    throw new IOException();
                } else {
                    nome = animal.getNome();
                    s = "SELECT SUM(p.litragem) as lit, p.data_producao from producao p where p.data_producao between '" + d1 + "' and '" + d2 + "' and id_vaca = " + animal.getId_animal() + " group by p.data_producao order by p.data_producao;";
                }
            } else {
                nome = "Geral";
                s = "SELECT SUM(p.litragem) as lit, p.data_producao from producao p where p.data_producao between '" + d1 + "' and '" + d2 + "' group by p.data_producao order by p.data_producao;";
            }

            DAOProducao dp = new DAOProducao();
            List<Producao> list = dp.pesquisaCustom(s);
            initGrafico(list, nome);
            d1 = null;
            d2 = null;
        } catch (ArithmeticException a) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Atenção!");
            dodsad.setHeaderText("Selecione uma data para carregar!");
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (IOException io) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Atenção!");
            dodsad.setHeaderText("Carregue um animal para continuar!");
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            cod_tx.requestFocus();
        } catch (Exception e) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Erro!");
            dodsad.setHeaderText("Ocorreu um erro ao carregar.");
            dodsad.setContentText(e.getMessage());
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            cod_tx.requestFocus();
        }
    }

    private void initGrafico(List<Producao> list, String nome) {
        cat_ax.setLabel("Data");
        num_ax.setLabel("Litros");

        XYChart.Series<String, Double> series = new XYChart.Series();
        for (Producao k : list) {
            series.getData().add(new XYChart.Data(k.getData_producao(), k.getLitragem()));
        }

        series.setName(nome);
        grafico.getData().add(series);

    }

    @FXML
    public void limpar() {
        todos_rbt.setSelected(true);
        desabilitaCampos();
        grafico.getData().clear();
    }

    @FXML
    public void buscaAnimal() {
        if (cod_tx.getText().equals("")) {
            Main.tocaErro();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {

                }
            });
        } else {
            try {
                DAOAnimal da = new DAOAnimal();
                animal = da.consultaAnimal(Integer.parseInt(cod_tx.getText()));
                if (animal.getId_tipo() != 1) {
                    animal = null;
                    throw new ArithmeticException();
                }
                nome_tx.setText(animal.getNome());
                peso_tx.setText(Double.toString(animal.getPeso()) + " Kg");
                nasc_tx.setText(animal.getNascimento());
                raca_tx.setText(selecionaRaca(animal.getId_raca()));
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
            } catch (Exception e) {
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

            }
        }
    }

    public class Mes {

        int num;
        String nome;

        public Mes(int n, String m) {
            num = n;
            nome = m;
        }

        public int getNum() {
            return num;
        }

        public String getNome() {
            return nome;
        }
    }

    ObservableList<Mes> listmes;
    ObservableList<Integer> listano;

    @FXML
    public void esteMes() {
        ano_combo.getSelectionModel().selectFirst();
        mes_combo.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
    }

    @FXML
    public void ultimos3() {
        LocalDate d = LocalDate.now();
        d1 = d.minusMonths(3);
        d1 = d1.withDayOfMonth(1);
        d2 = d;
        carregar();
    }

    @FXML
    public void ultimos6() {
        LocalDate d = LocalDate.now();
        d1 = d.minusMonths(6);
        d1 = d1.withDayOfMonth(1);
        d2 = d;
        carregar();
    }

    @FXML
    public void ultimos12() {
        LocalDate d = LocalDate.now();
        d1 = d.minusYears(1);
        d1 = d1.withDayOfMonth(1);
        d2 = d;
        carregar();
    }

    private void initComboboxes() {
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
        listano = FXCollections.observableArrayList();
        listmes.add(new Mes(1, "Janeiro"));
        listmes.add(new Mes(2, "Fevereiro"));
        listmes.add(new Mes(3, "Março"));
        listmes.add(new Mes(4, "Abril"));
        listmes.add(new Mes(5, "Maio"));
        listmes.add(new Mes(6, "Junho"));
        listmes.add(new Mes(7, "Julho"));
        listmes.add(new Mes(8, "Agosto"));
        listmes.add(new Mes(9, "Setembro"));
        listmes.add(new Mes(10, "Outubro"));
        listmes.add(new Mes(11, "Novembro"));
        listmes.add(new Mes(12, "Dezembro"));

        for (int i = 2000; i <= LocalDate.now().getYear(); i++) {
            listano.add(i);
        }
        listano.sort((Integer m1, Integer m2) -> {
                if (m1==m2) {
                    return 0;
                } else if (m1<m2) {
                    return 1;
                } else {
                    return -1;
                }
            
            
        });
        ano_combo.setItems(listano);
        mes_combo.setItems(listmes);
    }

    @FXML
    public void desabilitaCampos() {
        animal = null;
        cod_tx.clear();
        nome_tx.clear();
        peso_tx.clear();
        raca_tx.clear();
        nasc_tx.clear();
        cod_tx.setDisable(true);
        nome_tx.setDisable(true);
        peso_tx.setDisable(true);
        nasc_tx.setDisable(true);
        raca_tx.setDisable(true);
    }

    @FXML
    public void habilitaCampos() {
        cod_tx.setDisable(false);
        nome_tx.setDisable(false);
        peso_tx.setDisable(false);
        nasc_tx.setDisable(false);
        raca_tx.setDisable(false);
    }

    public void initMedia(){
        try{
            DAOProducao dd = new DAOProducao();
            media_tx.setText(Double.toString(Math.round(dd.mediaGeral()*100)/100));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Analítico [Produção] - BoviPlus");
        }
        initComboboxes();
        initMedia();
        desabilitaCampos();
    }

}
