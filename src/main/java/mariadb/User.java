package mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import com.ctc.Utils;

/**
 * @author DavidSauce
 * @version 11-03-2018
 *
 */
public class User {

	private int UserID;
	private String UserName;
	private int BorrowedBooks;

	/**
	 * Constructor: No parameters. Creates an User empty object.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 */
	public User() {

	}

	/**
	 * Constructor: Creates an User object using parameters userID, userName,
	 * borrowedBooks
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param userID
	 * @param userName
	 * @param borrowedBooks
	 */
	public User(int userID, String userName, int borrowedBooks) {
		super();
		UserID = userID;
		UserName = userName;
		BorrowedBooks = borrowedBooks;
	}

	/**
	 * Constructor: Creates an User object using parameters userID
	 *
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param userID
	 */
	public User(int userID) {
		super();
		UserID = userID;
	}

	/**
	 * Constructor: Creates an User object using parameters userName
	 *
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param userName
	 */
	public User(String userName) {
		super();
		UserName = userName;
		// No need to fill BorrowedBooks as it has 0 as default value in DB
	}

	/**
	 * Constructor: Gets User data from DB with pUserID and creates an User object
	 * with DB data.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @param pUserID
	 * @throws SQLException
	 */
	public User(Connection pConnection, int pUserID) throws SQLException {
		PreparedStatement s = pConnection.prepareStatement("SELECT * FROM users WHERE UserID =  ? ");
		s.setInt(1, pUserID);
		ResultSet rs = s.executeQuery();

		while (rs.next()) {
			UserID = rs.getInt("UserID");
			UserName = rs.getString("UserName");
			BorrowedBooks = rs.getInt("BorrowedBooks");
		}
	}

	/**
	 * Constructor: Gets User data from DB with pUserName and creates an User object
	 * with DB data.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @param pUserName
	 * @throws SQLException
	 */
	public User(Connection pConnection, String pUserName) throws SQLException {
		PreparedStatement s = pConnection.prepareStatement("SELECT * FROM users WHERE UserName =  ? ");
		s.setString(1, pUserName);
		ResultSet rs = s.executeQuery();

		while (rs.next()) {
			UserID = rs.getInt("UserID");
			UserName = rs.getString("UserName");
			BorrowedBooks = rs.getInt("BorrowedBooks");
		}
	}

	/**
	 * Getter: Returns a String with all User data concatenated.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return User fields concatenated as a single String.
	 */
	public String getRecordAsString() {
		return this.UserID + " - " + this.UserName + " - " + this.BorrowedBooks;
	}

	/**
	 * Getter: Returns UserID
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return
	 */
	public int getUserID() {
		return UserID;
	}

	/**
	 * Setter: Sets UserID.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param userID
	 */
	public void setUserID(int userID) {
		UserID = userID;
	}

	/**
	 * Getter: Returns UserName.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * Setter: Sets UserName
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param userName
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * Getter: Returns BorrowedBooks.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return
	 */
	public int getBorrowedBooks() {
		return BorrowedBooks;
	}

	/**
	 * Setter: Sets BorrowedBooks.
	 * 
	 * @author DavidSauce
	 * @param borrowedBooks
	 */
	public void setBorrowedBooks(int borrowedBooks) {
		BorrowedBooks = borrowedBooks;
	}

	/**
	 * Setter: Set BorrowedBooks in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 */
	public int setBorrowedBooks(Connection pConnection) throws SQLException {
		PreparedStatement s = pConnection.prepareStatement("UPDATE users SET BorrowedBooks = ? WHERE UserID = ?");
		s.setInt(1, this.getBorrowedBooks());
		s.setInt(2, this.getUserID());
		int q = s.executeUpdate();
		return q;
	}

	/**
	 * Checks if Userid exists in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return boolean true if UserID exists in DB.
	 * @throws SQLException
	 */
	public boolean existsUserIDInDB(Connection pConnection) throws SQLException {
		User u = new User(pConnection, this.getUserID());
		return u != null;
	}

	/**
	 * Returns true if UserName exactly exists in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 * @return boolean true if UserName exists in DB.
	 * @throws SQLException
	 */
	public boolean existsUserNameInDB(Connection pConnection) throws SQLException {
		User u = new User(pConnection, this.getUserName());
		return u.getUserName() != null;
	}

	/**
	 * Writes a new User in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return int. Number of rows affected. Should be 1.
	 * @throws SQLException
	 */
	public int addUserToDB(Connection pConnection) throws SQLException {
		PreparedStatement s = pConnection.prepareStatement("INSERT INTO users (UserName) VALUES ( ? )");
		s.setString(1, this.getUserName());
		int q = s.executeUpdate();
		return q;
	}

