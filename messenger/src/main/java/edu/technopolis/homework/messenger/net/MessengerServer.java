package edu.technopolis.homework.messenger.net;

import edu.technopolis.homework.messenger.store.MessageStore;
import edu.technopolis.homework.messenger.store.Messages;
import edu.technopolis.homework.messenger.store.UserStore;
import edu.technopolis.homework.messenger.store.Users;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Основной класс для сервера сообщений
 */
public class MessengerServer {

  public static final int DEFAULT_MAX_CONNECT = 16;

  private int port;

  public boolean isFinished() {
    return finished;
  }

  private boolean finished = false;



  private MessageStore messageStore;
  private UserStore userStore;

  private int maxConnection = DEFAULT_MAX_CONNECT;

  private ExecutorService service;

  private ConcurrentHashMap<Long, Session> activeUsers;

  public ConcurrentHashMap<Long, Session> getActiveUsers() {
    return activeUsers;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setProtocol(Protocol protocol) {

    this.protocol = protocol;
  }

  private Protocol protocol;

  public MessageStore getMessageStore() {
    return messageStore;
  }
  public UserStore getUserStore() {
    return userStore;
  }

  public void start() throws IOException, SQLException, ClassNotFoundException {
    activeUsers = new ConcurrentHashMap<>();
    service = Executors.newFixedThreadPool(DEFAULT_MAX_CONNECT);
    port = 19000;
    protocol = new BinaryProtocol();
    messageStore = new Messages();
    userStore = new Users();

    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("Started");

    while (true) {
      Socket socket = serverSocket.accept();
      Session session = new Session(socket, this);
      service.submit(session);
    }

  }

  public void stop() {
    finished = true;
    try {
      service.awaitTermination(2, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
    MessengerServer server = new MessengerServer();
    server.start();
  }

}