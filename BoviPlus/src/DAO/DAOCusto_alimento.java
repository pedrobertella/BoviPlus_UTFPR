package DAO;

import Entidades.Custo_alimento;

import ConexaoBD.Conexao;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCusto_alimento {
    private Conexao conexao;
    //private String sql;

    public DAOCusto_alimento() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public void cadastrarCusto_alimento(Custo_alimento custo_alimento) throws SQLException {
//Insere endereco no banco de dados
        String sql = "INSERT INTO custo_alimento (id_alimento, id_contas_pagar, qtd, valor) VALUES (" 
                + custo_alimento.getId_alimento() + "," + custo_alimento.getId_contas_pagar() + "," 
                + custo_alimento.getQtd() + "," + custo_alimento.getValor() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
}
    public Custo_alimento consultaCusto_alimento(int id_contas_pagar) throws SQLException {
        Custo_alimento custo_alimento = new Custo_alimento();
        String sql = "SELECT * FROM custo_alimento WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        custo_alimento.setId_alimento(rs.getInt("id_alimento"));
        custo_alimento.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        custo_alimento.setQtd(rs.getFloat("qtd"));
        custo_alimento.setValor(rs.getFloat("valor"));
        return custo_alimento;
    }
    
    public void atualizarCusto_alimento(int id_alimento, int id_contas_pagar, double qtd, double valor) throws SQLException {
        String sql = "UPDATE custo_alimento set qtd = " + qtd + ", valor = " + valor + " WHERE id_alimento = " + id_alimento 
                + " AND id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    } 
    
    public void deletarCusto_alimento(int id_contas_pagar) throws SQLException {
        String sql = "DELETE FROM custo_alimento WHERE id_contas_pagar = "  + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
}
