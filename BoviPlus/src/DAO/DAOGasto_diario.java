package DAO;

import ConexaoBD.Conexao;
import Entidades.Gasto_diario;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOGasto_diario {

    public Conexao c;

    public DAOGasto_diario() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        c = new Conexao();
        c.abrirConexao();
    }

    public void cadastrarGasto(Gasto_diario g) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "INSERT INTO gasto_diario (id_alimento, dia, valor) VALUES (" + g.getId_alimento() + ",'"
                + g.getDia() + "'," + g.getValor() + ");";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql);
    }

    public void atualizarGasto(Gasto_diario a) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "UPDATE gasto_diario set valor = " + a.getValor() + " WHERE id_alimento = " + a.getId_alimento() + " and dia = '" + a.getDia() + "';";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql);
    }

    public List<Gasto_diario> listarTodosporAlimento(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Gasto_diario> est = new ArrayList<>();
        String sql = "SELECT * FROM gasto_diario where id_alimento = " + id + ";";
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Gasto_diario a;
        while (rs.next()) {
            a = new Gasto_diario(rs.getInt("id_alimento"), rs.getDouble("valor"), rs.getString("dia"));
            est.add(a);
        }

        return est;
    }

    public Gasto_diario consultaGasto(int id, String data) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        try {
            String sql = "SELECT * FROM gasto_diario where id_alimento = " + id + " and dia = '" + data + "';";
            Statement stm = c.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            Gasto_diario a;
            rs.next();
            a = new Gasto_diario(rs.getInt("id_alimento"), rs.getDouble("valor"), rs.getString("dia"));
            return a;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Gasto_diario> listarTodosporData(String d1, String d2) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Gasto_diario> est = new ArrayList<>();
        String sql = "SELECT * FROM gasto_diario where dia between '" + d1 + "' and '" + d2 + "';";
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Gasto_diario a;
        while (rs.next()) {
            a = new Gasto_diario(rs.getInt("id_alimento"), rs.getDouble("valor"), rs.getString("dia"));
            est.add(a);
        }

        return est;
    }

    public List<Gasto_diario> pesquisaCustom(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Gasto_diario> est = new ArrayList<>();
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Gasto_diario a;
        while (rs.next()) {
            a = new Gasto_diario(rs.getInt("id_alimento"), rs.getDouble("valor"), rs.getString("dia"));
            est.add(a);
        }

        return est;
    }

}