	/**
	 * Delete User from DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 * @return int. Number of rows affected. Should be 1.
	 * @throws SQLException
	 */
	public int deleteUserFromDB(Connection pConnection) throws SQLException {
		try {
			PreparedStatement s = pConnection.prepareStatement("DELETE FROM users WHERE UserID = ?");
			s.setInt(1, this.getUserID());
			int q = s.executeUpdate();
			return q;
		} catch (SQLIntegrityConstraintViolationException e) {
			return -1;
		}
	}

	/**
	 * Modifies UserName in DB. Sets UserName as new value.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 * @return int. Number of rows affected. Should be 1.
	 * @throws SQLException
	 */
	public int modifyUserNameInDB(Connection pConnection) throws SQLException {
		try {
			PreparedStatement s = pConnection.prepareStatement("UPDATE users SET UserName = ? WHERE UserID = ?");
			s.setString(1, this.getUserName());
			s.setInt(2, this.getUserID());
			int q = s.executeUpdate();
			return q;
		} catch (SQLIntegrityConstraintViolationException e) {
			return -1;
		}
	}

	/**
	 * Returns an ArrayList with all Users in DB
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return ArrayList<User> with all users as User elements of the array.
	 * @throws SQLException
	 */
	public ArrayList<User> getAllUsers(Connection pConnection) throws SQLException {
		ArrayList<User> lArrayUsers = new ArrayList<User>();
		PreparedStatement s = pConnection.prepareStatement("SELECT * FROM users");
		ResultSet rs = s.executeQuery();

		while (rs.next()) {

			UserID = rs.getInt("UserID");
			UserName = rs.getString("UserName");
			BorrowedBooks = rs.getInt("BorrowedBooks");

			lArrayUsers.add(new User(UserID, UserName, BorrowedBooks));
		}
		return lArrayUsers;
	}

	/**
	 * Borrows a book to an users. Set Book.Lent=True, increase User.BorrowedBook
	 * and creates record in Loans table.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param b
	 *            Book to borrow
	 * @param pConnection
	 *            Active connection to DB
	 * @return boolean. True if Book is borrowed to user. False if not.
	 * @throws SQLException
	 */
	public boolean borrowBook(Connection pConnection, Book b) throws SQLException {
		if (b.isLent()) {
			Utils.consoleMsg("INFO: Book \"" + b.getBookTitle() + "\" is not available to lent.");
			return false;
		}

		// Insert record in Loans table
		new Loan(b.getBookID(), this.UserID).insertLoan(pConnection);

		// Increase Borrowed books
		this.increaseBorrowedBooksInDB(pConnection);

		// Set Book as lent
		b.setLent(true);
		b.setLent(pConnection);

		return true;
	}

	/**
	 * Returns a book to the library. Set Book.Lent=False, decrease
	 * User.BorrowedBook and deletes record in Loans table.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param b
	 *            Book to return
	 * @param pConnection
	 *            Active connection to DB
	 * @return boolean. True if Book is returned to library. False if not.
	 * @throws SQLException
	 */
	public boolean returnBook(Connection pConnection, Book b) throws SQLException {

		// Delete record in Loans table
		if (new Loan(b.getBookID(), this.UserID).deleteLoan(pConnection) == 0) {
			return false;
		}

		// Decrease Borrowed books
		this.decreaseBorrowedBooksInDB(pConnection);

		// Set Book as not lent
		b.setLent(false);
		b.setLent(pConnection);

		return true;
	}

	/**
	 * Increase user BorrowedBooks field in DB
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @throws SQLException
	 */
	public int increaseBorrowedBooksInDB(Connection pConnection) throws SQLException {
		try {
			PreparedStatement s = pConnection.prepareStatement("UPDATE users SET BorrowedBooks = ? WHERE UserID = ?");
			s.setInt(1, this.BorrowedBooks + 1);
			s.setInt(2, this.getUserID());
			int q = s.executeUpdate();
			return q;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Decrease user BorrowedBooks field in DB
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @throws SQLException
	 */
	public int decreaseBorrowedBooksInDB(Connection pConnection) throws SQLException {
		try {
			PreparedStatement s = pConnection.prepareStatement("UPDATE users SET BorrowedBooks = ? WHERE UserID = ?");
			s.setInt(1, this.BorrowedBooks - 1);
			s.setInt(2, this.getUserID());
			int q = s.executeUpdate();
			return q;
		} catch (Exception e) {
			return -1;
		}
	}
}
