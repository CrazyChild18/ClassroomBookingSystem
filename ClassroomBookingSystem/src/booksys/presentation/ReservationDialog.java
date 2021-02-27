/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation ;

import booksys.application.domain.Reservation ;

import java.awt.* ;
import java.awt.event.* ;

class ReservationDialog extends BookingDialog
{
  protected TextField name ;
  protected TextField phone ;
  protected TextField room ;
  protected Label     nameLabel ;
  protected Label     phoneLabel ;
  protected Label     roomLabel ;
  
  ReservationDialog(Frame owner, String title)
  {
    this(owner, title, null) ;
  }

  // This constructor initializes fields with data from an existing booking.
  // This is useful for completing Exercise 7.6.
  
  ReservationDialog(Frame owner, String title, Reservation r){
    super(owner, title, r) ;

    nameLabel = new Label("Name:", Label.RIGHT) ;
    name = new TextField(30) ;
    if (r != null) {
      name.setText(r.getCustomer().getName()) ;
    }

    phoneLabel = new Label("Phone no:", Label.RIGHT) ;
    phone = new TextField(15) ;
    if (r != null) {
      phone.setText(r.getCustomer().getPhoneNumber()) ;
    }
    
    roomLabel = new Label("Room no:", Label.RIGHT) ;
    room = new TextField(15) ;
    if (r != null) {
      room.setText(r.getCustomer().getPhoneNumber()) ;
    }
        
    // Lay out components in dialog
    
    setLayout( new GridLayout(0, 2) ) ;

    add(timeLabel) ;
    add(time) ;

    add(nameLabel) ;
    add(name) ;

    add(phoneLabel) ;
    add(phone) ;
    
    add(coversLabel) ;
    add(covers) ;
    
    add(roomLabel) ;
    add(room) ;

    add(tableNumberLabel) ;
    add(tableNumber) ;
    
    add(ok) ;
    add(cancel) ;
    
    pack() ;
  }

  String getCustomerName()
  {
    return name.getText() ;
  }

  String getPhoneNumber()
  {
    return phone.getText() ;
  }
  
  String getRoom() {
	  return room.getText();
  }
}
