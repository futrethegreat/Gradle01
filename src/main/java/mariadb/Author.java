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
public class Author {

	private int AuthorID;
	private String AuthorName;

	/**
	 * Constructor: No parameters. Creates an Author empty object.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 */
	public Author() {
		super();
	}

	/**
	 * Constructor: Creates an Author object using parameters pAuthorID and
	 * pAuthorName.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pAuthorID
	 * @param pAuthorName
	 */
	public Author(int pAuthorID, String pAuthorName) {
		super();
		AuthorID = pAuthorID;
		AuthorName = pAuthorName;
	}

	/**
	 * Constructor: Gets Author data from DB with pAuthorID and creates an Author
	 * object with DB data.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @param pAuthorID
	 * @throws SQLException
	 */
	public Author(Connection pConnection, int pAuthorID) throws SQLException {
		PreparedStatement s = pConnection.prepareStatement("SELECT * FROM authors WHERE AuthorID =  ? ");
		s.setInt(1, pAuthorID);
		ResultSet rs = s.executeQuery();

		if (rs.next()) {
			AuthorID = rs.getInt("AuthorID");
			AuthorName = (rs.getString("AuthorName"));
		}
	}

	/**
	 * Constructor: Gets Author data from DB with pAuthorName and creates an Author
	 * object with DB data.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB * @param pAuthorName
	 * @throws SQLException
	 */
	public Author(Connection pConnection, String pAuthorName) {
		try {
			PreparedStatement s = pConnection.prepareStatement("SELECT * FROM authors WHERE AuthorName =  ?");
			s.setString(1, pAuthorName);
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				AuthorID = rs.getInt("AuthorID");
				AuthorName = rs.getString("AuthorName");
			}
		} catch (Exception e) {
			Utils.consoleMsg("ERROR: Constructor Author(Connection pConnection, String pAuthorName)");

		}
	}

	/**
	 * Constructor: Creates an Author object using parameters pAuthorID .
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pAuthorID
	 */
	public Author(int pAuthorID) {
		super();
		AuthorID = pAuthorID;
	}

	/**
	 * Constructor: Creates an Author object using parameters pAuthorName.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pAuthorName
	 */
	public Author(String pAuthorName) {
		super();
		AuthorName = pAuthorName;
	}

	/**
	 * Getter: Returns a String with all Author data concatenated.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return String
	 */
	public String getAuthorAsString() {
		return this.AuthorID + " - " + this.AuthorName;
	}

	/**
	 * Getter: Returns AuthorID.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return int
	 */
	public int getAuthorID() {
		return AuthorID;
	}

	/**
	 * Setter: Sets AuthorID.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param authorID
	 */
	public void setAuthorID(int authorID) {
		AuthorID = authorID;
	}

	/**
	 * Getter: Returns AuthorName.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return String
	 */
	public String getAuthorName() {
		return AuthorName;
	}

	/**
	 * Setter: Sets AuthorName.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param authorName
	 */
	public void setAuthorName(String authorName) {
		AuthorName = authorName;
	}

	/**
	 * Writes a new Author in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return int. Number of rows affected. Should be 1.
	 * @throws SQLException
	 */
	public int addAuthorToDB(Connection pConnection) throws SQLException {
		PreparedStatement s = pConnection.prepareStatement("INSERT INTO authors (AuthorName) VALUES ( ? )");
		s.setString(1, this.getAuthorName());
		int q = s.executeUpdate();
		return q;
	}

	/**
	 * Delete Author from DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return int. Number of rows affected. Should be 1.
	 * @throws SQLException
	 */
	public int deleteAuthorFromDB(Connection pConnection) throws SQLException {
		try {
			PreparedStatement s = pConnection.prepareStatement("DELETE FROM authors WHERE AuthorID = ?");
			s.setInt(1, this.getAuthorID());
			int q = s.executeUpdate();
			return q;
		} catch (SQLIntegrityConstraintViolationException e) {
			return -1;
		}
	}

	/**
	 * Modifies Author name in DB. Sets AuthorName as new value.
	 * 
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return int. Number of rows affected. Should be 1.
	 * @throws SQLException
	 */
	public int modifyAuthorNameInDB(Connection pConnection) throws SQLException {
		try {
			PreparedStatement s = pConnection.prepareStatement("UPDATE authors SET AuthorName = ? WHERE AuthorID = ?");
			s.setString(1, this.getAuthorName());
			s.setInt(2, this.getAuthorID());
			int q = s.executeUpdate();
			return q;
		} catch (SQLIntegrityConstraintViolationException e) {
			return -1;
		}
	}

	/**
	 * Checks if AuthorID exists in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return boolean true if exits and Author with the same ID.
	 * @throws SQLException
	 */
	public boolean existsAuthorIDInDB(Connection pConnection) throws SQLException {
		Author a = new Author(pConnection, this.getAuthorID());
		return a != null;
	}

	/**
	 * Returns true if AuthorName exactly exists in DB.
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @param pConnection
	 *            Active connection to DB
	 * @return boolean true if exits and Author with the same name.
	 * @throws SQLException
	 */
	public boolean existsAuthorNameInDB(Connection pConnection) throws SQLException {
		Author a = new Author(pConnection, this.getAuthorName());
		return a.getAuthorName() != null;
	}

	/**
	 * Returns an ArrayList with all Authors in DB
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return ArrayList<Author> with all authors as Author elements of the array.
	 * @throws SQLException
	 */
	public ArrayList<Author> getAllAuthors(Connection pConnection) throws SQLException {
		ArrayList<Author> lArrayAuthors = new ArrayList<Author>();
		PreparedStatement s = pConnection.prepareStatement("SELECT * FROM authors");
		ResultSet rs = s.executeQuery();

		while (rs.next()) {

			AuthorID = rs.getInt("AuthorID");
			AuthorName = rs.getString("AuthorName");

			lArrayAuthors.add(new Author(AuthorID, AuthorName));
		}
		return lArrayAuthors;
	}

	/**
	 * Return all Author fields concatenated in a String
	 * 
	 * @author DavidSauce
	 * @version 11-03-2018
	 * @return Author record fields concatenated as single String.
	 */
	public String getRecordAsString() {
		return this.AuthorID + " - " + this.AuthorName;
	}

}
