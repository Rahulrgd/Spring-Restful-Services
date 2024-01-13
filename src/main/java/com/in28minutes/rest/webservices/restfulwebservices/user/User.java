package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_details")
public class User {

  @Id
  @GeneratedValue
  private Integer id;

  public List<Post> getPost() {
    return post;
  }

  public void setPost(List<Post> post) {
    this.post = post;
  }

  @Size(min = 2, message = "Name must have atlease 2 characters.")
  // @JsonProperty("user-name")
  private String name;

  @Past(message = "Birth Date must be in past.")
  // @JsonProperty("birth-date")
  private LocalDate birthDate;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Post> post;

  public User() {}

  public User(Integer id, String name, LocalDate birthDate) {
    super();
    this.id = id;
    this.name = name;
    this.birthDate = birthDate;
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

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public String toString() {
    return (
      "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]"
    );
  }
}
