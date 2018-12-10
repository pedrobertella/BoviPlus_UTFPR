package Telas;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SobreController {

    private int count = 0;
    
    @FXML
    private Button bt_sair;
    
    @FXML
    public void supresa(){
        count++;
        if(count==7){
            Main.tocaStart();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(getClass().getResource("/Imagens/Cow.png").toExternalForm()));
            alert.setGraphic(imageView);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert.setTitle("Muuuuuuuuuuu!");
            alert.setHeaderText("Muuuuuuuuuuuuuuuuuuuuuuuuuuuuu"
                    + "\nuuuuuuuuuuuuuuuuuuuu...");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
        }
    }

    @FXML
    protected void voltarTelaPrincipal(){
        TelaPrincipalController.fecharSobre();
    }
}
