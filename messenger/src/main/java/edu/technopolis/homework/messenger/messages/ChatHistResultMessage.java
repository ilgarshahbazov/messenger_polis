package edu.technopolis.homework.messenger.messages;

import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class ChatHistResultMessage extends Message {
  private List<TextClientMessage> messageList;

  public List<TextClientMessage> getMessageList() {
    return messageList;
  }

  public void setMessageList(List<TextClientMessage> messageList) {
    this.messageList = messageList;
  }

  public ChatHistResultMessage(List<TextClientMessage> messageList) {
    setType(Type.MSG_CHAT_HIST_RESULT);
    this.messageList = messageList;
  }
}
