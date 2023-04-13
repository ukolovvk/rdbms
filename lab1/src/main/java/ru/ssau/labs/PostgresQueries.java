package ru.ssau.labs;

import java.sql.*;

public class PostgresQueries {

    private static final String JDBC_URL_DB = "jdbc:postgresql://127.0.0.1:5433/lab_db";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASS = "admin";
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(JDBC_URL_DB, POSTGRES_USER, POSTGRES_PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed connect to postgres", ex);
        }
    }

    public static void join() {
        String SQL = """
                SELECT * FROM employees AS e INNER JOIN positions AS p ON e.position_id = p.id
            """;

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getInt("id"));
                System.out.println("first_name: " + resultSet.getString("first_name"));
                System.out.println("last_name: " + resultSet.getString("last_name"));
                System.out.println("position: " + resultSet.getString("position"));
                System.out.println("salary: " + resultSet.getDouble("salary"));
                System.out.println("manager_id: " + resultSet.getInt("manager_id"));
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //positions с средней зарплатой более 45
    public static void having() {
        String SQL = """
                SELECT p.position, avg(e.salary) as avg_salary FROM employees as e
                INNER JOIN positions AS p ON e.position_id = p.id
                GROUP BY p.position HAVING avg(e.salary) > 45
            """;
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Positions with average salary > 45\n");
            while (resultSet.next()) {
                System.out.println("for position: " + resultSet.getString("position"));
                System.out.println("average salary: " + resultSet.getDouble("avg_salary"));
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //вывести иерархию менеджеров для конкретного сотрудника
    public static void recursive() {
         String SQL = """
          WITH RECURSIVE temp (id, last_name, manager_id, hierarchy) AS (
                      select temp.id, temp.last_name, temp.manager_id,
                             cast (temp.last_name as VARCHAR(100)) as hierarchy
                      FROM employees AS temp WHERE temp.id = 11
                      UNION
                      SELECT temp2.id, temp2.last_name, temp2.manager_id,
                             cast (temp.hierarchy || ' -> ' || temp2.last_name as VARCHAR(100))
                      FROM employees as temp2 INNER JOIN temp ON temp.manager_id = temp2.id
                  )
                  SELECT hierarchy || ' (boss)' as result FROM temp where temp.id = 1;           
             """;
         try (PreparedStatement statement = connection.prepareStatement(SQL)) {
             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()) {
                 System.out.println("Manager hierarchy for employee with id = 11: "
                         + resultSet.getString("result"));
                 System.out.println();
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }
}