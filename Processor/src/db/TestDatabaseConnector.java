package db;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by DeCekoMon on 10/11/2014.
 */
public class TestDatabaseConnector {

    private static Connection connection;


    @BeforeClass
    public static void init() {
        connection = DBConnections.getConnection();
    }

    //Test to see if connection is alive
    @Test
    public void testConnection() {
        Connection connection = DBConnections.getConnection();
       Assert.assertTrue(connection != null);
    }



}