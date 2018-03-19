package com.ctc;

import java.sql.SQLException;
import java.util.ArrayList;

import mariadb.User;

/**
 * Tasks to manage Users in DB: new, delete, modify and list all.
 * 
 * @version 11-03-2018
 * @User DavidSauce
 *
 */
public class UserManagement {

  /**
   * Creates new User in DB using object data. Checks if exists and if there are problems.
   * 
   * @User DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void newUser() throws SQLException {
    System.out.println("Enter NEW user full name: ");
    String lUserName = Main.keyboard.nextLine().trim();

    User lUser = new User();
    lUser.setUserName(lUserName);

    if (lUser.existsUserNameInDB(Main.c)) {
      System.out.println("WARNING: " + lUserName + " not created. User already exists in DB.");
    } else {
      if (lUser.addUserToDB(Main.c) == 1) {
        System.out.println("INFO: " + lUserName + " created succesfully in DB.");
      } else {
        System.out.println("ERROR: " + lUserName + " does not exist but NOT created in DB.");
      }
    }
  }

  /**
   * Deletes User in DB using object data. Checks if exists and if there are problems.
   * 
   * @User DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void deleteUser() throws SQLException {
    System.out.println("Enter User ID to be deleted: ");
    int lUserID = Main.keyboard.nextInt();
    Main.keyboard.nextLine();

    User lUser = new User(lUserID);

    switch (lUser.deleteUserFromDB(Main.c)) {
    case 1:
      System.out.println("INFO: " + lUserID + " deleted SUCCESSFULLY.");
      break;
    case 0:
      System.out.println(
          "INFO: User with ID:" + lUserID + " does not exist in DB. Nothing to delete.");
      break;
    case -1:
      System.out.println(
          "INFO: User with ID:" + lUserID + " not deleted. Return borrowed books first.");
      break;
    default:
      System.out.println("ERROR: Something happened deleting the User using ID.");
      break;
    }
  }

  /**
   * Modifies User in DB using object data. Checks if exists and if there are problems.
   * 
   * @User DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void modifyUser() throws SQLException {
    String lUserOldName = "";
    String lUserNewName = "";

    System.out.println("Enter User ID to be modified: ");
    int lUserID = Main.keyboard.nextInt();
    Main.keyboard.nextLine();
    User lUser = new User(Main.c, lUserID);

    if (lUser.getUserName() == null) {
      System.out.println(
          "INFO: User with ID:" + lUserID + " does not exist in DB. Nothing to modify.");
    } else {
      lUserOldName = lUser.getUserName();
      System.out.println("Current name: " + lUserOldName + ". Enter new User Name: ");
      lUserNewName = Main.keyboard.nextLine();
      lUser.setUserName(lUserNewName);

      switch (lUser.modifyUserNameInDB(Main.c)) {
      case 1:
        System.out.println("INFO: User with ID: " + lUserID + " modified SUCCESSFULLY from: "
            + lUserOldName + " to: " + lUserNewName);
        break;
      case 0:
        System.out.println(
            "INFO: User with ID:" + lUserID + " does not exist in DB. Nothing to modify.");
        break;
      case -1:
        System.out.println("INFO: User with ID:" + lUserID + " not modified.");
        break;
      default:
        System.out.println("ERROR: Something happened deleting the User using ID.");
        break;
      }
    }
  }

  /**
   * Displays in screen all Users in DB.
   * 
   * @User DavidSauce
   * @version 11-03-2018
   * @throws SQLException
   */
  static void getUserList() throws SQLException {

    ArrayList<User> lUsers = new User().getAllUsers(Main.c);

    System.out.println("User list");
    System.out.println("----------");
    for (int i = 0; i < lUsers.size(); i++) {
      System.out.println(lUsers.get(i).getRecordAsString());
    }
    System.out.println();
  }

}
