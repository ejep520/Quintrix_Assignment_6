package com.quintrix.jepsen.erik.sixth.model;

public class Person {
  private String fName, lName;
  private Integer personId, deptId;

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

  public Integer getPersonId() {
    return personId;
  }

  public String getfName() {
    return fName;
  }

  public String getlName() {
    return lName;
  }

  public Integer getDeptId() {
    return deptId;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }

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

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public String toString() {
    return String.format("%d. %s %s (dept %d)", personId, fName, lName, deptId);
  }
}
