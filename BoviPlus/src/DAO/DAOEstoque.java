package DAO;

import ConexaoBD.Conexao;
import Entidades.Estoque;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOEstoque {

    public Conexao c;

    public DAOEstoque() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        c = new Conexao();
        c.abrirConexao();
    }

    public void cadastrarEstoque(Estoque e) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "INSERT INTO estoque VALUES (" + e.getId_movimento() + "," + e.getId_alimento() + "," + e.getQuant() + "," + e.getValor_uni() + ", '" + e.getData_compra() + "');";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql);
    }

    public Estoque consultaEstoque(int id_movimento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM estoque WHERE id_alimento = " + id_movimento + ";";
        c.rs = c.stm.executeQuery(sql);
        c.rs.next();
        Estoque e = new Estoque(c.rs.getInt("id_movimento"), c.rs.getInt("id_alimentto"), c.rs.getDouble("quant"), c.rs.getDouble("valor_uni"), c.rs.getString("data_compra"));
        return e;
    }

    public void atualizarEstoque(Estoque a) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "UPDATE estoque set quant = " + a.getQuant() + " WHERE id_movimento = " + a.getId_movimento() + ";";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarMovimento(int id) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "DELETE FROM estoque WHERE id_movimento = " + id + ";";
        c.stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT MAX(id_movimento) as idn FROM estoque;";
        c.rs = c.stm.executeQuery(sql);
        c.rs.next();
        int id = c.rs.getInt("idn");
        return (id + 1);
    }

    public List<Estoque> listarPorAlimento(int alimento) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Estoque> est = new ArrayList<>();
        String sql = "SELECT * FROM estoque where id_alimento = " + alimento + " order by data_compra asc;";
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Estoque a;
        while (rs.next()) {
            a = new Estoque(rs.getInt("id_movimento"), rs.getInt("id_alimento"), rs.getDouble("quant"), rs.getDouble("valor_uni"), rs.getString("data_compra"));
            est.add(a);
        }

        return est;
    }

    public double somaPorAlimento(int alimento) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        double soma = 0;
        try {
            String sql = "SELECT sum(quant) as sominha FROM estoque where id_alimento = " + alimento + ";";
            Statement stm = c.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.next();
            soma = rs.getDouble("sominha");
        } catch (Exception e) {
            System.out.println("SEM ESTOQUE");
        }

        return soma;
    }

    public List<Estoque> pesquisaCustom(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Estoque> est = new ArrayList<>();
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Estoque a;
        while (rs.next()) {
            a = new Estoque(c.rs.getInt("id_movimento"), c.rs.getInt("id_alimentto"), c.rs.getDouble("quant"), c.rs.getDouble("valor_uni"), c.rs.getString("data_compra"));
            est.add(a);
        }

        return est;
    }

}
