package edu.technopolis.homework.messenger.messages.commands;

import edu.technopolis.homework.messenger.messages.Message;
import edu.technopolis.homework.messenger.net.Session;

/**
 * Created by ilgar on 20.06.17.
 */
public interface Command  {
    void execute(Session session, Message message) throws CommandException;
}
