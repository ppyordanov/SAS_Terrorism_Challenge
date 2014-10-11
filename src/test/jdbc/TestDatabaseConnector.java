package test.jdbc;

import org.junit.Assert;
import main.jdbc.DatabaseConnector;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DeCekoMon on 10/11/2014.
 */
public class TestDatabaseConnector {

    private static Connection connection;


    @BeforeClass
    public static void init() {
        connection = DatabaseConnector.getConnection();
    }

    //Test to see if connection is alive
    @Test
    public void testConnection() {
        Connection connection = DatabaseConnector.getConnection();
       Assert.assertTrue(connection != null);
    }

    @Test
    public void testStatement() throws SQLException {
        Statement statement = connection.createStatement();
        final String sql = "CREATE TABLE IF NOT EXISTS Owner(\n" +
                "ownerid INTEGER,\n" +
                "name VARCHAR(32),\n" +
                "phone VARCHAR(16),\n" +
                "PRIMARY KEY (ownerid)\n" +
                ");";
       boolean res =  statement.execute(sql);
        Assert.assertTrue(res);


    }

}
