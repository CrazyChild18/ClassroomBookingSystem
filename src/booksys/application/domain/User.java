/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain;

public class User {
  private String name;
  private String phoneNumber;
  private Boolean can;

  public User(String n, String p) {
    name = n;
    phoneNumber = p;
  }
  
  public String getName() {
    return name;
  }
  

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
