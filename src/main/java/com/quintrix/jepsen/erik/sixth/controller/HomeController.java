package com.quintrix.jepsen.erik.sixth.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.quintrix.jepsen.erik.sixth.model.Person;


@RestController // Fun fact: @RestController annotation includes @ResponseBody as part of its
                // definition, making subsequent @ResponseBody annotations within the
                // @RestController redundant.
public class HomeController {
  private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory;
  private RestTemplate restTemplate;

  public HomeController() {
    simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    simpleClientHttpRequestFactory.setReadTimeout(5000);
    simpleClientHttpRequestFactory.setConnectTimeout(5000);
    restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
  }

  @GetMapping("/robert/find")
  public String home() {
    Person[] people =
        restTemplate.getForObject("http://localhost:8080/person/first/robert", Person[].class);
    String reply = "";
    for (int i = 0; i < people.length; i++) {
      reply += people[i].toString();
      if (i < people.length - 1)
        reply += System.lineSeparator();
    }
    return reply;
  }

  @GetMapping("/robert/add")
  public ResponseEntity<String> robertAdd() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("fName", "Robert");
    map.add("lName", "Johnson\", -1); DROP TABLE persons;");
    map.add("deptId", "3");

    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
    ResponseEntity<String> response =
        restTemplate.postForEntity("http://localhost:8080/person/new", httpEntity, String.class);
    if (response.getBody().equals("Success"))
      return response;
    return new ResponseEntity<String>("Fail", HttpStatus.NOT_ACCEPTABLE);
  }

  @GetMapping("/robert/fail")
  public ResponseEntity<String> robertFail() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("fName", "Robert");

    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
    ResponseEntity<String> response;
    try {
      response =
          restTemplate.postForEntity("http://localhost:8080/person/new", httpEntity, String.class);
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

}
