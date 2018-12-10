package Telas;

import DAO.DAOAlimentacao;
import DAO.DAOAlimento;
import DAO.DAOEstoque;
import Entidades.Alimentacao;
import Entidades.Alimento;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AnaliticoAlimentacaoController implements Initializable {

    @FXML
    private ListView<Alimento> lista;

    @FXML
    private LineChart<String, Double> grafico;

    @FXML
    private CategoryAxis xaxis;

    @FXML
    private NumberAxis yaxis;

    ObservableList<Alimento> list;
    DAOAlimento da;

    public void initLista() {
        list = FXCollections.observableArrayList();
        try {
            da = new DAOAlimento();
            List<Alimento> listU = da.listarTodos();
            list.addAll(listU);
            lista.setItems(null);
            lista.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lista.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                carregaDados();
            }
        });
    }

    public void initGrafico(String nome, double est, double gasto) {
        xaxis.setLabel("Dias");
        yaxis.setLabel("Estoque");
        int dias = 0;
        XYChart.Series<String, Double> series = new XYChart.Series();
        while (est > 0-gasto) {
            series.getData().add(new XYChart.Data(Integer.toString(dias), est));
            est = est - gasto;
            dias++;
        }

        series.setName(nome);
        grafico.getData().clear();
        grafico.getData().add(series);
    }

    public void carregaDados() {
        try {
            Alimento u = lista.getSelectionModel().getSelectedItem();
            DAOAlimentacao dudu = new DAOAlimentacao();
            if (dudu.ultimaData(u.getId()) == null) {
                throw new ArithmeticException();
            }
            String uData = dudu.ultimaData(u.getId());
            System.out.println(uData);
            List<Alimentacao> ll = dudu.pesquisaCustom("select * from alimentacao where data_alimentacao = '" + uData + "' and id_alimento = " + u.getId() + ";");
            double soma = 0;
            for (Alimentacao a : ll) {
                soma = soma + a.getQtd();
            }
            DAOEstoque de = new DAOEstoque();
            double est = de.somaPorAlimento(u.getId());
            initGrafico(u.getNome(), est, soma);
        } catch (ArithmeticException a) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Sem alimentações");
            alert1.setHeaderText("Não existem alimentações registradas com este alimento,\n"
                    + "portanto, não é possível calcular a previsão de fim de estoque.");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {

                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLista();
        if (Main.window) {
            Main.stage.setTitle("Previsão de Estoque [Alimentação] - BoviPlus");
        }
    }

}
