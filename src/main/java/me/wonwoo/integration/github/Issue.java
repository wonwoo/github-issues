package me.wonwoo.integration.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Created by wonwoolee on 2017. 4. 23..
 */
public class Issue {

  private Long id;
  private Long number;
  private String title;
  private String body;
  private String state;

  @JsonProperty("created_at")
  private LocalDateTime created;
  @JsonProperty("updated_at")
  private LocalDateTime updated;
  @JsonProperty("closed_at")
  private LocalDateTime closed;

  private GithubUser user;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public LocalDateTime getClosed() {
    return closed;
  }

  public void setClosed(LocalDateTime closed) {
    this.closed = closed;
  }

  public GithubUser getUser() {
    return user;
  }

  public void setUser(GithubUser user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Issue{" +
        "id=" + id +
        ", number=" + number +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        ", state='" + state + '\'' +
        ", created=" + created +
        ", updated=" + updated +
        ", closed=" + closed +
        ", user=" + user +
        '}';
  }
}
