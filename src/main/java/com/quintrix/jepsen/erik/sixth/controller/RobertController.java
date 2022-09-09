package com.quintrix.jepsen.erik.sixth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.quintrix.jepsen.erik.sixth.model.Person;


@RestController // Fun fact: @RestController annotation includes @ResponseBody as part of its
                // definition, making subsequent @ResponseBody annotations within the
                // @RestController redundant.
public class RobertController {
  private HttpHeaders httpPostHeaders;
  private RestTemplate restTemplate;
  private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory;

  @Value("${sixth.baseUri}")
  private String baseUri;

  public RobertController() {
    simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    simpleClientHttpRequestFactory.setReadTimeout(5000);
    simpleClientHttpRequestFactory.setConnectTimeout(5000);
    restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
    httpPostHeaders = new HttpHeaders();
    httpPostHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
  }

  @GetMapping("/robert/find")
  public Person[] robertFind() {
    return restTemplate.getForObject(baseUri + "/person/first/robert", Person[].class);
  }

  @GetMapping("/robert/add")
  public Person robertAdd() {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("fName", "Robert");
    map.add("lName", "Johnson\", -1); DROP TABLE persons;");
    map.add("deptId", "3");

    return restTemplate.postForObject(baseUri + "/person/new",
        new HttpEntity<MultiValueMap<String, String>>(map, httpPostHeaders), Person.class);
  }

  @GetMapping("/robert/fail")
  public ResponseEntity<String> robertFail() {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("fName", "Robert");

    ResponseEntity<String> response;
    try {
      response = restTemplate.postForEntity(baseUri + "/person/new",
          new HttpEntity<MultiValueMap<String, String>>(map, httpPostHeaders), String.class);
    } catch (HttpClientErrorException e) {
      String reply = e.getLocalizedMessage() + System.lineSeparator();
      for (StackTraceElement element : e.getStackTrace()) {
        reply += element.toString();
        reply += System.lineSeparator();
      }
      response = new ResponseEntity<String>(reply, HttpStatus.NOT_ACCEPTABLE);
    }
    return response;
  }

  @GetMapping("/robert/delete/{id}")
  public void robertDelete(@PathVariable int id) {
    try {
      restTemplate.delete(baseUri + "/person/delete/{id}", id);
    } catch (RestClientException e) {
      e.printStackTrace();
    }
  }

  @GetMapping("/robert/rename/{id}/{lName}")
  public Person robertRename(@PathVariable Integer id, @PathVariable String lName) {
    MultiValueMap<String, String> map;
    HttpEntity<MultiValueMap<String, String>> requestEntity;

    map = new LinkedMultiValueMap<String, String>();
    map.add("lName", lName);

    requestEntity = new HttpEntity<>(map, httpPostHeaders);

    return restTemplate.patchForObject(baseUri + "/person/" + id.toString() + "/last",
        requestEntity, Person.class);
  }
}
