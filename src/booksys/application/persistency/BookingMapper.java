/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import booksys.application.domain.Booking;
import booksys.application.domain.User;
import booksys.application.domain.Reservation;
import booksys.application.domain.Table;
import booksys.storage.Database;

public class BookingMapper {
  // Singleton:

  private static BookingMapper uniqueInstance;

  public static BookingMapper getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new BookingMapper();
    }
    return uniqueInstance;
  }

  public List<Booking> getBookings(int class_num_input) {
    List<Booking> v = new ArrayList<Booking>();
    try {
      Database.getInstance();
      Statement stmt = Database.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery("SELECT * FROM Reservation WHERE class='" + class_num_input + "'");
      while (rset.next()) {
        int oid = rset.getInt("oid");
        String covers = rset.getString("covers");
//        LocalDate bdate = LocalDate.parse(rset.getString("date"));
        
        LocalTime btime = LocalTime.parse(rset.getString("time"));
        int table = rset.getInt("table_id");
        int cust = rset.getInt("customer_id");
        String aTimes = rset.getString("arrivalTime");
        String room = rset.getString("room");
        LocalTime atime = null;
        if (aTimes != null) {
          atime = LocalTime.parse(aTimes);
        }
        PersistentTable t = TableMapper.getInstance().getTableForOid(table);
        PersistentUser c = UserMapper.getInstance().getUserForOid(cust);
        PersistentReservation r = new PersistentReservation(oid, covers, class_num_input, btime, room, t, c, atime);
        v.add(r);
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return v;
  }
  
  
  public List<Booking> getBookings(String room) {
	    List<Booking> v = new ArrayList<Booking>();
	    try {
	      Database.getInstance();
	      Statement stmt = Database.getConnection().createStatement();
	      ResultSet rset = stmt.executeQuery("SELECT * FROM Reservation WHERE room='" + room + "'");
	      while (rset.next()) {
	        int oid = rset.getInt("oid");
	        String covers = rset.getString("covers");
//	        LocalDate bdate = LocalDate.parse(rset.getString("date"));
	        
	        LocalTime btime = LocalTime.parse(rset.getString("time"));
	        int table = rset.getInt("table_id");
	        int cust = rset.getInt("customer_id");
	        String aTimes = rset.getString("arrivalTime");
	        int class_num = rset.getInt("class");
	        LocalTime atime = null;
	        if (aTimes != null) {
	          atime = LocalTime.parse(aTimes);
	        }
	        PersistentTable t = TableMapper.getInstance().getTableForOid(table);
	        PersistentUser c = UserMapper.getInstance().getUserForOid(cust);
	        PersistentReservation r = new PersistentReservation(oid, covers, class_num, btime, room, t, c, atime);
	        v.add(r);
	      }
	      rset.close();
	      stmt.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return v;
	  }
  

  public PersistentReservation addReservation(String covers, int class_num, LocalTime time, String room_num, Table table, User customer, LocalTime arrivalTime) {
    int oid = Database.getInstance().getId();
   performUpdate("INSERT INTO Reservation VALUES ('" + oid + "', '" + covers + "', '2020-12-08', '" + time.toString() + "', '" + ((PersistentTable) table).getId() + "', '" + 
		    ((PersistentUser) customer).getId() + "', NULL, '" + String.valueOf(class_num) + "', '" + room_num + "')");
    return new PersistentReservation(oid, covers, class_num, time, room_num, table, customer, arrivalTime);
  }

  public void updateBooking(Booking b) {
    PersistentBooking pb = (PersistentBooking) b;
    StringBuffer sql = new StringBuffer(128);

    sql.append("UPDATE ");
    sql.append("Reservation");
    sql.append(" SET ");
    sql.append(" covers = ");
    sql.append(pb.getCovers());
    sql.append(", date = '");
    sql.append(String.valueOf(pb.getClassNum()));
    sql.append("', time = '");
    sql.append(pb.getTime().toString());
    sql.append("', table_id = ");
    sql.append(((PersistentTable) pb.getTable()).getId());
      PersistentReservation pr = (PersistentReservation) pb;
      sql.append(", customer_id = ");
      sql.append(((PersistentUser) pr.getCustomer()).getId());
      sql.append(", arrivalTime = ");
        sql.append("NULL");
    sql.append(" WHERE oid = ");
    sql.append(pb.getId());

    performUpdate(sql.toString());
  }

  public void deleteBooking(Booking b) {
    performUpdate("DELETE FROM Reservation WHERE oid = '" + ((PersistentBooking) b).getId() + "'");
//    System.out.println("DELETE FROM Reservation WHERE oid = '" + ((PersistentBooking) b).getId() + "'");
  }

  private void performUpdate(String sql) {
    try {
      Database.getInstance();
      Statement stmt = Database.getConnection().createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
