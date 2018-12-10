package Telas;

import DAO.DAORaca;
import Entidades.Raca;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RacasController implements Initializable {

    @FXML
    private Button add_bt;

    @FXML
    private Button del_bt;

    @FXML
    private Button ref_bt;

    @FXML
    private TableView<Raca> tabela;

    @FXML
    private TableColumn<Raca, Integer> cod_col;

    @FXML
    private TableColumn<Raca, String> nome_col;

    @FXML
    private TableColumn<Raca, String> desc_col;

    @FXML
    private TableColumn<Raca, Integer> tempo_col;
    
    @FXML
    public void initTable(){
        cod_col.setCellValueFactory(new PropertyValueFactory<>("id_raca"));
        nome_col.setCellValueFactory(new PropertyValueFactory<>("nome"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tempo_col.setCellValueFactory(new PropertyValueFactory<>("tempo_gestacao"));
        try{
            DAORaca da = new DAORaca();
            List<Raca> l1 = da.listarTudo();
            ObservableList<Raca> l2 = FXCollections.observableArrayList();
            l2.addAll(l1);
            tabela.setItems(null);
            tabela.setItems(l2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void adicionar() throws IOException{
        Stage c = new Stage();
        c.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("CadastroRaca.fxml"));
        Scene scene = new Scene(fxmlDB);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Cadastro de Raça [Animais] - BoviPlus");
        c.show();
    }
    
    @FXML
    public void apagar(){
        try {
            if (tabela.getSelectionModel().getSelectedItem() == null) {
                throw new ArithmeticException();
            }
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Deseja continuar?");
            ko.setHeaderText("Você tem certeza que deseja excluir a raça selecionada?");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    try {
                        DAORaca da = new DAORaca();
                        da.deletarRaca(tabela.getSelectionModel().getSelectedItem().getId_raca());
                        Main.tocaSucesso();
                        Alert as = new Alert(Alert.AlertType.INFORMATION);
                        ImageView imagaeView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Sucesso!");
                        as.setHeaderText("A raça foi apagada!");
                        as.showAndWait().ifPresent(ss -> {
                            if (ss == ButtonType.OK) {
                            }
                        });
                        initTable();
                    } catch (Exception e) {
                        Main.tocaErro();
                        Alert as = new Alert(Alert.AlertType.ERROR);
                        ImageView imagaeView = new ImageView(new Image(new File("error.png").toURI().toString()));
                        as.setGraphic(imagaeView);
                        ((Stage) as.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                        as.setTitle("Atenção!");
                        as.setHeaderText("Não foi possível excluir!");
                        as.setContentText("Podem existir animais pertencentes a esta raça, verifique.");
                        as.showAndWait().ifPresent(ss -> {
                            if (rr == ButtonType.OK) {
                            }
                            
                        });
                    }

                } else {

                }

            });

        } catch (ArithmeticException e) {

        }
    }
    
    @FXML
    public void editar(){
        try {
            Stage c = new Stage();
            c.setResizable(false);
            FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("CadastroRaca.fxml"));
            Parent root = fxmlDB.load();
            CadastroRacaController cont = fxmlDB.getController();
            Scene scene = new Scene(root);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Cadastro de Raça [Animais] - BoviPlus");
            if (tabela.getSelectionModel().getSelectedItem() == null) {
                throw new ArithmeticException();
            }
            cont.carregar(tabela.getSelectionModel().getSelectedItem().getId_raca());
            c.show();
        } catch (Exception e) {

        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    editar();
                }
            }
        });
    }    
    
}
