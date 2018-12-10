package DAO;

import Entidades.Contas_pagar;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOContas_pagar {

    private Conexao conexao;

    public DAOContas_pagar() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }

    public void cadastrarContas_pagar(Contas_pagar contas_pagar) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        if (contas_pagar.getData_pagamento() != null) {
            String sql = "INSERT INTO contas_pagar (id_contas_pagar, valor, data_pagamento, data_vencimento, origem) VALUES (" + contas_pagar.getId_contas_pagar() + ","
                    + contas_pagar.getValor() + ",'" + contas_pagar.getData_pagamento() + "','" + contas_pagar.getData_vencimento() + "', " + contas_pagar.getOrigem() + ");";
            Statement stm = conexao.getConnection().createStatement();
            stm.execute(sql);
        } else {
            String sql = "INSERT INTO contas_pagar (id_contas_pagar, valor, data_vencimento, origem) VALUES (" + contas_pagar.getId_contas_pagar() + ","
                    + contas_pagar.getValor() + ",'" + contas_pagar.getData_vencimento() + "', " + contas_pagar.getOrigem() + ");";
            Statement stm = conexao.getConnection().createStatement();
            stm.execute(sql);
        }

    }

    public Contas_pagar consultaContas_pagar(int id_contas_pagar) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Contas_pagar contas_pagar = new Contas_pagar();
        String sql = "SELECT * FROM contas_pagar WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        contas_pagar.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        contas_pagar.setValor(rs.getDouble("valor"));
        try {
            contas_pagar.setData_pagamento(rs.getString("data_pagamento"));
        } catch (Exception e) {

        }
        contas_pagar.setOrigem(rs.getInt("origem"));
        contas_pagar.setData_vencimento(rs.getString("data_vencimento"));
        return contas_pagar;
    }

    public void atualizarContas_pagar(int id_contas_pagar, double valor, String data_vencimento, String data_pagamento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (data_pagamento != null) {
            String sql = "UPDATE contas_pagar set valor = " + valor + ", data_vencimento = '" + data_vencimento + "', data_pagamento = '" + data_pagamento + "' WHERE id_contas_pagar = " + id_contas_pagar + ";";
            Statement stm = conexao.getConnection().createStatement();
            stm.execute(sql);
        } else {
            String sql = "UPDATE contas_pagar set valor = " + valor + ", data_vencimento = '" + data_vencimento + "', data_pagamento = NULL WHERE id_contas_pagar = " + id_contas_pagar + ";";
            Statement stm = conexao.getConnection().createStatement();
            stm.execute(sql);
        }

    }

    public void deletarContas_pagar(int id_contas_pagar) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "DELETE FROM contas_pagar WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery("SELECT MAX(id_contas_pagar) as idn FROM contas_pagar;");
        rs.next();
        int o = rs.getInt("idn") + 1;
        return o;
    }

    public List<Contas_pagar> vencemHoje(String data) throws SQLException {
        Contas_pagar pagar;
        String sql = "SELECT * FROM contas_pagar WHERE data_vencimento = '" + data + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        List<Contas_pagar> a = new ArrayList<>();

        while (rs.next()) {
            pagar = new Contas_pagar();
            pagar.setId_contas_pagar(rs.getInt("id_contas_pagar"));
            pagar.setValor(rs.getDouble("valor"));
            pagar.setData_pagamento(rs.getString("data_pagamento"));
            pagar.setOrigem(rs.getInt("origem"));
            pagar.setData_vencimento(rs.getString("data_vencimento"));
            a.add(pagar);
        }

        return a;
    }

    public List<Contas_pagar> pesquisaCustom(String sql) throws SQLException {
        Contas_pagar pagar;
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        List<Contas_pagar> a = new ArrayList<>();

        while (rs.next()) {
            pagar = new Contas_pagar();
            pagar.setId_contas_pagar(rs.getInt("id_contas_pagar"));
            pagar.setValor(rs.getDouble("valor"));
            pagar.setData_pagamento(rs.getString("data_pagamento"));
            pagar.setOrigem(rs.getInt("origem"));
            pagar.setData_vencimento(rs.getString("data_vencimento"));
            a.add(pagar);
        }

        return a;
    }

}
