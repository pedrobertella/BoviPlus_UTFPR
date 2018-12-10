package DAO;

import Entidades.Entrega_Leite;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOEntrega_Leite {

    private Conexao conexao;

    public DAOEntrega_Leite() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }

    public void cadastrar_entrega_leite(Entrega_Leite entrega_leite) throws SQLException {

        String sql = "INSERT INTO entrega_leite (id_entrega, qtd, inicio, fim,valor_litro, id_conta) VALUES (" + entrega_leite.getId_entrega() + ","
                + entrega_leite.getQtd() + ",'" + entrega_leite.getInicio() + "','" + entrega_leite.getFim() + "'," + entrega_leite.getValor_litro() + ", " + entrega_leite.getId_conta() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);

    }

    public Entrega_Leite consultar_entrega_leite(int id_entrega) throws SQLException {
        Entrega_Leite entrega_leite = new Entrega_Leite();
        String sql = "SELECT * FROM entrega_leite WHERE id_entrega = " + id_entrega + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        entrega_leite.setId_entrega(rs.getInt("id_entrega"));
        entrega_leite.setQtd(rs.getDouble("qtd"));
        entrega_leite.setInicio(rs.getString("inicio"));
        entrega_leite.setFim(rs.getString("fim"));
        entrega_leite.setValor_litro(rs.getDouble("valor_litro"));
        entrega_leite.setId_conta(rs.getInt("id_conta"));

        return entrega_leite;
    }

    public Entrega_Leite consultaConta(int id) throws SQLException {
        Entrega_Leite entrega_leite = new Entrega_Leite();
        String sql = "SELECT * FROM entrega_leite WHERE id_conta = " + id + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        entrega_leite.setId_entrega(rs.getInt("id_entrega"));
        entrega_leite.setQtd(rs.getDouble("qtd"));
        entrega_leite.setInicio(rs.getString("inicio"));
        entrega_leite.setFim(rs.getString("fim"));
        entrega_leite.setValor_litro(rs.getDouble("valor_litro"));
        entrega_leite.setId_conta(rs.getInt("id_conta"));

        return entrega_leite;
    }

    public void atualizarEntrega_Leite(int id_entrega, double qtd, String inicio, String fim, double valor_litro, int id_conta) throws SQLException {
        String sql = "UPDATE entrega_leite set qtd = " + qtd + ", inicio = '" + inicio + "', fim = '" + fim + "', id_conta = " + id_conta + ", valor_litro = " + valor_litro
                + " WHERE id_entrega = " + id_entrega + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarEntrega_Leite(int id_entrega) throws SQLException {
        String sql = "DELETE FROM entrega_leite WHERE id_entrega = " + id_entrega + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int nextID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT MAX(id_entrega) as idn FROM entrega_leite;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getInt("idn") + 1;
    }

    public boolean verificaPeriodo(String data1, String data2) throws SQLException {
        String sql;
        sql = "select * from entrega_leite where inicio between '" + data1 + "' and '" + data2 + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        if (rs.next()) {
            return true;
        }
        sql = "select * from entrega_leite where fim between '" + data1 + "' and '" + data2 + "';";
        rs = stm.executeQuery(sql);
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public String proximaData() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Entrega_Leite a = consultar_entrega_leite(nextID() - 1);

        return a.getFim();
    }
}
