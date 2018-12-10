/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import DAO.DAOAnimal;
import DAO.DAODoenca;
import DAO.DAOEnfermidade;
import Entidades.Animal;
import Entidades.Doenca;
import Entidades.Enfermidade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author new
 */
public class CadastroEnfermidadeController implements Initializable {

int aux=1, aux2;
    private Animal animal;
    private Doenca doenca;
    private int cod_animal;
    private int cod_doenca;
    private boolean alt = false;
    private boolean carregado = false;
    
     @FXML
    private TextField id_doenca_tx;

    @FXML
    private TextField nome_doenca_tx;

    @FXML
    private Button buscadoenca_btn;

    @FXML
    private TextField id_anim_tx;

    @FXML
    private TextField cod_animal_tx;

    @FXML
    private Button buscaanimal_btn;

    @FXML
    private TextField tempo_carencia_tx;

    @FXML
    private TextField tempo_tratamento_tx;

    @FXML
    private DatePicker data_pk;

    @FXML
    private Label titulo_tela;

    @FXML
    private ImageView icone_img;

    @FXML
    private ButtonBar barradebotoes;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    @FXML
    private Button excluir_bt;

    @FXML
    private Label cod_doenca_label;

    @FXML
    private Label cod_animal_label;

    @FXML
    private Label cod_animal_label1;

    @FXML
    private Label cod_animal_label11;

    @FXML
    private Label cod_animal_label111;

    @FXML
    private Label cod_animal_label1111;
    
    @FXML
    private Button hoje_btn;

    @FXML
    private Button ontem_btn;
       
    @FXML
    public void listDoencas() throws IOException {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaDoenca.fxml"));
            Scene scene = new Scene(fxmlDB);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Consulta de Doenças [Saúde] - BoviPlus");
            c.show();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

    }
    
    @FXML
    public void listEnfermidades() throws IOException {
        try {
            Stage c = new Stage();
            c.setResizable(false);
            Parent fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaEnfermidade.fxml"));
            Scene scene = new Scene(fxmlDB);
            c.getIcons().add(new Image("/Imagens/Iconew.png"));
            c.setScene(scene);
            c.setTitle("Consulta de Enfermidade [Saúde] - BoviPlus");
            c.show();
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

    }
    
    @FXML
    public void listAnimal() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("ConsultaAnimal.fxml"));
        Parent root = fxmlDB.load();
        Scene scene = new Scene(root);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Consulta [Animal] - BoviPlus");
        c.show();
        Main.window = true;
    }
    
    @FXML
    public void buscaAnimal() throws ClassNotFoundException, IllegalAccessException, FileNotFoundException, InstantiationException {
        if (id_anim_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código do animal!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                int id_animal = Integer.parseInt(id_anim_tx.getText());
                DAOAnimal animal_dao = new DAOAnimal();
                animal = animal_dao.consultaAnimal(id_animal);
                cod_animal = Integer.parseInt(id_anim_tx.getText());
                cod_animal_tx.setText(animal.getNome());
            } catch (SQLException e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("O animal não foi encontrado");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }
    
    @FXML
    public void buscaDoenca() throws ClassNotFoundException, IllegalAccessException, FileNotFoundException, InstantiationException {
        if (id_doenca_tx.getText().equals("")) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Código em branco");
            alert1.setHeaderText("Para buscar digite o código da doença!");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {
                int id_doenca = Integer.parseInt(id_doenca_tx.getText());
                DAODoenca doenca_dao = new DAODoenca();
                doenca = doenca_dao.consultar_doenca(id_doenca);
                cod_doenca = Integer.parseInt(id_doenca_tx.getText());
                nome_doenca_tx.setText(doenca.getNome());
            } catch (SQLException e) {
                Main.tocaErro();
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                alert1.setGraphic(imageView);
                ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                alert1.setTitle("Não encontrado");
                alert1.setHeaderText("A doença não foi encontrada");
                alert1.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }
    
    @FXML
    public void cadastrarEnfermidade() {
        if (animal == null) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção");
            alert1.setHeaderText("Carregue um animal para continuar...");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else if (doenca == null) {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção");
            alert1.setHeaderText("Carregue uma doença para continuar...");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
                try {
                DAOEnfermidade dao = new DAOEnfermidade();
                Enfermidade enfermidade = new Enfermidade();
                enfermidade.setId_doenca(Integer.parseInt(id_doenca_tx.getText()));
                enfermidade.setId_animal(Integer.parseInt(id_anim_tx.getText()));
                enfermidade.setTempo_carencia(Integer.parseInt(tempo_carencia_tx.getText()));
                enfermidade.setTempo_tratamento(Integer.parseInt(tempo_tratamento_tx.getText()));
                enfermidade.setData_enfermidade(data_pk.getValue().toString());
 
                dao.cadastrar_enfermidade(enfermidade);
                    Main.tocaSucesso();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Sucesso.");
                    alert1.setHeaderText("Enfermidade registrada com sucesso.");
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    limpaTela();
                } catch (Exception e) {
                    Main.tocaErro();
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    alert1.setGraphic(imageView);
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    alert1.setTitle("Atenção");
                    alert1.setHeaderText("Não foi possível salvar!");
                    alert1.setContentText(e.getMessage());
                    alert1.showAndWait().ifPresent(rj -> {
                        if (rj == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    limpaTela();
                }
            }
        }
    
    @FXML
    public void limpaTela() {
        id_doenca_tx.setText("");
        nome_doenca_tx.setText("");
        id_anim_tx.setText("");
        cod_animal_tx.setText("");
        tempo_carencia_tx.setText("");
        tempo_tratamento_tx.setText("");
        data_pk.setValue(null);
    }
    
    @FXML
    public void hojeDataCad() {
        data_pk.setValue(LocalDate.now());
    }
    
    @FXML
    public void ontemDataCad() {
        data_pk.setValue(LocalDate.now().minusDays(1));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Cadastro de Exames [Saúde] - BoviPlus");
        }
    }

}

