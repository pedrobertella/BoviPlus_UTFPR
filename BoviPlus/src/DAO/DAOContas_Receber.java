package DAO;

import Entidades.Contas_Receber;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOContas_Receber {

    private Conexao conexao;

    public DAOContas_Receber() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }

    public void cadastrarContas_receber(Contas_Receber conta_receber) throws SQLException {
        String sql = "";
        if (conta_receber.getData_recebimento() != null) {
            sql = "INSERT INTO contas_receber (qtd, valor, data_vencimento, data_recebimento, descricao, producao) VALUES (" + conta_receber.getQtd() + ","
                    + conta_receber.getValor() + ",'" + conta_receber.getData_vencimento() + "','" + conta_receber.getData_recebimento() + "','"
                    + conta_receber.getDescricao() + "'," + conta_receber.isProducao() + ");";
        } else {
            sql = "INSERT INTO contas_receber (qtd, valor, data_vencimento, descricao, producao) VALUES (" + conta_receber.getQtd() + ","
                    + conta_receber.getValor() + ",'" + conta_receber.getData_vencimento() + "','"
                    + conta_receber.getDescricao() + "'," + conta_receber.isProducao() + ");";
        }

        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public Contas_Receber consultar_contas_receber(int id_contas_receber) throws SQLException {
        Contas_Receber contas_receber = new Contas_Receber();
        String sql = "SELECT * FROM contas_receber WHERE id_contas_receber = " + id_contas_receber + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        contas_receber.setId_contas_receber(rs.getInt("id_contas_receber"));
        contas_receber.setValor(rs.getDouble("valor"));
        contas_receber.setQtd(rs.getDouble("qtd"));
        contas_receber.setData_recebimento(rs.getString("data_recebimento"));
        contas_receber.setDescricao(rs.getString("descricao"));
        contas_receber.setProducao(rs.getBoolean("producao"));
        contas_receber.setData_vencimento(rs.getString("data_vencimento"));

        return contas_receber;
    }

    public void atualizarContas_Receber(Contas_Receber c) throws SQLException {
        String sql = "";
        if (c.getData_recebimento() != null) {
            sql = "UPDATE contas_receber set valor = " + c.getValor() + ", data_vencimento = '" + c.getData_vencimento() + "', descricao = '" + c.getDescricao() + "', data_recebimento = '" + c.getData_recebimento() + "',  producao = " + c.isProducao()
                    + ", qtd = " + c.getQtd() + " WHERE id_contas_receber = " + c.getId_contas_receber() + ";";
        } else {
            sql = "UPDATE contas_receber set valor = " + c.getValor() + ", data_vencimento = '" + c.getData_vencimento() + "', descricao = '" + c.getDescricao() + "', data_recebimento = NULL,  producao = " + c.isProducao()
                    + ", qtd = " + c.getQtd() + " WHERE id_contas_receber = " + c.getId_contas_receber() + ";";
        }

        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarContas_Receber(int id_contas_receber) throws SQLException {
        String sql = "DELETE FROM contas_receber WHERE id_contas_receber = " + id_contas_receber + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int lastID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT MAX(id_contas_receber) as idn FROM contas_receber;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getInt("idn");
    }

    public List<Contas_Receber> vencemHoje(String data) throws SQLException {
        Contas_Receber contas_receber;
        String sql = "SELECT * FROM contas_receber WHERE data_vencimento = '" + data + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        List<Contas_Receber> a = new ArrayList<>();

        while (rs.next()) {
            contas_receber = new Contas_Receber();
            contas_receber.setId_contas_receber(rs.getInt("id_contas_receber"));
            contas_receber.setValor(rs.getDouble("valor"));
            contas_receber.setQtd(rs.getDouble("qtd"));
            contas_receber.setData_recebimento(rs.getString("data_recebimento"));
            contas_receber.setDescricao(rs.getString("descricao"));
            contas_receber.setProducao(rs.getBoolean("producao"));
            contas_receber.setData_vencimento(rs.getString("data_vencimento"));
            a.add(contas_receber);
        }

        return a;
    }
    public List<Contas_Receber> pesquisaCustom(String sql) throws SQLException {
        Contas_Receber contas_receber;
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        List<Contas_Receber> a = new ArrayList<>();

        while (rs.next()) {
            contas_receber = new Contas_Receber();
            contas_receber.setId_contas_receber(rs.getInt("id_contas_receber"));
            contas_receber.setValor(rs.getDouble("valor"));
            contas_receber.setQtd(rs.getDouble("qtd"));
            contas_receber.setData_recebimento(rs.getString("data_recebimento"));
            contas_receber.setDescricao(rs.getString("descricao"));
            contas_receber.setProducao(rs.getBoolean("producao"));
            contas_receber.setData_vencimento(rs.getString("data_vencimento"));
            a.add(contas_receber);
        }

        return a;
    }
    
}
