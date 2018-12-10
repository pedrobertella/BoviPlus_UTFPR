package Telas;

import DAO.DAOAlimentacao;
import DAO.DAOAlimento;
import DAO.DAOAnimal;
import DAO.DAOContas_Receber;
import DAO.DAOContas_pagar;
import DAO.DAOEstoque;
import DAO.DAOExame;
import DAO.DAOReproducao;
import DAO.DAOVacina;
import Entidades.Alimentacao;
import Entidades.Alimento;
import Entidades.Animal;
import Entidades.Contas_Receber;
import Entidades.Contas_pagar;
import Entidades.Evento;
import Entidades.Exame;
import Entidades.Reproducao;
import Entidades.Vacina;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TelaInicioController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;

    @FXML
    private ListView<Evento> avisos_lv;

    DAOReproducao dr;
    DAOAnimal da;
    DAOContas_pagar dcp;
    DAOContas_Receber dcr;
    DAOExame de;
    ObservableList<Evento> lista;
    DAOVacina dv;
    List<Integer> diasalim;

    private String buscaNome(int id) {
        Animal a;
        try {
            da = new DAOAnimal();
            a = da.consultaAnimal(id);
            return a.getNome();
        } catch (Exception e) {
            return null;
        }
    }

    private String origemConta(Contas_pagar a) {

        if (a.getOrigem() == 0) {
            return "Custo Geral";
        } else if (a.getOrigem() == 1) {
            return "Reprodução";
        } else if (a.getOrigem() == 2) {
            return "Alimento";
        } else if (a.getOrigem() == 3) {
            return "Saúde";
        }
        return null;
    }

    @FXML
    public void abreEvento() throws IOException {
        try {
            Evento a = avisos_lv.getSelectionModel().getSelectedItem();
            Stage cadastro = new Stage();
            Main.window = false;
            Parent fxmlDB = null;
            Main.dataConsulta = a.data.toString();
            if (a.origem.equals("nascimento") && Main.getUser().getPermissoes().charAt(5) == '1') {
                fxmlDB = FXMLLoader.load(getClass().getResource("AcompanhamentoReproducao.fxml"));
                cadastro.setTitle("Acompanhamento [Reprodução] - BoviPlus");
            } else if (a.origem.equals("secagem") && Main.getUser().getPermissoes().charAt(5) == '1') {
                fxmlDB = FXMLLoader.load(getClass().getResource("AcompanhamentoReproducao.fxml"));
                cadastro.setTitle("Acompanhamento [Reprodução] - BoviPlus");
            } else if (a.origem.equals("receber") && Main.getUser().getPermissoes().charAt(3) == '1') {
                fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaReceber.fxml"));
                cadastro.setTitle("Consulta de Contas a Receber [Financeiro] - BoviPlus");
            } else if (a.origem.equals("pagar") && Main.getUser().getPermissoes().charAt(3) == '1') {
                fxmlDB = FXMLLoader.load(getClass().getResource("ConsultaPagar.fxml"));
                cadastro.setTitle("Consulta de Contas a Pagar [Financeiro] - BoviPlus");
            } else if (a.origem.equals("vacina") && Main.getUser().getPermissoes().charAt(2) == '1') {
                return;
            } else if (a.origem.equals("alimento") && Main.getUser().getPermissoes().charAt(6) == '1') {
                fxmlDB = FXMLLoader.load(getClass().getResource("AnaliticoAlimentacao.fxml"));
                cadastro.setTitle("Previsão de Estoque [Alimentação] - BoviPlus");
            } else if (a.origem.equals("outros")) {
                return;
            }

            Scene scene = new Scene(fxmlDB);
            cadastro.getIcons().add(new Image("/Imagens/Iconew.png"));
            cadastro.setScene(scene);
            cadastro.setResizable(false);
            cadastro.show();
            Main.window = true;
        } catch (NullPointerException n) {
            Main.tocaErro();
            Alert al3 = new Alert(Alert.AlertType.WARNING);
            ImageView imageView = new ImageView(new Image(new File("warning.png").toURI().toString()));
            al3.setGraphic(imageView);
            ((Stage) al3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
            al3.setTitle("Oops...");
            al3.setHeaderText("Parece que você não possui permissão\npara acessar esta parte do sistema...");
            al3.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CLOSE) {

                }
            });
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        avisos_lv.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    abreEvento();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        if (Main.window) {
            Main.stage.setTitle("Início - BoviPlus");
        }
        Label lab = new Label("Carregando avisos...");
        lab.setTextFill(Color.WHITE);
        lab.setFont(new Font(24));
        avisos_lv.setPlaceholder(lab);
        new Thread() {
            @Override
            public void run() {
                initAvisos();
            }

        }.start();
    }

    private List<Alimento> poucoEstoque() {
        diasalim = new ArrayList<>();
        List<Alimento> liss = new ArrayList<>();
        try {
            DAOAlimento ddd = new DAOAlimento();
            List<Alimento> lalala = ddd.listarTodos();
            for (Alimento u : lalala) {
                DAOAlimentacao dudu = new DAOAlimentacao();
                if (dudu.ultimaData(u.getId()) != null) {
                    String uData = dudu.ultimaData(u.getId());
                    List<Alimentacao> ll = dudu.pesquisaCustom("select * from alimentacao where data_alimentacao = '" + uData + "' and id_alimento = " + u.getId() + ";");
                    double soma = 0;
                    for (Alimentacao a : ll) {
                        soma = soma + a.getQtd();
                    }
                    DAOEstoque de = new DAOEstoque();
                    double est = de.somaPorAlimento(u.getId());
                    int dias = 0;
                    while (est > 0 - soma) {
                        est = est - soma;
                        dias++;
                    }
                    dias--;
                    if (dias <= Main.pref.diasest) {
                        liss.add(u);
                        diasalim.add(dias);
                    }
                }

            }
        } catch (Exception e) {

        }
        return liss;
    }

    public void initAvisos() {
        try {
            lista = FXCollections.observableArrayList();
            dr = new DAOReproducao();
            LocalDate a = LocalDate.now().plusDays(Main.pref.dianasc);
            List<Reproducao> j = dr.proximosNacimentos(LocalDate.now().toString(), a.toString());
            String linha;

            a = LocalDate.now().plusDays(Main.pref.diasec);
            List<Reproducao> k = dr.proximosSecagem(LocalDate.now().toString(), a.toString());

            dcr = new DAOContas_Receber();
            dcp = new DAOContas_pagar();
            dv = new DAOVacina();
            de = new DAOExame();

            List<Exame> ex = de.pesquisaCustom("select * from exame where categoria = 'P' and intervalo > 0;");
            List<Vacina> vc = dv.pesquisaCustom("select * from vacina where periodica = true;");
            List<Vacina> vacinas = new ArrayList<>();
            List<Contas_Receber> l = dcr.pesquisaCustom("select * from contas_receber where data_vencimento between '" + LocalDate.now().toString() + "' and '" + LocalDate.now().plusDays(Main.pref.creceber).toString() + "' and data_recebimento is null;");
            List<Contas_pagar> m = dcp.pesquisaCustom("select * from contas_pagar where data_vencimento between '" + LocalDate.now().toString() + "' and '" + LocalDate.now().plusDays(Main.pref.cpagar).toString() + "' and data_pagamento is null;");
            List<Alimento> al = poucoEstoque();
            Evento u;
            if (!ex.isEmpty()) {
                for (Exame e : ex) {
                    LocalDate d = LocalDate.parse(e.getData_exame()).plusDays(e.intervalo);
                    long dif = DAYS.between(LocalDate.now(), d);
                    if (dif <= Main.pref.diaexame) {
                        linha = "EXAME PERIÓDICO \n" + e.getNome() + "\nem " + dif + " dias";
                        u = new Evento(linha, "vacina", d);
                        lista.add(u);
                    }
                }
            }
            if (!vc.isEmpty()) {
                LocalDate d;
                for (Vacina v : vc) {
                    if (nomeMesparaNumero(v.getMes()) < LocalDate.now().getMonthValue()) {
                        d = LocalDate.of(LocalDate.now().getYear() + 1, nomeMesparaNumero(v.getMes()), 1);
                    } else {
                        d = LocalDate.of(LocalDate.now().getYear(), nomeMesparaNumero(v.getMes()), 1);
                    }
                    if (DAYS.between(LocalDate.now(), d) <= Main.pref.diavacina) {
                        vacinas.add(v);
                    }
                }
                if (!vacinas.isEmpty()) {
                    for (Vacina v : vacinas) {
                        linha = "VACINA PERIÓDICA \n" + v.getNome() + " - " + v.getMes() + "\nCampanha: " + v.getCampanha() + " - Categoria: " + v.getCat_animais();
                        LocalDate f;
                        if (nomeMesparaNumero(v.getMes()) < LocalDate.now().getMonthValue()) {
                            f = LocalDate.of(LocalDate.now().getYear() + 1, nomeMesparaNumero(v.getMes()), 1);
                        } else {
                            f = LocalDate.of(LocalDate.now().getYear(), nomeMesparaNumero(v.getMes()), 1);
                        }
                        u = new Evento(linha, "vacina", f);
                        lista.add(u);
                    }
                }
            }
            if (!al.isEmpty()) {
                int co = 0;
                for (Alimento i : al) {
                    linha = "POUCO ESTOQUE: " + diasalim.get(co) + " dias restantes\nAlimento: " + i.getNome() + "\nVerifique a disponbilidade na Previsão de Estoque.";
                    u = new Evento(linha, "alimento", LocalDate.now().plusDays(diasalim.get(co)));
                    lista.add(u);
                    co++;
                }
            }
            if (!j.isEmpty()) {
                String diferenca1;
                for (Reproducao i : j) {
                    diferenca1 = "em " + DAYS.between(LocalDate.now(), LocalDate.parse(i.getPrevisao())) + " dias";
                    if (LocalDate.now().equals(LocalDate.parse(i.getPrevisao()))) {
                        diferenca1 = "hoje";
                    }
                    linha = "NASCIMENTO: " + diferenca1 + "\n" + i.getPrevisao() + "\nVaca: " + buscaNome(i.getId_mae()) + " | Inseminação nº: " + i.getId_gestacao();
                    u = new Evento(linha, "nascimento", LocalDate.parse(i.getPrevisao()));
                    lista.add(u);
                }
            }
            if (!k.isEmpty()) {
                String diferenca2;
                for (Reproducao i : k) {
                    diferenca2 = "em " + DAYS.between(LocalDate.now(), LocalDate.parse(i.getPrevisao())) + " dias";
                    if (LocalDate.now().equals(LocalDate.parse(i.getPrevisao()))) {
                        diferenca2 = "hoje";
                    }
                    linha = "SECAGEM: " + diferenca2 + "\n" + i.getPrevisao() + "\nVaca: " + buscaNome(i.getId_mae()) + " nº: " + i.getId_mae();
                    u = new Evento(linha, "secagem", LocalDate.parse(i.getPrevisao()));
                    lista.add(u);
                }
            }
            if (!l.isEmpty()) {
                String diferenca3;
                for (Contas_Receber i : l) {
                    diferenca3 = "em " + DAYS.between(LocalDate.now(), LocalDate.parse(i.data_vencimento)) + " dias";
                    if (LocalDate.now().equals(LocalDate.parse(i.data_vencimento))) {
                        diferenca3 = "hoje";
                    }
                    linha = "CONTA A RECEBER: " + diferenca3 + "\nVencimento: " + i.getData_vencimento() + "\nCód: " + i.id_contas_receber + " | Valor: " + Double.toString(i.getValor() * i.getQtd()) + " | " + i.descricao + "\n";
                    u = new Evento(linha, "receber", LocalDate.parse(i.getData_vencimento()));
                    lista.add(u);
                }
            }
            if (!m.isEmpty()) {
                String diferenca3;
                for (Contas_pagar i : m) {
                    diferenca3 = "em " + DAYS.between(LocalDate.now(), LocalDate.parse(i.getData_vencimento())) + " dias";
                    if (LocalDate.now().equals(LocalDate.parse(i.getData_vencimento()))) {
                        diferenca3 = "hoje";
                    }
                    linha = "CONTA A PAGAR: " + diferenca3 + "\nVencimento: " + i.getData_vencimento() + "\nCód: " + i.getId_contas_pagar() + " | Valor: " + Double.toString(i.getValor()) + " | " + origemConta(i) + "\n";
                    u = new Evento(linha, "pagar", LocalDate.parse(i.getData_vencimento()));
                    lista.add(u);
                }
            }
            if (!Main.outros_eventos.isEmpty()) {
                for (Evento e : Main.outros_eventos) {
                    lista.add(e);
                }
            }
            if (vacinas.isEmpty() && j.isEmpty() && l.isEmpty() && m.isEmpty() && k.isEmpty() && Main.outros_eventos.isEmpty() && al.isEmpty() && ex.isEmpty()) {
                Label lab = new Label("Sem avisos.");
                lab.setTextFill(Color.WHITE);
                lab.setFont(new Font(24));
                Platform.runLater(() -> {
                    avisos_lv.setPlaceholder(lab);
                });
            }
            lista.sort((Evento m1, Evento m2) -> {
                if (m1.data.isEqual(m2.data)) {
                    return 0;
                } else if (m1.data.isAfter(m2.data)) {
                    return 1;
                } else {
                    return -1;
                }
            });

            avisos_lv.setCellFactory(new Callback<ListView<Evento>, ListCell<Evento>>() {
                @Override
                public ListCell<Evento> call(ListView<Evento> param) {

                    ListCell<Evento> cell = new ListCell<Evento>() {
                        {
                            this.setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: #252525;");
                            prefHeightProperty().set(100);
                            prefWidthProperty().bind(avisos_lv.widthProperty().subtract(20)); // 1
                            setMaxWidth(Control.USE_PREF_SIZE);
                        }

                        @Override
                        protected void updateItem(Evento ev, boolean bt1) {
                            super.updateItem(ev, bt1);
                            if (ev != null) {
                                String imagem = "";
                                if (ev.origem.equals("nascimento")) {
                                    imagem = "/Imagens/insemi.png";
                                } else if (ev.origem.equals("secagem")) {
                                    imagem = "/Imagens/Cow.png";
                                } else if (ev.origem.equals("receber") || ev.origem.equals("pagar")) {
                                    imagem = "/Imagens/conta.png";
                                } else if (ev.origem.equals("vacina")) {
                                    imagem = "/Imagens/saude.png";
                                } else if (ev.origem.equals("outros")) {
                                    imagem = "/Imagens/warningBig.png";
                                } else if (ev.origem.equals("alimento")) {
                                    imagem = "/Imagens/Food.png";
                                }

                                Image img = new Image(getClass().getResource(imagem).toExternalForm());
                                ImageView imgView = new ImageView(img);
                                imgView.setFitHeight(50);
                                imgView.setFitWidth(50);
                                setGraphic(imgView);
                                this.setWrapText(true);
                                setText(ev.texto);

                            }
                        }
                    };

                    return cell;
                }

            });
            avisos_lv.setItems(lista);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Main.tocaErro();
                Alert al3 = new Alert(Alert.AlertType.ERROR);
                ImageView imageView = new ImageView(new Image(new File("error.png").toURI().toString()));
                al3.setGraphic(imageView);
                ((Stage) al3.getDialogPane().getScene().getWindow()).getIcons().add(new Image(new File("Iconew.png").toURI().toString()));
                al3.setTitle("Oops");
                al3.setHeaderText("Ocorreu um erro ao verificar os avisos!");
                al3.setContentText(e.getMessage());
                al3.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.CLOSE) {

                    }
                });
            });
        }

    }

    private int nomeMesparaNumero(String nome) {
        switch (nome) {
            case "Janeiro":
                return 1;
            case "Fevereiro":
                return 2;
            case "Março":
                return 3;
            case "Abril":
                return 4;
            case "Maio":
                return 5;
            case "Junho":
                return 6;
            case "Julho":
                return 7;
            case "Agosto":
                return 8;
            case "Setembro":
                return 9;
            case "Outubro":
                return 10;
            case "Novembro":
                return 11;
            default:
                return 12;
        }

    }

}
