package com.quintrix.jepsen.erik.sixth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"personId", "fName", "lName", "dept"})
public class Person {
  @JsonProperty("fname")
  private String fname;
  @JsonProperty("lname")
  private String lname;
  @JsonProperty("personId")
  private Integer personId;
  @JsonProperty("dept")
  private Department dept;

  public Person() {
    super();
  }

  public Person(String fname, String lname, int personId, Department dept) {
    super();
    this.fname = fname;
    this.lname = lname;
    this.dept = dept;
    this.personId = personId;
  }

  @JsonProperty("personId")
  public Integer getPersonId() {
    return personId;
  }

  @JsonProperty("fname")
  public String getfname() {
    return fname;
  }

  @JsonProperty("lname")
  public String getlname() {
    return lname;
  }

  @JsonProperty("dept")
  public Department getDept() {
    return dept;
  }

  @JsonProperty("fname")
  public void setfname(String fname) {
    this.fname = fname;
  }

  @JsonProperty("lname")
  public void setlname(String lname) {
    this.lname = lname;
  }

  @JsonProperty("dept")
  public void setDept(Department dept) {
    this.dept = dept;
  }

  public boolean equals(Object b) {
    if (b == null)
      return false;
    if (b.getClass() != Person.class)
      return false;
    if (dept == null)
      return ((Person) b).getDept() == null;
    return (this.fname.equals(((Person) b).getfname()))
        && (this.lname.equals(((Person) b).getlname()))
        && (this.dept.equals(((Person) b).getDept()));
  }

  @JsonProperty("personId")
  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public String toString() {
    return String.format("%d. %s %s (dept %d)", personId, fname, lname, dept.getDeptNumber());
  }
}
