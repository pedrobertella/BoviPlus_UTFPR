package Telas;

import DAO.DAOAnimal;
import DAO.DAOProducao;
import Entidades.Animal;
import Entidades.Producao;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProducaoSimplificadoController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private TableView<pc> tabela;

    @FXML
    private TableColumn<pc, Integer> cod_col;

    @FXML
    private TableColumn<pc, String> vaca_col;

    @FXML
    private TableColumn<pc, Double> litr_col;

    @FXML
    private TextField cod_tx;

    @FXML
    private TextField nome_label;

    @FXML
    private TextField litro_tx;

    @FXML
    private Button salvar_bt;

    @FXML
    private Button finalizar_bt;

    @FXML
    private Button iniciar_bt;

    @FXML
    private Button editar_bt;

    @FXML
    private TextField total_tx;

    Animal atual;
    DAOAnimal da;
    Producao prod;
    DAOProducao dp;
    List<Integer> ordem;
    List<Integer> newordem;
    int indice = 0;
    int editando = 0;
    int usandoordem = 0;
    double total = 0;

    ObservableList<pc> list = FXCollections.observableArrayList();

    public class pc {

        public int cod;
        public String nome;
        public double litros;

        public pc(int c, String n, double l) {
            cod = c;
            nome = n;
            litros = l;
        }

        public int getCod() {
            return cod;
        }

        public void setCod(int cod) {
            this.cod = cod;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getLitros() {
            return litros;
        }

        public void setLitros(double litros) {
            this.litros = litros;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Main.window) {
            Main.stage.setTitle("Registro Simplificado [Produção] - BoviPlus");
        }
        try {
            iniciar_bt.requestFocus();
            da = new DAOAnimal();
            dp = new DAOProducao();
            newordem = new ArrayList<>();
            cod_col.setCellValueFactory(new PropertyValueFactory("cod"));
            vaca_col.setCellValueFactory(new PropertyValueFactory("nome"));
            litr_col.setCellValueFactory(new PropertyValueFactory("litros"));
            tabela.setPlaceholder(new Label("Sem registros."));

        } catch (Exception ex) {
            Logger.getLogger(ProducaoSimplificadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void iniciarRegistro() {
        try {
            list = FXCollections.observableArrayList();
            usandoordem = 1;
            ordem = da.getOrdem();
            cod_tx.setText(ordem.get(indice).toString());
            iniciar_bt.setDisable(true);
            Main.tocaStart();
            buscaAnimal();
        } catch (Exception e) {
            cod_tx.requestFocus();
        }

    }

    @FXML
    public void finalizarRegistro() {
        try {
            if (!dp.consultaProducaoDia(LocalDate.now().toString())) {
                throw new Exception();
            }
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Mesclar produção?");
            alert1.setHeaderText("Já existe uma produção registrada hoje, deseja mesclar os registros?");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Mesclando!");
                    al.setHeaderText("Mesclando as produções. Por favor aguarde...");
                    al.show();

                    Producao k;
                    for (pc i : list) {
                        try {
                            k = dp.consultaProducao(i.cod, LocalDate.now().toString());
                            k.setLitragem(i.litros + k.getLitragem());
                            dp.atualizarProducao(k.getId_vaca(), k.getData_producao(), k.getLitragem());
                        } catch (Exception ss) {
                            k = new Producao();
                            k.setId_vaca(i.cod);
                            k.setData_producao(LocalDate.now().toString());
                            k.setLitragem(i.litros);
                            try {
                                dp.cadastrarProducao(k);
                            } catch (Exception ex) {
                                Main.tocaErro();
                                Alert ko = new Alert(Alert.AlertType.ERROR);
                                ImageView imagaView = new ImageView(new Image(new File("error.png").toURI().toString()));
                                ko.setGraphic(imagaView);
                                ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                                ko.setTitle("Atenção!");
                                ko.setHeaderText("Ocorreu um erro ao salvar a produção.");
                                ko.setContentText(ex.getMessage());
                                ko.showAndWait().ifPresent(rr -> {
                                    if (rr == ButtonType.OK) {
                                        System.out.println("Pressed OK.");
                                    }
                                });
                            }
                        }
                    }

                    try {
                        for (int i : newordem) {
                            System.out.println(i);
                        }
                        da.resetaOrdem(newordem);
                    } catch (Exception f) {
                        System.out.println("Oops");
                    }

                    al.close();
                    Main.tocaSucesso();
                    Alert al3 = new Alert(Alert.AlertType.INFORMATION);
                    Image image = new Image(new File("ok.png").toURI().toString());
                    ImageView mageView = new ImageView(image);
                    al3.setTitle("Pronto");
                    al3.setGraphic(mageView);
                    al3.setHeaderText("O registro foi finalizado com sucesso!");
                    ((Stage) al3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    al3.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.CLOSE) {

                        }
                    });
                } else {

                }
            });

        } catch (Exception e) {
            for (pc i : list) {
                Producao p = new Producao();
                p.setId_vaca(i.cod);
                p.setData_producao(LocalDate.now().toString());
                p.setLitragem(i.litros);
                try {
                    dp.cadastrarProducao(p);
                } catch (Exception ex) {
                    Main.tocaErro();
                    Alert ko = new Alert(Alert.AlertType.ERROR);
                    ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                    ko.setGraphic(imageView);
                    ((Stage) ko.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                    ko.setTitle("Atenção!");
                    ko.setHeaderText("Ocorreu um erro ao salvar a produção.");
                    ko.setContentText(ex.getMessage());
                    ko.showAndWait().ifPresent(rr -> {
                        if (rr == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                }
            }
            try {
                for (int i : newordem) {
                    System.out.println(i);
                }
                da.resetaOrdem(newordem);
            } catch (Exception f) {
                System.out.println("Oops");
            }
            Main.tocaSucesso();
            Alert al3 = new Alert(Alert.AlertType.INFORMATION);
            ImageView imageView = new ImageView(new Image(new File("ok.png").toURI().toString()));
            al3.setGraphic(imageView);
            ((Stage) al3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            al3.setTitle("Pronto");
            al3.setHeaderText("O registro foi finalizado com sucesso!");
            al3.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CLOSE) {

                }
            });
        }

        total = 0;
        iniciar_bt.setDisable(false);
        limpa();
        total_tx.clear();
        tabela.setItems(null);
        indice = 0;
        editando = 0;
        usandoordem = 0;
        atual = null;
        da = null;
        prod = null;
        dp = null;
        ordem = null;
        list = null;
    }

    private void proximo() {
        if (ordem.size() > indice) {
            cod_tx.setText(ordem.get(indice).toString());
            buscaAnimal();
        } else if (ordem.isEmpty()) {

        } else {
            Main.tocaConfirma();
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            ImageView imageView = new ImageView(new Image(new File("confirm.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Finalizar registro?");
            alert1.setHeaderText("A lista de vacas chegou ao fim, deseja finalizar o registro de produção?");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    finalizarRegistro();
                } else {

                }
            });
        }
    }

    private int retornaIndiceAnimalnaLista(int id) {
        int j;

        for (j = 0; j < list.size(); j++) {
            if (id == list.get(j).cod) {
                break;
            }
        }
        return j;
    }

    @FXML
    public void salva() {
        try {
            buscaAnimal();

            if (editando != 1) {
                pc a = new pc(Integer.parseInt(cod_tx.getText()), atual.getNome(), Double.parseDouble(litro_tx.getText()));
                indice++;
                list.add(a);
                newordem.add(Integer.parseInt(cod_tx.getText()));
                total = total + Double.parseDouble(litro_tx.getText());
            } else {
                total = total - list.get(retornaIndiceAnimalnaLista(Integer.parseInt(cod_tx.getText()))).litros;
                total = total + Double.parseDouble(litro_tx.getText());
                list.get(retornaIndiceAnimalnaLista(Integer.parseInt(cod_tx.getText()))).litros = Double.parseDouble(litro_tx.getText());
            }

            tabela.setItems(null);
            tabela.setItems(list);
            tabela.refresh();
            editando = 0;

            total_tx.setText(Double.toString(total));
            limpa();
            if (usandoordem == 1) {
                proximo();
            }
        } catch (Exception e) {
            Main.tocaErro();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção!");
            alert1.setHeaderText("Ocorreu um erro ao salvar.");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }

    }

    @FXML
    public void buscaAnimal() {
        try {
            atual = null;
            atual = da.consultaAnimal(Integer.parseInt(cod_tx.getText()));
            if (atual.getId_tipo() != 1) {
                throw new ArithmeticException();
            }
            if (!atual.isLactacao()) {
                throw new IOException();
            }
            for (int j : newordem) {
                if (Integer.parseInt(cod_tx.getText()) == j) {
                    if (editando != 1) {
                        throw new NoSuchMethodException();
                    }
                }
            }
            litro_tx.requestFocus();
            printaNome();

        } catch (NoSuchMethodException hh) {
            Main.tocaErro();
            cod_tx.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção!");
            alert1.setHeaderText("Produção já registrada.");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (IOException k) {
            Main.tocaErro();
            cod_tx.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção!");
            alert1.setHeaderText("A vaca não está em lactação! Verifique...");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (ArithmeticException a) {
            Main.tocaErro();
            cod_tx.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Atenção!");
            alert1.setHeaderText("O animal não é uma vaca! Verifique...");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } catch (Exception e) {
            Main.tocaErro();
            cod_tx.clear();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
            alert1.setGraphic(imageView);
            ((Stage) alert1.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            alert1.setTitle("Não encontrada");
            alert1.setHeaderText("A Vaca não foi encontrada");
            alert1.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    private void printaNome() {
        System.out.println(atual.getNome());
        String temp = atual.getNome();
        nome_label.setText(temp);
    }

    @FXML
    public void editarRegistro() {
        editando = 1;
        pc j = tabela.getSelectionModel().getSelectedItem();
        cod_tx.setText(Integer.toString(j.cod));
        buscaAnimal();
    }

    public void limpa() {
        cod_tx.clear();
        nome_label.clear();
        litro_tx.clear();
    }

}
