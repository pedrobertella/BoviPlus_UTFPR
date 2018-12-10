package DAO;

import Entidades.Tipo;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOTipo {
    private Conexao conexao;

    public DAOTipo() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
    
    public int cadastrar_tipo(Tipo tipo) throws SQLException {
//Insere contas a receber no banco de dados
        String sql = "INSERT INTO tipo (id_tipo, nome, descricao) VALUES (" + tipo.getId_tipo()+ ",'" 
                + tipo.getDescricao() + ",'" + tipo.getNome() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
//Consulta e retorna o codigo da conta a receber
        sql = "SELECT * FROM tipo WHERE id_tipo = " + tipo.getId_tipo() + " AND descricao = '" + tipo.getDescricao()
                + " AND nome = '" + tipo.getNome() + "');";
                
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_venda");
        //Recebe como parametro um codigo e consulta a conta a receber que tem esse codigo
}
    public Tipo consultar_tipo(int id_tipo) throws SQLException {
        Tipo tipo = new Tipo();
        String sql = "SELECT * FROM tipo WHERE id_tipo = " + tipo.getId_tipo() + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        tipo.setId_tipo(rs.getInt("id_tipo"));
        tipo.setNome(rs.getString("nome"));
        tipo.setDescricao(rs.getString("descricao"));

        return tipo;
  }  
    
    public void atualizarTipo(int id_tipo, String nome, String descricao) throws SQLException {
        String sql = "UPDATE tipo SET nome = '" + nome + "', descricao = '" + descricao + "' WHERE id_tipo = " + id_tipo + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarTipo(int id_tipo) throws SQLException {
        String sql = "DELETE FROM tipo WHERE id_tipo = " + id_tipo + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
    
}