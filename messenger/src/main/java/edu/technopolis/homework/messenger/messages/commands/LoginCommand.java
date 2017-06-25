package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.User;
import edu.technopolis.homework.messenger.messages.LoginMessage;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.StatusMessage;
import edu.technopolis.homework.messenger.messages.TextMessage;
import edu.technopolis.homework.messenger.net.Session;

/**
 * Created by ilgar on 21.06.17.
 */
public class LoginCommand implements Command {

  @Override
  public void execute(Session session, Message msg) throws CommandException {
    if (session.getUser() != null) {
      TextMessage sendMessage = new StatusMessage();
      sendMessage.setText("already logged in");
      session.send(sendMessage);
    } else {

      try {
        LoginMessage loginMessage = (LoginMessage) msg;
        session.setUser(new User());
        session.getUser().setName(loginMessage.getLogin());
        session.getUser().setPassword(loginMessage.getPassword());
        User realUser = session.getServer().getUserStore()
            .getUser(loginMessage.getLogin());
        if (realUser == null) {
          realUser = session.getServer().getUserStore()
              .addUser(session.getUser());
        } else if (!realUser.getPassword().equals(loginMessage
            .getPassword())) {
          StatusMessage response = new StatusMessage();
          response.setText("Incorrect password");
          session.send(response);
          return;
        }
        session.getServer().getActiveUsers()
            .put(realUser.getId(), session);
        session.setUser(realUser);
        System.out.println("SUCCESS");
        session.send(StatusMessage.userInfo(session.getUser(), true));
      } catch (ClassCastException e) {
        throw new CommandException("Incorrect class", e);
      }



    }
  }
}
