package edu.technopolis.homework.messenger.messages;

import java.util.List;

public class ChatCreateMessage extends Message {

  private List<Long> users;

  public List<Long> getUsers() {
    return users;
  }

  public void setUsers(List<Long> users) {
    this.users = users;
  }

  public ChatCreateMessage() {
    setType(Type.MSG_CHAT_CREATE);
  }
}
