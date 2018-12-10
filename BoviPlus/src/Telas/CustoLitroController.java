package Telas;

import DAO.DAOContas_Receber;
import DAO.DAOContas_pagar;
import DAO.DAOGasto_diario;
import DAO.DAOProducao;
import Entidades.Contas_Receber;
import Entidades.Contas_pagar;
import Entidades.Gasto_diario;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

public class CustoLitroController implements Initializable {

    @FXML
    private LineChart<String, Double> grafico;

    @FXML
    private CategoryAxis xaxis;

    @FXML
    private NumberAxis yaxis;

    @FXML
    private TextField alim_tx;

    @FXML
    private TextField saude_tx;

    @FXML
    private TextField ctotal_tx;

    @FXML
    private TextField venda_tx;

    @FXML
    private TextField custo_tx;

    @FXML
    private TextField lucro_tx;

    DAOProducao dp;
    DAOContas_pagar dv;
    DAOGasto_diario dg;

    public List<Double> getGastos(String data1, String data2) {
        List<Double> lista = new ArrayList<>();
        try {
            dp = new DAOProducao();
            double litros = dp.producaoPeriodo(data1, data2);
            dg = new DAOGasto_diario();
            List<Gasto_diario> alim = dg.listarTodosporData(data1, data2);
            double soma1 = 0, soma2 = 0;
            for (Gasto_diario k : alim) {
                soma1 = soma1 + k.getValor();
            }
            dv = new DAOContas_pagar();
            List<Contas_pagar> vac = dv.pesquisaCustom("select * from contas_pagar where origem = 3 and data_pagamento between '" + data1 + "' and '" + data2 + "';");
            for (Contas_pagar c : vac) {
                soma2 = soma2 + c.getValor();
            }

            lista.add(soma1 / litros);
            lista.add(soma2 / litros);
            DAOContas_Receber dr = new DAOContas_Receber();
            List<Contas_Receber> ll = dr.pesquisaCustom("select * from contas_receber where producao = true and data_recebimento between '" + data1 + "' and '" + data2 + "';");
            double mediarecebida, temp = 0;
            for (Contas_Receber p : ll) {
                temp = temp + (p.valor);
            }
            mediarecebida = temp / ll.size();
            lista.add(mediarecebida);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    private String mesporNumero(int num) {
        switch (num) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            default:
                return "Dezembro";
        }

    }

    public void processaDados() {
        xaxis.setLabel("Mês");
        yaxis.setLabel("Valor");

        XYChart.Series<String, Double> series = new XYChart.Series();
        series.setName("Custo de produção por mês");
        XYChart.Series<String, Double> series1 = new XYChart.Series();
        series1.setName("Lucro por mês");
        XYChart.Series<String, Double> series2 = new XYChart.Series();
        series2.setName("Valor de venda por mês");

        List<Double> dados = new ArrayList<>();
        int i = 5;
        while (i > -1) {
            dados = getGastos(LocalDate.now().minusMonths(i).withDayOfMonth(1).toString(), LocalDate.now().minusMonths(i).withDayOfMonth(LocalDate.now().minusMonths(i).lengthOfMonth()).toString());
            series.getData().add(new XYChart.Data(mesporNumero(LocalDate.now().minusMonths(i).getMonthValue()), Math.round((dados.get(0) + dados.get(1)) * 100.0) / 100.0));
            series1.getData().add(new XYChart.Data(mesporNumero(LocalDate.now().minusMonths(i).getMonthValue()), Math.round((dados.get(2) - dados.get(0) + dados.get(1)) * 100.0) / 100.0));
            series2.getData().add(new XYChart.Data(mesporNumero(LocalDate.now().minusMonths(i).getMonthValue()), Math.round(dados.get(2) * 100.0) / 100.0));
            i--;
        }

        alim_tx.setText(Double.toString(Math.round(dados.get(0) * 100.0) / 100.0));
        saude_tx.setText(Double.toString(Math.round(dados.get(1) * 100.0) / 100.0));
        ctotal_tx.setText(Double.toString(Math.round((dados.get(0) + dados.get(1)) * 100.0) / 100.0));
        venda_tx.setText(Double.toString(Math.round(dados.get(2) * 100.0) / 100.0));
        custo_tx.setText(Double.toString(Math.round((dados.get(0) + dados.get(1)) * 100.0) / 100.0));
        lucro_tx.setText(Double.toString(Math.round((dados.get(2) - dados.get(0) + dados.get(1)) * 100.0) / 100.0));
        Platform.runLater(() -> {
            grafico.getData().clear();
            grafico.getData().add(series);
            grafico.getData().add(series2);
            grafico.getData().add(series1);
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread() {
            @Override
            public void run() {
                processaDados();
            }
        }.start();
    }

}
