/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation extends BookingImp {
  private User  user;
  private LocalTime arrivalTime;

  public Reservation(String c, int class_num, LocalTime t, String room, Table tab, User cust, LocalTime arr) {
    super(c, class_num, room, t, tab);
    user = cust;
    arrivalTime = arr;
  }

  public User getCustomer() {
    return user;
  }

  public String getDetails() {
    StringBuffer details = new StringBuffer(64);
    details.append(covers);
    details.append(room);
    return details.toString();
  }

@Override
public String getRoomNumber() {
	return room	;
}


}
