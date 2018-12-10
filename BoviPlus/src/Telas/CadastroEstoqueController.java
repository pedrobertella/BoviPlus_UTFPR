package Telas;

import DAO.DAOAlimento;
import DAO.DAOEstoque;
import Entidades.Alimento;
import Entidades.Estoque;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroEstoqueController implements Initializable {

    @FXML
    private AnchorPane panep;

    @FXML
    private TextField codigo_tx;

    @FXML
    private TextField atual_tx;

    @FXML
    private TextField qtd_tx;

    @FXML
    private Label estoque_label;

    @FXML
    private TextField valor_tx;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button limpar_bt;

    Alimento a;
    Estoque e;
    PagarAlimentacaoController cont;

    public void carregar(int id_alimento) {
        try {
            DAOAlimento d = new DAOAlimento();
            DAOEstoque de = new DAOEstoque();
            a = d.consultaAlimento(id_alimento);
            codigo_tx.setText(a.getNome());
            atual_tx.setText(Double.toString(de.somaPorAlimento(a.getId())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void financeiro() throws IOException {
        Stage c = new Stage();
        c.setResizable(false);
        Main.window = false;
        FXMLLoader fxmlDB = new FXMLLoader(getClass().getResource("PagarAlimentacao.fxml"));
        Parent root = fxmlDB.load();
        cont = fxmlDB.getController();
        Scene scene = new Scene(root);
        c.getIcons().add(new Image("/Imagens/Iconew.png"));
        c.setScene(scene);
        c.setTitle("Lançamento de Conta a Pagar [Financeiro] - BoviPlus");
        cont.initValores(e.getQuant(), e.getValor_uni(), a.getId());
        c.show();
        Main.window = true;
    }

    @FXML
    public void salvar() {
        if (qtd_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Informe uma quantidade para salvar...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            qtd_tx.requestFocus();
        } else if (valor_tx.getText().trim().isEmpty()) {
            Main.tocaConfirma();
            Alert ko = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            ko.setGraphic(imageView);
            ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            ko.setTitle("Atenção!");
            ko.setHeaderText("Informe um valor unitário para salvar...");
            ko.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
            valor_tx.requestFocus();
        } else {
            try {
                DAOEstoque de = new DAOEstoque();
                e = new Estoque(de.proximoID(), a.getId(), Double.parseDouble(qtd_tx.getText()), Double.parseDouble(valor_tx.getText()), LocalDate.now().toString());
                de.cadastrarEstoque(e);
                Main.tocaSucesso();
                Alert ko = new Alert(Alert.AlertType.INFORMATION);
                ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Sucesso!");
                ko.setHeaderText("O estoque foi lançado!");
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
                financeiro();
            } catch (Exception e) {
                Main.tocaErro();
                Alert ko = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                ko.setGraphic(imageView);
                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                ko.setTitle("Atenção!");
                ko.setHeaderText("Ocorreu um erro ao gravar!");
                ko.setContentText(e.getMessage());
                ko.showAndWait().ifPresent(rr -> {
                    if (rr == ButtonType.OK) {
                    }
                });
            }

        }

    }

    @FXML
    public void limpar() {
        qtd_tx.clear();
        valor_tx.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Stage u = (Stage) panep.getScene().getWindow();
            u.setOnCloseRequest(b -> {
                if (cont != null) {
                    if (cont.nFechar) {
                        b.consume();
                    }
                }
            });
        });

    }

}
