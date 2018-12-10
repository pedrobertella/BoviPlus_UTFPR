package Telas;

import ConexaoBD.Conexao;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CadastroProdutorController implements Initializable {

    @FXML
    private TextField nome_tx;

    @FXML
    private TextField cpf_tx;

    @FXML
    private DatePicker data_dp;

    @FXML
    private TextField fazenda_tx;
    
    private String nome;
 
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        } else if(CPF.equals("00000000000")){
            return true;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0         
                // (48 eh a posicao de '0' na tabela ASCII)         
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public void carrega() {
        try {
            Conexao c = new Conexao();
            c.abrirConexao();
            Statement stm = c.getConnection().createStatement();
            ResultSet rs = stm.executeQuery("select * from produtor");
            rs.next();
            nome = rs.getString("nome");
            nome_tx.setText(rs.getString("nome"));
            cpf_tx.setText(rs.getString("cpf"));
            data_dp.setValue(LocalDate.parse(rs.getString("nasc")));
            fazenda_tx.setText(rs.getString("fazenda"));

        } catch (Exception e) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Erro!");
            dodsad.setHeaderText("Ocorreu um erro ao carregar os dados.");
            dodsad.setContentText(e.getMessage());
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void salvar() {
        try {
            if (!isCPF(cpf_tx.getText())) {
                throw new ArithmeticException();
            }
            
            Conexao c = new Conexao();
            c.abrirConexao();
            Statement stm = c.getConnection().createStatement();
            stm.execute("update produtor set nome = '"+nome_tx.getText()+"', cpf = '"+cpf_tx.getText()+"', nasc = '"+data_dp.getValue().toString()+"', fazenda = '"+fazenda_tx.getText()+"' where nome = '"+nome+"';");
            Main.tocaSucesso();
            Alert dodsad = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Salvo!");
            dodsad.setHeaderText("Alterações salvas com sucesso");
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });

        } catch (ArithmeticException a) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Atenção!");
            dodsad.setHeaderText("CPF Inválido");
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                }
            });
        } catch (Exception e) {
            Main.tocaConfirma();
            Alert dodsad = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            dodsad.setGraphic(imageView);
            ((Stage) dodsad.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            dodsad.setTitle("Erro!");
            dodsad.setHeaderText("Ocorreu um erro ao carregar os dados.");
            dodsad.setContentText(e.getMessage());
            dodsad.showAndWait().ifPresent(rr -> {
                if (rr == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carrega();
    }

}
