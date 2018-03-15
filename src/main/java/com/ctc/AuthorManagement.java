package com.ctc;

import java.sql.SQLException;
import java.util.ArrayList;

import mariadb.Author;

/**
 * Tasks to manage Authors in DB: new, delete, modify and list all.
 * 
 * @author DavidSauce
 * @version 11-03-2018
 */
public class AuthorManagement {
  /**
   * Creates new Author in DB using object data. Checks if exists and if there are problems.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void newAuthor() throws SQLException {

    Main.c.setAutoCommit(false);

    System.out.println("Enter NEW author full name: ");
    String lAuthorName = Main.keyboard.nextLine().trim();

    Author lAuthor = new Author();
    lAuthor.setAuthorName(lAuthorName);

    if (lAuthor.existsAuthorNameInDB(Main.c)) {
      Main.c.rollback();
      Functions
          .consoleMsg("WARNING: " + lAuthorName + " not created. Author already exists in DB.");
    } else {
      if (lAuthor.addAuthorToDB(Main.c) == 1) {
        Main.c.commit();
        Functions.consoleMsg("INFO: " + lAuthorName + " created succesfully in DB.");
      } else {
        Main.c.rollback();
        Functions.consoleMsg("ERROR: " + lAuthorName + " does not exist but NOT created in DB.");
      }
    }

    Main.c.setAutoCommit(true);
  }

  /**
   * Deletes Author in DB using object data. Checks if exists and if there are problems.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void deleteAuthor() throws SQLException {
    Main.c.setAutoCommit(false);

    System.out.println("Enter Author ID to be deleted: ");
    int lAuthorID = Main.keyboard.nextInt();
    Main.keyboard.nextLine();

    Author lAuthor = new Author(lAuthorID);

    switch (lAuthor.deleteAuthorFromDB(Main.c)) {
    case 1:
      Main.c.commit();
      Functions.consoleMsg("INFO: " + lAuthorID + " deleted SUCCESSFULLY.");
      break;
    case 0:
      Main.c.rollback();
      Functions.consoleMsg(
          "INFO: Author with ID:" + lAuthorID + " does not exist in DB. Nothing to delete.");
      break;
    case -1:
      Main.c.rollback();
      Functions.consoleMsg(
          "INFO: Author with ID:" + lAuthorID + " not deleted. Delete related books first.");
      break;
    default:
      Main.c.rollback();
      Functions.consoleMsg("ERROR: Something happened deleting the Author using ID.");
      break;
    }
    Main.c.setAutoCommit(true);
  }

  /**
   * Modifies Author in DB using object data. Checks if exists and if there are problems.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void modifyAuthor() throws SQLException {
    Main.c.setAutoCommit(false);

    String lAuthorOldName = "";
    String lAuthorNewName = "";

    System.out.println("Enter Author ID to be modified: ");
    int lAuthorID = Main.keyboard.nextInt();
    Main.keyboard.nextLine();
    Author lAuthor = new Author(Main.c, lAuthorID);

    if (lAuthor.getAuthorName() == null) {
      Main.c.rollback();
      Functions.consoleMsg(
          "INFO: Author with ID:" + lAuthorID + " does not exist in DB. Nothing to modify.");
    } else {
      lAuthorOldName = lAuthor.getAuthorName();
      System.out.println("Current name: " + lAuthorOldName + ". Enter new Author Name: ");
      lAuthorNewName = Main.keyboard.nextLine();
      lAuthor.setAuthorName(lAuthorNewName);

      switch (lAuthor.modifyAuthorNameInDB(Main.c)) {
      case 1:
        Main.c.commit();
        Functions.consoleMsg("INFO: Author with ID: " + lAuthorID + " modified SUCCESSFULLY from: "
            + lAuthorOldName + " to: " + lAuthorNewName);
        break;
      case 0:
        Main.c.rollback();
        Functions.consoleMsg(
            "INFO: Author with ID:" + lAuthorID + " does not exist in DB. Nothing to modify.");
        break;
      case -1:
        Main.c.rollback();
        Functions.consoleMsg("INFO: Author with ID:" + lAuthorID + " not modified.");
        break;
      default:
        Main.c.rollback();
        Functions.consoleMsg("ERROR: Something happened deleting the Author using ID.");
        break;
      }
    }

    Main.c.setAutoCommit(true);
  }

  /**
   * Displays in screen all authors in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void getAuthorList() throws SQLException {

    ArrayList<Author> lAuthors = new Author().getAllAuthors(Main.c);

    System.out.println("Author list");
    System.out.println("----------");
    for (Author a : lAuthors) {
      System.out.println(a.getRecordAsString());
    }
    System.out.println();
  }
}
