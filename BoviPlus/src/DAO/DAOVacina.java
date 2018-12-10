package DAO;

import Entidades.Vacina;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOVacina {

    private Conexao conexao;

    public DAOVacina() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }

    public void cadastrarVacina(Vacina vacina) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Statement stm = conexao.getConnection().createStatement();
        if (vacina.getPeriodica() == true) {
            System.out.println("if periodica");
            String sql = "INSERT INTO vacina (id_vacina, nome, indicacao, contra_indc, periodica, campanha, mes, cat_animais) VALUES (" + vacina.getId_vacina()
                    + ",'" + vacina.getNome() + "','" + vacina.getIndicacao() + "','" + vacina.getContra_indc() + "'," + vacina.getPeriodica()
                    + ",'" + vacina.getCampanha() + "','" + vacina.getMes() + "','" + vacina.getCat_animais() + "');";

            stm.execute(sql);
        } else {
            String sql = "INSERT INTO vacina (id_vacina, nome, indicacao, contra_indc, periodica) VALUES (" + vacina.getId_vacina()
                    + ",'" + vacina.getNome() + "','" + vacina.getIndicacao() + "','" + vacina.getContra_indc() + "'," + vacina.getPeriodica() + ");";

            stm.execute(sql);
        }
    }

    public Vacina consultaVacina(int id_vacina) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Vacina vacina = new Vacina();
        String sql = "SELECT * FROM vacina WHERE id_vacina = " + id_vacina + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        vacina.setId_vacina(rs.getInt("id_vacina"));
        vacina.setNome(rs.getString("nome"));
        vacina.setIndicacao(rs.getString("indicacao"));
        vacina.setContra_indc(rs.getString("contra_indc"));
        vacina.setPeriodica(rs.getBoolean("periodica"));
        vacina.setCampanha(rs.getString("campanha"));
        vacina.setMes(rs.getString("mes"));
        vacina.setCat_animais(rs.getString("cat_animais"));
        return vacina;
    }

    public List<Vacina> pesquisaCustom(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Vacina vacina;
        List<Vacina> vc = new ArrayList<>();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            vacina = new Vacina();
            vacina.setId_vacina(rs.getInt("id_vacina"));
            vacina.setNome(rs.getString("nome"));
            vacina.setIndicacao(rs.getString("indicacao"));
            vacina.setContra_indc(rs.getString("contra_indc"));
            vacina.setPeriodica(rs.getBoolean("periodica"));
            vacina.setCampanha(rs.getString("campanha"));
            vacina.setMes(rs.getString("mes"));
            vacina.setCat_animais(rs.getString("cat_animais"));
            vc.add(vacina);
        }

        return vc;
    }

    public void atualizarVacina(int id_vacina, String nome, String indicacao, String contra_indc, Boolean periodica, String campanha, String mes, String cat_animais) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //System.out.println("entrou na dao");
        conexao.abrirConexao();
        String sql = "UPDATE vacina set nome = '" + nome + "', indicacao = '" + indicacao + "', contra_indc = '" + contra_indc + "', periodica = " + periodica
                + ", campanha = '" + campanha + "', mes = '" + mes + "', cat_animais = '" + cat_animais + "' WHERE id_vacina = " + id_vacina + ";";
        //System.out.println("pré-execução do UPDATE na dao");
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarVacina(int id_vacina) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "DELETE FROM vacina WHERE id_vacina = " + id_vacina + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "SELECT MAX(id_vacina) as idn FROM vacina;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int asdasd = rs.getInt("idn");
        return (asdasd + 1);
    }
    
    public List<Vacina> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Vacina> vacinas = new ArrayList<>();
        try {
            Statement stm = conexao.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Vacina v = new Vacina();
                v.setId_vacina(rs.getInt("id_vacina"));
                v.setNome(rs.getString("nome"));
                v.setIndicacao(rs.getString("indicacao"));
                v.setContra_indc(rs.getString("contra_indc"));
                v.setPeriodica(rs.getBoolean("periodica"));
                v.setCampanha(rs.getString("campanha"));
                v.setMes(rs.getString("mes"));
                v.setCat_animais(rs.getString("cat_animais"));

                vacinas.add(v);

            }
            stm.close();
            rs.close();
            conexao.connection.close();
        } catch (SQLException ex) {
            return null;
        }

        return vacinas;
    }
}
