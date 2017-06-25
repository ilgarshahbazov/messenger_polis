package edu.technopolis.homework.messenger.messages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Простое текстовое сообщение
 */
public class TextMessage extends Message {

  private String text;
  private Long chatId;
  private LocalDateTime timestamp;

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public TextMessage() {
    setType(Type.MSG_TEXT);
  }


  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    if (!super.equals(other)) {
      return false;
    }
    TextMessage message = (TextMessage) other;
    return Objects.equals(text, message.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), text);
  }

  @Override
  public String toString() {
    return "TextMessage{"
        + "text='" + text + '\''
        + '}';
  }

}