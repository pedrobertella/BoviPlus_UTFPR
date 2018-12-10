package Telas;

import DAO.DAOContas_Receber;
import DAO.DAOContas_pagar;
import Entidades.Contas_Receber;
import Entidades.Contas_pagar;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CalculoLucroFinanceiroController implements Initializable {

    @FXML
    private DatePicker inicio_dp;

    @FXML
    private Button dias15_bt;

    @FXML
    private Button dias30_bt;

    @FXML
    private Button dias60_bt;

    @FXML
    private DatePicker fim_dp;

    @FXML
    private Button hoje_bt;

    @FXML
    private RadioButton ptodos_rbt;

    @FXML
    private ToggleGroup pagargrupo;

    @FXML
    private RadioButton preprod_rbt;

    @FXML
    private RadioButton palim_rbt;

    @FXML
    private RadioButton psaude_rbt;

    @FXML
    private RadioButton pgeral_rbt;

    @FXML
    private RadioButton rtodos_rbt;

    @FXML
    private ToggleGroup recebergrupo;

    @FXML
    private RadioButton rprod_rbt;

    @FXML
    private RadioButton rgeral_rbt;

    @FXML
    private Button carregar_bt;

    @FXML
    private BarChart<?, ?> grafico;

    @FXML
    private CategoryAxis xaxis;

    @FXML
    private NumberAxis yaxis;

    @FXML
    private TextField gastos_tx;

    @FXML
    private TextField rend_tx;

    @FXML
    private TextField lucro_tx;

    public String strcat(String a, String b) {
        return a + b;
    }

    @FXML
    public void dias15() {
        inicio_dp.setValue(LocalDate.now().minusDays(15));
    }

    @FXML
    public void dias30() {
        inicio_dp.setValue(LocalDate.now().minusDays(30));
    }

    @FXML
    public void dias60() {
        inicio_dp.setValue(LocalDate.now().minusDays(60));
    }

    @FXML
    public void hjdp() {
        fim_dp.setValue(LocalDate.now());
    }

    @FXML
    public void esteMes() {
        fim_dp.setValue(LocalDate.now());
        inicio_dp.setValue(LocalDate.now().withDayOfMonth(1));
        buscar();
    }

    @FXML
    public void mesAnterior() {
        LocalDate temp = LocalDate.now().withDayOfMonth(1);
        temp = temp.minusMonths(1);
        inicio_dp.setValue(temp);
        fim_dp.setValue(temp.withDayOfMonth(temp.lengthOfMonth()));
        buscar();
    }

    @FXML
    public void ultimos90() {
        fim_dp.setValue(LocalDate.now());
        inicio_dp.setValue(LocalDate.now().minusDays(90));
        buscar();
    }

    @FXML
    public void ultimos6() {
        fim_dp.setValue(LocalDate.now());
        inicio_dp.setValue(LocalDate.now().minusMonths(6));
        buscar();
    }

    @FXML
    public void buscar() {
        try {
            if (inicio_dp.getValue() != null && fim_dp.getValue() != null) {
                if (inicio_dp.getValue().isAfter(fim_dp.getValue())) {
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
                    fim_dp.setValue(inicio_dp.getValue());
                }

                DAOContas_pagar dp = new DAOContas_pagar();
                List<Contas_pagar> aa = dp.pesquisaCustom(montaSQLPagar());
                DAOContas_Receber dr = new DAOContas_Receber();
                List<Contas_Receber> bb = dr.pesquisaCustom(montaSQLReceber());

                initGrafico(bb, aa);
            } else {
                Main.tocaConfirma();
                Alert ko = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("É necerrário o preenchimento das datas! Verifique...");
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
            }

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

    private void initGrafico(List<Contas_Receber> bb, List<Contas_pagar> aa) {
        grafico.getData().clear();

        xaxis.setLabel("Categoria");
        yaxis.setLabel("Valor");
        double somap = 0, somar = 0, lucro;

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Rendimento");
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Gastos");
        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Receita no Período");

        for (Contas_pagar cp : aa) {
            somap = somap + cp.getValor();
        }

        for (Contas_Receber cp : bb) {
            somar = somar + cp.getValor() * cp.getQtd();
        }

        lucro = somar - somap;

        gastos_tx.setText("R$ " + Double.toString(Math.round(somap * 100) / 100));
        rend_tx.setText("R$ " + Double.toString(Math.round(somar * 100) / 100));
        lucro_tx.setText("R$ " + Double.toString(Math.round(lucro * 100) / 100));
        dataSeries1.getData().add(new XYChart.Data("Rendimento", somar));
        dataSeries2.getData().add(new XYChart.Data("Gastos", somap));
        dataSeries3.getData().add(new XYChart.Data("Receita no Período", lucro));

        grafico.getData().addAll(dataSeries1, dataSeries2, dataSeries3);
    }

    private String montaSQLPagar() {
        String s = "select * from contas_pagar where id_contas_pagar > 0";

        if (inicio_dp.getValue() != null) {
            s = strcat(s, " and data_pagamento >= '" + inicio_dp.getValue().toString() + "'");
        }
        if (fim_dp.getValue() != null) {
            s = strcat(s, " and data_pagamento <= '" + fim_dp.getValue().toString() + "'");
        }

        if (pgeral_rbt.isSelected()) {
            s = strcat(s, " and origem = 0");
        } else if (preprod_rbt.isSelected()) {
            s = strcat(s, " and origem = 1");
        } else if (palim_rbt.isSelected()) {
            s = strcat(s, " and origem = 2");
        } else if (psaude_rbt.isSelected()) {
            s = strcat(s, " and origem = 3");
        }

        return strcat(s, ";");
    }

    private String montaSQLReceber() {
        String s = "select * from contas_receber where id_contas_receber > 0";

        if (inicio_dp.getValue() != null) {
            s = strcat(s, " and data_recebimento >= '" + inicio_dp.getValue().toString() + "'");
        }
        if (fim_dp.getValue() != null) {
            s = strcat(s, " and data_recebimento <= '" + fim_dp.getValue().toString() + "'");
        }

        if (rgeral_rbt.isSelected()) {
            s = strcat(s, " and producao = false");
        } else if (rprod_rbt.isSelected()) {
            s = strcat(s, " and producao = true");
        }

        return strcat(s, ";");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
