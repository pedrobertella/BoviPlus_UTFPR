package DAO;

import Entidades.Tipo_alimento;
import ConexaoBD.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOTipo_alimento {
    private Conexao conexao;

    public DAOTipo_alimento() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
    
    public int cadastrarTipo_alimento(Tipo_alimento tipo_alimento) throws SQLException {
        String sql = "INSERT INTO tipo_alimento (id_tipo_alimento, nome, descricao) VALUES (" + tipo_alimento.getId_tipo_alimento()+ ",'" 
                + tipo_alimento.getDescricao() + ",'" + tipo_alimento.getNome() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
        
        sql = "SELECT * FROM tipo_alimento WHERE id_tipo_alimento = " + tipo_alimento.getId_tipo_alimento() + " AND descricao = '" + tipo_alimento.getDescricao()
                + " AND nome = '" + tipo_alimento.getNome() + "');";
                
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_tipo_alimento");
}
    public Tipo_alimento consultarTipo_alimento(int id_tipo_alimento) throws SQLException {
        Tipo_alimento tipo_alimento = new Tipo_alimento();
        String sql = "SELECT * FROM tipo_alimento WHERE id_tipo_alimento = " + tipo_alimento.getId_tipo_alimento() + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        tipo_alimento.setId_tipo_alimento(rs.getInt("id_tipo_alimento"));
        tipo_alimento.setNome(rs.getString("nome"));
        tipo_alimento.setDescricao(rs.getString("descricao"));

        return tipo_alimento;
  }  
    
    public void atualizarTipo_alimento(int id_tipo_alimento, String nome, String descricao) throws SQLException {
        String sql = "UPDATE tipo_alimento SET nome = '" + nome + "', descricao = '" + descricao + "' WHERE id_tipo_alimento = " + id_tipo_alimento + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarTipo_alimento(int id_tipo_alimento) throws SQLException {
        String sql = "DELETE FROM tipo_alimento WHERE id_tipo_alimento = " + id_tipo_alimento + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
    
}