package DAO;

import ConexaoBD.Conexao;
import Entidades.Lote;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marlos Augusto
 */
public class DAOLote {
    
    private Conexao conexao;
    
    public DAOLote() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
    
    public void cadastrarLote(Lote lote) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();

        String sql = "INSERT INTO lote (id_lote, nome_lote) VALUES ("
                + lote.getId_lote() + ",'" + lote.getNome_lote() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);

    }

    public Lote consultarLote(int id_lote) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Lote lote = new Lote();
        String sql = "SELECT * FROM lote WHERE id_lote = " + id_lote + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        lote.setId_lote(rs.getInt("id_lote"));
        lote.setNome_lote(rs.getString("nome_lote"));
        return lote;
    }

    public void atualizarLote(int id_lote, String nome_lote, int novoid) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "UPDATE lote set id_lote = " + novoid + ", nome_lote = '" + nome_lote + " WHERE id_lote = " + id_lote + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarLote(int id_lote) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "DELETE FROM lote WHERE id_lote = " + id_lote + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "SELECT MAX(id_lote) as idn FROM lote;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int asdasd = rs.getInt("idn");
        return (asdasd + 1);
    }

    public List<Lote> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Lote> lotes = new ArrayList<>();
        try {
            Statement stm = conexao.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Lote l = new Lote();
                l.setId_lote(rs.getInt("id_lote"));
                l.setNome_lote(rs.getString("nome_lote"));
                
                lotes.add(l);
            }
            stm.close();
            rs.close();
            conexao.connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro! Lista não retornada.");
            return null;
        }

        return lotes;
    }
}
