package edu.technopolis.homework.messenger.store;

import java.sql.SQLException;

@FunctionalInterface
public interface CheckedConsumer<T> {
  void accept(T arg) throws SQLException;
}
