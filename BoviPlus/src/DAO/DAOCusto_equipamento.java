package DAO;

import Entidades.Custo_equipamento;

import ConexaoBD.Conexao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCusto_equipamento {
    private Conexao conexao;
    //private String sql;

    public DAOCusto_equipamento() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public int cadastrarCusto_equipamento(Custo_equipamento custo_equipamento) throws SQLException {
//Insere endereco no banco de dados
        String sql = "INSERT INTO custo_equipamento (id_equipamento, id_contas_pagar, qtd, valor) VALUES (" 
                + custo_equipamento.getId_equipamento() + "," + custo_equipamento.getId_contas_pagar() + "," 
                + custo_equipamento.getQtd() + "," + custo_equipamento.getValor() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
//Consulta e retorna o codigo do endereco inserido
        sql = "SELECT * FROM custo_equipamento WHERE id_equipamento = " + custo_equipamento.getId_equipamento() 
                + " AND id_contas_pagar = " + custo_equipamento.getId_contas_pagar() + " AND qtd = " 
                + custo_equipamento.getQtd() + " AND valor = " + custo_equipamento.getValor() + ");";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_equipamento");
        //Recebe como parametro um codigo e consulta o endereco que tem esse codigo
}
    public Custo_equipamento consultaCusto_equipamento(int id_equipamento, int id_contas_pagar) throws SQLException {
        Custo_equipamento custo_equipamento = new Custo_equipamento();
        String sql = "SELECT * FROM custo_equipamento WHERE id_equipamento = " + id_equipamento + " AND id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        custo_equipamento.setId_equipamento(rs.getInt("id_equipamento"));
        custo_equipamento.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        custo_equipamento.setQtd(rs.getInt("qtd"));
        custo_equipamento.setValor(rs.getDouble("valor"));
        return custo_equipamento;
    }
    
    public void atualizarCusto_equipamento(int id_equipamento, int id_contas_pagar, int qtd, double valor) throws SQLException {
        String sql = "UPDATE custo_equipamento set qtd = " + qtd + ", valor = " + valor + " WHERE id_equipamento = " + id_equipamento 
                + " AND id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarCusto_equipamento(int id_equipamento, int id_contas_pagar) throws SQLException {
        String sql = "DELETE FROM custo_equipamento WHERE id_equipamento = " + id_equipamento 
                + " AND id_contas_pagar = "  + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
}
