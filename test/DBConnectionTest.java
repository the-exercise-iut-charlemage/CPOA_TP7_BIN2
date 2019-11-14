import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utils.DBConnection;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import static org.junit.Assert.*;

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