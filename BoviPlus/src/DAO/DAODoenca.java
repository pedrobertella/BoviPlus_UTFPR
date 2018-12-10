package DAO;

import Entidades.Doenca;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAODoenca {
    private Conexao conexao_doenca;

    public DAODoenca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao_doenca = new Conexao();
    }
    
     public Doenca cadastrar_doenca(int id_doenca, String nome, String descricao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        conexao_doenca.abrirConexao();
        Doenca doenca = new Doenca();
        String sql_doenca = "INSERT INTO doenca (id_doenca, nome, descricao) VALUES (" + id_doenca + ",'"
                + nome + "','" + descricao + "');";
        
        Statement stm = conexao_doenca.getConnection().createStatement();
        stm.execute(sql_doenca);
        return doenca;
    }

    public Doenca consultar_doenca(int id_doenca) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_doenca.abrirConexao();
        Doenca doenca = new Doenca();

        String sql = "SELECT * FROM doenca WHERE id_doenca = " + id_doenca + ";";
        conexao_doenca.rs = conexao_doenca.stm.executeQuery(sql);
        conexao_doenca.rs.next();

        doenca.setId_doenca(conexao_doenca.rs.getInt("id_doenca"));
        doenca.setNome(conexao_doenca.rs.getString("nome"));
        doenca.setDescricao(conexao_doenca.rs.getString("descricao"));

        return doenca;
    }

    public void atualizarDoenca(int id_doenca, String nome, String descricao) throws SQLException {
        String sql = "UPDATE doenca set nome = '" + nome + "', descricao = '" + descricao + "' WHERE id_doenca = " + id_doenca + ";";
        Statement stm = conexao_doenca.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarDoenca(int id_doenca) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_doenca.abrirConexao();
        String sql_doenca = "DELETE FROM doenca WHERE id_doenca = " + id_doenca + ";";
        conexao_doenca.stm.execute(sql_doenca);
        System.out.println(id_doenca);               
    } 
    
    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        conexao_doenca.abrirConexao();
        String sql = "SELECT MAX(id_doenca) as idn FROM doenca;";
        conexao_doenca.rs = conexao_doenca.stm.executeQuery(sql);
        conexao_doenca.rs.next();
        int id = conexao_doenca.rs.getInt("idn");
        return (id+1);
    } 
    
    public List<Doenca> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_doenca.abrirConexao();
        List<Doenca> doencas = new ArrayList<>();
        try {
            Statement stm = conexao_doenca.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Doenca m = new Doenca();
                m.setId_doenca(rs.getInt("id_doenca"));
                m.setNome(rs.getString("nome"));
                m.setDescricao(rs.getString("descricao"));

                doencas.add(m);

            }
            stm.close();
            rs.close();
            conexao_doenca.connection.close();
        } catch (SQLException ex) {
            return null;
        }

        return doencas;
    }
}