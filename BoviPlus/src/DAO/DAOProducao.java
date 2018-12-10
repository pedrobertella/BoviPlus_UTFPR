package DAO;

import Entidades.Producao;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOProducao {

    private Conexao conexao;
    //private String sql;

    public DAOProducao() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public void cadastrarProducao(Producao producao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "INSERT INTO producao (id_vaca, data_producao, litragem) VALUES (" + producao.getId_vaca() + ",'"
                + producao.getData_producao() + "'," + producao.getLitragem() + ");";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);

    }

    public Producao consultaProducao(int id_vaca, String data) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        System.out.println(id_vaca);
        System.out.println(data);
        Producao producao = new Producao();
        String sql = "SELECT * FROM producao WHERE id_vaca = " + id_vaca + " and data_producao = '" + data + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        producao.setId_vaca(rs.getInt("id_vaca"));
        producao.setData_producao(rs.getString("data_producao"));
        producao.setLitragem(rs.getDouble("litragem"));
        return producao;
    }
    
    public boolean consultaProducaoDia(String data) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "SELECT * FROM producao WHERE data_producao = '" + data + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        if(rs.next()){
            return true;
        }
        else{
            return false;
        }
    }

    public void atualizarProducao(int id_vaca, String data_producao, double litragem) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "UPDATE producao set litragem = " + litragem
                + " WHERE id_vaca = " + id_vaca + " AND data_producao = '" + data_producao + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarProducao(int id_vaca, String data_producao) throws SQLException {
        String sql = "DELETE FROM producao WHERE id_vaca = " + id_vaca + " AND data_producao = '"
                + data_producao + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }
    
    public List<Producao> buscapordataeanimal(int idanimal, String data1, String data2) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
        conexao.abrirConexao();
        String sql = "SELECT litragem, data_producao, id_vaca from producao where id_vaca = "+ idanimal+ " and data_producao between '" + data1 + "' and '" + data2 + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Producao x;
        List<Producao> k = new ArrayList<>();
        while(rs.next()){
            x = new Producao();
            x.setData_producao(rs.getString("data_producao"));
            x.setLitragem(rs.getDouble("litragem"));
            k.add(x);
        }
        return k;
    }
    
    
    public List<Producao> pesquisaCustom(String sql) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
        conexao.abrirConexao();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Producao x;
        List<Producao> k = new ArrayList<>();
        while(rs.next()){
            x = new Producao();
            x.setData_producao(rs.getString("data_producao"));
            x.setLitragem(rs.getDouble("lit"));
            k.add(x);
        }
        return k;
    }
    
    public List<Producao> buscaporanimal(int idanimal) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
        conexao.abrirConexao();
        String sql = "SELECT litragem, data_producao, id_vaca from producao where id_vaca = "+ idanimal+ ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Producao x;
        List<Producao> k = new ArrayList<>();
        while(rs.next()){
            x = new Producao();
            x.setData_producao(rs.getString("data_producao"));
            x.setLitragem(rs.getDouble("litragem"));
            k.add(x);
        }
        return k;
    }
    
    public double mediaGeral() throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao.abrirConexao();
        String sql = "select sum(litragem)/count(*) as mediageral from producao;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Double media = 0.0;
        
        if(rs.next()){
            media  = rs.getDouble("mediageral");
        }
        return Math.round(media * 100)/100;
    }
  
    public double mediaAnimal(int id) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao.abrirConexao();
        String sql = "select sum(litragem)/count(*) as media from producao where id_vaca = "+ id+";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Double media = 0.0;
        
        if(rs.next()){
            media  = rs.getDouble("media");
        }
        return Math.round(media * 100)/100;
    }
    
    public double producaoPeriodo(String data1, String data2) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        conexao.abrirConexao();
        String sql = "select sum(litragem) as sominha from producao where data_producao between '"+data1+"' and '"+data2+"';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Double litros = 0.0;
        
        if(rs.next()){
            litros = rs.getDouble("sominha");
        }
        return litros;
    }
}
