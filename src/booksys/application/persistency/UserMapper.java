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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import booksys.application.domain.User;
import booksys.storage.Database;

public class UserMapper {
  // Implementation of hidden cache

  private Map<Integer, PersistentUser> cache;

  private PersistentUser getFromCache(int oid) {
    return (PersistentUser) cache.get(oid);
  }

  private PersistentUser getFromCacheByDetails(String name, String phone) {
    for (PersistentUser c : cache.values()) {
      if (name.equals(c.getName()) && phone.equals(c.getPhoneNumber())) {
        return c;
      }
    }
    return null;
  }

  private void addToCache(PersistentUser c) {
    cache.put(c.getId(), c);
  }

  // Constructor:

  private UserMapper() {
    cache = new HashMap<Integer, PersistentUser>();
  }

  // Singleton:

  private static UserMapper uniqueInstance;

  public static UserMapper getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new UserMapper();
    }
    return uniqueInstance;
  }

  public PersistentUser getUser(String n, String p) {
    PersistentUser c = getFromCacheByDetails(n, p);
    if (c == null) {
      c = getUser("SELECT * FROM User WHERE name = '" + n + "' AND phoneNumber = '" + p + "'");
      if (c == null) {
    	 JOptionPane.showMessageDialog(null, "User info error! Can't add reservation!");
      }else {
    	  String[] names = n.split("-");
    	  if(!names[0].equals("A") && !names[0].equals("T") && !names[0].equals("M")) {
    		  JOptionPane.showMessageDialog(null, "User can't add reservation!");
    	  }
      }
      addToCache(c);
    }
    return c;
  }

  PersistentUser getUserForOid(int oid) {
    PersistentUser c = getFromCache(oid);
    if (c == null) {
      c = getUser("SELECT * FROM User WHERE oid ='" + oid + "'");
      if (c != null) {
        addToCache(c);
      }
    }
    return c;
  }

  // Add a customer to the database

  PersistentUser addUser(String name, String phone) {
    PersistentUser c = getFromCacheByDetails(name, phone);
    if (c == null) {
      try {
        Database.getInstance();
        Statement stmt = Database.getConnection().createStatement();
        stmt.executeUpdate("INSERT INTO User (name, phoneNumber)" + "VALUES ('" + name + "', '" + phone + "')");
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      c = getUser(name, phone);
    }
    return c;
  }

  private PersistentUser getUser(String sql) {
    PersistentUser c = null;
    try {
      Database.getInstance();
      Statement stmt = Database.getConnection().createStatement();
      ResultSet rset = stmt.executeQuery(sql);
      while (rset.next()) {
        int oid = rset.getRow();
        String name = rset.getString("name");
        String phone = rset.getString("phoneNumber");
        c = new PersistentUser(oid, name, phone);
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return c;
  }
}
