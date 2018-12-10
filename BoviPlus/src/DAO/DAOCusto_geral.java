package DAO;

import Entidades.Custo_alimento;

import ConexaoBD.Conexao;
import Entidades.Custo_geral;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCusto_geral {
    private Conexao conexao;

    public DAOCusto_geral() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        conexao = new Conexao();
        conexao.abrirConexao();
    }
    
    public void cadastrarCusto_geral(Custo_geral c) throws SQLException {
        String sql = "INSERT INTO custo_geral (id_contas_pagar, qtd, valor, descricao) VALUES ("+c.getId_contas_pagar()+"," + c.getQtd() + "," 
                + c.getValor() + ",'" + c.getDescricao() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
}
    public Custo_geral consultaCusto_geral(int id) throws SQLException {
        Custo_geral c = new Custo_geral();
        String sql = "SELECT * FROM custo_geral WHERE id_contas_pagar = " + id + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        c.setId_geral(rs.getInt("id_geral"));
        c.setId_contas_pagar(rs.getInt("id_contas_pagar"));
        c.setQtd(rs.getInt("qtd"));
        c.setValor(rs.getDouble("valor"));
        c.setDescricao(rs.getString("descricao"));
        return c;
    }
    
    public void atualizarCusto_geral(int id, int idcp, int qtd, double valor, String desc) throws SQLException {
        String sql = "UPDATE custo_geral set qtd = " + qtd + ", valor = " + valor + ", id_contas_pagar = "+idcp+", descricao = '" +desc+ "' WHERE id_geral = " + id +";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    } 
    
    public void deletarCusto_geral(int id) throws SQLException {
        String sql = "DELETE FROM custo_geral WHERE id_contas_pagar = " + id + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
    
    public int nextID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT MAX(id_geral) as idn FROM custo_geral;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getInt("idn") + 1;
    } 
}
