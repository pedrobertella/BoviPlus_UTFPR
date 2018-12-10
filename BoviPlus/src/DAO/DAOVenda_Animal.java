package DAO;

import Entidades.Venda_Animal;
import ConexaoBD.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOVenda_Animal {
    private Conexao conexao;

    public DAOVenda_Animal() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
    public int cadastrar_animal_venda(Venda_Animal venda_animal) throws SQLException {
//Insere contas a receber no banco de dados
        String sql = "INSERT INTO venda_animal (id_venda, id_animal, valor, data_venda) VALUES (" + venda_animal.getId_venda()+ ",'" 
                + venda_animal.getId_animal() + ",'" + venda_animal.getValor() + ",'" + venda_animal.getData_venda() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
//Consulta e retorna o codigo da conta a receber
        sql = "SELECT * FROM venda_animal WHERE id_venda = " + venda_animal.getId_venda() + " AND id_animal = " + venda_animal.getId_animal()
                + " AND valor = " + venda_animal.getValor() + " AND data_venda = '" + venda_animal.getData_venda() + "');";
                
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_venda");
        //Recebe como parametro um codigo e consulta a conta a receber que tem esse codigo
    }
    
    public Venda_Animal consultar_venda_animal(int id_venda) throws SQLException {
        Venda_Animal venda_animal = new Venda_Animal();
        String sql = "SELECT * FROM venda_animal WHERE id_venda = " + id_venda + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        venda_animal.setId_venda(rs.getInt("id_venda"));
        venda_animal.setId_animal(rs.getInt("id_animal"));
        venda_animal.setValor(rs.getDouble("valor"));
        venda_animal.setData_venda(rs.getString("data_venda"));

        return venda_animal;
    }  
    
    public void atualizarVenda_Animal(int id_venda, String id_animal, String valor, String data_venda) throws SQLException {
        String sql = "UPDATE venda_animal set id_animal = " + id_animal + ", valor = " + valor + ", data_venda = '" + data_venda 
                + "' WHERE id_venda = " + id_venda + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarVenda_Animal(int id_venda) throws SQLException {
        String sql = "DELETE FROM venda_animal WHERE id_venda = " + id_venda + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    } 
}