package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.StatusMessage;
import edu.technopolis.homework.messenger.net.Session;

/**
 * Created by ilgar on 21.06.17.
 */
public class StatusCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    StatusMessage message = (StatusMessage) msg;
    System.out.println("System message: ");
    System.out.println(message.getText());
  }
}
