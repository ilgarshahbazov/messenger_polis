package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.TextClientMessage;
import edu.technopolis.homework.messenger.net.Session;

/**
 * Created by ilgar on 21.06.17.
 */
public class TextClientCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    TextClientMessage message = (TextClientMessage) msg;
    System.out.print(message.getTimestamp());
    System.out.print("| ");
    System.out.print( message.getSenderLogin());
    System.out.print(": ");
    System.out.println(message.getText());
  }
}
