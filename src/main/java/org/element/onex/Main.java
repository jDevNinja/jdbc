package org.element.onex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException {
    String url = "jdbc:postgresql://localhost:5442/postgres";
    String username = "user";
    String password = "pass";

    Connection connection = DriverManager.getConnection(url, username, password);
  }
}
