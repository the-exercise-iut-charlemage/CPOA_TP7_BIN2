package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection(String nomDB) throws SQLException {
        String userName = "hydris";
        String password = "bonbon1a";
        String serverName = "hydris.n-e-t.name";
        String portNumber = "3306";
        String dbName = nomDB;
        String urlDB = "jdbc:mariadb://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        this.connection = DriverManager.getConnection(urlDB, userName, password);
    }

    public synchronized static void setNomDB(String nomDB) throws SQLException {
        dbConnection = new DBConnection(nomDB);
    }

    public synchronized static Connection getConnection() throws SQLException {
        if (dbConnection == null) {
            setNomDB("cpoa"); // default
        }
        return dbConnection.connection;
    }
}
