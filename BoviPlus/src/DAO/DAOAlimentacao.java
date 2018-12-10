package DAO;


import Entidades.Alimentacao;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAlimentacao {
    private Conexao conexao;
    //private String sql;

    public DAOAlimentacao() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }

    public void cadastrarAlimentacao_individual(Alimentacao alimentacao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        
        
        String sql = "INSERT INTO alimentacao (id_alimento, id_animal, qtd, data_alimentacao) VALUES (" + alimentacao.getId_alimento() 
                + "," + alimentacao.getId_animal() + "," + alimentacao.getQtd() + ",'" + alimentacao.getData_alimentacao() + "');";
        
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
       
    }
    public void cadastrarAlimentacao_lote(Alimentacao alimentacao, double qtd, int lote) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();

        String sql2 = "SELECT id_animal FROM animal WHERE lote = " + lote + ";";
                
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql2);
        
        while(rs.next()) {
            
            String sql = "INSERT INTO alimentacao (id_alimento, id_animal, qtd, data_alimentacao) VALUES (" + alimentacao.getId_alimento() 
                + "," + rs.getInt("id_animal") + "," + alimentacao.getQtd() + ",'" + alimentacao.getData_alimentacao() + "');";
            
            stm.execute(sql);
        }
    }
    
    public Alimentacao consultaAlimentacao(int id_alimento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Alimentacao alimentacao = new Alimentacao();
        String sql = "SELECT * FROM alimentacao WHERE id_alimento = " + id_alimento + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        alimentacao.setId_alimento(rs.getInt("id_alimento"));
        alimentacao.setId_animal(rs.getInt("id_animal"));
        alimentacao.setQtd(rs.getDouble("qtd"));
        alimentacao.setData_alimentacao(rs.getString("data_alimentacao"));
        return alimentacao;
    }
    
    public Alimentacao consultaAlimentacao2(String nome) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Alimentacao alimentacao = new Alimentacao();
        String sql = "SELECT * FROM alimento WHERE nome  ilike= '" + nome + "';";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        alimentacao.setId_alimento(rs.getInt("id_alimento"));
        alimentacao.setId_animal(rs.getInt("id_animal"));
        alimentacao.setQtd(rs.getDouble("qtd"));
        alimentacao.setData_alimentacao(rs.getString("data_alimentacao"));
        return alimentacao;
    }
    
    public void atualizarAlimentacao(int id_alimento, int id_animal, double qtd, String data_alimentacao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "UPDATE alimentacao set qtd = " + qtd + " WHERE id_alimento = " + id_alimento 
                + " AND id_animal = " + id_animal + " AND data = '" + data_alimentacao + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.executeQuery(sql);                
    } 
    
    public void deletarAlimentacao(int id_alimento, int id_animal, String data_alimentacao) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "DELETE FROM alimentacao WHERE id_alimento = " + id_alimento 
                + " AND id_animal = "  + id_animal + " AND data = '" + data_alimentacao + "';";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);                
    }
    
    public List<Alimentacao> listarTodos() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        conexao.abrirConexao();
        List<Alimentacao> alimentacao = new ArrayList<>();
        
        String sql = "SELECT * FROM alimentacao";
        
        try{
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            Alimentacao al = new Alimentacao();
            al.setId_animal(rs.getInt("id_animal"));
            al.setId_alimento(rs.getInt("id_alimento"));
            al.setData_alimentacao(rs.getString("data_alimentacao"));
            al.setQtd(rs.getDouble("qtd"));
            alimentacao.add(al);
        }
        stm.close();
        rs.close();
        } catch(SQLException e){
            System.out.println("ERRO!");
            return null;
        }
        return alimentacao;
    }
    
    public List<Alimentacao> pesquisaCustom(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        conexao.abrirConexao();
        List<Alimentacao> alimentacao = new ArrayList<>();        
        try{
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            Alimentacao al = new Alimentacao();
            al.setId_animal(rs.getInt("id_animal"));
            al.setId_alimento(rs.getInt("id_alimento"));
            al.setData_alimentacao(rs.getString("data_alimentacao"));
            al.setQtd(rs.getDouble("qtd"));
            alimentacao.add(al);
        }
        stm.close();
        rs.close();
        } catch(SQLException e){
            System.out.println("ERRO!");
        }
        return alimentacao;
    }
    
    public String ultimaData() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao.abrirConexao();
        String sql = "select max(data_alimentacao) as ultimadata from alimentacao;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getString("ultimadata");
    }
    
    public String ultimaData(int alim) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao.abrirConexao();
        String sql = "select max(data_alimentacao) as ultimadata from alimentacao where id_alimento = "+alim+";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getString("ultimadata");
    }
    
    public List<Alimentacao> pesquisarAlimentacao(String id_alimento) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Alimentacao> alimentacao = new ArrayList<>();
        String sql = "SELECT * FROM ALIMENTACAO ali, ALIMENTO almto WHERE ali.id_alimento = almto.id_alimento AND almto.id_alimento ='" + id_alimento + "' ORDER BY ali.data_alimentacao;";
        try {
            Statement stm = conexao.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            
            while(rs.next()){
            Alimentacao al = new Alimentacao();
            al.setId_animal(rs.getInt("id_animal"));
            al.setId_alimento(rs.getInt("id_alimento"));
            al.setData_alimentacao(rs.getString("data_alimentacao"));
            al.setQtd(rs.getDouble("qtd"));
            alimentacao.add(al);
                
            }
            stm.close();
            rs.close();
            conexao.connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro! Lista não retornada.");
            return null;
        }
        
        return alimentacao;
    }
     
}