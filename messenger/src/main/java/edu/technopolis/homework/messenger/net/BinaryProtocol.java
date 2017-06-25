package edu.technopolis.homework.messenger.net;

import edu.technopolis.homework.messenger.messages.Message;

import java.io.*;

/**
 * Created by ilgar on 21.06.17.
 */
public class BinaryProtocol implements Protocol {

  @Override
  public Message decode(byte[] bytes) throws ProtocolException {
    try (ObjectInputStream ois = new ObjectInputStream(
        new ByteArrayInputStream(bytes))) {
      return (Message) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new ProtocolException("Failed to decode message", e);
    }
  }

  @Override
  public byte[] encode(Message msg) throws ProtocolException {

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(msg);
      return baos.toByteArray();
    } catch (IOException e) {
      throw new ProtocolException("Failed to encode message", e);
    }
  }
}
