package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class DBManager
{
	private final String DB_DRIVER = "org.hsqldb.jdbcDriver";
	private final String DB_URL = "jdbc:hsqldb:file:sql/testdb;shutdown=true;hsqldb.default_table_type=cached";
	private final String DB_USERNAME = "sa";
	private final String DB_PASSWORD = "";
	private static DBManager instance;
	
	private DBManager() {}
	
	public static DBManager getInstance()
	{
		if(instance == null) instance = new DBManager();
		return instance;
	}

	public Connection connect()
	{
		Connection c = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();//Imprimir error
			System.exit(0);//terminate
		}

		try {
			c = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			c.setAutoCommit(false);//false: no guarda automaticamente los cambios, true guarda automaticamente los cambios
		} catch (SQLException e) {//Siempre puede tirar SQLException, el mensaje varia
			e.printStackTrace();//Imprimir error
			System.exit(0);//terminate
		}

		return c;
	}
	
	public boolean tableExists(String tableName)
	{
		boolean result = false;
		Connection con = connect();
		try {
			DatabaseMetaData metaData = con.getMetaData();
			ResultSet res = metaData.getTables(null, null, tableName.toUpperCase(), null);

			if(res.next()) {
				result = true;
			}
			res.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public void shutdown() throws Exception
	{
		Connection c = connect();
		Statement s = c.createStatement();
		s.execute("SHUTDOWN");
		c.close();
	}
}