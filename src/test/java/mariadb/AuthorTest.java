package mariadb;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorTest {
  int AuthorID;
  String AuthorName;

  @Before
  public void setUp() throws Exception {
    AuthorID = 1;
    AuthorName = "Stephen King";
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testAuthor() {

    // Check constructor
    Author author = new Author();

    // Check Set methods
    author.setAuthorID(AuthorID);
    author.setAuthorName(AuthorName);

    // Check Get methods
    assertEquals("Author ID not properly set.", AuthorID, author.getAuthorID());
    assertEquals("Author name not properly set.", AuthorName, author.getAuthorName());

    return;
  }

}
