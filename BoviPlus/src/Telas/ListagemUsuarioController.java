package Telas;

import DAO.DAOUsuario;
import Entidades.Usuario;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ListagemUsuarioController implements Initializable {

    @FXML
    private TableView<Usuario> tabela;

    @FXML
    private TableColumn<Usuario, Integer> cod_col;

    @FXML
    private TableColumn<Usuario, String> nome_col;

    @FXML
    private TableColumn<Usuario, String> per_col;

    @FXML
    private Button cad_bt;

    public ObservableList<Usuario> ab;

    @FXML
    public void listar() {
        try {

            DAOUsuario d = new DAOUsuario();
            List<Usuario> a = d.listaUsers();
            ab = FXCollections.observableArrayList();
            for (Usuario i : a) {
                ab.add(i);
            }
            tabela.setItems(null);
            tabela.setItems(ab);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void cadastro() throws IOException {
        Main.id_user = Integer.toString(tabela.getSelectionModel().getSelectedItem().getId_user());
        Stage cadastro = new Stage();
        Main.window = false;
        cadastro.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("CadastroUsuario.fxml"));
        Scene scene = new Scene(fxmlDB);
        cadastro.getIcons().add(new Image("/Imagens/Iconew.png"));
        cadastro.setScene(scene);
        cadastro.setTitle("Cadastro de Usuário [Sistema] - BoviPlus");
        cadastro.show();
        Main.window = true;
    }

    @FXML
    public void add() throws IOException {
        Stage cadastro = new Stage();
        Main.window = false;
        cadastro.setResizable(false);
        Parent fxmlDB = FXMLLoader.load(getClass().getResource("CadastroUsuario.fxml"));
        Scene scene = new Scene(fxmlDB);
        cadastro.getIcons().add(new Image("/Imagens/Iconew.png"));
        cadastro.setScene(scene);
        cadastro.setTitle("Cadastro de Usuário [Sistema] - BoviPlus");
        cadastro.show();
        Main.window = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Usuários [Sistema] - BoviPlus");
        }
        tabela.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        cadastro();
                    } catch (IOException ex) {
                        System.out.println("ERRO");
                    }
                }
            }
        });
        listar();
        cod_col.setCellValueFactory(new PropertyValueFactory("id_user"));
        nome_col.setCellValueFactory(new PropertyValueFactory("nome"));
        per_col.setCellValueFactory(new PropertyValueFactory("permissoes"));
    }

}
