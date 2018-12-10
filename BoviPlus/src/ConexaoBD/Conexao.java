package ConexaoBD;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Conexao {

    public Connection connection;
    public Statement stm;
    public ResultSet rs;

    public void abrirConexao() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String driverName = "org.postgresql.Driver";

        File file = new File("configDB.dbc");
        Scanner sc = new Scanner(file);
            
        String nomebanco = sc.nextLine();
        String ipbanco = sc.nextLine();
        String senha = sc.nextLine();   
        String databaseURL = "jdbc:postgresql://" + ipbanco + "/" + nomebanco;
        String usuario = "postgres";
        
        try {
            //Carrega o driver
            Class.forName(driverName).newInstance();

            //Conecta o BD
            connection = DriverManager.getConnection(databaseURL, usuario, senha);
            stm = connection.createStatement();
        } catch (SQLException e) {
               System.out.println("Nao conectou ao banco");
        }

    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return the stm
     */
    public Statement getStm() {
        return stm;
    }

    /**
     * @param stm the stm to set
     */
    public void setStm(Statement stm) {
        this.stm = stm;
    }
}
