package com.ctc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import mariadb.Book;
import mariadb.DBUnitUtils;
import mariadb.MariaDB;
import mariadb.UtilsCTBAdmin;

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

	private static void mainMenu() throws Exception {
		loop: while (true) {

			System.out.println();
			System.out.println("MENU");
			System.out.println("----");
			System.out.println("1. Author Management.");
			System.out.println("2. Book Management.");
			System.out.println("3. User Management.");
			System.out.println("4. Loan Management.");
			System.out.println("................");
			System.out.println("6. Selenium Menu test.");
			System.out.println("7. Selenium test.");
			System.out.println("8. Library Utils.");
			System.out.println("9. CTBAdmin Utils.");
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
			case 6:
				Main.seleniumClickMenuTest();
				break;
			case 7:
				Main.seleniumTest();
				break;
			case 8:
				Main.libraryUtilsMenu();
				break;
			case 9:
				Main.ctbAdminUtilsMenu();
				break;
			default:
				System.out.println("BYE !!!");
				break loop;
			}
		}

	}

	private static void seleniumTest() throws InterruptedException {
		WebDriver wd;
		@SuppressWarnings("unused")
		String parentHandle;
		Utils.setEnvironment();

		wd = openBrowser();

		// HOME
		wd.get("https://iclvtestweb.capitool.com");
		Utils.waitUntil_isPresent(wd, By.linkText("ACCEDER A CUENTA"));
		WebElement lnkSignIn = wd.findElement(By.linkText("ACCEDER A CUENTA"));
		lnkSignIn.click();

		// Pagina de Login
		parentHandle = wd.getWindowHandle();
		for (String winHandle : wd.getWindowHandles()) {
			wd.switchTo().window(winHandle);
		}
		Utils.waitUntil_isPresent(wd, By.name("submitTrefi"));
		WebElement txtUserName = wd.findElement(By.name("signinid"));
		WebElement txtPassword = wd.findElement(By.name("pass"));
		WebElement btnLogin = wd.findElement(By.name("submitTrefi"));
		txtUserName.clear();
		txtPassword.clear();
		txtUserName.sendKeys("davidsauce");
		txtPassword.sendKeys("Welcome1");
		btnLogin.click();

		// Pagina ppal The Tool
		parentHandle = wd.getWindowHandle();
		for (String winHandle : wd.getWindowHandles()) {
			wd.switchTo().window(winHandle);
		}
		Utils.waitUntil_isPresent(wd, By.linkText("Payables"));
		WebElement lnkPayables = wd.findElement(By.linkText("Payables"));
		lnkPayables.click();

		// Pagina Payables
		parentHandle = wd.getWindowHandle();
		for (String winHandle : wd.getWindowHandles()) {
			wd.switchTo().window(winHandle);
		}

		// Access table data
		Utils.waitUntil_isPresent(wd, By.id("ot80"));
		WebElement InvoicesTable = wd.findElement(By.id("ot80"));
		// Table rows
		List<WebElement> tableRows = InvoicesTable.findElements(By.tagName("tr"));
		System.out.println("No of rows are : " + tableRows.size());
		for (int i = 0; i < tableRows.size(); i++) {
			System.out.println(tableRows.get(i).getText());
		}

		// Row#1 columns
		List<WebElement> rowCells = tableRows.get(1).findElements(By.tagName("td"));
		System.out.println("No of cols are : " + rowCells.size());
		for (int i = 0; i < rowCells.size(); i++) {
			System.out.println(rowCells.get(i).getText());
		}
		// Click on first data row
		Utils.waitUntil_isClickable(wd, rowCells.get(1));
		rowCells.get(1).click();

		// Click on list box row to unfold
		WebElement lstOption = wd.findElement(By.xpath("//*[@id=\"selectedActTypeid_chosen\"]/a/div/b"));
		lstOption.click();
		// Click on Pay invoice option from the list
		lstOption = wd.findElement(By.xpath("//*[@id=\"selectedActTypeid_chosen\"]/div/ul/li[1]"));
		lstOption.click();

		// Wait until the list is folded and the Amount field is displayed again. Then
		// type an amount.
		Utils.waitUntil_isPresent(wd, By.id("PAYAMT"));
		WebElement txtAmount = wd.findElement(By.id("PAYAMT"));
		txtAmount.clear();
		txtAmount.sendKeys("1.1");

		BigDecimal amount2Pay = new BigDecimal("1.1");
		BigDecimal previousAmount;
		BigDecimal expectedAmount;
		BigDecimal nextAmount;
		Utils.consoleMsg("Open column: " + rowCells.get(6).getText().replace(",", ""));
		previousAmount = new BigDecimal(rowCells.get(6).getText().replace(",", ""));
		expectedAmount = previousAmount.subtract(amount2Pay);

		// Click on Execute button
		WebElement btnExecute = wd.findElement(By.id("execute1"));
		btnExecute.click();

		// Click on Refresh button
		WebElement btnRefresh = wd.findElement(By.id("refreshicon"));
		btnRefresh.click();
		Thread.sleep(1500); // ESTA ESPERA HAY QUE HACERLO DINAMICO
		InvoicesTable = wd.findElement(By.id("ot80"));
		tableRows = InvoicesTable.findElements(By.tagName("tr"));
		rowCells = tableRows.get(1).findElements(By.tagName("td"));
		nextAmount = new BigDecimal(rowCells.get(6).getText().replace(",", ""));

		Utils.consoleMsg("Previous: " + previousAmount.toString() + " - Expected: " + expectedAmount.toString()
				+ " - Returned: " + nextAmount.toString());
		if (expectedAmount.compareTo(nextAmount) == 0) {
			System.out.println("WORKING GOOD! :-D");
		} else {
			System.out.println("WORKING BAD! X-(");
		}

		Utils.consoleMsg("BYE!!!");

		wd.quit();
	}

	private static void seleniumClickMenuTest() throws InterruptedException {
		WebDriver wd;
		@SuppressWarnings("unused")
		String parentHandle;
		Utils.setEnvironment();

		wd = openBrowser();

		// HOME
		wd.get("https://iclvtestweb.capitool.com");
		Utils.waitUntil_isPresent(wd, By.linkText("ACCEDER A CUENTA"));
		WebElement lnkSignIn = wd.findElement(By.linkText("ACCEDER A CUENTA"));
		lnkSignIn.click();

		// Pagina de Login
		parentHandle = wd.getWindowHandle();
		for (String winHandle : wd.getWindowHandles()) {
			wd.switchTo().window(winHandle);
		}
		Utils.waitUntil_isPresent(wd, By.name("submitTrefi"));
		WebElement txtUserName = wd.findElement(By.name("signinid"));
		WebElement txtPassword = wd.findElement(By.name("pass"));
		WebElement btnLogin = wd.findElement(By.name("submitTrefi"));
		txtUserName.clear();
		txtPassword.clear();
		txtUserName.sendKeys("davidsauce");
		txtPassword.sendKeys("Welcome1");
		btnLogin.click();

		// Pagina ppal The Tool
		parentHandle = wd.getWindowHandle();
		for (String winHandle : wd.getWindowHandles()) {
			wd.switchTo().window(winHandle);
		}
		
		//Minimizar Capital Tool Company Limited
		WebElement lnkCTC = wd.findElement(By.xpath("//*[@title='HK1296102']"));
		lnkCTC.click();
		Thread.sleep(500);
		
		//Maximizar DANPER
		WebElement lnkDANPER = wd.findElement(By.xpath("//*[@title='PE20170040938']"));
		lnkDANPER.click();
		
		//Click en link Payables
		Utils.waitUntil_isPresent(wd, By.linkText("Payables"));
		WebElement lnkPayables = wd.findElement(By.linkText("Payables"));
		lnkPayables.click();
		
		//Minimizar DANPER
		lnkDANPER.click();
		Thread.sleep(500);
		
		//Maximizar CTCLATAM
		WebElement lnkCTCLATAM = wd.findElement(By.xpath("//*[@title='PE20523625633']"));
		lnkCTCLATAM.click();
		
		//Click en link Invoices
		Utils.waitUntil_isPresent(wd, By.linkText("Invoices"));
		WebElement lnkInvoices = wd.findElement(By.linkText("Invoices"));
		lnkInvoices.click();
		
		// Access Supplier tables
		Utils.waitUntil_isPresent(wd, By.id("ot55"));
		WebElement SuppliersTable = wd.findElement(By.id("ot55"));
		// Table rows
		List<WebElement> tableRows = SuppliersTable.findElements(By.tagName("tr"));
		System.out.println("No of rows are : " + tableRows.size());
		for (int i = 0; i < tableRows.size(); i++) {
			System.out.println(tableRows.get(i).getText());
		}

		
		

		// Pagina Payables
		parentHandle = wd.getWindowHandle();
		for (String winHandle : wd.getWindowHandles()) {
			wd.switchTo().window(winHandle);
		}

		// Access table data
		Utils.waitUntil_isPresent(wd, By.id("ot80"));
		WebElement InvoicesTable = wd.findElement(By.id("ot80"));
		// Table rows
		tableRows = InvoicesTable.findElements(By.tagName("tr"));
		System.out.println("No of rows are : " + tableRows.size());
		for (int i = 0; i < tableRows.size(); i++) {
			System.out.println(tableRows.get(i).getText());
		}

		// Row#1 columns
		List<WebElement> rowCells = tableRows.get(1).findElements(By.tagName("td"));
		System.out.println("No of cols are : " + rowCells.size());
		for (int i = 0; i < rowCells.size(); i++) {
			System.out.println(rowCells.get(i).getText());
		}
		// Click on first data row
		Utils.waitUntil_isClickable(wd, rowCells.get(1));
		rowCells.get(1).click();

		// Click on list box row to unfold
		WebElement lstOption = wd.findElement(By.xpath("//*[@id=\"selectedActTypeid_chosen\"]/a/div/b"));
		lstOption.click();
		// Click on Pay invoice option from the list
		lstOption = wd.findElement(By.xpath("//*[@id=\"selectedActTypeid_chosen\"]/div/ul/li[1]"));
		lstOption.click();

		// Wait until the list is folded and the Amount field is displayed again. Then
		// type an amount.
		Utils.waitUntil_isPresent(wd, By.id("PAYAMT"));
		WebElement txtAmount = wd.findElement(By.id("PAYAMT"));
		txtAmount.clear();
		txtAmount.sendKeys("1.1");

		BigDecimal amount2Pay = new BigDecimal("1.1");
		BigDecimal previousAmount;
		BigDecimal expectedAmount;
		BigDecimal nextAmount;
		Utils.consoleMsg("Open column: " + rowCells.get(6).getText().replace(",", ""));
		previousAmount = new BigDecimal(rowCells.get(6).getText().replace(",", ""));
		expectedAmount = previousAmount.subtract(amount2Pay);

		// Click on Execute button
		WebElement btnExecute = wd.findElement(By.id("execute1"));
		btnExecute.click();

		// Click on Refresh button
		WebElement btnRefresh = wd.findElement(By.id("refreshicon"));
		btnRefresh.click();
		Thread.sleep(1500); // ESTA ESPERA HAY QUE HACERLO DINAMICO
		InvoicesTable = wd.findElement(By.id("ot80"));
		tableRows = InvoicesTable.findElements(By.tagName("tr"));
		rowCells = tableRows.get(1).findElements(By.tagName("td"));
		nextAmount = new BigDecimal(rowCells.get(6).getText().replace(",", ""));

		Utils.consoleMsg("Previous: " + previousAmount.toString() + " - Expected: " + expectedAmount.toString()
				+ " - Returned: " + nextAmount.toString());
		if (expectedAmount.compareTo(nextAmount) == 0) {
			System.out.println("WORKING GOOD! :-D");
		} else {
			System.out.println("WORKING BAD! X-(");
		}

		Utils.consoleMsg("BYE!!!");

		wd.quit();
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

	private static void libraryUtilsMenu() throws Exception {
		loop: while (true) {

			System.out.println();
			System.out.println("LIBRARY UTILITIES MENU");
			System.out.println("-----------------------");
			System.out.println("1. Partial export from DB.");
			System.out.println("2. Full DB export.");
			System.out.println("3. Create DB in memory.");
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
			case 2:
				DBUnitUtils.generateXML(Utils.dbDriverName, Utils.dbUrl, Utils.dbUser, Utils.dbPassword, Utils.dbName,
						"LibraryFull");
				break;
			case 3:
				DBUnitUtils.createSchema();
				DBUnitUtils.importDataSet();

				DataSource dataSource = DBUnitUtils.dataSource();
				Connection c = dataSource.getConnection();

				Book b = new Book(c, 1);
				Utils.consoleMsg(b.getRecordAsString());

				c.close();

				break;
			default:
				break loop;
			}
		}
	}

	private static void ctbAdminUtilsMenu() throws Exception {
		loop: while (true) {

			System.out.println();
			System.out.println("CTBADMIN UTILITIES MENU");
			System.out.println("-----------------------");
			System.out.println("1. Partial export from DB.");
			System.out.println("2. Full DB export.");
			System.out.println("3. Create DB in memory.");
			System.out.println("4. .");
			System.out.println();
			System.out.println("9. Test.");
			System.out.println("................");
			System.out.println("Other. RETURN PREVIOUS MENU.");
			System.out.println("---------------------------");
			System.out.println("Choose Option: ");
			int lMenuOption = keyboard.nextInt();
			keyboard.nextLine(); // to avoid last \n as nextInt does not read it.

			switch (lMenuOption) {
			case 1:
				UtilsCTBAdmin.generatePartialXML(Utils.dbCTBAdminDriverName, Utils.dbCTBAdminUrl, Utils.dbCTBAdminUser,
						Utils.dbCTBAdminPassword, Utils.dbCTBAdminName, "CTBAdminPartial");
				break;
			case 2:
				UtilsCTBAdmin.generateXML(Utils.dbCTBAdminDriverName, Utils.dbCTBAdminUrl, Utils.dbCTBAdminUser,
						Utils.dbCTBAdminPassword, Utils.dbCTBAdminName, "CTBAdminFull");
				break;
			case 9:

				MariaDB mDB = new MariaDB();
				Connection conn;
				conn = mDB.connectDatabase(Utils.dbIP, Utils.dbPort, Utils.dbName, Utils.dbUser, Utils.dbPassword);

				// PreparedStatement s = conn.prepareStatement("SELECT * FROM books");
				PreparedStatement s = conn.prepareStatement(
						"select bl.BookID,bl.BookTitle,price from library.books bl inner join shop.books bs on bl.BookID=bs.BookID; ");
				ResultSet rs = s.executeQuery();

				while (rs.next()) {
					System.out.println(rs.getString("bl.BookID") + " - " + rs.getString("bl.BookTitle") + " - "
							+ rs.getString("bs.price"));
				}

				// DBUnitUtils.createSchema();
				// DBUnitUtils.importDataSet();
				//
				// DataSource dataSource = DBUnitUtils.dataSource();
				// Connection c = dataSource.getConnection();
				//
				// Book b = new Book(c, 1);
				// Utils.consoleMsg(b.getRecordAsString());
				//
				// c.close();

				break;
			default:
				break loop;
			}
		}
	}

	@SuppressWarnings("unused")
	private static WebDriver openBrowser() {
		// Passing the real webdriver for the browser selected
		Utils.consoleMsg("Before setting up browser driver for browser: " + Utils.BROWSER);

		System.setProperty(Utils.DriverType, Utils.DriverFile);
		// driver se puede usar en LoginStep por dependency injection usando
		// picocontainer
		// (hook y loginstep extienden base)
		if (Utils.BROWSER == "CHH") {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("headless");
			chromeOptions.addArguments("window-size=1200x800");
			return new ChromeDriver(chromeOptions);
		} else if (Utils.BROWSER == "FFH") {
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(firefoxBinary);
			return new FirefoxDriver(firefoxOptions);

			// FirefoxOptions options = new FirefoxOptions();
			// options.setHeadless(true);
			// return new FirefoxDriver(options);
		} else if (Utils.BROWSER == "CH") {
			return new ChromeDriver();
		} else if (Utils.BROWSER == "FF") {
			return new FirefoxDriver();
		} else {
			return new FirefoxDriver();
		}
	}

}
