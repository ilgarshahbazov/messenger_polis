package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.User;
import edu.technopolis.homework.messenger.messages.InfoMessage;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.StatusMessage;
import edu.technopolis.homework.messenger.net.Session;

/**
 * Created by ilgar on 21.06.17.
 */
public class InfoCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    InfoMessage message = (InfoMessage) msg;
    if (message.getUserId() == null) {
      if (session.getUser() != null) {
        message.setUserId(session.getUser().getId());
      } else {
        session.send(StatusMessage.firstLogIn());
        return;
      }
    }
    User user = session.getServer().getUserStore()
        .getUserById(message.getUserId());
    session.send(StatusMessage.userInfo(user, false));
  }
}
