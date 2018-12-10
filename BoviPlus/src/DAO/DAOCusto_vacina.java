package DAO;

import Entidades.Custo_vacina;

import ConexaoBD.Conexao;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCusto_vacina {
    private Conexao conexao;
    //private String sql;

    public DAOCusto_vacina() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public void cadastrarCusto_vacina(Custo_vacina custo_vacina) throws SQLException {
//Insere endereco no banco de dados
        String sql = "INSERT INTO custo_vacina (id_vacina, id_contas_pagar, qtd, valor) VALUES (" 
                + custo_vacina.getId_vacina() + "," + custo_vacina.getId_contas_pagar() + "," 
                + custo_vacina.getQtd() + "," + custo_vacina.getValor() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);

}
    public Custo_vacina consultaCusto_vacina(int id_contas_pagar) throws SQLException {
        Custo_vacina custo_vacina = new Custo_vacina();
        String sql = "SELECT * FROM custo_vacina WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        custo_vacina.setId_vacina(rs.getInt("id_vacina"));
        custo_vacina.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        custo_vacina.setQtd(rs.getInt("qtd"));
        custo_vacina.setValor(rs.getDouble("valor"));
        return custo_vacina;
    }
    
    public void atualizarCusto_vacina(int id_vacina, int id_contas_pagar, int qtd, double valor) throws SQLException {
        String sql = "UPDATE custo_vacina set qtd = " + qtd + ", valor = " + valor + " WHERE id_contas_pagar = " + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    } 
    
    public void deletarCusto_vacina(int id_contas_pagar) throws SQLException {
        String sql = "DELETE FROM custo_vacina where id_contas_pagar = "  + id_contas_pagar + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
}
