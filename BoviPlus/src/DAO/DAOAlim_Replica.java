package DAO;

import ConexaoBD.Conexao;
import Entidades.Alim_Replica;
import Entidades.Alimento;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAlim_Replica {
    
    private Conexao c;

    public DAOAlim_Replica() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, FileNotFoundException {
        c = new Conexao();
        c.abrirConexao();
    }
    
     public void cadastro(Alim_Replica a) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "INSERT INTO alim_replica VALUES (" + a.alim + ","
                + a.animal + "," + a.quant + ");";
        Statement stm = c.getConnection().createStatement();
        stm.execute(sql);
    }

    public void atualizarAlim(Alim_Replica a) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String ss = "UPDATE alim_replica set quant = " + a.quant + " WHERE alimento = " + a.alim + " and animal = "+a.animal+";";
        Statement stm = c.getConnection().createStatement();
        stm.execute(ss);
    }

    public void deletarAlim(Alim_Replica a) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String ss = "DELETE FROM alim_replica WHERE alimento = " + a.alim + "and animal = "+a.animal+";";
        c.stm.execute(ss);
    }

    public List<Alim_Replica> listarTodos() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Alim_Replica> est = new ArrayList<>();
        String sql = "SELECT * FROM alim_replica;";
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Alim_Replica a;
        while(rs.next()){
            a = new Alim_Replica(rs.getInt("alimento"), rs.getInt("animal"), rs.getDouble("quant"));
            est.add(a);
        }

        return est;
    }

    public List<Alim_Replica> pesquisaCustom(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<Alim_Replica> est = new ArrayList<>();
        Statement stm = c.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        Alim_Replica a;
        while(rs.next()){
            a = new Alim_Replica(rs.getInt("alimento"), rs.getInt("animal"), rs.getDouble("quant"));
            est.add(a);
        }

        return est;
    }    
    
}
