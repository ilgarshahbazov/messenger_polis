package edu.technopolis.homework.messenger.messages;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatHistMessage extends Message {

  Long chatId;

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public ChatHistMessage() {
    setType(Type.MSG_CHAT_HIST);
  }
}
