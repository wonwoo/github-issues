package me.wonwoo.integration.github;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wonwoolee on 2017. 4. 23..
 */
public class GithubUser {

  private String login;

  private Long id;

  @JsonProperty("avatar_url")
  private String avatar;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
}
