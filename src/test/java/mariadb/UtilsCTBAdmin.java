package mariadb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import com.ctc.Utils;

public class UtilsCTBAdmin

{

	public static void generateXML(String driverName, String urlDB, String userDB, String passwordDB, String schemaBD,
			String nameXML) throws SQLException {

		Connection conn = null;

		try {
			// Connect to the database
			DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
			conn = DriverManager.getConnection(urlDB, userDB, passwordDB);
			IDatabaseConnection connection = new DatabaseConnection(conn, schemaBD);

			/*
			 * Added these 2 lines to get rid of the warning WARN
			 * org.dbunit.dataset.AbstractTableMetaData - Potential problem found: The
			 * configured data type factory 'class
			 * org.dbunit.dataset.datatype.DefaultDataTypeFactory' might cause problems with
			 * the current database 'MySQL' (e.g. some datatypes may not be supported
			 * properly). In rare cases you might see this message because the list of
			 * supported database products is incomplete (list=[derby]). If so please
			 * request a java-class update via the forums.If you are using your own
			 * IDataTypeFactory extending DefaultDataTypeFactory, ensure that you override
			 * getValidDbProducts() to specify the supported database products.
			 */
			DatabaseConfig dbConfig = connection.getConfig();
			dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

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
			FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream(Utils.path2XML + nameXML + ".xml"));

			// Export the data
			datasetWriter.write(partialDataSet);

		} catch (Exception exc) {
			exc.printStackTrace();

		} finally {
			Utils.consoleMsg("Full DB File: " + Utils.path2XML + nameXML + ".xml created successfully!");
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

			/*
			 * Added these 2 lines to get rid of the warning WARN
			 * org.dbunit.dataset.AbstractTableMetaData - Potential problem found: The
			 * configured data type factory 'class
			 * org.dbunit.dataset.datatype.DefaultDataTypeFactory' might cause problems with
			 * the current database 'MySQL' (e.g. some datatypes may not be supported
			 * properly). In rare cases you might see this message because the list of
			 * supported database products is incomplete (list=[derby]). If so please
			 * request a java-class update via the forums.If you are using your own
			 * IDataTypeFactory extending DefaultDataTypeFactory, ensure that you override
			 * getValidDbProducts() to specify the supported database products.
			 */
			DatabaseConfig dbConfig = connection.getConfig();
			dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

			QueryDataSet partialDataSet = new QueryDataSet(connection);
			// Specify the SQL to run to retrieve the data
			partialDataSet.addTable("receivable","select * from receivable where FKDEBTOR=\"56b1263c9322c\" or FKDEBTOR=\"56b09470e02ea\" or FKDEBTOR=\"56b0fc99a5601\"");
			partialDataSet.addTable("legalentity","select * from legalentity where LEGALENTITYID=\"56b1263c9322c\" or LEGALENTITYID=\"56b09470e02ea\" or LEGALENTITYID=\"56b0fc99a5601\"");
			partialDataSet.addTable("lender","select * from ctbadmin.lender where LEGALENTITYID=\"56b095bd4fbf1\" or LEGALENTITYID=\"56b129500205d\"");
//			partialDataSet.addTable("le","select * from le where LEID=\"PE20306637305\" or LEID=\"KELEI38432\" or LEID=\"TZ0000630784\"");
			partialDataSet.addTable("recline","select * from recline where RECEIVABLEID=\"56b095c4a330a\" OR RECEIVABLEID=\"56bd980d2e176\" OR RECEIVABLEID=\"56b1295486edb\"");

			// Specify the location of the flat file(XML)
			FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream(Utils.path2XML + nameXML + ".xml"));

			// Export the data
			datasetWriter.write(partialDataSet);

			Utils.consoleMsg("Partial DB File: " + Utils.path2XML + nameXML + ".xml created successfully!");

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
					new FlatDtdDataSet(new FileInputStream(Utils.path2XML + nameXML + ".xml")));

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			conn.close();
		}
	}

	private static IDataSet readDataSet(String nameXML) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(Utils.path2XML + nameXML + ".xml"));
	}

	private static void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(Utils.dbTestingDriver, Utils.dbTestingURL,
				Utils.dbTestingUser, Utils.dbTestingPassword) {

			// Para quitar el warning sobre los dttos tipos de datos entre bases de datos
			@Override
			public IDatabaseConnection getConnection() throws Exception {
				IDatabaseConnection connection = super.getConnection();

				connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
						new org.dbunit.ext.h2.H2DataTypeFactory());

				return connection;
			}
		};

		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	public static void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet("CTBAdminPartial");
		cleanlyInsertDataset(dataSet);
	}

	public static void createSchema() throws Exception {
		RunScript.execute(Utils.dbTestingURL, Utils.dbTestingUser, Utils.dbTestingPassword, Utils.dbTestingSchema, null,
				false);
	}

	public static DataSource dataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(Utils.dbTestingURL);
		dataSource.setUser(Utils.dbTestingUser);
		dataSource.setPassword(Utils.dbTestingPassword);
		return dataSource;
	}
}