package db;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnections {

    private Connection connection;
    private static DBConnections connector = null;
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:1001/hackathon";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "password";


    private DBConnections() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME,
                    DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            System.err.println("Failed to make connection!");
        }



    }

    public static Connection getConnection() {
        if (connector == null) {
            connector = new DBConnections();

        }
        return connector.connection;

    }



}