package Telas;

import DAO.DAOAnimal;
import DAO.DAOProducao;
import Entidades.Animal;
import Entidades.Producao;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RendimentoGrupoController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TableView<pc> tabela;

    @FXML
    private TableColumn<pc, Integer> cod_col;

    @FXML
    private TableColumn<pc, String> vaca_col;

    @FXML
    private TableColumn<pc, Double> media_col;

    @FXML
    private RadioButton acima_rbt;

    @FXML
    private RadioButton abaixo_rbt;

    @FXML
    private TextField media_tx;

    @FXML
    private Button carregar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private RadioButton mes1_rbt;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private RadioButton mes3_rbt;

    @FXML
    private RadioButton mes6_rbt;

    @FXML
    private RadioButton mes9_rbt;

    @FXML
    private RadioButton mes9a_rbt;

    @FXML
    private TextField numero_tx;

    @FXML
    private Button rendimento_bt;

    public class pc {

        public int cod;
        public String vaca;
        public double litros;

        public pc(int cod, String n, double l) {
            this.cod = cod;
            vaca = n;
            litros = l;
        }

        public String getVaca() {
            return vaca;
        }

        public void setVaca(String nome) {
            this.vaca = nome;
        }

        public int getCod() {
            return cod;
        }

        public void setCod(int cod) {
            this.cod = cod;
        }

        public double getLitros() {
            return litros;
        }

        public void setLitros(double litros) {
            this.litros = litros;
        }

    }
    public Stage estagio;
    List<Integer> aa;
    ObservableList<pc> list = FXCollections.observableArrayList();
    DAOAnimal da;
    DAOProducao dp;

    @FXML
    public void carregar() {
        try {
            list = FXCollections.observableArrayList();
            da = new DAOAnimal();
            dp = new DAOProducao();

            LocalDate i, j;
            if (mes1_rbt.isSelected()) {
                i = LocalDate.now().minusMonths(1);
                j = LocalDate.now();
                aa = da.consultaHistorico(i.toString(), j.toString());
            } else if (mes3_rbt.isSelected()) {
                i = LocalDate.now().minusMonths(3);
                j = LocalDate.now().minusMonths(1);
                aa = da.consultaHistorico(i.toString(), j.toString());
            } else if (mes6_rbt.isSelected()) {
                i = LocalDate.now().minusMonths(6);
                j = LocalDate.now().minusMonths(3);
                aa = da.consultaHistorico(i.toString(), j.toString());
            } else if (mes9_rbt.isSelected()) {
                i = LocalDate.now().minusMonths(9);
                j = LocalDate.now().minusMonths(6);
                aa = da.consultaHistorico(i.toString(), j.toString());
            } else if (mes9a_rbt.isSelected()) {
                i = LocalDate.now().minusYears(90);
                j = LocalDate.now().minusMonths(9);
                aa = da.consultaHistorico(i.toString(), j.toString());
            } else if (abaixo_rbt.isSelected()) {
                i = LocalDate.now().minusYears(90);
                j = LocalDate.now();
                List<Integer> kk = da.consultaHistorico(i.toString(), j.toString());
                Double mgeral = Double.parseDouble(media_tx.getText());
                aa = new ArrayList<>();
                for (int k : kk) {
                    if (dp.mediaAnimal(k) <= mgeral) {
                        aa.add(k);
                    }
                }
            } else {
                i = LocalDate.now().minusYears(90);
                j = LocalDate.now();
                List<Integer> kk = da.consultaHistorico(i.toString(), j.toString());
                Double mgeral = Double.parseDouble(media_tx.getText());
                aa = new ArrayList<>();
                for (int k : kk) {
                    if (dp.mediaAnimal(k) > mgeral) {
                        aa.add(k);
                    }
                }
            }

            pc u;
            if (aa.isEmpty()) {
                throw new ArithmeticException();
            }
            for (int k : aa) {
                u = new pc(k, buscaNome(k), mediaDiaria(k));
                list.add(u);
            }
            numero_tx.setText(Integer.toString(aa.size()));
            tabela.setItems(null);
            tabela.setItems(list);
        } catch (ArithmeticException a) {
            Main.tocaErro();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Não existem vacas neste grupo!!!");
            ko.setContentText(a.getMessage());
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
                }
            });
        }
    }

    @FXML
    public void limpar() {
        mes1_rbt.setSelected(true);
        tabela.setItems(null);
        numero_tx.clear();
    }

    private double mediaDiaria(int id) {
        try {
            dp = new DAOProducao();
            List<Producao> a = dp.buscaporanimal(id);

            double num = 0.0, media;
            int i = 0;

            for (Producao j : a) {
                num = num + j.getLitragem();
                i++;
            }
            media = num / i;
            media = Math.round(media * 100.0) / 100.0;
            return media;
        } catch (Exception e) {
            return 0.00;
        }

    }

    @FXML
    public void rendimentoAnimal() {
        try {
            Main.id_animal_prod = Integer.toString(tabela.getSelectionModel().getSelectedItem().cod);
            estagio = new Stage();
            estagio.setResizable(false);
            Main.window = false;
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("RendimentoIndividual.fxml"));
            Scene scene = new Scene(fxmlDB);
            estagio.getIcons().add(new Image("/Imagens/Iconew.png"));
            estagio.setScene(scene);
            estagio.setTitle("Rendimento por Animal [Produção] - BoviPlus");
            estagio.show();
            Main.window = true;
        } catch (Exception k) {
            System.out.println("erro");
        }

    }

    private String buscaNome(int id) {
        try {
            da = new DAOAnimal();
            Animal a = da.consultaAnimal(id);
            return a.getNome();
        } catch (Exception e) {
            return null;
        }
    }

    private void mediaGeral() throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        dp = new DAOProducao();
        media_tx.setText(Double.toString(dp.mediaGeral()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Rendimento por Grupo [Produção] - BoviPlus");
        }
        cod_col.setCellValueFactory(new PropertyValueFactory("cod"));
        vaca_col.setCellValueFactory(new PropertyValueFactory("vaca"));
        media_col.setCellValueFactory(new PropertyValueFactory("litros"));
        tabela.setPlaceholder(new Label("Sem conteúdo para exibir."));
        mes1_rbt.setSelected(true);

        try {
            mediaGeral();
        } catch (Exception e) {
            System.out.println("ERRO");
        }
    }

}
