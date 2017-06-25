package edu.technopolis.homework.messenger.messages;

import edu.technopolis.homework.messenger.User;

/**
 * Created by ilgar on 21.06.17.
 */
public class StatusMessage extends TextMessage {
  public StatusMessage() {
    setType(Type.MSG_STATUS);
  }

  public static StatusMessage firstLogIn() {
    StatusMessage message = new StatusMessage();
    message.setText("First log in");
    return message;
  }

  public static StatusMessage userInfo(User user, boolean isFull) {
    StatusMessage message = new StatusMessage();
    String response;
    if (user != null) {
      response = "Id: " + user.getId().toString() + "\n"
          + "Login: " + user.getName().toString() + "\n";

      if (isFull) {
        response += "Password: " + user.getPassword() + "\n";
      }
    } else {
      response = "Incorrect login or password";
    }

    message.setText(response);
    return message;
  }

  public static StatusMessage wrongChatMessage() {
    StatusMessage sendMessage = new StatusMessage();
    sendMessage.setText("Wrong chat");
    return sendMessage;
  }
}
