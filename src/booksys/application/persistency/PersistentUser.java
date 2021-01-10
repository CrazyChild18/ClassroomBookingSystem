/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency;

import booksys.application.domain.User;

class PersistentUser extends User {
  private int oid;


  PersistentUser(int id, String n, String p) {
    super(n, p);
    oid = id;

  }

  int getId() {
    return oid;
  }

  
}
