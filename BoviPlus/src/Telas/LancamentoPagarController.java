package Telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class LancamentoPagarController implements Initializable {

    @FXML
    private Button geral_bt;

    @FXML
    private Button alim_bt;

    @FXML
    private Button reprod_bt;

    @FXML
    private Button saude_bt;

    @FXML
    private AnchorPane panetela;

    @FXML
    public void geral() {
        try {
            resetaCores();
            AnchorPane aa = FXMLLoader.load(getClass().getResource("PagarGeral.fxml"));
            panetela.getChildren().setAll(aa);
            geral_bt.setStyle("-fx-background-color: #606060;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void reprod() {
        try {
            resetaCores();
            AnchorPane aa = FXMLLoader.load(getClass().getResource("PagarReproducao.fxml"));
            panetela.getChildren().setAll(aa);
            reprod_bt.setStyle("-fx-background-color: #606060;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void alim() {
        try {
            resetaCores();
            AnchorPane aa = FXMLLoader.load(getClass().getResource("PagarAlimentacao.fxml"));
            panetela.getChildren().setAll(aa);
            alim_bt.setStyle("-fx-background-color: #606060;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void saude() {
        try {
            resetaCores();
            AnchorPane aa = FXMLLoader.load(getClass().getResource("PagarSaude.fxml"));
            panetela.getChildren().setAll(aa);
            saude_bt.setStyle("-fx-background-color: #606060;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void resetaCores() {
        geral_bt.setStyle("-fx-background-color: #303030;");
        alim_bt.setStyle("-fx-background-color: #303030;");
        reprod_bt.setStyle("-fx-background-color: #303030;");
        saude_bt.setStyle("-fx-background-color: #303030;");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        geral();
    }

}
