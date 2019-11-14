import org.junit.Test;
import utils.DBConnection;

import java.sql.SQLException;

public class DBConnectionTest {

    @Test
    public void testConnection() throws SQLException {
        DBConnection.getConnection();
    }

    @Test(expected = SQLException.class)
    public void testBadName() throws SQLException {
        DBConnection.setNomDB("noexistantbdd");
        DBConnection.getConnection();
    }


}