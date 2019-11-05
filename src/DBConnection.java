import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection(String nomDB) {
        try {
            String userName = "miu";
            String password = "bonbon1a";
            String serverName = "localhost";
            String portNumber = "3306";
            String dbName = "cpoa";
            String urlDB = "jdbc:mariadb://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            this.connection = DriverManager.getConnection(urlDB, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public synchronized static void setNomDB(String nomDB) {
        dbConnection = new DBConnection(nomDB);
    }

    public synchronized static Connection getConnection() {
        if (dbConnection == null) {
            setNomDB("cpoa"); // default
        }
        return dbConnection.connection;
    }
}
