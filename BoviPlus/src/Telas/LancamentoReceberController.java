package Telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class LancamentoReceberController implements Initializable {

    @FXML
    private Button geral_bt;

    @FXML
    private Button prod_bt;

    @FXML
    private AnchorPane panetela;
    
    @FXML
    public void geral() {
        try {
            resetaCores();
            AnchorPane aa = FXMLLoader.load(getClass().getResource("ReceberGeral.fxml"));
            panetela.getChildren().setAll(aa);
            geral_bt.setStyle("-fx-background-color: #606060;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void prod() {
        try {
            resetaCores();
            AnchorPane aa = FXMLLoader.load(getClass().getResource("ReceberProducao.fxml"));
            panetela.getChildren().setAll(aa);
            prod_bt.setStyle("-fx-background-color: #606060;");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    private void resetaCores() {
        geral_bt.setStyle("-fx-background-color: #303030;");
        prod_bt.setStyle("-fx-background-color: #303030;");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        geral();
    }    
    
}
