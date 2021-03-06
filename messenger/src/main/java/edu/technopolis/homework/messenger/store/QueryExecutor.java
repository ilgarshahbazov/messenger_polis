package edu.technopolis.homework.messenger.store;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;

public class QueryExecutor {
  static final String MYSQL_ADRESS = "jdbc:mysql://sql11.freesqldatabase.com/sql11182010";
  static final String USERNAME = "sql11182010";
  static final String PASSWORD = "CL3bmdS2vy";

  private BasicDataSource ds;

  QueryExecutor() {
    ds = new BasicDataSource();
    ds.setDriverClassName("com.mysql.jdbc.Driver");
    ds.setUsername(USERNAME);
    ds.setPassword(PASSWORD);
    ds.setUrl(MYSQL_ADRESS);
  }

  public void execQuery(String query,
                        Object[] args,
                        CheckedConsumer<ResultSet> handler)
      throws SQLException {
    try (Connection connection
             = ds.getConnection()) {
      PreparedStatement stmt = connection.prepareStatement(query);
      for (int i = 1; i <= args.length; ++i) {
        stmt.setObject(i, args[i - 1]);
      }
      System.out.println(stmt);
      ResultSet rs = stmt.executeQuery();
      handler.accept(rs);
      rs.close();
      stmt.close();
    }
  }

  public long execUpdate(String query, Object[] args)  throws SQLException {
    long result = 0;
    try (Connection connection
             = ds.getConnection()) {
      PreparedStatement stmt = connection.prepareStatement(query,
          Statement.RETURN_GENERATED_KEYS);
      for (int i = 1; i <= args.length; ++i) {
        stmt.setObject(i, args[i - 1]);
      }
      System.out.println(stmt);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
      result = rs.getLong(1);
      rs.close();
      stmt.close();
    }
    return result;
  }
}
