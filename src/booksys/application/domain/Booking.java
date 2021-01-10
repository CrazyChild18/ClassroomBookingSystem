package booksys.application.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Booking {

  public String getCovers();

  public int getClassNum();

  public LocalTime getEndTime();

  public LocalTime getTime();

  public Table getTable();

  public int getTableNumber();
  
  public String getRoomNumber();

  public String getDetails();

  public void setCovers(String c);

  public void setClass(int d);

  public void setTime(LocalTime t);

  public void setTable(Table t);
}
