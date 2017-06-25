package edu.technopolis.homework.messenger.messages;

/**
 * Created by ilgar on 21.06.17.
 */
public class TextClientMessage extends TextMessage {
  private String senderLogin;

  public String getSenderLogin() {
    return senderLogin;
  }

  public TextClientMessage(TextMessage message, String login) {
    setChatId(message.getChatId());
    setText(message.getText());
    setId(message.getId());
    setSenderId(message.getSenderId());
    setTimestamp(message.getTimestamp());
    setType(Type.MSG_TEXT_CLIENT);
    senderLogin = login;
  }

  @Override
  public String toString() {
    return "TextClientMessage{"
        + "senderLogin='" + senderLogin
        + "textMessage=" + super.toString() + '\''
        + '}';
  }
}
