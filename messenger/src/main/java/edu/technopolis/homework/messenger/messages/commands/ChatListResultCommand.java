package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.ChatListResultMessage;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.net.Session;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatListResultCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    try {
      ChatListResultMessage message = (ChatListResultMessage) msg;
      System.out.println("Chats: ");
      for (Long chatId: message.getChats()) {
        System.out.println(chatId);
      }
    } catch (ClassCastException e) {
      throw new CommandException(e);
    }
  }
}
