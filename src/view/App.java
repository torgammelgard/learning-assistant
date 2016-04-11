package view;

import model.DBSource;

import javax.swing.*;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());

        // Test mongodb driver by printing all database names
        for (String DBName : DBSource.getMongoClient().listDatabaseNames()) {
            System.out.println(DBName);
        }

        // Close the Mongo Client
        DBSource.closeMongoClient();
    }
}
