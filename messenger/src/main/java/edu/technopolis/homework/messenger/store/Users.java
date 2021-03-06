package edu.technopolis.homework.messenger.store;

import edu.technopolis.homework.messenger.User;

import java.sql.SQLException;

public class Users implements UserStore {

  QueryExecutor executor;

  @Override
  public User addUser(User user) {
    Object[] args = {user.getName(), user.getPassword()};
    try {
      user.setId(executor.execUpdate("INSERT INTO USERS (login, " +
          "password)" +
          "VALUES(?, ?)", args));
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

  @Override
  public User updateUser(User user) {
    Object[] args = {user.getName(), user.getPassword()};
    try {
      executor.execQuery("UPDATE USERS SET LOGIN=?, " +
              "PASSWORD=? WHERE ID=?",
          args, rs -> { } );
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

  @Override
  public User getUser(String login) {
    final User[] result = {new User()};
    result[0].setName(login);
    Object[] args = {login};
    try {
      executor.execQuery("SELECT ID, PASSWORD FROM USERS WHERE LOGIN=?",
          args, rs -> {
            try {
              if (rs.next()) {
                result[0].setId(rs.getLong("ID"));
                result[0].setPassword(rs.getString("PASSWORD"));
              } else {
                result[0] = null;
              }
            } catch (SQLException e) {
              e.printStackTrace();
              result[0] = null;
            }
          } );
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return result[0];
  }

  @Override
  public User getUserById(Long id) {
    final User[] result = {new User()};
    result[0].setId(id);
    Object[] args = {id};
    try {
      executor.execQuery("SELECT LOGIN, PASSWORD FROM USERS WHERE ID=?",
          args, rs -> {
            try {
              if (rs.next()) {
                result[0].setPassword(rs.getString("PASSWORD"));
                result[0].setName(rs.getString("LOGIN"));
              } else {
                result[0] = null;
              }
            } catch (SQLException e) {
              e.printStackTrace();
              result[0] = null;
            }
          } );
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return result[0];
  }

  public Users() {
    executor = new QueryExecutor();
  }
}