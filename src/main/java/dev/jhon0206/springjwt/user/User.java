package dev.jhon0206.springjwt.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 50, nullable = false)
  private String name;
  @Column(name = "last_name", length = 100, nullable = false)
  private String lastName;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(name = "password", nullable = false)
  private String pass;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RolesEnum role;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusEnum status;

  public User() {
  }

  public User(Integer id, String name, String lastName, String email, String pass, RolesEnum role,
      StatusEnum status) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.pass = pass;
    this.role = role;
    this.status = status;
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public RolesEnum getRole() {
    return role;
  }

  public void setRole(RolesEnum role) {
    this.role = role;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public static class UserBuilder {
    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String pass;
    private RolesEnum role;
    private StatusEnum status;

    public UserBuilder id(Integer id) {
      this.id = id;
      return this;
    }

    public UserBuilder name(String name) {
      this.name = name;
      return this;
    }

    public UserBuilder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder pass(String pass) {
      this.pass = pass;
      return this;
    }

    public UserBuilder role(RolesEnum role) {
      this.role = role;
      return this;
    }

    public UserBuilder status(StatusEnum status) {
      this.status = status;
      return this;
    }

    public User build() {
      return new User(id, name, lastName, email, pass, role, status);
    }
  }
}
