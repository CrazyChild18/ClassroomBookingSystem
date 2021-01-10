package booksys.application.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class BookingImp implements Booking {
  protected String  covers;
  protected int class_num;
  protected LocalTime time;
  protected Table     table;
  protected String room;

  public BookingImp(String c, int class_num, String room, LocalTime t, Table tab) {
    covers = c;
    this.class_num = class_num;
    time = t;
    table = tab;
    this.room = room;
  }

  public String getCovers() {
    return covers;
  }

  public int getClassNum() {
    return class_num;
  }

  public LocalTime getEndTime() {
    return time.plusHours(2);// End time defaults to 2 hours after time of booking
  }

  public LocalTime getTime() {
    return time;
  }

  public Table getTable() {
    return table;
  }

  public int getTableNumber() {
    return table.getNumber();
  }
  
  public String getRoom() {
	return room;
}

  public void setCovers(String c) {
    covers = c;
  }

  public void setClass(int d) {
	  class_num = d;
  }

  public void setTime(LocalTime t) {
    time = t;
  }

  public void setRoom(String r) {
	room = r;
}
  
  public void setTable(Table t) {
    table = t;
  }
}
