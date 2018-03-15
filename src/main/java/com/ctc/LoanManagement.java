package com.ctc;

import java.sql.SQLException;
import java.util.ArrayList;

import mariadb.Author;
import mariadb.Book;
import mariadb.Loan;
import mariadb.LoanDetails;
import mariadb.User;

/**
 * Tasks to manage Loans in DB.
 * 
 * @version 11-03-2018
 * @User DavidSauce
 *
 */
public class LoanManagement {

  /**
   * Gets books borrowed to the UserID entered.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void getUserBooks() throws SQLException {
    System.out.println("Enter user ID: ");
    int lUserID = Main.keyboard.nextInt();

    LoanManagement.getLoansList(lUserID);
  }

  /**
   * Borrows BookID to UserID
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void borrowBook() throws SQLException {
    System.out.println("Enter user ID: ");
    int lUserID = Main.keyboard.nextInt();
    User u = new User(Main.c, lUserID);

    if (u.getUserName() == null) {
      System.out.println("User number: " + lUserID + " not detected in database.");
      return;
    }

    System.out.println("User " + u.getUserName() + " detected.");
    System.out.println("Enter book ID: ");
    int lBookID = Main.keyboard.nextInt();
    Book b = new Book(Main.c, lBookID);

    if (b.getBookTitle() == null) {
      System.out.println("Book number: " + lBookID + " not detected in database.");
      return;
    }

    if (u.borrowBook(Main.c, b)) {
      System.out.println("Book \"" + b.getBookTitle() + "\" borrowed to user " + u.getUserName());
      LoanManagement.getLoansList(lUserID);
    }

  }

  /**
   * Returns BookID from UserID
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void returnBook() throws SQLException {
    System.out.println("Enter user ID: ");
    int lUserID = Main.keyboard.nextInt();
    User u = new User(Main.c, lUserID);

    if (u.getUserName() == null) {
      System.out.println("User number: " + lUserID + " not detected in database.");
      return;
    }

    LoanManagement.getLoansList(lUserID);

    System.out.println("Enter book ID: ");
    int lBookID = Main.keyboard.nextInt();
    Book b = new Book(Main.c, lBookID);

    if (b.getBookTitle() == null) {
      System.out.println("Book number: " + lBookID + " not detected in database.");
      return;
    }

    if (u.returnBook(Main.c, b)) {
      LoanManagement.getLoansList(lUserID);
    } else {
      System.out.println(
          "INFO: Book \"" + b.getBookTitle() + "\" was not borrowed to user " + u.getUserName());
    }

  }

  /**
   * Displays in screen all loans in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void getLoansList(int pUserID) throws SQLException {

    Loan lLoan = new Loan(pUserID);
    ArrayList<LoanDetails> lLoanDetails = lLoan.getBorrowedBooks(Main.c);

    if (lLoanDetails.size() == 0) {
      System.out.println("User has 0 books borrowed.");
      return;
    }

    User u = new User(0);

    for (LoanDetails ld : lLoanDetails) {

      if (u.getUserID() != ld.getUserDetails().getUserID()) {
        u = ld.getUserDetails();
        System.out.println();
        System.out.println(
            "User " + u.getUserName() + " has " + u.getBorrowedBooks() + " books borrowed.");
        System.out.println("Books details");
      }
      Book b = ld.getBookDetails();
      Author a = b.getAuthor();

      System.out
          .print(b.getBookID() + " - " + b.getBookTitle() + " - " + a.getAuthorName() + " - ");
      System.out.print(ld.getLoanStartDate().toString() + " - ");
      System.out.println(ld.getLoanEndDate().toString());

    }
    System.out.println();
  }

}
