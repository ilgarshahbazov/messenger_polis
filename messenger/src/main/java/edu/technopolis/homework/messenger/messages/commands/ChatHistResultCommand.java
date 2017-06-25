package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.*;
import edu.technopolis.homework.messenger.net.Session;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatHistResultCommand implements Command {
  @Override
  public void execute(Session session, Message msg) throws CommandException {
    ChatHistResultMessage message = (ChatHistResultMessage) msg;
    List<TextClientMessage> msgs = message.getMessageList();
    msgs.sort(Comparator.comparing(TextMessage::getTimestamp));
    for (TextClientMessage current: msgs) {
      CommandByMessage.getCommand(Type.MSG_TEXT_CLIENT)
          .execute(null, current);
    }
  }
}
