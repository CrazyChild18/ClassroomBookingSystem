/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency;

import java.time.LocalDate;
import java.time.LocalTime;

import booksys.application.domain.*;

class PersistentReservation extends Reservation implements PersistentBooking {
  private int oid;

  public PersistentReservation(int id, String c, int class_num, LocalTime time, String room, Table tab, User cust, LocalTime arrivalTime) {
    super(c, class_num, time, room, tab, cust, arrivalTime);
    oid = id;
  }

  /* public because getId defined in an interface and hence public */

  public int getId() {
    return oid;
  }
}
