package DAO;

import Entidades.Custo_reproducao;

import ConexaoBD.Conexao;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCusto_reproducao {

    private Conexao conexao;
    //private String sql;

    public DAOCusto_reproducao() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public void cadastrarCusto_reproducao(Custo_reproducao custo_reproducao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
//Insere endereco no banco de dados
        String sql = "INSERT INTO custo_reproducao (id_gestacao, id_contas_pagar, qtd, valor) VALUES ("
                + custo_reproducao.getId_gestacao() + "," + custo_reproducao.getId_contas_pagar() + ","
                + custo_reproducao.getQtd() + "," + custo_reproducao.getValor() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);

    }

    public Custo_reproducao consultaCusto_reproducao(int id_gestacao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Custo_reproducao custo_reproducao = new Custo_reproducao();
        String sql = "SELECT * FROM custo_reproducao WHERE id_gestacao = " + id_gestacao + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        custo_reproducao.setId_gestacao(rs.getInt("id_gestacao"));
        custo_reproducao.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        custo_reproducao.setQtd(rs.getInt("qtd"));
        custo_reproducao.setValor(rs.getDouble("valor"));
        return custo_reproducao;
    }

    public Custo_reproducao consultaCusto(int id_contas_pagar) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Custo_reproducao custo_reproducao = new Custo_reproducao();
        String sql = "SELECT * FROM custo_reproducao WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        custo_reproducao.setId_gestacao(rs.getInt("id_gestacao"));
        custo_reproducao.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        custo_reproducao.setQtd(rs.getInt("qtd"));
        custo_reproducao.setValor(rs.getDouble("valor"));
        return custo_reproducao;
    }

    public void atualizarCusto_reproducao(int id_gestacao, int id_contas_pagar, int qtd, double valor) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "UPDATE custo_reproducao set qtd = " + qtd + ", valor = " + valor + " WHERE id_gestacao = " + id_gestacao
                + " AND id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarCusto_reproducao(int id_contas_pagar) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "DELETE FROM custo_reproducao WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

}
