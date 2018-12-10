package DAO;

import Entidades.Equipamento;
import ConexaoBD.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOEquipamento {
    private Conexao conexao;
    //private String sql;

    public DAOEquipamento() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public int cadastrarEquipamento(Equipamento equipamento) throws SQLException {
//Insere endereco no banco de dados
        String sql = "INSERT INTO equipamento (id_equipamento, nome, descricao, qtd) VALUES (" + equipamento.getId_equipamento() + ",'" 
                + equipamento.getNome() + "','" + equipamento.getDescricao() + "'," + equipamento.getQtd() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
//Consulta e retorna o codigo do endereco inserido
        sql = "SELECT * FROM equipamento WHERE id_equipamento = " + equipamento.getId_equipamento() + " AND nome = '" + equipamento.getNome() 
                + "' AND descricao = '" + equipamento.getDescricao() + "' AND qtd = " + equipamento.getQtd() + ";";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_equipamento");
        //Recebe como parametro um codigo e consulta o endereco que tem esse codigo
}
    public Equipamento consultaEquipamento(int id_equipamento) throws SQLException {
        Equipamento equipamento = new Equipamento();
        String sql = "SELECT * FROM equipamento WHERE id_equipamento = " + id_equipamento + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        equipamento.setId_equipamento(rs.getInt("id_equipamento"));
        equipamento.setNome(rs.getString("nome"));
        equipamento.setDescricao(rs.getString("descricao"));
        equipamento.setQtd(rs.getInt("qtd"));
        return equipamento;
    }
    
    public void atualizarEquipamento(int id_equipamento, String nome, String descricao, int qtd) throws SQLException {
        String sql = "UPDATE equipamento set nome = '" + nome + "', descricao = '" + descricao + "', qtd = " + qtd 
                + " WHERE id_equipamento = " + id_equipamento + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    }
    
    public void deletarEquipamento(int id_equipamento) throws SQLException {
        String sql = "DELETE FROM equipamento WHERE id_equipamento = " + id_equipamento + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
}
