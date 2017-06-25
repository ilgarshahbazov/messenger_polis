package edu.technopolis.homework.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import edu.technopolis.homework.messenger.User;
import edu.technopolis.homework.messenger.messages.commands.CommandByMessage;
import edu.technopolis.homework.messenger.messages.commands.CommandException;
import edu.technopolis.homework.messenger.messages.Message;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session implements Runnable {

  /**
   * Пользователь сессии, пока не прошел логин, user == null
   * После логина устанавливается реальный пользователь
   */
  static final int MAX_MESSAGE_SIZE = 65536;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  private User user;

  // сокет на клиента
  private Socket socket;

  /**
   * С каждым сокетом связано 2 канала in/out
   */
  private InputStream in;
  private OutputStream out;

  public MessengerServer getServer() {
    return server;
  }

  private MessengerServer server;

  public void send(Message msg) {
    try {
      out.write(server.getProtocol().encode(msg));
      out.flush();
    } catch (IOException | ProtocolException e) {
      e.printStackTrace();
    }

  }

  public void onMessage(Message msg) {
    try {
      CommandByMessage.getCommand(msg.getType()).execute(this, msg);
    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    if (user != null) {
      getServer().getActiveUsers().remove(user.getId());
    }
    try {
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Session(Socket socket, MessengerServer server) {
    this.socket = socket;
    this.server = server;

    try {
      in = socket.getInputStream();
      out = socket.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    byte [] binMessage = new byte[MAX_MESSAGE_SIZE];
    while (true) {
      try {
        int result = in.read(binMessage);
        if (result == -1 || server.isFinished()) {
          close();
          break;
        }
        Message message = server.getProtocol().decode(binMessage);
        onMessage(message);
      } catch (IOException | ProtocolException e) {
        e.printStackTrace();
        close();
        break;
      }
    }
  }
}