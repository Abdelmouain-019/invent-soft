package com.project.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.project.Utils.FileHandler;

public class Database {
    private static String Url = "jdbc:sqlite:src/main/database/database.db";
    private static Connection conn;

    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(Url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Init() {
        String InitDataBase = FileHandler.ReadFile("SQL/init.sql");
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            String[] sqlStatments = InitDataBase.split(";");
            for (String s : sqlStatments) {
                String t = s.trim();
                if (t.isEmpty())
                    continue;
                stmt.execute(t);
            }
            System.out.println("***********************************");
            System.out.println("The Table is ready to use!!");
            System.out.println("***********************************");
        } catch (SQLException e) {
            System.out.println("***********************************");
            e.printStackTrace();
            System.out.println("***********************************");
        }
    }

}
