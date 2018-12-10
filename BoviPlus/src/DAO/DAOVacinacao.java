package DAO;

import Entidades.Vacinacao;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOVacinacao {
    private Conexao conexao;
    //private String sql;

    public DAOVacinacao() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public int cadastrarVacinacao(Vacinacao vacinacao) throws SQLException {
//Insere endereco no banco de dados
        String sql = "INSERT INTO vacinacao (id_animal, id_vacina, dose, data_vacinacao, motivo) VALUES (" 
                + vacinacao.getId_animal() + "," + vacinacao.getId_vacina() + "," + vacinacao.getDose() + ",'" 
                + vacinacao.getData() + "','" + vacinacao.getMotivo() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
//Consulta e retorna o codigo do endereco inserido
        sql = "SELECT * FROM vacinacao WHERE id_animal = " + vacinacao.getId_animal() + " AND id_vacina = " 
                + vacinacao.getId_vacina() + " AND dose = " + vacinacao.getDose() + " AND data_vacinacao = '" 
                + vacinacao.getData() + "' AND motivo = '" + vacinacao.getMotivo() + "');";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_animal");
        //Recebe como parametro um codigo e consulta o endereco que tem esse codigo
}
    public Vacinacao consultaVacinacao(int id_animal) throws SQLException {
        Vacinacao vacinacao = new Vacinacao();
        String sql = "SELECT * FROM vacinacao WHERE id_animal = " + id_animal + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        vacinacao.setId_animal(rs.getInt("id_animal"));
        vacinacao.setId_vacina(rs.getInt("id_vacina"));
        vacinacao.setDose(rs.getDouble("dose"));
        vacinacao.setData(rs.getString("data_vacinacao"));
        vacinacao.setMotivo(rs.getString("motivo"));        
        return vacinacao;
    }
    
    public Vacinacao consultaVac(int id_animal) throws SQLException {
        Vacinacao vacinacao = new Vacinacao();
        String sql = "SELECT * FROM vacinacao WHERE id_vacinacao = " + id_animal + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        vacinacao.setId_animal(rs.getInt("id_animal"));
        vacinacao.setId_vacina(rs.getInt("id_vacina"));
        vacinacao.setDose(rs.getDouble("dose"));
        vacinacao.setData(rs.getString("data_vacinacao"));
        vacinacao.setMotivo(rs.getString("motivo"));        
        return vacinacao;
    }
    
    //Para atualizar e deletar vacinação em determinado animal em certo dia;
    public void atualizarVacinacao(int id_animal, int id_vacina, double dose, String data, String motivo) throws SQLException {
        String sql = "UPDATE vacinacao set dose = " + dose + "', motivo = '" + motivo 
                + "' WHERE id_animal = " + id_animal + " AND id_vacina = " + id_vacina + " AND data = '" + data + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarVacinacao(int id_animal, int id_vacina, String data) throws SQLException {
        String sql = "DELETE FROM vacinacao WHERE id_animal = " + id_animal + " AND id_vacina = " 
                + id_vacina + " AND data = '" + data + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    } 
}
