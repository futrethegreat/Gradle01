package mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

/**
 * @author DavidSauce
 * @version 11-03-2018
 *
 */
public class Book {

  private int BookID;
  private String BookTitle;
  private int AuthorID;
  private boolean Lent;
  private Author Author;

  /**
   * Constructor: No parameters. Creates a Book empty object.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   */
  public Book() {
  }

  /**
   * Constructor: Creates a Book object using parameters pBookID .
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pAuthorID
   */
  public Book(int pBookID) {
    super();
    BookID = pBookID;
  }

  /**
   * Constructor: Creates a Book object using parameters pBookTitle.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pBookTitle
   */
  public Book(String pBookTitle) {
    super();
    BookTitle = pBookTitle;
  }

  /**
   * Constructor: Creates a Book object using parameters bookID, bookTitle, authorID, lent, author.
   *
   * @author DavidSauce
   * @version 11-03-2018
   * @param bookID
   * @param bookTitle
   * @param authorID
   * @param lent
   * @param author
   */
  public Book(int bookID, String bookTitle, int authorID, boolean lent, Author author) {
    super();
    BookID = bookID;
    BookTitle = bookTitle;
    AuthorID = authorID;
    Lent = lent;
    Author = author;
  }

  /**
   * Constructor: Gets Book data from DB with pBookID and creates a Book object with DB data.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   *          Active connection to DB
   * @param pBookID
   * @throws SQLException
   */
  public Book(Connection pConnection, int pBookID) throws SQLException {
    PreparedStatement s = pConnection.prepareStatement("SELECT * FROM books WHERE BookID =  ? ");
    s.setInt(1, pBookID);
    ResultSet rs = s.executeQuery();

    while (rs.next()) {
      BookID = rs.getInt("BookID");
      BookTitle = rs.getString("BookTitle");
      AuthorID = rs.getInt("AuthorID");
      Lent = rs.getBoolean("Lent");
      Author = new Author(pConnection, AuthorID);
    }
  }

  /**
   * Constructor: Gets Book data from DB with pBookTitle and creates a Book object with DB data.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   *          Active connection to DB
   * @param pBookID
   * @throws SQLException
   */
  public Book(Connection pConnection, String pBookTitle) throws SQLException {
    PreparedStatement s = pConnection.prepareStatement("SELECT * FROM books WHERE BookTitle =  ? ");
    s.setString(1, pBookTitle);
    ResultSet rs = s.executeQuery();

    while (rs.next()) {
      BookID = rs.getInt("BookID");
      BookTitle = rs.getString("BookTitle");
      AuthorID = rs.getInt("AuthorID");
      Lent = rs.getBoolean("Lent");
      Author = new Author(pConnection, AuthorID);
    }
  }

  /**
   * Getter: Returns a String with all Book data concatenated.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return String
   */
  public String getRecordAsString() {
    return this.BookID + " - " + this.BookTitle + " - " + this.Author.getAuthorName() + " - "
        + this.Lent;
  }

  /**
   * Getter: Returns BookID.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return int
   */
  public int getBookID() {
    return BookID;
  }

  /**
   * Setter: Sets BookID.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param bookID
   */
  public void setBookID(int bookID) {
    BookID = bookID;
  }

  /**
   * Getter: Returns BookTitle.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return String
   */
  public String getBookTitle() {
    return BookTitle;
  }

  /**
   * Setter: Sets BookTitle.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param bookTitle
   */
  public void setBookTitle(String bookTitle) {
    BookTitle = bookTitle;
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
   * Getter: Returns if Book is lent (Lent property == true).
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return boolean
   */
  public boolean isLent() {
    return Lent;
  }

  /**
   * Setter: Sets Lent property.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param lent
   */
  public void setLent(boolean lent) {
    Lent = lent;
  }

  /**
   * Setter: Set Lent in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   *          Active connection to DB
   * @return rows affected. Should be 1.
   */
  public int setLent(Connection pConnection) throws SQLException {
    PreparedStatement s = pConnection
        .prepareStatement("UPDATE books SET Lent = ? WHERE BookID = ?");
    s.setBoolean(1, this.isLent());
    s.setInt(2, this.getBookID());
    int q = s.executeUpdate();
    return q;
  }

  /**
   * Getter: Returns Book Author object.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return boolean
   */
  public Author getAuthor() {
    return Author;
  }

  /**
   * Setter: Sets Book Author object.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   */

  public void setAuthor(Author author) {
    Author = author;
  }

  /**
   * Checks if BookID exists in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   *          Active connection to DB
   * @return boolean true if BookID exists in DB.
   * @throws SQLException
   */
  public boolean existsBookIDInDB(Connection pConnection) throws SQLException {
    Book b = new Book(pConnection, this.getBookID());
    return b != null;
  }

  /**
   * Returns true if BookTitle exactly exists in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   *          Active connection to DB * @return boolean
   * @throws SQLException
   * @return boolean true if BookTitle exists in DB.
   * 
   */
  public boolean existsBookTitleInDB(Connection pConnection) throws SQLException {
    Book b = new Book(pConnection, this.getBookTitle());
    return b.getBookTitle() != null;
  }

  /**
   * Writes a new Book in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return int. Number of rows affected. Should be 1.
   * @throws SQLException
   */
  public int addBookToDB(Connection pConnection) throws SQLException {
    PreparedStatement s = pConnection
        .prepareStatement("INSERT INTO books (BookTitle,AuthorID,Lent) VALUES ( ?, ?, ? )");
    s.setString(1, this.getBookTitle());
    s.setInt(2, this.getAuthorID());
    s.setBoolean(3, this.isLent());
    int q = s.executeUpdate();
    return q;
  }

  /**
   * Delete Book from DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return int. Number of rows affected. Should be 1.
   * @throws SQLException
   */
  public int deleteBookFromDB(Connection pConnection) throws SQLException {
    try {
      PreparedStatement s = pConnection.prepareStatement("DELETE FROM books WHERE BookID = ?");
      s.setInt(1, this.getBookID());
      int q = s.executeUpdate();
      return q;
    } catch (SQLIntegrityConstraintViolationException e) {
      return -1;
    }
  }

  /**
   * Modifies Book title in DB. Sets BookTitle as new value.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return int. Number of rows affected. Should be 1.
   * @throws SQLException
   */
  public int modifyBookTitleInDB(Connection pConnection) throws SQLException {
    try {
      PreparedStatement s = pConnection
          .prepareStatement("UPDATE books SET BookTitle = ? WHERE BookID = ?");
      s.setString(1, this.getBookTitle());
      s.setInt(2, this.getBookID());
      int q = s.executeUpdate();
      return q;
    } catch (SQLIntegrityConstraintViolationException e) {
      return -1;
    }
  }

  /**
   * Returns an ArrayList with all Books in DB
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return ArralyList<Book> with all books as Book elements of the array.
   * @throws SQLException
   */
  public ArrayList<Book> getAllBooks(Connection pConnection) throws SQLException {
    ArrayList<Book> lArrayBooks = new ArrayList<Book>();
    PreparedStatement s = pConnection.prepareStatement("SELECT * FROM books");
    ResultSet rs = s.executeQuery();

    while (rs.next()) {

      BookID = rs.getInt("BookID");
      BookTitle = rs.getString("BookTitle");
      AuthorID = rs.getInt("AuthorID");
      Lent = rs.getBoolean("Lent");
      Author = new Author(pConnection, AuthorID);

      lArrayBooks.add(new Book(BookID, BookTitle, AuthorID, Lent, Author));
    }
    return lArrayBooks;
  }
}
