package DAO;

import Entidades.Enfermidade;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOEnfermidade {

    private Conexao conexao;

    public DAOEnfermidade() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }

    public void cadastrar_enfermidade(Enfermidade enfermidade) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
//Insere contas a receber no banco de dados
        /*String sql = "INSERT INTO enfermidade (id_animal, id_doenca, medicacao, tempo_carencia,tempo_tratamento,data_enfermidade) VALUES (" + enfermidade.getId_animal() + ","
                + enfermidade.getId_doenca() + "," + enfermidade.getMedicacao() + "," + enfermidade.getTempo_carencia() + "," + enfermidade.getData_enfermidade() + "," + enfermidade.getTempo_tratamento() + ");";*/
        String sql = "INSERT INTO enfermidade (id_animal, id_doenca, tempo_carencia,tempo_tratamento,data_enfermidade) VALUES (" + enfermidade.getId_animal() + ","
                + enfermidade.getId_doenca() + "," + enfermidade.getTempo_carencia() + "," + enfermidade.getTempo_tratamento() + ",'" + enfermidade.getData_enfermidade() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public Enfermidade consultar_enfermidade(int id_animal) throws SQLException {
        Enfermidade enfermidade = new Enfermidade();
        String sql = "SELECT * FROM enfermidade WHERE id_entrega = " + id_animal + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        enfermidade.setId_animal(rs.getInt("id_animal"));
        enfermidade.setId_doenca(rs.getInt("id_doenca"));
        //enfermidade.setMedicacao(rs.getString("medicacao"));
        enfermidade.setTempo_carencia(rs.getInt("tempo_carencia"));
        enfermidade.setTempo_tratamento(rs.getInt("tempo_tratamento"));
        enfermidade.setData_enfermidade(rs.getString("data_enfermidade"));

        return enfermidade;
    }

    /*public void atualizarEnfermidade(int id_animal, int id_doenca, String medicacao, int tempo_carencia, int tempo_tratamento, String data_enfermidade) throws SQLException {
        String sql = "UPDATE enfermidade set medicacao = '" + medicacao + "', tempo_carencia = " + tempo_carencia + ", tempo_tratamento = " + tempo_tratamento
                + " WHERE id_animal = " + id_animal + " AND id_doenca = " + id_doenca + " AND data_enfermidade = '" + data_enfermidade + "';";*/
    public void atualizarEnfermidade(int id_animal, int id_doenca, int tempo_carencia, int tempo_tratamento, String data_enfermidade) throws SQLException {
        String sql = "UPDATE enfermidade set tempo_carencia = " + tempo_carencia + ", tempo_tratamento = " + tempo_tratamento
                + " WHERE id_animal = " + id_animal + " AND id_doenca = " + id_doenca + " AND data_enfermidade = '" + data_enfermidade + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);
    }

    public void deletarEnfermidade(int id_animal, int id_doenca, String data_enfermidade) throws SQLException {
        String sql = "DELETE FROM enfermidade WHERE id_animal = " + id_animal + " AND id_doenca = " + id_doenca + " AND data_enfermidade = '" + data_enfermidade + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }
    
    public List<Enfermidade> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Enfermidade> enfermidades = new ArrayList<>();
        try {
            Statement stm = conexao.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Enfermidade e = new Enfermidade();
            e.setId_animal(rs.getInt("id_animal"));
            e.setId_doenca(rs.getInt("id_doenca"));
            //e.setMedicacao(rs.getString("medicacao"));
            e.setTempo_carencia(rs.getInt("tempo_carencia"));
            e.setTempo_tratamento(rs.getInt("tempo_tratamento"));
            e.setData_enfermidade(rs.getString("data_enfermidade"));

                enfermidades.add(e);

            }
            stm.close();
            rs.close();
            conexao.connection.close();
        } catch (SQLException ex) {
            return null;
        }

        return enfermidades;
    }
}
