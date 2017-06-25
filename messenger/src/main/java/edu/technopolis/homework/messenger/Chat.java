package edu.technopolis.homework.messenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilgar on 21.06.17.
 */
public class Chat {
  private Long id;
  private List<Long> users;

  public List<Long> getUsers() {
    return users;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsers(List<Long> users) {
    this.users = users;
  }

  public Chat(long id) {
    this.id = id;
    users = new ArrayList<>();
  }
}
