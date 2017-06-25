package edu.technopolis.homework.messenger.messages;

public class InfoMessage extends Message {

  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public InfoMessage() {
    setType(Type.MSG_INFO);
  }
}
