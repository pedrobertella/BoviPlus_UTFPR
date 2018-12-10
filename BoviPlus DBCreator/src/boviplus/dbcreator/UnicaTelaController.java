package boviplus.dbcreator;

import ConexaoBD.Conexao;
import ConexaoBD.Conexao_1;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class UnicaTelaController implements Initializable {

    Conexao c;
    Conexao_1 c1;
    String senha;

    @FXML
    private TextField nome_lb;

    @FXML
    private Button cria_bt;

    @FXML
    private TextField senha_tx;

    @FXML
    private Button conect_bt;

    @FXML
    public void conectaBD() {
        try {
            senha = "";
            c = new Conexao();
            if (senha_tx.getText().trim().isEmpty()) {
                throw new IOException();
            }
            senha = senha_tx.getText();
            c.abrirConexao(senha);
            cria_bt.setDisable(false);
            nome_lb.setDisable(false);
            nome_lb.requestFocus();
        } catch (Exception e) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Não foi possível estabelecer conexão com o servidor do banco de dados.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    @FXML
    public void criaDB() {
        if (nome_lb.getText().trim().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Atenção!");
            alert2.setHeaderText("Por favor, digite um nome para o banco de dados.");
            alert2.showAndWait().ifPresent(rj -> {
                if (rj == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        } else {
            try {

                c1 = new Conexao_1();
                c.abrirConexao(senha);
                String sql = "CREATE DATABASE " + nome_lb.getText() + ";";
                Statement stm = c.getConnection().createStatement();
                stm.execute(sql);

                c1.abrirConexao(nome_lb.getText().toLowerCase(), senha);
                sql = "CREATE TABLE RACA (\n"
                        + "ID_RACA INT NOT NULL,\n"
                        + "NOME VARCHAR(45) NOT NULL,\n"
                        + "TEMPO_GESTACAO INT NOT NULL,\n"
                        + "DESCRICAO VARCHAR(140) NOT NULL,\n"
                        + "CONSTRAINT PK_RACA PRIMARY KEY(ID_RACA)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE TIPO (\n"
                        + "ID_TIPO INT NOT NULL,\n"
                        + "NOME VARCHAR(30) NOT NULL,\n"
                        + "DESCRICAO VARCHAR(45) NOT NULL,\n"
                        + "CONSTRAINT PK_TIPO PRIMARY KEY(ID_TIPO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE LOTE(\n"
                        + "ID_LOTE INT DEFAULT 0 PRIMARY KEY NOT NULL,\n"
                        + "NOME_LOTE VARCHAR(30) DEFAULT 'Indefinido'\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE ANIMAL (\n"
                        + "ID_ANIMAL INT NOT NULL,\n"
                        + "NOME VARCHAR(20),\n"
                        + "NASCIMENTO DATE,\n"
                        + "PESO DECIMAL(7,3),\n"
                        + "DESCRICAO VARCHAR(300) NOT NULL,\n"
                        + "CONSTRAINT PK_ANIMAL PRIMARY KEY(ID_ANIMAL),\n"
                        + "ID_MAE INT,\n"
                        + "ID_PAI INT,\n"
                        + "ID_TIPO INT,\n"
                        + "ID_RACA INT,\n"
                        + "LACTACAO BOOLEAN DEFAULT FALSE NULL,\n"
                        + "ID_LOTE int,\n"
                        + "CONSTRAINT FK_ANIMAL_MAE FOREIGN KEY (ID_MAE) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ANIMAL_PAI FOREIGN KEY (ID_PAI) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ANIMAL_TIPO FOREIGN KEY (ID_TIPO) REFERENCES TIPO(ID_TIPO) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ANIMAL_RACA FOREIGN KEY (ID_RACA) REFERENCES RACA(ID_RACA) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ANIMAL_LOTE FOREIGN KEY (ID_LOTE) REFERENCES LOTE (ID_LOTE) ON UPDATE CASCADE ON DELETE NO ACTION\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE VENDA_ANIMAL (\n"
                        + "ID_VENDA INT NOT NULL,\n"
                        + "ID_ANIMAL INT NOT NULL,\n"
                        + "VALOR DECIMAL(9,2) NOT NULL,\n"
                        + "DATA_VENDA DATE NOT NULL,\n"
                        + "CONSTRAINT FK_VENDA_ANIMAL_ANIMAL FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_VENDA_ANIMAL PRIMARY KEY(ID_VENDA)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE contas_receber\n"
                        + "(\n"
                        + "  id_contas_receber SERIAL,\n"
                        + "  QTD NUMERIC (9,3),\n"
                        + "  VALOR NUMERIC (9,2),\n"
                        + "  data_vencimento date not null,\n"
                        + "  data_recebimento date,\n"
                        + "  descricao varchar(150),\n"
                        + "  producao boolean default false,\n"
                        + "  PRIMARY KEY (id_contas_receber)\n"
                        + " );\n"
                        + "\n"
                        + "CREATE TABLE ENTREGA_LEITE (\n"
                        + "ID_ENTREGA INT NOT NULL,\n"
                        + "QTD DECIMAL(10,3) NOT NULL,\n"
                        + "INICIO DATE NOT NULL,\n"
                        + "FIM DATE NOT NULL,\n"
                        + "VALOR_LITRO DECIMAL(3,2) NOT NULL,\n"
                        + "ID_CONTA INTEGER,\n"
                        + "CONSTRAINT PK_ENTREGA_LEITE PRIMARY KEY(ID_ENTREGA),\n"
                        + "FOREIGN KEY(ID_CONTA) REFERENCES CONTAS_RECEBER(ID_CONTAS_RECEBER) ON UPDATE CASCADE ON DELETE SET NULL\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE PRODUCAO (\n"
                        + "ID_VACA INT NOT NULL,\n"
                        + "DATA_PRODUCAO DATE NOT NULL,\n"
                        + "LITRAGEM DECIMAL(6,3) NOT NULL,\n"
                        + "CONSTRAINT FK_PRODUCAO_VACA FOREIGN KEY (ID_VACA) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_PRODUCAO primary key(ID_VACA, DATA_PRODUCAO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE DOENCA (\n"
                        + "ID_DOENCA INT NOT NULL,\n"
                        + "NOME VARCHAR(45) NOT NULL,\n"
                        + "DESCRICAO VARCHAR(200) NOT NULL,\n"
                        + "CONSTRAINT PK_DOENCA PRIMARY KEY(ID_DOENCA)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE ENFERMIDADE (\n"
                        + "ID_ANIMAL INT NOT NULL,\n"
                        + "ID_DOENCA INT NOT NULL,\n"
                        + "TEMPO_CARENCIA INT NOT NULL,\n"
                        + "TEMPO_TRATAMENTO INT NOT NULL,\n"
                        + "DATA_ENFERMIDADE DATE NOT NULL,\n"
                        + "CONSTRAINT FK_ENFERMIDADE_ANIMAL FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ENFERMIDADE_DOENCA FOREIGN KEY (ID_DOENCA) REFERENCES DOENCA(ID_DOENCA) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_ENFERMIDADE PRIMARY KEY(DATA_ENFERMIDADE, ID_ANIMAL, ID_DOENCA)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE VACINA (\n"
                        + "ID_VACINA INT NOT NULL,\n"
                        + "NOME VARCHAR(25) NOT NULL,\n"
                        + "INDICACAO VARCHAR(90) NOT NULL,\n"
                        + "CONTRA_INDC VARCHAR(90) NOT NULL,\n"
                        + "PERIODICA BOOLEAN DEFAULT FALSE NOT NULL,\n"
                        + "CAMPANHA VARCHAR(50) NULL,\n"
                        + "MES VARCHAR(10) NULL,\n"
                        + "CAT_ANIMAIS VARCHAR(30) NULL,\n"
                        + "CONSTRAINT PK_VACINA PRIMARY KEY(ID_VACINA)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE MEDICAMENTO (\n"
                        + "ID_MED INT NOT NULL,\n"
                        + "NOME VARCHAR(25) NOT NULL,\n"
                        + "INDICACAO VARCHAR(100) NOT NULL,\n"
                        + "CONTRA_INDC VARCHAR(100) NOT NULL,\n"
                        + "CONSTRAINT PK_MEDICAMENTO PRIMARY KEY(ID_MED)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE VACINACAO (\n"
                        + "id_vacinacao integer not null,\n"
                        + "ID_ANIMAL INT NOT NULL,\n"
                        + "ID_VACINA INT NOT NULL,\n"
                        + "DOSE DECIMAL(5,2) NOT NULL,\n"
                        + "DATA_VACINACAO DATE NOT NULL,\n"
                        + "MOTIVO VARCHAR(45) NOT NULL,\n"
                        + "CONSTRAINT FK_VACINACAO_ANIMAL FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_VACINACAO_VACINA FOREIGN KEY (ID_VACINA) REFERENCES VACINA(ID_VACINA) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_VACINACAO PRIMARY KEY(ID_ANIMAL, ID_VACINA, DATA_VACINACAO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE REPRODUCAO (\n"
                        + "ID_GESTACAO INT NOT NULL,\n"
                        + "ID_MAE INT NOT NULL,\n"
                        + "ID_PAI INT NOT NULL,\n"
                        + "PREVISAO DATE NOT NULL,\n"
                        + "DATA_INSEMINACAO DATE NOT NULL,\n"
                        + "CONSTRAINT FK_REPRODUCAO_ANIMAL_MAE FOREIGN KEY (ID_MAE) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_REPRODUCAO_ANIMAL_PAI FOREIGN KEY (ID_PAI) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_REPRODUCAO PRIMARY KEY(ID_GESTACAO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE TIPO_ALIMENTO (\n"
                        + "ID_TIPO_ALIMENTO INT NOT NULL,\n"
                        + "NOME VARCHAR(30) NOT NULL,\n"
                        + "DESCRICAO VARCHAR(45) NOT NULL,\n"
                        + "CONSTRAINT PK_TIPO_ALIMENTO PRIMARY KEY(ID_TIPO_ALIMENTO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE UNIDADE_MEDIDA (\n"
                        + "ID_UNDMDD INT NOT NULL,\n"
                        + "NOME VARCHAR(30) NOT NULL,\n"
                        + "DESCRICAO VARCHAR(45) NOT NULL,\n"
                        + "CONSTRAINT PK_UNDMDD PRIMARY KEY(ID_UNDMDD)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE ALIMENTO (\n"
                        + "ID_ALIMENTO INT NOT NULL,\n"
                        + "NOME VARCHAR(15) NOT NULL,\n"
                        + "CONSTRAINT PK_ALIMENTO PRIMARY KEY(ID_ALIMENTO),\n"
                        + "ID_TIPO_ALIMENTO INT,\n"
                        + "ID_UNDMDD INT,\n"
                        + "CONSTRAINT FK_ALIMENTO_TIPO FOREIGN KEY (ID_TIPO_ALIMENTO) REFERENCES TIPO_ALIMENTO(ID_TIPO_ALIMENTO) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ALIMENTO_UNDMDD FOREIGN KEY (ID_UNDMDD) REFERENCES UNIDADE_MEDIDA(ID_UNDMDD) ON UPDATE CASCADE ON DELETE NO ACTION\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE ESTOQUE (\n"
                        + "\n"
                        + "	ID_MOVIMENTO INTEGER NOT NULL,\n"
                        + "	ID_ALIMENTO INTEGER NOT NULL,\n"
                        + "	QUANT NUMERIC(13,3) NOT NULL,\n"
                        + "	VALOR_UNI NUMERIC (12,2) NOT NULL,\n"
                        + "	DATA_COMPRA DATE NOT NULL,\n"
                        + "	PRIMARY KEY (ID_MOVIMENTO),\n"
                        + "	FOREIGN KEY (ID_ALIMENTO) REFERENCES ALIMENTO(ID_ALIMENTO) ON UPDATE CASCADE ON DELETE CASCADE\n"
                        + "\n"
                        + ");\n"
                        + "\n"
                        + "\n"
                        + "CREATE TABLE GASTO_DIARIO (\n"
                        + "	ID_ALIMENTO INTEGER NOT NULL,\n"
                        + "	VALOR NUMERIC(12,2),\n"
                        + "	DIA DATE,\n"
                        + "	PRIMARY KEY(ID_ALIMENTO, DIA),\n"
                        + "	FOREIGN KEY (ID_ALIMENTO) REFERENCES alimento(ID_ALIMENTO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE ALIMENTACAO (\n"
                        + "ID_ALIMENTO INT NOT NULL,\n"
                        + "ID_ANIMAL INT NOT NULL,\n"
                        + "QTD DECIMAL(6,3) NOT NULL,\n"
                        + "DATA_ALIMENTACAO DATE NOT NULL,\n"
                        + "CONSTRAINT FK_ALIMENTACAO_ALIMENTO FOREIGN KEY (ID_ALIMENTO) REFERENCES ALIMENTO(ID_ALIMENTO) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_ALIMENTACAO_ANIMAL FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_ALIMENTACAO PRIMARY KEY(ID_ANIMAL, ID_ALIMENTO, DATA_ALIMENTACAO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE CONTAS_PAGAR (\n"
                        + "ID_CONTAS_PAGAR INT NOT NULL,\n"
                        + "VALOR DECIMAL(10,2) NOT NULL,\n"
                        + "DATA_PAGAMENTO DATE NULL,\n"
                        + "DATA_VENCIMENTO DATE NOT NULL,\n"
                        + "ORIGEM INT,\n"
                        + "CONSTRAINT PK_CONTAS_PAGAR PRIMARY KEY(ID_CONTAS_PAGAR)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE EQUIPAMENTO (\n"
                        + "ID_EQUIPAMENTO INT NOT NULL,\n"
                        + "NOME VARCHAR(25) NOT NULL,\n"
                        + "DESCRICAO VARCHAR(45) NOT NULL,\n"
                        + "QTD INT NOT NULL,\n"
                        + "CONSTRAINT PK_EQUIPAMENTO PRIMARY KEY(ID_EQUIPAMENTO)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE CUSTO_EQUIPAMENTO (\n"
                        + "ID_EQUIPAMENTO INT NOT NULL,\n"
                        + "ID_CONTAS_PAGAR INT NOT NULL,\n"
                        + "QTD INT NOT NULL,\n"
                        + "VALOR DECIMAL(9,2) NOT NULL,\n"
                        + "CONSTRAINT FK_CUSTO_EQUIPAMENTO_EQUIPAMENTO FOREIGN KEY (ID_EQUIPAMENTO) REFERENCES EQUIPAMENTO(ID_EQUIPAMENTO) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_CUSTO_EQUIPAMENTO_CONTAS_PAGAR FOREIGN KEY (ID_CONTAS_PAGAR) REFERENCES CONTAS_PAGAR(ID_CONTAS_PAGAR) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_CUSTO_EQUIPAMENTO PRIMARY KEY(ID_EQUIPAMENTO, ID_CONTAS_PAGAR)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE custo_vacina (\n"
                        + "    id_vacina integer,\n"
                        + "    id_contas_pagar integer REFERENCES contas_pagar(id_contas_pagar) ON UPDATE CASCADE,\n"
                        + "    qtd integer NOT NULL,\n"
                        + "    valor numeric(9,2) NOT NULL,\n"
                        + "    CONSTRAINT pk_custo_vacina PRIMARY KEY (id_vacina, id_contas_pagar)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE CUSTO_REPRODUCAO (\n"
                        + "ID_GESTACAO INT NOT NULL,\n"
                        + "ID_CONTAS_PAGAR INT NOT NULL,\n"
                        + "QTD INT  NOT NULL,\n"
                        + "VALOR DECIMAL(9,2) NOT NULL,\n"
                        + "CONSTRAINT FK_CUSTO_REPRODUCAO_CONTAS_PAGAR FOREIGN KEY (ID_CONTAS_PAGAR) REFERENCES CONTAS_PAGAR(ID_CONTAS_PAGAR) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT FK_CUSTO_REPRODUCAO_GESTACAO FOREIGN KEY (ID_GESTACAO) REFERENCES REPRODUCAO(ID_GESTACAO) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_CUSTO_REPRODUCAO PRIMARY KEY(ID_GESTACAO, ID_CONTAS_PAGAR)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE EXAME (\n"
                        + "ID_EXAME INT NOT NULL,\n"
                        + "NOME VARCHAR(45) NOT NULL,\n"
                        + "DESCRICAO VARCHAR(300) NOT NULL,\n"
                        + "DATA_EXAME DATE NOT NULL,\n"
                        + "CATEGORIA CHAR NOT NULL,\n"
                        + "INTERVALO INT NOT NULL,\n"
                        + "CONSTRAINT PK_EXAME PRIMARY KEY(ID_EXAME),\n"
                        + "ID_ANIMAL INT,\n"
                        + "CONSTRAINT FK_EXAME_ANIMAL FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE NO ACTION\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE CUSTO_ALIMENTO (\n"
                        + "ID_ALIMENTO INT NOT NULL,\n"
                        + "ID_CONTAS_PAGAR INT NOT NULL,\n"
                        + "QTD DECIMAL(10,3) NOT NULL,\n"
                        + "VALOR DECIMAL(9,2) NOT NULL,\n"
                        + "CONSTRAINT FK_CUSTO_ALIMENTO_CONTAS_PAGAR FOREIGN KEY (ID_CONTAS_PAGAR) REFERENCES CONTAS_PAGAR(ID_CONTAS_PAGAR) ON UPDATE CASCADE ON DELETE NO ACTION,\n"
                        + "CONSTRAINT PK_CUSTO_ALIMENTO PRIMARY KEY(ID_ALIMENTO, ID_CONTAS_PAGAR)\n"
                        + ");\n"
                        + "\n"
                        + "\n"
                        + "CREATE TABLE USUARIO(\n"
                        + "ID_USER SERIAL NOT NULL,\n"
                        + "NOME VARCHAR(30) NOT NULL,\n"
                        + "SENHA VARCHAR(30) NOT NULL,\n"
                        + "PERMISSOES CHAR(7) DEFAULT '0000000',\n"
                        + "CONSTRAINT PK_USUARIO PRIMARY KEY(ID_USER)\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE ORDEM_PRODUCAO\n"
                        + "(\n"
                        + "    ID_ORDEM SERIAL PRIMARY KEY NOT NULL,\n"
                        + "    ID_ANIMAL INT NOT NULL,\n"
                        + "    CONSTRAINT ORDEM_PRODUCAO_ANIMAL_FK FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL (ID_ANIMAL) ON DELETE CASCADE ON UPDATE CASCADE\n"
                        + ");\n"
                        + "-- alter sequence ordem_producao_id_ordem_seq restart with 1;\n"
                        + "\n"
                        + "CREATE TABLE LACTACAO(\n"
                        + "	ID_ANIMAL INT PRIMARY KEY,\n"
                        + "	DATA_LAC DATE NOT NULL,\n"
                        + "	FLAG BOOLEAN NOT NULL,\n"
                        + "	FOREIGN KEY (ID_ANIMAL) REFERENCES ANIMAL(ID_ANIMAL) ON UPDATE CASCADE ON DELETE CASCADE\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE PREFSIS(\n"
                        + "\n"
                        + "	ID INT PRIMARY KEY,\n"
                        + "	AVISO_SECAGEM INT,\n"
                        + "	AVISO_NASCIMENTO INT,\n"
                        + "	ATALHO1 VARCHAR(50),\n"
                        + "	ATALHO2 VARCHAR(50),\n"
                        + "	ATALHO3 VARCHAR(50),\n"
                        + "	ATALHO4 VARCHAR(50),\n"
                        + "	ATALHO5 VARCHAR(50),\n"
                        + "	CRECEBER integer,\n"
                        + "    	CPAGAR integer,\n"
                        + "	AVISO_VACINA integer,\n"
                        + "	AVISO_EXAME integer,\n"
                        + "	MUDO boolean,\n"
                        + "	AUTOALIMENTACAO boolean,\n"
                        + "	PREVESTOQUE integer\n"
                        + ");\n"
                        + "\n"
                        + "CREATE TABLE CUSTO_GERAL(\n"
                        + "	ID_GERAL SERIAL,\n"
                        + "	ID_CONTAS_PAGAR INTEGER,\n"
                        + "	QTD INTEGER,\n"
                        + "	VALOR NUMERIC(9,2),\n"
                        + "	DESCRICAO VARCHAR(150),\n"
                        + "	PRIMARY KEY (ID_GERAL),\n"
                        + "	FOREIGN KEY (ID_CONTAS_PAGAR) REFERENCES CONTAS_PAGAR(ID_CONTAS_PAGAR) ON UPDATE CASCADE ON DELETE CASCADE\n"
                        + ");\n"
                        + "\n"
                        + "create table produtor(\n"
                        + "	nome varchar(100) not null,\n"
                        + "	cpf varchar(11) not null,\n"
                        + "	nasc date not null,\n"
                        + "	fazenda varchar(100),\n"
                        + "	primary key(nome)\n"
                        + ");\n"
                        + "CREATE TABLE ALIM_REPLICA(\n"
                        + "	ALIMENTO INTEGER NOT NULL,\n"
                        + "	ANIMAL INTEGER NOT NULL,\n"
                        + "	QUANT NUMERIC(12, 3),\n"
                        + "	PRIMARY KEY (ALIMENTO, ANIMAL),\n"
                        + "	FOREIGN KEY (ALIMENTO) REFERENCES alimento(id_alimento),\n"
                        + "	FOREIGN KEY (ANIMAL) REFERENCES animal(id_animal)\n"
                        + ");"
                        + "\n"
                        + "INSERT INTO TIPO VALUES(0, 'PAIS_INICIAIS', 'ANTI-BUG');\n"
                        + "INSERT INTO TIPO VALUES(1, 'VACA', 'VACA');\n"
                        + "INSERT INTO TIPO VALUES(2, 'BOI', 'BOI');\n"
                        + "INSERT INTO TIPO VALUES(3, 'BEZERRO', 'BEZERRO');\n"
                        + "INSERT INTO TIPO VALUES(4, 'NOVILHA', 'NOVILHA');\n"
                        + "INSERT INTO USUARIO (NOME, SENHA, PERMISSOES) VALUES ('ADMIN', '1234', '1111111');\n"
                        + "INSERT INTO RACA VALUES(0, 'PAIS_INICIAIS', 0, 'ANTI-BUG');\n"
                        + "INSERT INTO RACA VALUES(1, 'JERSEY', 279, 'JERSEY');\n"
                        + "INSERT INTO RACA VALUES(2, 'HOLANDESA', 283, 'HD');\n"
                        + "INSERT INTO RACA VALUES(3, 'PARDO SUÍÇO', 299, 'PS');\n"
                        + "INSERT INTO RACA VALUES(4, 'GIROLANDO', 285, 'GIROLANDO');\n"
                        + "INSERT INTO RACA VALUES(5, 'JERSEY-HOLANDA', 281, 'JERSEY-HOLANDA');\n"
                        + "INSERT INTO LOTE (ID_LOTE, NOME_LOTE) VALUES (0, 'indefinido');\n"
                        + "INSERT INTO ANIMAL VALUES(0, '', '01-01-0001', 0.0, '', NULL, NULL, 0, 0, false,0);\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(1, 'CEREAL', 'CEREAL');\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(2, 'FARELO', 'FARELO');\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(3, 'RAÇÃO', 'RAÇÃO');\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(4, 'TUBÉRCULO', 'TUBÉRCULO');\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(5, 'SILAGEM', 'SILAGEM');\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(6, 'FORRAGEM', 'FORRAGEM');\n"
                        + "INSERT INTO TIPO_ALIMENTO VALUES(7, 'OUTROS', 'OUTROS');\n"
                        + "INSERT INTO UNIDADE_MEDIDA VALUES(1, 'KG', 'KG');\n"
                        + "INSERT INTO UNIDADE_MEDIDA VALUES(2, 'FARDO', 'FARDO');\n"
                        + "INSERT INTO UNIDADE_MEDIDA VALUES(3, 'SACA', 'SACA');\n"
                        + "INSERT INTO UNIDADE_MEDIDA VALUES(4, 'OUTROS', 'OUTROS');\n"
                        + "insert into prefsis values (1,60, 15, 'CadastroAnimal.fxml', 'ProducaoSimplificado.fxml', 'RegistroInseminacao.fxml','ConsultaAlimentacao.fxml', 'ContasPagar.fxml', 5, 5, 15, 15, false, false, 15);\n"
                        + "insert into produtor values ('Fulano', '00000000000', '01-01-2001', 'Fazenda BoviPlus');";

                stm = c1.getConnection().createStatement();
                stm.execute(sql);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("O banco de dados foi criado com sucesso.");
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                Platform.exit();

            } catch (Exception kk) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Atenção!");
                alert2.setHeaderText("Ocorreu um erro ao criar o Banco de Dados, verifique "
                        + "a existencia de um banco de dados com o mesmo nome ou se o servidor PostgreSQL está ativo.");
                alert2.setContentText(kk.getMessage());
                alert2.showAndWait().ifPresent(rj -> {
                    if (rj == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cria_bt.setDisable(true);
        nome_lb.setDisable(true);
    }

}
