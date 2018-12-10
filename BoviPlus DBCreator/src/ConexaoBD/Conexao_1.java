package ConexaoBD;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao_1 {

    public Connection connection;

    public void abrirConexao(String nomedb, String senha) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        
        String driverName = "org.postgresql.Driver";    
        String ipbanco = "localhost";
        String databaseURL = "jdbc:postgresql://" + ipbanco + "/" + nomedb;
        String usuario = "postgres";
        
        
            //Carrega o driver
            Class.forName(driverName).newInstance();

            //Conecta o BD
            connection = DriverManager.getConnection(databaseURL, usuario, senha);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
