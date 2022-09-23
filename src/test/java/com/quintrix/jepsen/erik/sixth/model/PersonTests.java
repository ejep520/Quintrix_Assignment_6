package com.quintrix.jepsen.erik.sixth.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTests {
  final String fName = "Joseph R.";
  final String lName = "Bork";

  @Test
  public void GivenIdenticalPersons_WhenTestedForIdenticalness_ReturnTrue() {
    Person person0, person1;
    person0 = new Person();
    person1 = new Person();
    person0.setfname(fName);
    person1.setfname(fName);
    person0.setlname(lName);
    person1.setlname(lName);
    Assertions.assertTrue(person0.equals(person1));
    Department dept = new Department();
    dept.setDeptName("TestDept");
    dept.setDeptNumber(-1);
    person0.setDept(dept);
    Assertions.assertFalse(person0.equals(person1));
    person1.setDept(dept);
    Assertions.assertTrue(person0.equals(person1));
  }

  @Test
  public void GivenPersonAndNull_WhenTestedForEquality_ReturnFalse() {
    Person person0 = new Person();
    person0.setfname(fName);
    person0.setlname(lName);
    Assertions.assertFalse(person0.equals(null));
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  public void GivenPersonAndAnotherClass_WhenTestedForEquality_ReturnFalse() {
    Person person0 = new Person();
    person0.setfname(fName);
    person0.setlname(lName);
    Assertions.assertFalse(person0.equals(fName));
  }
}
