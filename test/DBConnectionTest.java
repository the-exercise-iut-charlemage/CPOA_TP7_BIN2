import org.junit.Assert;
import org.junit.Test;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class DBConnectionTest {

    @Test
    public void test_connection_contrutor() throws SQLException {
        DBConnection.getConnection();
    }

    @Test(expected = SQLException.class)
    public void test_bad_name() throws SQLException {
        DBConnection.setNomDB("noexistantbdd");
        DBConnection.getConnection();
    }

    @Test
    public void test_multi_connection() throws SQLException {
        Connection connection1 = DBConnection.getConnection();
        Connection connection2 = DBConnection.getConnection();
        assertEquals("Les connection sont diferente", connection1.toString(), connection2.toString());
    }

    @Test
    public void test_nouvelle_connection() throws SQLException {
        Connection connection1 = DBConnection.getConnection();
        DBConnection.setNomDB("cours_php");
        Connection connection2 = DBConnection.getConnection();
        assertNotEquals("Les connection sont idantique", connection1.toString(), connection2.toString());
    }

}