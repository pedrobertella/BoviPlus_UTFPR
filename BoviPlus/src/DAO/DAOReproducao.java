package DAO;

import Entidades.Reproducao;

import ConexaoBD.Conexao;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOReproducao {

    private Conexao conexao;
    //private String sql;

    public DAOReproducao() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }
//Esse metodo vai cadastrar um endereco e vai retornar o cod_endereco

    public List<Reproducao> listaTodos() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Reproducao> lista = new ArrayList<>();
        String sql = "SELECT * FROM reproducao;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Reproducao r;
        while (rs.next()) {
            r = new Reproducao();
            r.setId_gestacao(rs.getInt("id_gestacao"));
            r.setId_mae(rs.getInt("id_mae"));
            r.setId_pai(rs.getInt("id_pai"));
            r.setPrevisao(rs.getString("previsao"));
            r.setData_inseminacao(rs.getString("data_inseminacao"));
            lista.add(r);
        }
        return lista;
    }

    public void cadastrarReproducao(Reproducao reproducao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
//Insere endereco no banco de dados
        String sql = "INSERT INTO reproducao (id_gestacao, id_mae, id_pai, previsao, data_inseminacao) VALUES ("
                + reproducao.getId_gestacao() + "," + reproducao.getId_mae() + "," + reproducao.getId_pai() + ",'"
                + reproducao.getPrevisao() + "','" + reproducao.getData_inseminacao() + "');";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
//Consulta e retorna o codigo do endereco inserido
        /*sql = "SELECT * FROM reproducao WHERE id_gestacao = " + reproducao.getId_gestacao()  + " AND id_mae = " 
                + reproducao.getId_mae() + " AND id_pai = " + reproducao.getId_pai() + " AND previsao = '" 
                + reproducao.getPrevisao() + "' AND data_inseminacao = '" + reproducao.getData_inseminacao() + "');";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();  
        return rs.getInt("id_gestacao");*/
        //Recebe como parametro um codigo e consulta o endereco que tem esse codigo
    }

    public Reproducao consultaReproducao(int id_gestacao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Reproducao reproducao = new Reproducao();
        String sql = "SELECT * FROM reproducao WHERE id_gestacao = " + id_gestacao + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        reproducao.setId_gestacao(rs.getInt("id_gestacao"));
        reproducao.setId_mae(rs.getInt("id_mae"));
        reproducao.setId_pai(rs.getInt("id_pai"));
        reproducao.setPrevisao(rs.getString("previsao"));
        reproducao.setData_inseminacao(rs.getString("data_inseminacao"));
        return reproducao;
    }
    
    public boolean verificaInseminacaoVaca(int id_animal, LocalDate data) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "SELECT * FROM reproducao WHERE id_mae = " + id_animal + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            if(LocalDate.parse(rs.getString("previsao")).isAfter(data)){
                return true;
            }
        }
        return false;
    }

    public void atualizarReproducao(int id_gestacao, int id_mae, int id_pai, String previsao, String data_inseminacao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "UPDATE reproducao set id_mae = " + id_mae + ", id_pai = " + id_pai + ", previsao = '" + previsao
                + "', data_inseminacao = '" + data_inseminacao + "' WHERE id_gestacao = " + id_gestacao + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarReproducao(int id_gestacao) throws SQLException, FileNotFoundException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        conexao.abrirConexao();
        String sql = "DELETE FROM reproducao WHERE id_gestacao = " + id_gestacao + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery("SELECT MAX(id_gestacao) as idn FROM reproducao;");
        rs.next();
        int o = rs.getInt("idn") + 1;
        return o;
    }

    public List<Reproducao> proximosNacimentos(String data1, String data2) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Reproducao> li = new ArrayList<>();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM reproducao where previsao between '" + data1 + "' and '" + data2 + "' order by previsao;");
        Reproducao r;
        while (rs.next()) {
            r = new Reproducao();
            r.setId_mae(rs.getInt("id_mae"));
            r.setId_pai(rs.getInt("id_pai"));
            r.setId_gestacao(rs.getInt("id_gestacao"));
            r.setData_inseminacao(rs.getString("data_inseminacao"));
            r.setPrevisao(rs.getString("previsao"));
            li.add(r);
        }

        return li;
    }

    public List<Reproducao> proximosSecagem(String data1, String data2) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Reproducao> li = new ArrayList<>();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM reproducao r where previsao between '" + data1 + "' and '" + data2 + "' and id_mae in(\n"
                + "select id_animal from animal where lactacao = true\n"
                + "\n"
                + ") order by previsao;");
        Reproducao r;
        while (rs.next()) {
            r = new Reproducao();
            r.setId_mae(rs.getInt("id_mae"));
            r.setId_pai(rs.getInt("id_pai"));
            r.setId_gestacao(rs.getInt("id_gestacao"));
            r.setData_inseminacao(rs.getString("data_inseminacao"));
            r.setPrevisao(rs.getString("previsao"));
            li.add(r);
        }

        return li;
    }

}
