package DAO;

import Entidades.Unidade_medida;
import ConexaoBD.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUnidade_medida {
    private Conexao conexao;

    public DAOUnidade_medida() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
    
    public int cadastrarUnidade_medida(Unidade_medida unidade_medida) throws SQLException {
        String sql = "INSERT INTO tipo_alimento (id_undmdd, nome, descricao) VALUES (" + unidade_medida.getId_undmdd()+ ",'" 
                + unidade_medida.getDescricao() + ",'" + unidade_medida.getNome() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
        
        sql = "SELECT * FROM unidade_medida WHERE id_undmdd = " + unidade_medida.getId_undmdd() + " AND descricao = '" + unidade_medida.getDescricao()
                + " AND nome = '" + unidade_medida.getNome() + "');";
                
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_undmdd");
}
    public Unidade_medida consultarUnidade_medida(int id_undmdd) throws SQLException {
        Unidade_medida unidade_medida = new Unidade_medida();
        String sql = "SELECT * FROM unidade_medida WHERE id_undmdd = " + unidade_medida.getId_undmdd() + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        unidade_medida.setId_undmdd(rs.getInt("id_tipo_alimento"));
        unidade_medida.setNome(rs.getString("nome"));
        unidade_medida.setDescricao(rs.getString("descricao"));

        return unidade_medida;
  }  
    
    public void atualizarUnidade_medida(int id_undmdd, String nome, String descricao) throws SQLException {
        String sql = "UPDATE unidade_medida SET nome = '" + nome + "', descricao = '" + descricao + "' WHERE id_undmdd = " + id_undmdd + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarUnidade_medida(int id_undmdd) throws SQLException {
        String sql = "DELETE FROM unidade_medida WHERE id_undmdd = " + id_undmdd + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
    
}