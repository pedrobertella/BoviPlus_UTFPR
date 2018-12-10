package Telas;

import DAO.DAORaca;
import Entidades.Raca;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CadastroRacaController implements Initializable {

    @FXML
    private Button add_bt;

    @FXML
    private Button del_bt;

    @FXML
    private TextField nome_tx;

    @FXML
    private TextField tempo_tx;

    @FXML
    private TextArea desc_ta;
    
    public Raca a;
    public boolean carregado = false;
    
    @FXML
    public void salvar(){
         if (nome_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Informe um nome para salvar...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            nome_tx.requestFocus();
        } else if (tempo_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa informar um tempo de gestação para salvar!");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            tempo_tx.requestFocus();
        } else if (desc_ta.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Você precisa preencher a descrição!");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            desc_ta.requestFocus();
        } else {
            if (!carregado) {
                try {
                    DAORaca d = new DAORaca();
                    Raca r = new Raca();
                    r.setId_raca(d.proximoID());
                    r.setNome(nome_tx.getText());
                    r.setTempo_gestacao(Integer.parseInt(tempo_tx.getText()));
                    r.setDescricao(desc_ta.getText());
                    d.cadastrar_raca(r);
                    a = r;
                    Main.tocaSucesso();
                    Alert ko = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Sucesso!");
                    ko.setHeaderText("A Raça foi cadastrada!");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                    carregado = true;
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert ko = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("Ocorreu um erro ao salvar!");
                    ko.setContentText(e.getMessage());
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                }

            } else {
                try {
                    DAORaca d = new DAORaca();
                    a.setNome(nome_tx.getText());
                    a.setDescricao(desc_ta.getText());
                    a.setTempo_gestacao(Integer.parseInt(tempo_tx.getText()));
                    d.atualizarRaca(a.getId_raca(), a.getNome(), a.getTempo_gestacao(), a.getDescricao());

                    Main.tocaSucesso();
                    Alert ko = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Sucesso!");
                    ko.setHeaderText("A raça foi atualizada!");
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });

                } catch (Exception e) {
                    Main.tocaErro();
                    Alert ko = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("Ocorreu um erro ao atualizar!");
                    ko.setContentText(e.getMessage());
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                        }
                    });
                }
            }
        }
    }
    
    @FXML
    public void cancelar(){
        Stage a = (Stage) del_bt.getScene().getWindow();
        a.close();
    }
    
    public void carregar(int id){
        try {
            DAORaca d = new DAORaca();
            a = d.consultar_raca(id);
            nome_tx.setText(a.getNome());
            tempo_tx.setText(Integer.toString(a.getTempo_gestacao()));
            desc_ta.setText(a.getDescricao());
            carregado = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
