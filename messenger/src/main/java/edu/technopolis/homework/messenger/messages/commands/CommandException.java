package edu.technopolis.homework.messenger.messages.commands;

/**
 * Created by ilgar on 20.06.17.
 */
public class CommandException extends Exception {
  public CommandException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public CommandException(String message) {
    super(message);
  }

  public CommandException(Throwable throwable) {
    super(throwable);
  }
}
