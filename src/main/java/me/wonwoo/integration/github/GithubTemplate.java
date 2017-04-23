package me.wonwoo.integration.github;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wonwoolee on 2017. 4. 23..
 */
@Service
public class GithubTemplate {

  private final CounterService counterService;
  private final RestTemplate restTemplate;

  public GithubTemplate(CounterService counterService, RestTemplateBuilder restTemplateBuilder) {
    this.counterService = counterService;
    this.restTemplate = restTemplateBuilder.build();
  }


  public List<Issue> doGetIssues(String organization, String project, Integer page) {
    String url = String.format(
        "https://api.github.com/repos/%s/%s/issues?page=%s", organization, project, page);
    return Arrays.asList(invoke(createRequestEntity(url), Issue[].class).getBody());
  }

  public Issue doGetIssue(String organization, String project, Long id) {
    String url = String.format(
        "https://api.github.com/repos/%s/%s/issues/%s", organization, project, id);
    return invoke(createRequestEntity(url), Issue.class).getBody();
  }


  private <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
    this.counterService.increment("issues.github.requests");
    try {
      return this.restTemplate.exchange(request, type);
    } catch (RestClientException ex) {
      this.counterService.increment("issues.github.failures");
      throw ex;
    }
  }

  private RequestEntity<?> createRequestEntity(String url) {
    try {
      return RequestEntity.get(new URI(url))
          .accept(MediaType.APPLICATION_JSON).build();
    } catch (URISyntaxException ex) {
      throw new IllegalStateException("Invalid URL " + url, ex);
    }
  }
}
