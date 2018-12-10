package DAO;

import Entidades.Medicamento;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOMedicamento {

    private Conexao conexao;

    public DAOMedicamento() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao = new Conexao();
    }

    public void cadastrarMedicamento(Medicamento medicamento) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Statement stm = conexao.getConnection().createStatement();
        String sql = "INSERT INTO medicamento (id_med, nome, indicacao, contra_indc) VALUES (" + medicamento.getId_med()
                    + ",'" + medicamento.getNome() + "','" + medicamento.getDescricao() + "','" + medicamento.getContra_indc() + "');";

        stm.execute(sql);
        
    }

    public Medicamento consultaMedicamento(int id_med) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Medicamento medicamento = new Medicamento();
        String sql = "SELECT * FROM medicamento WHERE id_med = " + id_med + ";";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        medicamento.setId_med(rs.getInt("id_med"));
        medicamento.setNome(rs.getString("nome"));
        medicamento.setIndicacao(rs.getString("indicacao"));
        medicamento.setContra_indc(rs.getString("contra_indc"));
        return medicamento;
    }

    public List<Medicamento> pesquisaCustom(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        Medicamento medicamento;
        List<Medicamento> vc = new ArrayList<>();
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            medicamento = new Medicamento();
            medicamento.setId_med(rs.getInt("id_med"));
            medicamento.setNome(rs.getString("nome"));
            medicamento.setIndicacao(rs.getString("indicacao"));
            medicamento.setContra_indc(rs.getString("contra_indc"));
            vc.add(medicamento);
        }

        return vc;
    }

    public void atualizarMedicamento(int id_med, String nome, String indicacao, String contra_indc) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //System.out.println("entrou na dao");
        conexao.abrirConexao();
        String sql = "UPDATE medicamento set nome = '" + nome + "', indicacao = '" + indicacao + "', contra_indc = '" + contra_indc + "' WHERE id_med = " + id_med + ";";
        //System.out.println("pré-execução do UPDATE na dao");
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarMedicamento(int id_med) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "DELETE FROM medicamento WHERE id_med = " + id_med + ";";
        Statement stm = conexao.getConnection().createStatement();
        stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        String sql = "SELECT MAX(id_med) as idn FROM medicamento;";
        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int asdasd = rs.getInt("idn");
        return (asdasd + 1);
    }
    
    public List<Medicamento> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao.abrirConexao();
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            Statement stm = conexao.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setId_med(rs.getInt("id_med"));
                m.setNome(rs.getString("nome"));
                m.setIndicacao(rs.getString("indicacao"));
                m.setContra_indc(rs.getString("contra_indc"));

                medicamentos.add(m);

            }
            stm.close();
            rs.close();
            conexao.connection.close();
        } catch (SQLException ex) {
            return null;
        }

        return medicamentos;
    }
}
