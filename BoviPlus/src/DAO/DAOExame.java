package DAO;

import Entidades.Exame;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOExame {

    private Conexao conexao_exame;

    public DAOExame() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao_exame = new Conexao();
    }

    public void cadastrar_exame(Exame exame) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        conexao_exame.abrirConexao();
        String sql_exame = "INSERT INTO exame (id_exame, nome, descricao, data_exame, categoria, intervalo, id_animal) VALUES (" + exame.getId_exame() + ",'"
                + exame.getNome() + "','" + exame.getDescricao() + "','" + exame.getData_exame() + "','" + exame.getCategoria() + "'," + exame.getIntervalo() + "," + exame.getId_animal() + ");";

        Statement stm = conexao_exame.getConnection().createStatement();
        stm.execute(sql_exame);
    }

    public Exame consultar_exame(int id_exame) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_exame.abrirConexao();
        Exame exame = new Exame();

        String sql = "SELECT * FROM exame WHERE id_exame = " + id_exame + ";";
        conexao_exame.rs = conexao_exame.stm.executeQuery(sql);
        conexao_exame.rs.next();

        exame.setId_exame(conexao_exame.rs.getInt("id_exame"));
        exame.setNome(conexao_exame.rs.getString("nome"));
        exame.setDescricao(conexao_exame.rs.getString("descricao"));
        exame.setData_exame(conexao_exame.rs.getString("data_exame"));
        exame.setCategoria(conexao_exame.rs.getString("categoria"));
        exame.setIntervalo(conexao_exame.rs.getInt("intervalo"));
        exame.setId_animal(conexao_exame.rs.getInt("id_animal"));
        return exame;
    }

    public List<Exame> pesquisaCustom(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_exame.abrirConexao();
        Exame exame;
        List<Exame> ll = new ArrayList<>();
        conexao_exame.rs = conexao_exame.stm.executeQuery(sql);
        while (conexao_exame.rs.next()) {
            exame = new Exame();
            exame.setId_exame(conexao_exame.rs.getInt("id_exame"));
            exame.setNome(conexao_exame.rs.getString("nome"));
            exame.setDescricao(conexao_exame.rs.getString("descricao"));
            exame.setData_exame(conexao_exame.rs.getString("data_exame"));
            exame.setCategoria(conexao_exame.rs.getString("categoria"));
            exame.setIntervalo(conexao_exame.rs.getInt("intervalo"));
            exame.setId_animal(conexao_exame.rs.getInt("id_animal"));
            ll.add(exame);
        }

        return ll;
    }

    public void atualizarExame(Exame exame) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException  {
        conexao_exame.abrirConexao();
        String sql = "UPDATE exame set nome = '" + exame.getNome() 
                + "', descricao = '" + exame.getDescricao() + "', data_exame = '" + exame.getData_exame()
                + "', categoria = '" + exame.getCategoria() + "', intervalo = " + exame.getIntervalo() 
                + ", id_animal = " + exame.getId_animal() + " WHERE id_exame = " + exame.getId_exame() + ";";
        System.out.println("ENTROUATUALIZAR");
        Statement stm = conexao_exame.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarExame(int id_exame) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_exame.abrirConexao();
        String sql_exame = "DELETE FROM exame WHERE id_exame = " + id_exame + ";";
        conexao_exame.stm.execute(sql_exame);
        System.out.println(id_exame);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_exame.abrirConexao();
        String sql = "SELECT MAX(id_exame) as idn FROM exame;";
        Statement stm = conexao_exame.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int id = rs.getInt("idn");
        return (id + 1);
    }
    
    public List<Exame> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_exame.abrirConexao();
        List<Exame> exames = new ArrayList<>();
        try {
            Statement stm = conexao_exame.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Exame m = new Exame();
            m.setId_exame(rs.getInt("id_exame"));
            m.setNome(rs.getString("nome"));
            m.setDescricao(rs.getString("descricao"));
            m.setData_exame(rs.getString("data_exame"));
            m.setCategoria(rs.getString("categoria"));
            m.setIntervalo(rs.getInt("intervalo"));
            m.setId_animal(rs.getInt("id_animal"));

                exames.add(m);

            }
            stm.close();
            rs.close();
            conexao_exame.connection.close();
        } catch (SQLException ex) {
            return null;
        }

        return exames;
    }
}
