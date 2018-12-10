package DAO;

import Entidades.Usuario;
import ConexaoBD.Conexao;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuario {

    private Conexao conexao_user;

    public DAOUsuario() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        conexao_user = new Conexao();
    }

    public int cadastrarUsuario(Usuario usuario) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_user.abrirConexao();
        String sql = "INSERT INTO usuario (nome, senha, permissoes) VALUES ('" + usuario.getNome()
                + "','" + usuario.getSenha() + "','" + usuario.getPermissoes() + "');";
        Statement stm = conexao_user.getConnection().createStatement();
        stm.execute(sql);

        sql = "SELECT * FROM usuario WHERE nome = '" + usuario.getNome() + "' AND senha = '"
                + usuario.getSenha() + "' AND permissoes = '" + usuario.getPermissoes() + "';";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getInt("id_user");
    }

    public Usuario consultaUsuario(int id_user) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_user.abrirConexao();
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM usuario WHERE id_user = " + id_user + ";";
        Statement stm = conexao_user.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        usuario.setId_user(rs.getInt("id_user"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPermissoes(rs.getString("permissoes"));
        return usuario;
    }

    public void atualizarUsuario(int id_user, String nome, String senha, String permissoes) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_user.abrirConexao();
        String sql = "UPDATE usuario set nome = '" + nome + "', senha = '" + senha + "', permissoes = '" + permissoes
                + "' WHERE id_user = " + id_user + ";";
        Statement stm = conexao_user.getConnection().createStatement();
        stm.executeQuery(sql);
    }

    public int deletarUsuario(int id_user) throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_user.abrirConexao();
        String sql_user = "DELETE FROM USUARIO WHERE id_user = " + id_user + ";";
        conexao_user.stm.execute(sql_user);
        return (0);
    }

    public List<Usuario> listaUsers() throws SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        conexao_user.abrirConexao();
        Usuario usuario;
        List<Usuario> a = new ArrayList<>();
        String sql = "SELECT * FROM usuario;";
        Statement stm = conexao_user.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setId_user(rs.getInt("id_user"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setPermissoes(rs.getString("permissoes"));
            a.add(usuario);
        }

        return a;
    }
    
    public boolean existeOutro(String nome) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
         conexao_user.abrirConexao();
        
        String sql = "SELECT * FROM usuario WHERE nome = '" + nome + "';";
        Statement stm = conexao_user.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
        if(rs.next()){
            return true;
        }
        
        return false;
    }
    
}
