package edu.technopolis.homework.messenger.store;

import edu.technopolis.homework.messenger.User;

public interface UserStore {
  /**
   * Добавить пользователя в хранилище
   * Вернуть его же
   */
  User addUser(User user);

  /**
   * Обновить информацию о пользователе
   */
  User updateUser(User user);

  /**
   *
   * Получить пользователя по логину/паролю
   * return null if user not found
   */
  User getUser(String login);

  /**
   *
   * Получить пользователя по id, например запрос информации/профиля
   * return null if user not found
   */
  User getUserById(Long id);
}
