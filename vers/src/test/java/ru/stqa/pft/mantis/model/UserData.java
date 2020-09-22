package ru.stqa.pft.mantis.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "mantis_user_table")
public class UserData {
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Column(name = "username")
  private String name;

  @Column(name = "realname")
  private String realName;

  @Column(name = "email")
  private String email;


  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public String getRealName() {
    return realName;
  }

  public String getEmail() {
    return email;
  }


  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }


  public UserData withId(int id) {
    this.id = id;
    return this;
  }


  public UserData withName(String name) {
    this.name = name;
    return this;
  }

  public UserData withRealName(String realName) {
    this.realName = realName;
    return this;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return id == userData.id &&
            Objects.equals(name, userData.name) &&
            Objects.equals(realName, userData.realName) &&
            Objects.equals(email, userData.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, realName, email);
  }
}