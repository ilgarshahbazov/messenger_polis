package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.Chat;
import edu.technopolis.homework.messenger.User;
import edu.technopolis.homework.messenger.messages.*;
import edu.technopolis.homework.messenger.net.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatHistCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    if (session.getUser() == null) {
      session.send(StatusMessage.firstLogIn());
    }
    ChatHistMessage message = (ChatHistMessage) msg;
    Chat chat = session.getServer().getMessageStore()
        .getChatById(message.getChatId());
    if (!chat.getUsers().contains(session.getUser().getId())) {
      session.send(StatusMessage.wrongChatMessage());
    }
    List<Long> msgIds = session.getServer().getMessageStore()
        .getMessagesFromChat(message.getChatId());
    List<TextClientMessage> result = new ArrayList<>();

    for (Long id: msgIds) {
      TextMessage currentMessage = (TextMessage) session.getServer()
          .getMessageStore().getMessageById(id);
      User user = session.getServer().getUserStore()
          .getUserById(currentMessage.getSenderId());
      result.add(new TextClientMessage(currentMessage, user.getName()));
    }

    session.send(new ChatHistResultMessage(result));

  }
}
