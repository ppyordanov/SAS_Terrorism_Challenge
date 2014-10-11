package main.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by DeCekoMon on 10/11/2014.
 */
public class TableBuilder {

    private static final String DATA_FILE = "data/globalterrorismdb_0814dist.txt";
    private static final String TABLE_NAME = "event";

    public static void main(String argc[]) throws IOException, SQLException {
        populateTables();
    }

    public static void buildTables() throws SQLException, IOException {
        Statement statement = DatabaseConnector.getConnection().createStatement();
        final String createQuery = createCreateTableQuery(TABLE_NAME);

        boolean res = statement.execute(createQuery);
        if (res) {
            System.err.println("ERROR executing create tables query!");
        }

    }

    public static void populateTables() throws IOException, SQLException {
        ArrayList<ArrayList<String>> entries = readDataEntries(DATA_FILE);
        Statement statement = DatabaseConnector.getConnection().createStatement();
        for (int i = 0; i < entries.size(); i++) {
            ArrayList<String> entry  = entries.get(i);
            String insertQuery = createInsertIntoTableQuery(entry, TABLE_NAME);
            boolean res = statement.execute(insertQuery);
            if (res) {
                System.err.println("ERROR executing insert into table query!");
            }
        }


    }

    private static String createInsertIntoTableQuery(ArrayList<String> entry, String tableName) {
        String insertIntoTableQuery = "INSERT INTO " + tableName + "\n" +
                "VALUES (";
        for (int i = 0; i < entry.size() - 1; i++) {
            String value = entry.get(i);
            insertIntoTableQuery += "\'" + value + "\', ";

        }
        insertIntoTableQuery += "\'" + entry.get(entry.size() - 1) + "\');";

        return insertIntoTableQuery;
    }

    private static String createCreateTableQuery(String tableName) throws IOException {

        ArrayList<String> attributes = readDataAttributes(DATA_FILE);
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n";
        for (String attribute : attributes) {
            createTableStatement += attribute + " text,\n";
        }


        createTableStatement += "PRIMARY KEY (eventid) );";
        return createTableStatement;
    }


    public static void buildTable() {

    }

    private static ArrayList<String> readDataAttributes(String fileName) throws IOException {
        ArrayList<String> attributes = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        String[] attributesArray = line.split("\t");
        for (int i = 0; i < attributesArray.length; i++) {
            attributes.add(attributesArray[i]);
        }
        return attributes;

    }

    private static ArrayList<ArrayList<String>> readDataEntries(String fileName) throws IOException {
        ArrayList<ArrayList<String>> entries = new ArrayList<ArrayList<String>>();

        ArrayList<String> entry = null;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        //skip the attributes line...
        line = br.readLine();
        while (line != null) {

            entry = new ArrayList<String>();
            String[] entryArray = line.split("\t");
            for (int i = 0; i < entryArray.length; i++) {
                entryArray[i] = entryArray[i].replaceAll("\"", " ");
                entryArray[i] = entryArray[i].replaceAll("\'", " ");
                entry.add(entryArray[i]);
            }

            //if column (last one) is empty we need to add one extra entry to list
            if (entry.size() == 133) {
                entry.add("");
            }
            entries.add(entry);
            line = br.readLine();
        }
        return entries;

    }

}












