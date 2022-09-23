package com.quintrix.jepsen.erik.sixth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import com.quintrix.jepsen.erik.sixth.client.SixthClient;
import com.quintrix.jepsen.erik.sixth.model.Department;
import com.quintrix.jepsen.erik.sixth.model.Person;

public class RobertControllerTests {
  @Test
  public void GivenMocks_WhenFindRobertCalled_ExpectFoo() {
    SixthClient client = Mockito.mock(SixthClient.class);
    RestTemplate template = Mockito.mock(RestTemplate.class);
    Person[] persons =
        new Person[] {new Person("Test", "Human", -1, new Department("Test Department", -1))};

    Mockito.when(client.getRestTemplate()).thenReturn(template);
    Mockito.when(client.getForObject(anyString(), any(), any())).thenReturn(persons);

    RobertController controller = new RobertController(client);

    assertEquals(controller.robertFind(), persons);
  }
}
