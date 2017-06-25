package edu.technopolis.homework.messenger;

/**
 *
 */
public class User {
  private Long id;
  private String name;

  @Override
  public String toString() {
    return "User{"
        + "id=" + id
        + ", name='" + name + '\''
        + '}';
  }

  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}