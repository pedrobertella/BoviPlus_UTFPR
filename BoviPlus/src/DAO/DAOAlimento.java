package DAO;

import Entidades.Alimento;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAlimento {

    private Conexao c;

    public DAOAlimento() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        c = new Conexao();
        c.abrirConexao();
    }

    public void cadastrarAlimento(Alimento alimento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "INSERT INTO alimento VALUES (" + alimento.getId() + ",'"
                + alimento.getNome() + "'," + alimento.getTipo() + "," + alimento.getUnid() + ");";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql);
    }

    public Alimento consultaAlimento(int id_alimento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM alimento WHERE id_alimento = " + id_alimento + ";";
        c.rs = c.stm.executeQuery(sql);
        c.rs.next();
        Alimento alimento = new Alimento(c.rs.getInt("id_alimento"), c.rs.getString("nome"), c.rs.getInt("id_tipo_alimento"), c.rs.getInt("id_undmdd"));
        return alimento;
    }

    public void atualizarAlimento(Alimento a) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql_alimento = "UPDATE alimento set nome = '" 
                + a.getNome() + "', id_tipo_alimento = " + a.getTipo() + ", id_undmdd = " 
                + a.getUnid() + " WHERE id_alimento = " + a.getId() + ";";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql_alimento);
    }

    public void deletarAlimento(int id_alimento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql_alimento = "DELETE FROM alimento WHERE id_alimento = " + id_alimento + ";";
        c.stm.execute(sql_alimento);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT MAX(id_alimento) as idn FROM alimento;";
        c.rs = c.stm.executeQuery(sql);
        c.rs.next();
        int id = c.rs.getInt("idn");
        return (id + 1);
    }

    public List<Alimento> listarTodos() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Alimento> est = new ArrayList<>();
        String sql = "SELECT * FROM ALIMENTO;";
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Alimento a;
        while(rs.next()){
            a = new Alimento(rs.getInt("id_alimento"), rs.getString("nome"), rs.getInt("id_tipo_alimento"), rs.getInt("id_undmdd"));
            est.add(a);
        }

        return est;
    }

    public List<Alimento> pesquisaCustom(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Alimento> est = new ArrayList<>();
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Alimento a;
        while(rs.next()){
            a = new Alimento(rs.getInt("id_alimento"), rs.getString("nome"), rs.getInt("id_tipo_alimento"), rs.getInt("id_undmdd"));
            est.add(a);
        }

        return est;
    }    
}
