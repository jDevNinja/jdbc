package org.element.onex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) throws SQLException {
    String url = "jdbc:postgresql://localhost:5442/postgres";
    String username = "user";
    String password = "pass";

    Connection connection = DriverManager.getConnection(url, username, password);

    // SQL statements without parameters are normally executed using Statement objects.
    Statement statement = connection.createStatement();

    boolean result =
        statement.execute("INSERT INTO account (balance, full_name) VALUES (25, 'John Black');");
    int updateCount = statement.getUpdateCount();
    System.out.println("Результат выполнения: " + result);
    System.out.println("Количество обновленных записей: " + updateCount);

    result = statement.execute("SELECT * FROM account");
    updateCount = statement.getUpdateCount();
    System.out.println("Результат выполнения: " + result);
    System.out.println("Количество обновленных записей: " + updateCount);
  }
}
