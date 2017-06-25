package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.Chat;
import edu.technopolis.homework.messenger.User;
import edu.technopolis.homework.messenger.messages.ChatCreateMessage;
import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.messages.StatusMessage;
import edu.technopolis.homework.messenger.messages.TextMessage;
import edu.technopolis.homework.messenger.net.Session;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatCreateCommand implements Command {
  @Override
  public void execute(Session session, Message message) throws CommandException {
    ChatCreateMessage msg = (ChatCreateMessage) message;

    if (session.getUser() == null) {
      session.send(StatusMessage.firstLogIn());
      return;
    }
    int uniqueSize = new HashSet<>(msg.getUsers()).size();
    if (msg.getUsers().contains(session.getUser().getId())
        || uniqueSize != msg.getUsers().size()
        || uniqueSize < 1) {
      StatusMessage response = new StatusMessage();
      response.setText("Incorrect parameters");
      session.send(response);
      return;
    }

    for (Long id: msg.getUsers()) {
      User user = session.getServer().getUserStore().getUserById(id);
      if (user == null) {
        StatusMessage response = new StatusMessage();
        response.setText("Incorrect user: " + id.toString());
        session.send(response);
        return;
      }
    }

    if (msg.getUsers().size() == 1) {
      List<Long> myChats = session.getServer().getMessageStore()
          .getChatsByUserId(session.getUser().getId());
      List<Long> hisChats = session.getServer().getMessageStore()
          .getChatsByUserId(msg.getUsers().get(0));
      myChats.retainAll(hisChats);
      for (Long chatId: myChats) {
        Chat chat = session.getServer()
            .getMessageStore().getChatById(chatId);
        if (chat.getUsers().size() == 2) {
          StatusMessage response = new StatusMessage();
          response.setText("Chat existed, id: " + chatId.toString());

          session.send(response);
          return;

        }
      }
    }
    Long newChatId = session.getServer().getMessageStore().addChat();
    session.getServer().getMessageStore()
        .addUserToChat(session.getUser().getId(), newChatId);
    System.out.println("adduser?");
    for (Long id: msg.getUsers()) {
      session.getServer().getMessageStore()
          .addUserToChat(id, newChatId);
    }
    StatusMessage response = new StatusMessage();
    response.setText("New chat created, id: " + newChatId.toString());

    session.send(response);

    TextMessage firstMessage = new TextMessage();
    firstMessage.setTimestamp(LocalDateTime.now());
    firstMessage.setSenderId(session.getUser().getId());
    firstMessage.setChatId(newChatId);
    firstMessage.setText("User " + session.getUser().getName()
        + " started chat #" + newChatId.toString());
    CommandByMessage.getCommand(firstMessage.getType())
        .execute(session, firstMessage);
  }
}
