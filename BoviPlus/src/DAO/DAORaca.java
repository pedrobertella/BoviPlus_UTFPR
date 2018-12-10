package DAO;

import Entidades.Raca;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAORaca {

    private Conexao conexao;

    public DAORaca() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }

    public void cadastrar_raca(Raca raca) throws SQLException {
        String sql = "INSERT INTO raca (id_raca, nome, tempo_gestacao, descricao) VALUES (" + raca.getId_raca() + ",'"
                + raca.getNome() + "'," + raca.getTempo_gestacao() + ",'" + raca.getDescricao() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public Raca consultar_raca(int id_raca) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Raca raca = new Raca();
        String sql = "SELECT * FROM raca WHERE id_raca = " + id_raca + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        raca.setId_raca(rs.getInt("id_raca"));
        raca.setNome(rs.getString("nome"));
        raca.setTempo_gestacao(rs.getInt("tempo_gestacao"));
        raca.setDescricao(rs.getString("descricao"));
        return raca;
    }

    public List<Raca> listarTudo() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Raca raca;
        List<Raca> ll = new ArrayList<>();
        String sql = "SELECT * FROM raca where id_raca > 0;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            raca = new Raca();
            raca.setId_raca(rs.getInt("id_raca"));
            raca.setNome(rs.getString("nome"));
            raca.setTempo_gestacao(rs.getInt("tempo_gestacao"));
            raca.setDescricao(rs.getString("descricao"));
            ll.add(raca);
        }

        return ll;
    }

    public void atualizarRaca(int id_raca, String nome, int tempo_gestacao, String descricao) throws SQLException {
        String sql = "UPDATE raca set nome = '" + nome + "', tempo_gestacao = " + tempo_gestacao + ", descricao = '" + descricao
                + "' WHERE id_raca = " + id_raca + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarRaca(int id_raca) throws SQLException {
        String sql = "DELETE FROM raca WHERE id_raca = " + id_raca + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }
    
    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT MAX(id_raca) as idn FROM raca;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int as = rs.getInt("idn");
        return (as + 1);
    }
}
