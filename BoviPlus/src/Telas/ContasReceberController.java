package Telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class ContasReceberController implements Initializable {

    @FXML
    private TabPane tabpane;

    @FXML
    private Tab hoje_tab;

    @FXML
    private AnchorPane hoje_pane;

    @FXML
    private Tab consulta_tab;

    @FXML
    private AnchorPane consulta_pane;

    @FXML
    private Tab lanc_tab;

    @FXML
    private AnchorPane lanc_pane;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Main.window){
            Main.stage.setTitle("Contas a Receber [Financeiro] - BoviPlus");
        }
        try{
            AnchorPane aa = FXMLLoader.load(getClass().getResource("LdiaReceber.fxml"));
            hoje_tab.setContent(aa);
            AnchorPane ac = FXMLLoader.load(getClass().getResource("ConsultaReceber.fxml"));
            consulta_tab.setContent(ac);
            AnchorPane ad = FXMLLoader.load(getClass().getResource("LancamentoReceber.fxml"));
            lanc_tab.setContent(ad);
        }catch(Exception e){
            System.out.println("DEU UM BAITA ERRO");
            System.out.println(e.getMessage());
        }
    }    
    
}
