package mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author DavidSauce
 * @version 11-03-2018
 *
 */
public class Loan {

  private int BookID;
  private int UserID;
  private LocalDate LoanStartDate;
  private LocalDate LoanEndDate;

  /**
   * Constructor: No parameters. Creates a Loan empty object.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   */
  public Loan() {
    super();
  }

  /**
   * Constructor: Creates a Loan object using parameters bookID, userID, loanStartDate, loanEndDate.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param bookID
   * @param userID
   * @param loanStartDate
   * @param loanEndDate
   */
  public Loan(int bookID, int userID, LocalDate loanStartDate, LocalDate loanEndDate) {
    super();
    BookID = bookID;
    UserID = userID;
    LoanStartDate = loanStartDate;
    LoanEndDate = loanEndDate;
  }

  /**
   * Constructor: Creates a Loan object using parameters bookID, userID.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param bookID
   * @param userID
   */
  public Loan(int bookID, int userID) {
    super();
    BookID = bookID;
    UserID = userID;

  }

  /**
   * Constructor: Creates a Loan object using parameters userID.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param userID
   */
  public Loan(int userID) {
    super();
    UserID = userID;
  }

  /**
   * Constructor: Creates a Loan object using data from DB for loan with pBookID.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   *          Active connection to DB
   * @return Loan Loan identified by pBookID or null if not exists.
   */
  public Loan getLoanByBookID(Connection pConnection, int pBookID) throws SQLException {
    Loan l;
    PreparedStatement s = pConnection.prepareStatement("SELECT * FROM loans WHERE BookID =  ? ");
    s.setInt(1, this.getBookID());
    ResultSet rs = s.executeQuery();

    if (rs.next()) {
      l = new Loan();
      l.setBookID(rs.getInt("BookID"));
      l.setUserID(rs.getInt("UserID"));
      l.setLoanStartDate(rs.getDate("LoanStartDate").toLocalDate());
      l.setLoanEndDate(rs.getDate("LoanEndDate").toLocalDate());
    } else {
      l = null;
    }

    return l;
  }

  /**
   * Getter: Returns a String with all Loan data concatenated.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return String
   */
  public String getRecordAsString() {
    return this.BookID + " - " + this.UserID + " - " + this.LoanStartDate + " - "
        + this.LoanEndDate;
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
   * Getter: Returns LoanStartDate.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return LocalDate
   */
  public LocalDate getLoanStartDate() {
    return LoanStartDate;
  }

  /**
   * Setter: Sets LoanStartDate.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param loanStartDate
   */
  public void setLoanStartDate(LocalDate loanStartDate) {
    LoanStartDate = loanStartDate;
  }

  /**
   * Getter: Returns LoanEndDate.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return LocalDate
   */
  public LocalDate getLoanEndDate() {
    return LoanEndDate;
  }

  /**
   * Setter: Sets LoanEndDate.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param loanEndDate
   */
  public void setLoanEndDate(LocalDate loanEndDate) {
    LoanEndDate = loanEndDate;
  }

  /**
   * Writes a new Loan in DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return int. Number of rows affected. Should be 1 or 0.
   * @throws SQLException
   */
  public int insertLoan(Connection pConnection) throws SQLException {
    this.setLoanStartDate(LocalDate.now());
    this.setLoanEndDate(LocalDate.now().plusMonths(1));

    PreparedStatement s = pConnection.prepareStatement("INSERT INTO loans VALUES ( ?, ?, ?, ? )");
    s.setInt(1, this.getBookID());
    s.setInt(2, this.getUserID());
    s.setDate(3, java.sql.Date.valueOf(this.getLoanStartDate()));
    s.setDate(4, java.sql.Date.valueOf(this.getLoanEndDate()));
    int q = s.executeUpdate();
    return q;

    // Statement s = pConnection.createStatement();
    // s.executeQuery("insert into loans values (" + this.BookID + "," + this.UserID + ",'"
    // + LocalDate.now() + "','" + LocalDate.now().plusMonths(1) + "')");
    // return true;
  }

  /**
   * Deletes a Loan from DB.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return int. Number of rows affected. Should be 1 or 0.
   * @throws SQLException
   */
  public int deleteLoan(Connection pConnection) throws SQLException {
    PreparedStatement s = pConnection
        .prepareStatement("DELETE FROM loans WHERE BookID = ? AND UserID = ?");
    s.setInt(1, this.getBookID());
    s.setInt(2, this.getUserID());
    int q = s.executeUpdate();
    return q;
  }

  /**
   * Returns an ArrayList with loans details in DB as LoanDetails elements of the array. If UserID =
   * 0, returns all loans. Else, returns loans for UserID.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param pConnection
   * @return ArralyList<LoanDetails>
   * @throws SQLException
   */
  public ArrayList<LoanDetails> getBorrowedBooks(Connection pConnection) throws SQLException {

    ArrayList<LoanDetails> lArrayLoans = new ArrayList<LoanDetails>();
    String lQuery = "SELECT u.UserID,UserName,BorrowedBooks,b.BookID,BookTitle,a.AuthorID, AuthorName,LoanStartDate,LoanEndDate "
        + "FROM books b INNER JOIN authors a ON b.AuthorID = a.AuthorID "
        + "INNER JOIN loans l on l.BookID = b.BookID "
        + "INNER JOIN users u on u.UserID = l.UserID";

    if (this.UserID != 0) {
      lQuery = lQuery + " WHERE u.UserID = ?";
    }

    PreparedStatement s = pConnection.prepareStatement(lQuery);

    if (this.UserID != 0) {
      s.setInt(1, this.UserID);
    }

    ResultSet rs = s.executeQuery();

    while (rs.next()) {

      User lUser = new User(rs.getInt("u.UserID"), rs.getString("UserName"),
          rs.getInt("BorrowedBooks"));
      Author lAuthor = new Author(rs.getInt("a.AuthorID"), rs.getString("AuthorName"));
      Book lBook = new Book(rs.getInt("b.BookID"), rs.getString("BookTitle"),
          rs.getInt("a.AuthorID"), true, lAuthor);
      LoanDetails lLoanDetails = new LoanDetails(lBook, lUser,
          rs.getDate("LoanStartDate").toLocalDate(), rs.getDate("LoanEndDate").toLocalDate());

      lArrayLoans.add(lLoanDetails);
    }

    return lArrayLoans;
  }
}
