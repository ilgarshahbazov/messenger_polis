package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.ChatListResultMessage;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.StatusMessage;
import edu.technopolis.homework.messenger.net.Session;

import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatListCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    if (session.getUser() == null) {

      session.send(StatusMessage.firstLogIn());

    } else {
      msg.setSenderId(session.getUser().getId());

      List<Long> chatList = session.getServer().getMessageStore()
          .getChatsByUserId(session.getUser().getId());
      ChatListResultMessage response = new ChatListResultMessage();
      response.setChats(chatList);

      session.send(response);

    }
  }
}
