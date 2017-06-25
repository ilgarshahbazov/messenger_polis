package edu.technopolis.homework.messenger.messages;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatListMessage extends Message {
  @Override
  public String toString() {
    return "ChatListMessage{"
        + '}';
  }

  public ChatListMessage() {
    setType(Type.MSG_CHAT_LIST);
  }
}
