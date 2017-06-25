package edu.technopolis.homework.messenger.messages;

import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatListResultMessage extends Message {
  private List<Long> chats;

  public List<Long> getChats() {
    return chats;
  }

  public void setChats(List<Long> chats) {
    this.chats = chats;
  }

  public ChatListResultMessage() {
    setType(Type.MSG_CHAT_LIST_RESULT);
  }
}
