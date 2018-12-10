package DAO;

import Entidades.Animal;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOAnimal {

    private Conexao conexao_animal;
    //private String sql;

    public DAOAnimal() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao_animal = new Conexao();
    }

    public void cadastrarAnimal(Animal animal) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        String sql = "INSERT INTO animal (id_animal, nome, nascimento, peso, descricao, id_mae, id_pai, id_tipo, id_raca, lactacao, id_lote) VALUES ("
                + animal.getId_animal() + ",'" + animal.getNome() + "','" + animal.getNascimento() + "'," + animal.getPeso() + ",'" + animal.getDescricao()
                + "'," + animal.getId_mae() + "," + animal.getId_pai() + "," + animal.getId_tipo() + "," + animal.getId_raca() + "," + animal.isLactacao()
                + "," + animal.getId_lote() + ");";
        Statement stm = conexao_animal.getConnection().createStatement();
        stm.execute(sql);

    }

    public void insertCustom(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        Statement stm = conexao_animal.getConnection().createStatement();
        stm.execute(sql);
    }

    public Animal consultaAnimal(int id_animal) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        Animal animal = new Animal();
        String sql = "SELECT * FROM animal WHERE id_animal = " + id_animal + ";";
        Statement stm = conexao_animal.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        animal.setId_animal(rs.getInt("id_animal"));
        animal.setNome(rs.getString("nome"));
        try {
            animal.setNascimento(rs.getString("nascimento"));
        } catch (Exception e) {

        }
        try {
            animal.setPeso(rs.getDouble("peso"));
        } catch (Exception e) {

        }
        animal.setDescricao(rs.getString("descricao"));
        animal.setId_mae(rs.getInt("id_mae"));
        animal.setId_pai(rs.getInt("id_pai"));
        animal.setId_tipo(rs.getInt("id_tipo"));
        animal.setId_raca(rs.getInt("id_raca"));
        animal.setLactacao(rs.getBoolean("lactacao"));
        animal.setId_lote(rs.getInt("id_lote"));
        return animal;
    }

    public void atualizarAnimal(int id_animal, String nome, String nascimento, double peso, String descricao, int id_mae, int id_pai, int id_tipo, int id_raca, boolean lactacao, int id_lote, int novoid) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        String sql = "UPDATE animal set id_animal = " + novoid + ", nome = '" + nome + "', nascimento = '" + nascimento + "', peso = " + peso
                + ", descricao = '" + descricao + "', id_mae = " + id_mae + ", id_pai = " + id_pai + ", id_tipo = "
                + id_tipo + ", id_raca = " + id_raca + ", lactacao = " + lactacao + ", id_lote = " + id_lote + " WHERE id_animal = " + id_animal + ";";
        Statement stm = conexao_animal.getConnection().createStatement();
        stm.execute(sql);
    }
    
    public void atualizaCustom(String sql) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        Statement stm = conexao_animal.getConnection().createStatement();
        stm.execute(sql);
    }

    public void deletarAnimal(int id_animal) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        String sql = "DELETE FROM animal WHERE id_animal = " + id_animal + ";";
        Statement stm = conexao_animal.getConnection().createStatement();
        stm.execute(sql);
    }

    public int proximoID() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        String sql = "SELECT MAX(id_animal) as idn FROM animal;";
        Statement stm = conexao_animal.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int asdasd = rs.getInt("idn");
        return (asdasd + 1);
    }

    public List<Animal> getList(String sql) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_animal.abrirConexao();
        List<Animal> animais = new ArrayList<>();
        try {
            Statement stm = conexao_animal.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Animal a = new Animal();
                a.setId_animal(rs.getInt("id_animal"));
                a.setNome(rs.getString("nome"));
                try {
                    a.setNascimento(rs.getString("nascimento"));
                } catch (Exception e) {

                }
                try {
                    a.setPeso(rs.getDouble("peso"));
                } catch (Exception e) {

                }
                a.setDescricao(rs.getString("descricao"));
                a.setId_mae(rs.getInt("id_mae"));
                a.setId_pai(rs.getInt("id_pai"));
                a.setId_tipo(rs.getInt("id_tipo"));
                a.setId_raca(rs.getInt("id_raca"));
                a.setLactacao(rs.getBoolean("lactacao"));
                a.setId_lote(rs.getInt("id_lote"));

                animais.add(a);

            }
            stm.close();
            rs.close();
            conexao_animal.connection.close();
        } catch (SQLException ex) {
            return null;
        }

        return animais;
    }

    public List<Integer> getOrdem() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();
        List<Integer> ordem = new ArrayList();

        String sql = "SELECT id_animal FROM ordem_producao ORDER BY id_ordem;";

        Statement stm = conexao_animal.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);

        while (rs.next()) {
            ordem.add(rs.getInt("id_animal"));
        }
        stm.close();
        rs.close();
        conexao_animal.connection.close();

        return ordem;
    }

    public void resetaOrdem(List<Integer> ordem) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();

        String sql = "ALTER SEQUENCE ordem_producao_id_ordem_seq RESTART WITH 1;";
        Statement stm = conexao_animal.connection.createStatement();
        stm.execute(sql);

        sql = "TRUNCATE TABLE ORDEM_PRODUCAO";
        stm.execute(sql);

        for (int i : ordem) {
            sql = "INSERT INTO ORDEM_PRODUCAO (ID_ANIMAL) VALUES (" + i + ");";
            stm.execute(sql);
        }

        stm.close();
        conexao_animal.connection.close();
    }

    public void delOrdem(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();

        String sql = "DELETE FROM ORDEM_PRODUCAO where id_animal = " + id + ";";
        Statement stm = conexao_animal.connection.createStatement();
        stm.execute(sql);
        stm.close();
    }

    public void addOrdem(int id) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();

        String sql = "INSERT INTO ORDEM_PRODUCAO (ID_ANIMAL) values (" + id + ");";
        Statement stm = conexao_animal.connection.createStatement();
        stm.execute(sql);
        stm.close();
    }

    public void addHistorico(Animal a, boolean b, LocalDate c) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();

        String sql = "INSERT INTO LACTACAO (ID_ANIMAL, FLAG, DATA_LAC) values (" + a.getId_animal() + ", " + b + ", '" + c.toString() + "');";
        Statement stm = conexao_animal.connection.createStatement();
        stm.execute(sql);
        stm.close();
    }

    public void altHistorico(Animal a, boolean b, LocalDate c) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();

        String sql = "UPDATE LACTACAO SET FLAG = " + b + ", data_lac = '" + c.toString() + "' where id_animal = " + a.getId_animal() + " ;";
        Statement stm = conexao_animal.connection.createStatement();
        stm.execute(sql);
        stm.close();
    }

    public List<Integer> consultaHistorico(String data1, String data2) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        conexao_animal.abrirConexao();
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT ID_ANIMAL, DATA_LAC FROM LACTACAO WHERE FLAG = TRUE AND DATA_LAC BETWEEN '" + data1 + "' and '" + data2 + "' ;";
        Statement stm = conexao_animal.connection.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        int aux;
        while (rs.next()) {
            aux = rs.getInt("id_animal");
            list.add(aux);
        }

        stm.close();
        return list;
    }

}
