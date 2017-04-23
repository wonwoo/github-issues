package me.wonwoo.integration.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by wonwoolee on 2017. 4. 23..
 */
@RunWith(SpringRunner.class)
@RestClientTest(GithubTemplate.class)
public class GithubTemplateTests {

  @MockBean
  private CounterService counterService;

  @Autowired
  private MockRestServiceServer server;

  @Autowired
  private GithubTemplate githubTemplate;


  @Test
  public void doGetIssuesTest() {
    expect("https://api.github.com/repos/wonwoo/spring-boot/issues?page=1", "issues.json");
    List<Issue> issues = githubTemplate.doGetIssues("wonwoo", "spring-boot", 1);
    this.server.verify();
    assertThat(issues).hasSize(2);
    assertIssue(issues.get(1), 221828035L, 8904L, "Autoconfigure Spring Integration's `LockRegistryLeaderInitiator`",
        "This PR adds support for auto-configuring Spring Integration's...", "open",
        "vpavic", 1149230L, "https://avatars2.githubusercontent.com/u/1149230?v=3");

    verify(this.counterService, times(1)).increment("issues.github.requests");
  }

  @Test
  public void doGetIssueTest() {
    expect("https://api.github.com/repos/wonwoo/spring-boot/issues/221949625", "issue.json");
    Issue issue = githubTemplate.doGetIssue("wonwoo", "spring-boot", 221949625L);
    assertIssue(issue, 221949625L, 8905L, "Avoiding double registration MBean for HikariDataSource. Fixes gh-5114",
        "Fix for #5114 blabla ttt", "open",
        "artemy-osipov", 1992775L, "https://avatars2.githubusercontent.com/u/1992775?v=3");
    verify(this.counterService, times(1)).increment("issues.github.requests");
  }


  private void assertIssue(Issue issue, Long id, Long number, String title,
                           String body, String state, String login, Long userId, String avatar) {

    assertThat(issue.getId()).isEqualTo(id);
    assertThat(issue.getNumber()).isEqualTo(number);
    assertThat(issue.getTitle()).isEqualTo(title);
    assertThat(issue.getBody()).isEqualTo(body);
    GithubUser user = issue.getUser();
    assertThat(user.getLogin()).isEqualTo(login);
    assertThat(user.getId()).isEqualTo(userId);
    assertThat(user.getAvatar()).isEqualTo(avatar);

  }

  private void expect(String url, String path) {
    this.server.expect(
        requestTo(url))
        .andRespond(withSuccess(
            new ClassPathResource(path, getClass()),
            MediaType.APPLICATION_JSON));
  }
}