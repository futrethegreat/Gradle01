package com.ctc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import mariadb.DBUnitUtils;
import mariadb.MariaDB;

public class Main {
	static MariaDB javaMySQLBasic = new MariaDB();
	public static Connection c;
	public static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		System.out.println(System.getProperty("user.dir"));
		Utils.setEnvironment();

		try {
			c = javaMySQLBasic.connectDatabase(Utils.dbIP, Utils.dbPort, Utils.dbName, Utils.dbUser, Utils.dbPassword);
			Main.mainMenu();
			keyboard.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: Closing application. Check database connection data and credentials.");

		} catch (Exception e) {
			System.out.println("ERROR: Exception not handled: ");
			e.printStackTrace();
		}

	}

	private static void mainMenu() throws SQLException {
		loop: while (true) {

			System.out.println();
			System.out.println("MENU");
			System.out.println("----");
			System.out.println("1. Author Management.");
			System.out.println("2. Book Management.");
			System.out.println("3. User Management.");
			System.out.println("4. Loan Management.");
			System.out.println("................");
			System.out.println("9. Utils.");
			System.out.println("................");
			System.out.println("0. EXIT.");
			System.out.println("----");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();

			switch (lMenuOption) {
			case 1:
				Main.authorManagementMenu();
				break;
			case 2:
				Main.bookManagementMenu();
				break;
			case 3:
				Main.userManagementMenu();
				break;
			case 4:
				Main.loanManagementMenu();
				break;
			case 9:
				Main.utilsMenu();
				break;
			default:
				System.out.println("BYE !!!");
				break loop;
			}
		}

	}

	private static void authorManagementMenu() throws SQLException {
		loop: while (true) {

			System.out.println();
			System.out.println("AUTHOR MANAGEMENT MENU");
			System.out.println("-----------------------");
			System.out.println("1. New author.");
			System.out.println("2. Delete author.");
			System.out.println("3. Modify author.");
			System.out.println("4. List authors.");
			System.out.println("................");
			System.out.println("0. RETURN PREVIOUS MENU.");
			System.out.println("---------------------------");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();
			keyboard.nextLine(); // to avoid last \n as nextInt does not read it.

			switch (lMenuOption) {
			case 1:
				AuthorManagement.newAuthor();
				break;
			case 2:
				AuthorManagement.deleteAuthor();
				break;
			case 3:
				AuthorManagement.modifyAuthor();
				break;
			case 4:
				AuthorManagement.getAuthorList();
				break;

			default:
				break loop;
			}
		}
	}

	private static void bookManagementMenu() throws SQLException {
		loop: while (true) {

			System.out.println();
			System.out.println("BOOK MANAGEMENT MENU");
			System.out.println("-----------------------");
			System.out.println("1. New book.");
			System.out.println("2. Delete book.");
			System.out.println("3. Modify book.");
			System.out.println("4. List books.");
			System.out.println("................");
			System.out.println("Other. RETURN PREVIOUS MENU.");
			System.out.println("---------------------------");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();
			keyboard.nextLine(); // to avoid last \n as nextInt does not read it.

			switch (lMenuOption) {

			case 1:
				BookManagement.newBook();
				break;
			case 2:
				BookManagement.deleteBook();
				break;
			case 3:
				BookManagement.modifyBook();
				break;
			case 4:
				BookManagement.getBookList();
				break;
			default:
				break loop;
			}
		}
	}

	private static void userManagementMenu() throws SQLException {
		loop: while (true) {

			System.out.println();
			System.out.println("USER MANAGEMENT MENU");
			System.out.println("-----------------------");
			System.out.println("1. New user.");
			System.out.println("2. Delete user.");
			System.out.println("3. Modify user.");
			System.out.println("4. List users.");
			System.out.println("................");
			System.out.println("Other. RETURN PREVIOUS MENU.");
			System.out.println("---------------------------");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();
			keyboard.nextLine(); // to avoid last \n as nextInt does not read it.

			switch (lMenuOption) {

			case 1:
				UserManagement.newUser();
				break;
			case 2:
				UserManagement.deleteUser();
				break;
			case 3:
				UserManagement.modifyUser();
				break;
			case 4:
				UserManagement.getUserList();
				break;
			default:
				break loop;
			}
		}
	}

	private static void loanManagementMenu() throws SQLException {
		loop: while (true) {

			System.out.println();
			System.out.println("LOAN MANAGEMENT MENU");
			System.out.println("-----------------------");
			System.out.println("1. Get user borrowed books.");
			System.out.println("2. Borrow book.");
			System.out.println("3. Return book.");
			System.out.println("4. List loans.");
			System.out.println("................");
			System.out.println("Other. RETURN PREVIOUS MENU.");
			System.out.println("---------------------------");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();
			keyboard.nextLine(); // to avoid last \n as nextInt does not read it.

			switch (lMenuOption) {
			case 1:
				LoanManagement.getUserBooks();
				break;
			case 2:
				LoanManagement.borrowBook();
				break;
			case 3:
				LoanManagement.returnBook();
				break;
			case 4:
				LoanManagement.getLoansList(0);
				break;
			default:
				break loop;
			}
		}
	}

	private static void utilsMenu() throws SQLException {
		loop: while (true) {

			System.out.println();
			System.out.println("UTILITIES MENU");
			System.out.println("-----------------------");
			System.out.println("1. Partial export from DB.");
			System.out.println("2. Full DB export.");
			System.out.println("3. .");
			System.out.println("4. .");
			System.out.println("................");
			System.out.println("Other. RETURN PREVIOUS MENU.");
			System.out.println("---------------------------");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();
			keyboard.nextLine(); // to avoid last \n as nextInt does not read it.

			switch (lMenuOption) {
			case 1:
				DBUnitUtils.generatePartialXML(Utils.dbDriverName, Utils.dbUrl, Utils.dbUser, Utils.dbPassword,
						Utils.dbName, "LibraryPartial");
				break;
			default:
				break loop;
			}
		}
	}
}
