/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import booksys.storage.Database;

public class BookingSystem {
  // Attributes:

  private int class_num_input;
  // Associations:
  private School  school = null;
  private List<Booking>         currentBookings;
  private List<Booking>         sameRoomBookings;
  private Booking               selectedBooking;

  private List<BookingObserver> observers  = new ArrayList<BookingObserver>();
  private static BookingSystem  uniqueInstance;

  private BookingSystem() {
	  school = new School();
  }

  public static BookingSystem getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new BookingSystem();
    }
    return uniqueInstance;
  }

  public void addObserver(BookingObserver o) {
    observers.add(o);
  }

  public void notifyObservers() {
    for (BookingObserver bo : observers) {
      bo.update();
    }
  }

  public boolean observerMessage(String message, boolean confirm) {
    BookingObserver bo = (BookingObserver) observers.get(0);
    return bo.message(message, confirm);
  }

  public void setClass(int class_num) {
	 class_num_input = class_num;
    currentBookings = school.getBookings(class_num_input);
    selectedBooking = null;
    notifyObservers();
  }

  public boolean addReservation(String covers, int class_num, LocalTime time, String room_num, int tno, String name, String phone) {
    if (!checkDoubleBooked(time, room_num, null) ) {
      String[] names = name.split("-");
      if(names[0].equals("A")) {
    	  Booking b = school.addReservation(covers, class_num, time, room_num, tno, name, phone);
    	  currentBookings.add(b);
          notifyObservers();
      }else {
    	  JOptionPane.showMessageDialog(null, "Error", "You can't add reservation!!", JOptionPane.ERROR_MESSAGE);
      }
      return true;
    }
    return false;
  }


  public void cancelSelected() {
    if (selectedBooking != null) {
      if (observerMessage("Are you sure?", true)) {
        currentBookings.remove(selectedBooking);
        school.removeBooking(selectedBooking);
        selectedBooking = null;
        notifyObservers();
      }
    }
  }

  
	// 点击显示详细信息
	public void showBooking(int tno, LocalTime time) {
		System.out.println("Check Info");
		selectedBooking = null;
		for (Booking b : currentBookings) {
			if (b.getTableNumber() == tno) {
				if (b.getTime().equals(time)) {
					selectedBooking = b;
				}
			}
			
		}
		if(selectedBooking == null) {
			JDialog addDialog = new JDialog();
			addDialog.setBounds(320, 180, 260, 200);
			addDialog.setTitle("Input info to add a Booking");
			addDialog.getContentPane().setLayout(new GridLayout(6,2));
			
			addDialog.add(new JLabel("Class"));
			JTextField Class_num = new JTextField(80);
			addDialog.add(Class_num);
			
			addDialog.add(new JLabel("Activity"));
			JTextField Covers = new JTextField(80);
			addDialog.add(Covers);
			
			addDialog.add(new JLabel("UserID"));
			JTextField Name = new JTextField(80);
			addDialog.add(Name);
			
			addDialog.add(new JLabel("Password"));
			JTextField Phone = new JTextField(80);
			addDialog.add(Phone);
			
			addDialog.add(new JLabel("Room Num"));
			JTextField Room = new JTextField(80);
			addDialog.add(Room);
			
			addDialog.setVisible(true);
			JButton okBtn = new JButton("Confirm");
			addDialog.add(okBtn);
			JButton cancelButton = new JButton("Cancel");
			addDialog.add(cancelButton);
			okBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("Create booking");
	    			int date = Integer.parseInt(Class_num.getText());
	    			String covers = Covers.getText();
	    			String name = Name.getText();
	    			String phone = Phone.getText();
	    			String room_num = Room.getText();
	    			addReservation(covers, date, time, room_num, tno, name, phone);
	    			addDialog.dispose();
	            }
	        });
			cancelButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	addDialog.dispose();
	            }
	        });
		}else {
			int res = JOptionPane.showConfirmDialog(null, "This is the info of the booking:"+"\n" + 
					"Week: " + selectedBooking.getTableNumber() + "\n" + 
					"Class number: " + selectedBooking.getDetails(), "", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION) {
				cancelSelected();
			}
		}
		
	}

	
  private boolean checkDoubleBooked(LocalTime startTime, int tno, Booking ignore) {
    boolean doubleBooked = false;

    LocalTime endTime = startTime.plusHours(2);
    for (Booking b : currentBookings) {
      if (b != ignore && b.getTableNumber() == tno && startTime.isBefore(b.getEndTime()) && endTime.isAfter(b.getTime())) {
        doubleBooked = true;
        observerMessage("Double booking!", false);
      }
    }
    return doubleBooked;
  }
  
  
  private boolean checkDoubleBooked(LocalTime startTime, String room_num, Booking ignore) {
	    boolean doubleBooked = false;
	    sameRoomBookings = school.getBookings(room_num);
	    LocalTime endTime = startTime.plusHours(2);
	    for (Booking b : sameRoomBookings) {
	      if (b != ignore && b.getRoomNumber() == room_num && startTime == b.getTime()) {
	        doubleBooked = true;
	        observerMessage("Double booking!", false);
	      }System.out.println("checkDoubleBooked"+ (b != ignore && b.getRoomNumber().equals(room_num) && b.getTime().equals(startTime)));
	      System.out.println("checkDoubleBooked: "+ b.getRoomNumber() +", "+room_num+", "+ startTime+", "+b.getTime());
	    }
	    System.out.println("");
	    return doubleBooked;
	  }

//  private boolean checkOverbooking(int tno, int covers) {
//    boolean overflow = false;
//    Table t = restaurant.getTable(tno);
//
//    if (t.getPlaces() < covers) {
//      overflow = !observerMessage("Ok to overfill table?", true);
//    }
//    return overflow;
//  }
  
  public int getClassInput() {
    return class_num_input;
  }

  public List<Booking> getBookings() {
    return new ArrayList<Booking>(currentBookings);
  }

  public Booking getSelectedBooking() {
    return selectedBooking;
  }

  public static List<Table> getTables() {
    return School.getTables();
  }
}
