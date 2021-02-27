/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain;

public class Table {
  private int number;
  private String places;

  public Table(int n, String c) {
    number = n;
    places = c;
  }

  public int getNumber() {
    return number;
  }

  public String getPlaces() {
    return places;
  }
}
