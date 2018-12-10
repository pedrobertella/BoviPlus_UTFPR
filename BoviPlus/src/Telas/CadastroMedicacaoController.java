package Telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class CadastroMedicacaoController implements Initializable {

    
    @FXML
    private TabPane tabpane;

    @FXML
    private Tab medicamento_tab;

    @FXML
    private AnchorPane medicamento_pane;

    @FXML
    private Tab vacina_tab;

    @FXML
    private AnchorPane vacina_pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Main.window){
            Main.stage.setTitle("Cadastro de Medicação [Saúde] - BoviPlus");
        }
        try{
            AnchorPane ac = FXMLLoader.load(getClass().getResource("CadastroMedicamento.fxml"));
            medicamento_tab.setContent(ac);
            AnchorPane ad = FXMLLoader.load(getClass().getResource("CadastroVacina.fxml"));
            vacina_tab.setContent(ad);
        }catch(Exception e){
            System.out.println("DEU UM BAITA ERRO");
            System.out.println(e.getMessage());
        }
    }    
    
}
