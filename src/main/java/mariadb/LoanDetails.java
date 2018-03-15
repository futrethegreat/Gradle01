package mariadb;

import java.time.LocalDate;

/**
 * @author DavidSauce
 * @version 11-03-2018
 *
 */
public class LoanDetails {
  private Book BookDetails;
  private User UserDetails;
  private LocalDate LoanStartDate;
  private LocalDate LoanEndDate;

  /**
   * Constructor: Creates a LoanDetails object with params data.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param bookDetails
   * @param userDetails
   * @param loanStartDate
   * @param loanEndDate
   */
  public LoanDetails(Book bookDetails, User userDetails, LocalDate loanStartDate,
      LocalDate loanEndDate) {
    super();
    setBookDetails(bookDetails);
    setUserDetails(userDetails);
    setLoanStartDate(loanStartDate);
    setLoanEndDate(loanEndDate);
  }

  /**
   * Getter: Returns a Book object related to Loan.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return Book
   */
  public Book getBookDetails() {
    return BookDetails;
  }

  /**
   * Setter: Sets the Book object related to Loan.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   */
  public void setBookDetails(Book bookDetails) {
    BookDetails = bookDetails;
  }

  /**
   * Getter: Returns an User object related to Loan.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @return User
   */
  public User getUserDetails() {
    return UserDetails;
  }

  /**
   * Setter: Sets the User object related to Loan.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   */
  public void setUserDetails(User userDetails) {
    UserDetails = userDetails;
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
   * Setter: Sets LoanStartDate.
   * 
   * @author DavidSauce
   * @version 11-03-2018
   * @param loanEndDate
   */
  public void setLoanEndDate(LocalDate loanEndDate) {
    LoanEndDate = loanEndDate;
  }
}
