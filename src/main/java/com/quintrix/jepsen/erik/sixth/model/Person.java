package com.quintrix.jepsen.erik.sixth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"personId", "fName", "lName", "deptId"})
public class Person {
  @JsonProperty("fName")
  private String fName;
  @JsonProperty("lName")
  private String lName;
  @JsonProperty("personId")
  private Integer personId;
  @JsonProperty("deptId")
  private Integer deptId;

  public Person() {
    super();
  }

  public Person(String fName, String lName, int personId, int deptId) {
    super();
    this.fName = fName;
    this.lName = lName;
    this.deptId = deptId;
    this.personId = personId;
  }

  @JsonProperty("personId")
  public Integer getPersonId() {
    return personId;
  }

  @JsonProperty("fName")
  public String getfName() {
    return fName;
  }

  @JsonProperty("lName")
  public String getlName() {
    return lName;
  }

  @JsonProperty("deptId")
  public Integer getDeptId() {
    return deptId;
  }

  @JsonProperty("fName")
  public void setfName(String fName) {
    this.fName = fName;
  }

  @JsonProperty("lName")
  public void setlName(String lName) {
    this.lName = lName;
  }

  @JsonProperty("deptId")
  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public boolean equals(Object b) {
    if (b == null)
      return false;
    if (b.getClass() != Person.class)
      return false;
    return (this.fName.equals(((Person) b).getfName()))
        && (this.lName.equals(((Person) b).getlName()))
        && (this.deptId.equals(((Person) b).getDeptId()));
  }

  @JsonProperty("personId")
  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public String toString() {
    return String.format("%d. %s %s (dept %d)", personId, fName, lName, deptId);
  }
}
