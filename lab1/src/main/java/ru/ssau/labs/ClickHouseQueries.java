package ru.ssau.labs;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClickHouseQueries {

    private static final String JDBC_URL_DB = "jdbc:clickhouse://localhost:8123/lab_db";
    private static final String CLICKHOUSE_USER = "user";
    private static final String CLICKHOUSE_PASS = "admin";
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(JDBC_URL_DB, CLICKHOUSE_USER, CLICKHOUSE_PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed connect to postgres", ex);
        }
    }

    public static void limitQuery() {
        String SQL = """
            SELECT first_name, last_name, average_estimate, group_number FROM students
            INNER JOIN groups g ON students.group_id = g.group_id ORDER BY average_estimate DESC
            LIMIT 5 BY group_id;
        """;
        try (PreparedStatement statement = connection.prepareStatement(SQL);
             ResultSet resultSet = statement.executeQuery()) {
             while (resultSet.next()) {
                 System.out.println("first_name = " + resultSet.getString("first_name"));
                 System.out.println("last_name = " + resultSet.getString("last_name"));
                 System.out.println("estimate = " + resultSet.getDouble("average_estimate"));
                 System.out.println("group number = " + resultSet.getString("group_number"));
                 System.out.println();
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error occurred during executing query");
        }
    }

    public static void replacingMergeTree() {
        String dropTable = "drop table if exists replacing_ex_table";
        String createTable = """
            create table replacing_ex_table (
                key Int64,
                first_field String,
                second_field String
            )
            ENGINE = ReplacingMergeTree()
            ORDER BY key
        """;
        String firstInsert = "INSERT INTO replacing_ex_table VALUES (1, 'first_field_1', 'second_field_1')";
        String secondInsert = "INSERT INTO replacing_ex_table VALUES (1, 'first_field_2', 'second_field_2')";
        String thirdInsert = "INSERT INTO replacing_ex_table VALUES (1, 'first_field_2', 'second_field_3')";
        String forthInsert = "INSERT INTO replacing_ex_table VALUES (1, 'first_field_4', 'second_field_5')";
        String fifthInsert = "INSERT INTO replacing_ex_table VALUES (1, 'first_field_4', 'second_field_5')";
        String sixthInsert = "INSERT INTO replacing_ex_table VALUES (2, 'first_field_6', 'second_field_7')";
        String seventhInsert = "INSERT INTO replacing_ex_table VALUES (2, 'first_field_7', 'second_field_3')";
        String select = "SELECT * FROM replacing_ex_table FINAL";
        List<String> queries = new ArrayList<>(Arrays.asList(dropTable, createTable, firstInsert,
                secondInsert, thirdInsert, forthInsert, fifthInsert, sixthInsert, seventhInsert));

        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (String query : queries) statement.addBatch(query);
            statement.executeBatch();
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                System.out.println("key = " + resultSet.getInt("key"));
                System.out.println("first_field = " + resultSet.getString("first_field"));
                System.out.println("second_field = " + resultSet.getString("second_field"));
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error occurred during executing query");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void collapsingMergeTree() {
        String dropTable = "drop table if exists collapsing_ex_table";
        String createTable = """
            create table collapsing_ex_table (
                 key Int64,
                 field String,
                 sign Int8
            )
            ENGINE = CollapsingMergeTree(sign)
            ORDER BY key
        """;
        String firstInsert = "INSERT INTO collapsing_ex_table VALUES (1, 'field_1', 1)";
        String secondInsert = "INSERT INTO collapsing_ex_table VALUES (1, 'field_1', -1)";
        String thirdInsert = "INSERT INTO collapsing_ex_table VALUES (1, 'field_2', 1)";
        String forthInsert = "INSERT INTO collapsing_ex_table VALUES (1, 'field_2', -1)";
        String fifthInsert = "INSERT INTO collapsing_ex_table VALUES (1, 'res_field', 1)";
        String select = "SELECT * FROM collapsing_ex_table FINAL";
        List<String> queries = new ArrayList<>(Arrays.asList(dropTable, createTable, firstInsert,
                secondInsert, thirdInsert, forthInsert, fifthInsert));

        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (String query : queries) statement.addBatch(query);
            statement.executeBatch();
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                System.out.println("key = " + resultSet.getInt("key"));
                System.out.println("field = " + resultSet.getString("field"));
                System.out.println("sign = " + resultSet.getInt("sign"));
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error occurred during executing query");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
