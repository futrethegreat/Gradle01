package mariadb;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ctc.Utils;

public class BookDBTest {

	@BeforeClass
	public static void createSchema() throws Exception {
		Utils.setEnvironment();
		RunScript.execute(Utils.dbTestingURL, Utils.dbTestingUser, Utils.dbTestingPassword, Utils.dbTestingSchema, null,
				false);
	}

	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet("LibraryFull");
		cleanlyInsertDataset(dataSet);
	}

	private IDataSet readDataSet(String nameXML) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(Utils.path2XML + nameXML + ".xml"));
	}

	private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
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

	@Test
	public void getBookByID() throws Exception {
		DataSource dataSource = dataSource();
		Connection c = dataSource.getConnection();

		Book b = new Book(c, 1);

		assertThat(b.getBookTitle(), is("El INGENIOSO HIDALGO don Quijote de la Mancha"));
		assertThat(b.isLent(), is(true));
		assertThat(b.getAuthor().getAuthorName(), is("Miguel de Cervante"));
	}

	private DataSource dataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(Utils.dbTestingURL);
		dataSource.setUser(Utils.dbTestingUser);
		dataSource.setPassword(Utils.dbTestingPassword);
		return dataSource;
	}
}
