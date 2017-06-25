package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.Chat;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.StatusMessage;
import edu.technopolis.homework.messenger.messages.TextClientMessage;
import edu.technopolis.homework.messenger.messages.TextMessage;
import edu.technopolis.homework.messenger.net.Session;

import java.time.LocalDateTime;

/**
 * Created by ilgar on 21.06.17.
 */
public class TextCommand implements Command {
  @Override
  public void execute(Session session, Message msg)
      throws CommandException {
    if (session.getUser() == null) {

      session.send(StatusMessage.firstLogIn());

    } else {
      msg.setSenderId(session.getUser().getId());

      TextMessage textMessage = (TextMessage) msg;
      textMessage.setTimestamp(LocalDateTime.now());
      Chat chat = session.getServer().getMessageStore()
          .getChatById(textMessage.getChatId());
      if (!chat.getUsers().contains(session.getUser().getId())) {

        session.send(StatusMessage.wrongChatMessage());

      }

      session.getServer().getMessageStore()
          .addMessage(textMessage.getChatId(), msg);

      for (Long userId : chat.getUsers()) {
        TextClientMessage outMessage
            = new TextClientMessage(textMessage,
            session.getUser().getName());
        System.out.println(session.getUser().getName());
        Session cur = session.getServer().getActiveUsers().get(userId);
        if (cur != null) {
          cur.send(outMessage);
        }
      }
    }
  }
}
