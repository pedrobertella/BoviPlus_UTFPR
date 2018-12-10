package Telas;

import DAO.DAOAlimentacao;
import DAO.DAOAlimento;
import DAO.DAOAnimal;
import Entidades.Alimentacao;
import Entidades.Alimento;
import Entidades.Animal;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HistoricoAlimentacaoController implements Initializable {

    @FXML
    private TableView<Alim> tabela;

    @FXML
    private TableColumn<Alim, String> alim_col;

    @FXML
    private TableColumn<Alim, Double> qtd_col;

    @FXML
    private TableColumn<Alim, String> data_col;

    @FXML
    private TextField id_tx;

    @FXML
    private TextField nome_tx;

    public class Alim {

        public String ali, date;
        public Double qtd;

        public Alim(String ali, String date, Double qtd) {
            this.ali = ali;
            this.date = date;
            this.qtd = qtd;
        }

        public String getAli() {
            return ali;
        }

        public String getDate() {
            return date;
        }

        public Double getQtd() {
            return qtd;
        }

    }

    private Animal animal;
    ObservableList<Alim> list;

    @FXML
    public void buscaAnimal() {
        if (id_tx.getText().equals("")) {
            Main.tocaErro();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {

                }
            });
        } else {
            try {
                DAOAnimal da = new DAOAnimal();
                animal = da.consultaAnimal(Integer.parseInt(id_tx.getText()));
                nome_tx.setText(animal.getNome());
            } catch (Exception e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O Animal não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                    }
                });

            }
        }
    }

    @FXML
    public void listAnimal() throws IOException {
        Stage a = new Stage();
        a.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("ConsultaAnimal.fxml"));
        Parent root = fxmlDB.load();
        Scene scene = new Scene(root);
        a.getIcons().add(new Image("/Imagens/Iconew.png"));
        a.setScene(scene);
        a.setTitle("Consulta [Animal] - BoviPlus");
        a.show();
        Main.window = true;
    }

    public void carregaDados() {
        try {
            if (animal == null) {
                Main.tocaConfirma();
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Atenção");
                alert1.setHeaderText("Carregue um animal para continuar!");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {

                    }
                });
            } else {
                DAOAlimentacao dd = new DAOAlimentacao();
                List<Alimentacao> la = dd.pesquisaCustom("select * from alimentacao where id_animal = " + animal.getId_animal() + ";");
                list = FXCollections.observableArrayList();
                la.sort((Alimentacao m1, Alimentacao m2) -> {
                    if (LocalDate.parse(m1.getData_alimentacao()).isEqual(LocalDate.parse(m2.getData_alimentacao()))) {
                        return 0;
                    } else if (LocalDate.parse(m1.getData_alimentacao()).isBefore(LocalDate.parse(m2.getData_alimentacao()))) {
                        return 1;
                    } else {
                        return -1;
                    }
                });
                Alim a;
                for(Alimentacao i : la){
                    a = new Alim(getNomeAlim(i.getId_alimento()), i.getData_alimentacao(), i.getQtd());
                    list.add(a);
                }
                tabela.setItems(null);
                tabela.setItems(list);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void limpar(){
        tabela.setItems(null);
        animal = null;
        id_tx.clear();
        nome_tx.clear();
        list = null;
    }
    
    private String getNomeAlim(int id){
        try{
            DAOAlimento ddd = new DAOAlimento();
            Alimento u = ddd.consultaAlimento(id);
            return u.getNome();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alim_col.setCellValueFactory(new PropertyValueFactory<>("ali"));
        qtd_col.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        data_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabela.setPlaceholder(new Label("Nenhuma alimentação."));
    }

}
