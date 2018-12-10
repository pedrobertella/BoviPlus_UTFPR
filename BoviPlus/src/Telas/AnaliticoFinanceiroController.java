package Telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class AnaliticoFinanceiroController implements Initializable {

    @FXML
    private Tab control_tab;

    @FXML
    private Tab calc_tab;
    
    @FXML
    private Tab litro_tab;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Analítico [Financeiro] - BoviPlus");
        }
        try{
            AnchorPane aa = FXMLLoader.load(getClass().getResource("ControleFinanceiro.fxml"));
            control_tab.setContent(aa);
            aa = FXMLLoader.load(getClass().getResource("CalculoLucroFinanceiro.fxml"));
            calc_tab.setContent(aa);
            aa = FXMLLoader.load(getClass().getResource("CustoLitro.fxml"));
            litro_tab.setContent(aa);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }    
    
}
