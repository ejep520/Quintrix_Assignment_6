package com.quintrix.jepsen.erik.sixth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quintrix.jepsen.erik.sixth.client.SixthClient;
import com.quintrix.jepsen.erik.sixth.model.Department;
import com.quintrix.jepsen.erik.sixth.model.Person;


@RestController // Fun fact: @RestController annotation includes @ResponseBody as part of its
                // definition, making subsequent @ResponseBody annotations within the
                // @RestController redundant.
public class RobertController {
  private HttpHeaders httpPostHeaders;
  private SixthClient client;
  private RestTemplate restTemplate;
  @Value("${sixth.baseUri}")
  private String baseUri;

  public RobertController() {
    httpPostHeaders = new HttpHeaders();
    httpPostHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    client = new SixthClient();
    restTemplate = client.getRestTemplate();
  }

  @GetMapping("/robert/find")
  public Person[] robertFind() {
    Person[] response = client.getForObject(baseUri + "/person/first/robert", Person[].class);
    return response;
  }

  @GetMapping("/robert/add")
  public ResponseEntity<Person> robertAdd() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    ObjectMapper objectMapper = new ObjectMapper();
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    Department dept = new Department("Administration", 3);
    Person newRobby = new Person("Robert", "Johnson\", -1); DROP TABLE persons;", -1, dept);
    try {
      map.add("person", objectMapper.writeValueAsString(newRobby));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, httpHeaders);
    return restTemplate.postForEntity(baseUri + "/person/new", httpEntity, Person.class);
  }

  @GetMapping("/robert/fail")
  public ResponseEntity<String> robertFail() {
    ResponseEntity<String> response;

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("fName", "Robert");

    try {
      response = restTemplate.postForEntity(baseUri + "/person/new",
          new HttpEntity<MultiValueMap<String, String>>(map, httpPostHeaders), String.class);
    } catch (HttpClientErrorException e) {
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.TEXT_PLAIN);
      String reply = e.getLocalizedMessage() + System.lineSeparator();
      for (StackTraceElement element : e.getStackTrace()) {
        reply += element.toString();
        reply += System.lineSeparator();
      }
      response = new ResponseEntity<String>(reply, httpHeaders, e.getStatusCode());
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

    return restTemplate.patchForObject(baseUri + "/person/" + id.toString() + "/lastName",
        requestEntity, Person.class);
  }
}
