package Telas;

import DAO.DAOContas_Receber;
import DAO.DAOContas_pagar;
import Entidades.Contas_Receber;
import Entidades.Contas_pagar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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

public class ControleFinanceiroController implements Initializable {

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
    private RadioButton rprod_rbt;

    @FXML
    private RadioButton rgeral_rbt;

    @FXML
    private Button carregar_bt;

    @FXML
    private PieChart pagar_pie;

    @FXML
    private PieChart receber_pie;
    
    @FXML
    private ToggleGroup pagarg;

    @FXML
    private ToggleGroup receberg;
    
    @FXML
    private TextField ptotal_tx;

    @FXML
    private TextField preprod_tx;

    @FXML
    private TextField palim_tx;

    @FXML
    private TextField psaude_tx;

    @FXML
    private TextField pgeral_tx;

    @FXML
    private TextField rtotal_tx;

    @FXML
    private TextField rprod_tx;

    @FXML
    private TextField rgeral_tx;

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
            }

            DAOContas_pagar dp = new DAOContas_pagar();
            List<Contas_pagar> aa = dp.pesquisaCustom(montaSQLPagar());
            DAOContas_Receber dr = new DAOContas_Receber();
            List<Contas_Receber> bb = dr.pesquisaCustom(montaSQLReceber());

            initGraficoPagar(aa);
            initGraficoReceber(bb);

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

    private String montaSQLPagar() {
        String s = "select * from contas_pagar where id_contas_pagar > 0";

        if (inicio_dp.getValue() != null) {
            s = strcat(s, " and data_vencimento >= '" + inicio_dp.getValue().toString() + "'");
        }
        if (fim_dp.getValue() != null) {
            s = strcat(s, " and data_vencimento <= '" + fim_dp.getValue().toString() + "'");
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
            s = strcat(s, " and data_vencimento >= '" + inicio_dp.getValue().toString() + "'");
        }
        if (fim_dp.getValue() != null) {
            s = strcat(s, " and data_vencimento <= '" + fim_dp.getValue().toString() + "'");
        }

        if (rgeral_rbt.isSelected()) {
            s = strcat(s, " and producao = false");
        } else if (rprod_rbt.isSelected()) {
            s = strcat(s, " and producao = true");
        }

        return strcat(s, ";");
    }

    @FXML
    public void abrePagar() throws IOException {
        Stage editar = new Stage();
        editar.setResizable(false);
        Main.window = false;
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaPagar.fxml"));
        Scene scene = new Scene(fxmlDB);
        editar.getIcons().add(new Image("/Imagens/Iconew.png"));
        editar.setTitle("Consulta de Contas a Pagar [Financeiro] - BoviPlus");
        editar.setScene(scene);
        editar.show();
        Main.window = true;
    }

    @FXML
    public void abreReceber() throws IOException {
        Stage editar = new Stage();
        editar.setResizable(false);
        Main.window = false;
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaReceber.fxml"));
        Scene scene = new Scene(fxmlDB);
        editar.getIcons().add(new Image("/Imagens/Iconew.png"));
        editar.setTitle("Consulta de Contas a Receber [Financeiro] - BoviPlus");
        editar.setScene(scene);
        editar.show();
        Main.window = true;
    }

    private void initGraficoPagar(List<Contas_pagar> list) {
        pagar_pie.getData().clear();
        double soma0 = 0, soma1 = 0, soma2 = 0, soma3 = 0, total = 0;

        for (Contas_pagar c : list) {
            if (c.getOrigem() == 0) {
                soma0 = soma0 + c.getValor();
            } else if (c.getOrigem() == 1) {
                soma1 = soma1 + c.getValor();
            } else if (c.getOrigem() == 2) {
                soma2 = soma2 + c.getValor();
            } else if (c.getOrigem() == 3) {
                soma3 = soma3 + c.getValor();
            }
            total = total + c.getValor();
        }

        pgeral_tx.setText("R$ " + Double.toString(Math.round(soma0*100)/100));
        preprod_tx.setText("R$ " + Double.toString(Math.round(soma1*100)/100));
        palim_tx.setText("R$ " + Double.toString(Math.round(soma2*100)/100));
        psaude_tx.setText("R$ " + Double.toString(Math.round(soma3*100)/100));
        ptotal_tx.setText("R$ " + Double.toString(Math.round(total*100)/100));
        PieChart.Data slice1 = new PieChart.Data("Outros", soma0);
        PieChart.Data slice2 = new PieChart.Data("Reprodução", soma1);
        PieChart.Data slice3 = new PieChart.Data("Alimento", soma2);
        PieChart.Data slice4 = new PieChart.Data("Saúde", soma3);

        pagar_pie.getData().addAll(slice1, slice2, slice3, slice4);
    }

    private void initGraficoReceber(List<Contas_Receber> list) {
        receber_pie.getData().clear();
        double soma0 = 0, soma1 = 0, total = 0;

        for (Contas_Receber c : list) {
            if (c.isProducao()) {
                soma0 = soma0 + c.getValor()*c.getQtd();
            } else {
                soma1 = soma1 + c.getValor()*c.getQtd();
            }
            total = total + c.getValor()*c.getQtd();
        }

        rtotal_tx.setText("R$ " + Double.toString(Math.round(total*100)/100));
        rprod_tx.setText("R$ " + Double.toString(Math.round(soma0*100)/100));
        rgeral_tx.setText("R$ " + Double.toString(Math.round(soma1*100)/100));
        PieChart.Data slice1 = new PieChart.Data("Produção", soma0);
        PieChart.Data slice2 = new PieChart.Data("Outros", soma1);

        receber_pie.getData().addAll(slice1, slice2);
    }

    public String strcat(String a, String b) {
        return a + b;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
