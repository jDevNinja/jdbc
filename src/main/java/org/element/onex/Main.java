package org.element.onex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Main {
  public static void main(String[] args) throws SQLException {
    String url = "jdbc:postgresql://localhost:5442/postgres";
    String username = "user";
    String password = "pass";

    Connection connection = DriverManager.getConnection(url, username, password);

    // SQL statements without parameters are normally executed using Statement objects.
    Statement statement = connection.createStatement();

    System.out.println("==statement.Insert==");
    // Добавим возвращение сгенерированного ключа
    boolean result =
        statement.execute(
            "INSERT INTO account (balance, full_name) VALUES (25, 'John Black');",
            Statement.RETURN_GENERATED_KEYS);
    ResultSet generatedKeys = statement.getGeneratedKeys();
    while (generatedKeys.next()) {
      System.out.println("Сгенерированный ключ: " + generatedKeys.getInt(1));
    }
    int updateCount = statement.getUpdateCount();
    System.out.println("Результат выполнения: " + result);
    System.out.println("Количество обновленных записей: " + updateCount + "\n");

    System.out.println("==statement.Select==");
    result = statement.execute("SELECT * FROM account");
    updateCount = statement.getUpdateCount();
    System.out.println("Результат выполнения: " + result);
    System.out.println("Количество обновленных записей: " + updateCount + "\n");

    // result == true => есть ResultSet, обработаем его
    ArrayList<Account> accounts = new ArrayList<>();
    ResultSet resultSet = statement.getResultSet();
    // нужно пройтись по всему ResultSet
    while (resultSet.next()) {
      // нужно указать название колонки и получить из нее соответсвующее значение
      int id = resultSet.getInt("id");
      int balance = resultSet.getInt("balance");
      String fullName = resultSet.getString("full_name");
      // создать объект и положить его в list
      accounts.add(new Account(id, fullName, balance));
    }

    System.out.println("Все найденные аккаунты: " + accounts);

    System.out.println("==statement.Update==");
    int randomBalance = new Random().nextInt(1000);
    String sql = String.format("UPDATE account SET balance = %d WHERE id = 1;", randomBalance);
    result = statement.execute(sql);
    updateCount = statement.getUpdateCount();
    System.out.println("Результат выполнения: " + result);
    System.out.println("Количество обновленных записей: " + updateCount + "\n");

    // Чтобы не хардкодить запрос как в строках выше и сделать более очевидно и оптимизированно чем
    // в строке со String.format() воспользуемся PreparedStatement
    System.out.println("==preparedStatement.Update==");
    String[] names = {"One", "Two", "Three"};
    int random = new Random().nextInt(3);
    sql = "UPDATE account SET full_name = ? WHERE id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, names[random]);
    preparedStatement.setInt(2, 2);
    result = preparedStatement.execute();
    updateCount = statement.getUpdateCount();
    System.out.println("Результат выполнения: " + result);
    System.out.println("Количество обновленных записей: " + updateCount + "\n");
  }
}

class Account {
  private int id;
  private String fullName;
  private int balance;

  public Account(int id, String fullName, int balance) {
    this.id = id;
    this.fullName = fullName;
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "Account{"
        + "id="
        + id
        + ", fullName='"
        + fullName
        + '\''
        + ", balance="
        + balance
        + '}';
  }
}
