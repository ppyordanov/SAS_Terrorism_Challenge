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



}
