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
import java.util.List;

import booksys.application.persistency.BookingMapper;
import booksys.application.persistency.UserMapper;
import booksys.application.persistency.TableMapper;

class School {
  BookingMapper  bm = BookingMapper.getInstance();
  UserMapper cm = UserMapper.getInstance();
  TableMapper    tm = TableMapper.getInstance();

  List<Booking> getBookings(int class_num_input) {
    return bm.getBookings(class_num_input);
  }
  
  List<Booking> getBookings(String room_num) {
	    return bm.getBookings(room_num);
	  }

  User getCustomer(String name, String phone) {
    return cm.getUser(name, phone);
  }

  Table getTable(int n) {
    return tm.getTable(n);
  }

  static List<Table> getTables() {
    return TableMapper.getInstance().getTables();
  }

  public Booking addReservation(String covers, int class_num, LocalTime time, String room_num, int tno, String name, String phone) {
    Table t = getTable(tno);
    User c = getCustomer(name, phone);
    
    return bm.addReservation(covers, class_num, time, room_num, t, c, null);
  }


  public void updateBooking(Booking b) {
    bm.updateBooking(b);
  }

  public void removeBooking(Booking b) {
    bm.deleteBooking(b);
  }
}
