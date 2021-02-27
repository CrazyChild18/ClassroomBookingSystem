/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation;

import java.awt.*;
import java.awt.event.*;

public class BookingSystemApp extends Frame {
  private static final long serialVersionUID = -8570215931205369707L;
  private StaffUI           ui;
  private String username;

  public BookingSystemApp(String us) {
    setTitle("Restaurant Booking System");
    setResizable(false);

    setMenuBar(new MenuBar());

    Menu fileMenu = new Menu("File");
    getMenuBar().add(fileMenu);

    MenuItem quit = new MenuItem("Quit");
    quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    fileMenu.add(quit);

    Menu dateMenu = new Menu("Select Class");
    getMenuBar().add(dateMenu);

    MenuItem display = new MenuItem("Select...");
    display.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ui.displayDate();
      }
    });
    dateMenu.add(display);
    Menu bookingMenu = new Menu("Booking");
    getMenuBar().add(bookingMenu);
    
    String[] names = us.split("-");
    if(names[0].equals("A")) {
    	
    }else {
    	dateMenu.disable();
    }

    //¹Ø±Õ
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    username = us;
    ui = new StaffUI(this, username);
    this.add(ui);

    this.pack();
    this.show();
  }

}
