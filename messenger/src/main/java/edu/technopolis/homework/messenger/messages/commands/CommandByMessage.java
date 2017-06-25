package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.Type;

import java.util.HashMap;

/**
 * Created by ilgar on 21.06.17.
 */
public class CommandByMessage {
  private static HashMap<Type, Command> messageToCommand;

  static {
    messageToCommand = new HashMap<>();
    messageToCommand.put(Type.MSG_LOGIN, new LoginCommand());
    messageToCommand.put(Type.MSG_TEXT, new TextCommand());
    messageToCommand.put(Type.MSG_CHAT_LIST, new ChatListCommand());
    messageToCommand.put(Type.MSG_CHAT_LIST_RESULT, new ChatListResultCommand());
    messageToCommand.put(Type.MSG_STATUS, new StatusCommand());
    messageToCommand.put(Type.MSG_TEXT_CLIENT, new TextClientCommand());
    messageToCommand.put(Type.MSG_CHAT_HIST, new ChatHistCommand());
    messageToCommand.put(Type.MSG_CHAT_HIST_RESULT, new ChatHistResultCommand());
    messageToCommand.put(Type.MSG_CHAT_CREATE, new ChatCreateCommand());
    messageToCommand.put(Type.MSG_INFO, new InfoCommand());
  }

  public static Command getCommand(Type type) {
    return messageToCommand.get(type);
  }
}
