package mariadb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

import com.ctc.Utils;

public class DBUnitUtils

{

	public static void generateXML(String driverName, String urlDB, String userDB, String passwordDB, String schemaBD,
			String nameXML) throws SQLException {

		Connection conn = null;

		try {
			// Connect to the database
			DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
			conn = DriverManager.getConnection(urlDB, userDB, passwordDB);
			IDatabaseConnection connection = new DatabaseConnection(conn, schemaBD);

			DatabaseSequenceFilter filter = new DatabaseSequenceFilter(connection);
			IDataSet datasetAll = new FilteredDataSet(filter, connection.createDataSet());
			QueryDataSet partialDataSet = new QueryDataSet(connection);

			String[] listTableNames = filter.getTableNames(datasetAll);
			for (int i = 0; i < listTableNames.length; i++) {
				final String tableName = listTableNames[i];
				// Specify the SQL to run to retrieve the data
				partialDataSet.addTable(tableName);

			}

			// Specify the location of the flat file(XML)
			FlatXmlWriter datasetWriter = new FlatXmlWriter(
					new FileOutputStream(Utils.path2XML + nameXML + ".xml"));

			// Export the data
			datasetWriter.write(partialDataSet);

		} catch (Exception exc) {
			exc.printStackTrace();

		} finally {
			Utils.consoleMsg("Partial DB " + schemaBD + " successfully exported to:" + nameXML);
			conn.close();
		}
	}

	public static void generatePartialXML(String driverName, String urlDB, String userDB, String passwordDB,
			String schemaBD, String nameXML) throws SQLException {
		Connection conn = null;

		try {
			// Connect to the database
			DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
			conn = DriverManager.getConnection(urlDB, userDB, passwordDB);
			IDatabaseConnection connection = new DatabaseConnection(conn, schemaBD);

			DatabaseConfig dbConfig = connection.getConfig();

			// added this line to get rid of the warning
			dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

			QueryDataSet partialDataSet = new QueryDataSet(connection);
			// Specify the SQL to run to retrieve the data
			partialDataSet.addTable("authors");
			partialDataSet.addTable("books");
			partialDataSet.addTable("loans");
			partialDataSet.addTable("users");

			// Specify the location of the flat file(XML)
			FlatXmlWriter datasetWriter = new FlatXmlWriter(
					new FileOutputStream(Utils.path2XML + nameXML + ".xml"));

			// Export the data
			datasetWriter.write(partialDataSet);

			Utils.consoleMsg("File: " + Utils.path2XML + nameXML + ".xml created successfully!");

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public static void createData(String driverName, String urlDB, String userDB, String passworDB, String nameXML)
			throws SQLException {

		Connection conn = null;
		try {
			// Connect to the database
			DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
			conn = DriverManager.getConnection(urlDB, userDB, passworDB);
			IDatabaseConnection connection = new DatabaseConnection(conn);

			DatabaseOperation.INSERT.execute(connection,
					// new FlatXmlDataSet(new FileInputStream("C:\\" + nameXML + ".xml")));
					new FlatDtdDataSet(new FileInputStream(Utils.path2XML + nameXML + ".xml")));

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public static void deleteData(String driverName, String urlDB, String userDB, String passworDB, String nameXML)
			throws SQLException {
		Connection conn = null;
		try {
			// Connect to the database
			DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
			conn = DriverManager.getConnection(urlDB, userDB, passworDB);
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseOperation.DELETE.execute(connection,
					// new FlatXmlDataSet(new FileInputStream("C:\\" + nameXML + ".xml")));
					new FlatDtdDataSet(new FileInputStream(Utils.path2XML + nameXML + ".xml")));

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			conn.close();
		}
	}

}