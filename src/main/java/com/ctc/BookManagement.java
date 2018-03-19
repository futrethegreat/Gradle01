package com.ctc;

import java.sql.SQLException;
import java.util.ArrayList;

import mariadb.Book;

/**
 * Tasks to manage Books in DB: new, delete, modify and list all.
 * 
 * @version 11-03-2018
 * @author DavidSauce
 *
 */
public class BookManagement {
  /**
   * Creates new Book in DB using object data. Checks if exists and if there are problems.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void newBook() throws SQLException {
    System.out.println("Enter NEW book full title: ");
    String lBookTitle = Main.keyboard.nextLine().trim();

    Book lBook = new Book();
    lBook.setBookTitle(lBookTitle);

    if (lBook.existsBookTitleInDB(Main.c)) {
      System.out.println("WARNING: " + lBookTitle + " not created. Book already exists in DB.");
    } else {
      if (lBook.addBookToDB(Main.c) == 1) {
        System.out.println("INFO: " + lBookTitle + " created succesfully in DB.");
      } else {
        System.out.println("ERROR: " + lBookTitle + " does not exist but NOT created in DB.");
      }
    }
  }

  /**
   * Deletes Book in DB using object data. Checks if exists and if there are problems.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void deleteBook() throws SQLException {
    System.out.println("Enter Book ID to be deleted: ");
    int lBookID = Main.keyboard.nextInt();
    Main.keyboard.nextLine();

    Book lBook = new Book(lBookID);

    switch (lBook.deleteBookFromDB(Main.c)) {
    case 1:
      System.out.println("INFO: " + lBookID + " deleted SUCCESSFULLY.");
      break;
    case 0:
      System.out.println(
          "INFO: Book with ID:" + lBookID + " does not exist in DB. Nothing to delete.");
      break;
    case -1:
      System.out.println(
          "INFO: Book with ID:" + lBookID + " not deleted. Delete related books first.");
      break;
    default:
      System.out.println("ERROR: Something happened deleting the Book using ID.");
      break;
    }
  }

  /**
   * Modifies Book in DB using object data. Checks if exists and if there are problems.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void modifyBook() throws SQLException {
    String lBookOldTitle = "";
    String lBookNewTitle = "";

    System.out.println("Enter Book ID to be modified: ");
    int lBookID = Main.keyboard.nextInt();
    Main.keyboard.nextLine();
    Book lBook = new Book(Main.c, lBookID);

    if (lBook.getBookTitle() == null) {
      System.out.println(
          "INFO: Book with ID:" + lBookID + " does not exist in DB. Nothing to modify.");
    } else {
      lBookOldTitle = lBook.getBookTitle();
      System.out.println("Current name: " + lBookOldTitle + ". Enter new Book Name: ");
      lBookNewTitle = Main.keyboard.nextLine();
      lBook.setBookTitle(lBookNewTitle);

      switch (lBook.modifyBookTitleInDB(Main.c)) {
      case 1:
        System.out.println("INFO: Book with ID: " + lBookID + " modified SUCCESSFULLY from: "
            + lBookOldTitle + " to: " + lBookNewTitle);
        break;
      case 0:
        System.out.println(
            "INFO: Book with ID:" + lBookID + " does not exist in DB. Nothing to modify.");
        break;
      case -1:
        System.out.println("INFO: Book with ID:" + lBookID + " not modified.");
        break;
      default:
        System.out.println("ERROR: Something happened deleting the Book using ID.");
        break;
      }
    }
  }

  /**
   * Displays in screen all books in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void getBookList() throws SQLException {

    ArrayList<Book> lBooks = new Book().getAllBooks(Main.c);

    System.out.println("Books list");
    System.out.println("----------");
    for (int i = 0; i < lBooks.size(); i++) {
      System.out.println(lBooks.get(i).getRecordAsString());
    }
    System.out.println();
  }

}
