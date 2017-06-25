package edu.technopolis.homework.messenger.store;

import java.util.List;
import java.util.Set;

import edu.technopolis.homework.messenger.Chat;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.TextMessage;

public interface MessageStore {

  /**
   * получаем список ид пользователей заданного чата
   */
  List<Long> getChatsByUserId(Long userId);

  /**
   * получить информацию о чате
   */
  Chat getChatById(Long chatId);

  /**
   * Список сообщений из чата
   */
  List<Long> getMessagesFromChat(Long chatId);

  /**
   * Получить информацию о сообщении
   */
  Message getMessageById(Long messageId);

  /**
   * Добавить сообщение в чат
   */
  Message addMessage(Long chatId, Message message);

  /**
   * Добавить пользователя к чату
   */
  void addUserToChat(Long userId, Long chatId);

  Long addChat();
}
