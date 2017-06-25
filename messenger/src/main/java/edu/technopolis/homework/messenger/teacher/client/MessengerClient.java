package edu.technopolis.homework.messenger.teacher.client;

import edu.technopolis.homework.messenger.messages.*;
import edu.technopolis.homework.messenger.messages.commands.CommandByMessage;
import edu.technopolis.homework.messenger.messages.commands.CommandException;
import edu.technopolis.homework.messenger.net.BinaryProtocol;
import edu.technopolis.homework.messenger.net.Protocol;
import edu.technopolis.homework.messenger.net.ProtocolException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MessengerClient {

  /**
   * Протокол, хост и порт инициализируются из конфига
   *
   * */
  private Protocol protocol;
  private int port;
  private String host;


  /**
   * Тред "слушает" сокет на наличие входящих сообщений от сервера
   */
  private Thread socketThread;
  private static Thread mainThread;

  /**
   * С каждым сокетом связано 2 канала in/out
   */
  private InputStream in;
  private OutputStream out;

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void initSocket() throws IOException {
    Socket socket = new Socket(host, port);
    in = socket.getInputStream();
    out = socket.getOutputStream();

    /**
     * Инициализируем поток-слушатель. Синтаксис лямбды скрывает создание анонимного класса Runnable
     */
    socketThread = new Thread(() -> {
      final byte[] buf = new byte[1024 * 64];
      System.out.println("Starting listener thread...");
      while (!Thread.currentThread().isInterrupted()) {
        try {
          // Здесь поток блокируется на ожидании данных
          int read = in.read(buf);
          if (read > 0) {

            // По сети передается поток байт, его нужно раскодировать с помощью протокола
            Message msg = protocol.decode(Arrays.copyOf(buf, read));
            onMessage(msg);
          } else if (read == -1) {
            close();
          }
        } catch (Exception e) {
          System.out.println("Failed to process connection: {}" + e.getMessage());
          Thread.currentThread().interrupt();
        }
      }
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    socketThread.start();
  }

  /**
   * Реагируем на входящее сообщение
   */
  public void onMessage(Message msg) {
    try {
      CommandByMessage.getCommand(msg.getType()).execute(null, msg);
    } catch (CommandException e) {
      e.printStackTrace();
    }
  }

  /**
   * Обрабатывает входящую строку, полученную с консоли
   * Формат строки можно посмотреть в вики проекта
   */
  public void processInput(String line) throws IOException, ProtocolException {
    String[] tokens = line.split(" ");
    System.out.println("Tokens: {" + Arrays.toString(tokens) + "}");
    if (tokens.length == 0) {
      System.out.println("invalid input");
    }
    String cmdType = tokens[0];
    switch (cmdType) {
      case "/login":
        LoginMessage loginMessage = new LoginMessage();
        if (tokens.length < 3) {
          System.out.println("Invalid input: " + line);
        }
        loginMessage.setLogin(tokens[1]);
        loginMessage.setType(Type.MSG_LOGIN);
        loginMessage.setPassword(tokens[2]);
        send(loginMessage);
        break;
      case "/help":
        System.out.println("/help - показать список " +
            "команд и общий хэлп по месседжеру");
        System.out.println("-----------------------------------------");
        System.out.println("/login <логин_пользователя> <пароль>" +
            "\n" +
            "/login qwerty qwerty");
        System.out.println("залогиниться (если логин не указан, " +
            "то авторизоваться)");
        System.out.println("-----------------------------------------");
        System.out.println("/info [id]");
        System.out.println("получить всю информацию о пользователе, " +
            "без аргументов - о себе");
        System.out.println("-----------------------------------------");
        System.out.println("/chat_list");
        System.out.println("получить список чатов " +
            "пользователя(только для залогиненных пользователей).");
        System.out.println("-----------------------------------------");
        System.out.println("/chat_create <user_id list>");
        System.out.println("создать новый чат, список пользователей " +
            "приглашенных в чат (только для залогиненных " +
            "пользователей).");
        System.out.println("/chat_create 1,2,3,4 - создать чат с " +
            "пользователями id=1, id=2, id=3, id=4");
        System.out.println("/chat_create 3 - создать чат с " +
            "пользователем id=3, если такой чат уже существует," +
            " вернуть существующий");
        System.out.println("-----------------------------------------");
        System.out.println("/chat_history <chat_id>");
        System.out.println("список сообщений из указанного чата " +
            "(только для залогиненных пользователей)");
        System.out.println("-----------------------------------------");
        System.out.println("/text <id> <message>");
        System.out.println("отправить сообщение в заданный чат, чат " +
            "должен быть в списке чатов пользователя " +
            "(только для залогиненных пользователей)");
        System.out.println("/text 3 Hello, it's pizza time!" +
            " - отправить " +
            "указанное сообщение в чат id=3");
        System.out.println("-----------------------------------------");

        break;
      case "/text":
        if (tokens.length < 2) {
          System.out.println("invalid input");
        }
        TextMessage sendMessage = new TextMessage();
        sendMessage.setChatId(Long.parseLong(tokens[1]));
        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < tokens.length; ++i) {
          builder.append(tokens[i]);
          builder.append(" ");
        }
        sendMessage.setText(builder.toString());
        send(sendMessage);
        break;
      case "/chat_list":
        ChatListMessage msg = new ChatListMessage();
        send(msg);
        break;
      case "/info":
        InfoMessage infoMessage = new InfoMessage();
        if (tokens.length > 1) {
          try {
            infoMessage.setUserId(Long.parseLong(tokens[1]));
          } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + line);
          }
        }
        send(infoMessage);
        break;
      case "/chat_history":
        if (tokens.length < 2) {
          System.out.println("Invalid input: " + line);
        }
        ChatHistMessage chatHistMessage = new ChatHistMessage();
        try {
          chatHistMessage.setChatId(Long.parseLong(tokens[1]));
        } catch (NumberFormatException e) {
          System.out.println("Invalid input: " + line);
        }
        send(chatHistMessage);
        break;
      case "/chat_create":
        if (tokens.length < 2) {
          System.out.println("Invalid input: " + line);
        }
        ChatCreateMessage chatCreateMessage = new ChatCreateMessage();
        chatCreateMessage.setUsers(new ArrayList<>());
        for (int i = 1; i < tokens.length; ++i) {
          try {
            chatCreateMessage.getUsers().add(Long.parseLong(tokens[i]));
          } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + line);
          }
        }
        send(chatCreateMessage);
        break;
      default:
        System.out.println("Invalid input: " + line);
    }
  }

  /**
   * Отправка сообщения в сокет клиент -> сервер
   */
  public void send(Message msg) throws IOException, ProtocolException {
    System.out.println(msg.toString());
    if (!closed) {
      out.write(protocol.encode(msg));
    }
    out.flush(); // принудительно проталкиваем буфер с данными
  }


  private boolean closed = false;

  public void close() {
    socketThread.interrupt();
    mainThread.interrupt();
    if (!closed) {
      try {
        in.close();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      closed = true;
    }
  }

  public MessengerClient(Protocol protocol, int port, String host) {
    this.protocol = protocol;
    this.port = port;
    this.host = host;
  }

  public static void main(String[] args) throws Exception {
    mainThread = Thread.currentThread();
    MessengerClient client =
        new MessengerClient(new BinaryProtocol(), 19000, "localhost");
    try {
      client.initSocket();
      // Цикл чтения с консоли
      Scanner scanner = new Scanner(System.in);
      System.out.println("$");
      while (!Thread.currentThread().isInterrupted()) {
        String input = scanner.nextLine();
        if ("q".equals(input)) {
          return;
        }
        try {
          client.processInput(input);
        } catch (ProtocolException | IOException e) {
          System.out.println("Failed to process user input" + e.getMessage());
        }
      }
    } catch (Exception e) {
      System.out.println("Application failed." + e.getMessage());
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }
}