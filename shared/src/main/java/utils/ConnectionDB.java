package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionDB {

    /**
     * Retorna uma conexão ativa à base de dados.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Properties props = new Properties();
            InputStream input = ConnectionDB.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
