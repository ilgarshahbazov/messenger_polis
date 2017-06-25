package edu.technopolis.homework.messenger.messages;

import java.io.Serializable;

/**
 *
 */
public abstract class Message implements Serializable {

  private Long id;
  private Long senderId;
  private Type type;
  private String text;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Message message = (Message) o;

    if (id != null ? !id.equals(message.id) : message.id != null) {
      return false;
    }
    if (senderId != null ? !senderId.equals(message.senderId) : message.senderId != null) {
      return false;
    }
    return type == message.type;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Message{"
        + "id=" + id
        + ", senderId=" + senderId
        + ", type=" + type
        + '}';
  }

  public void setText(String text) {
    this.text = text;
  }
}
