package server;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the connection to our database server
 * 
 * @author Stuart
 * 
 */
public class Database {

	private static final Logger LOG = Logger
			.getLogger(Database.class.getName());
	private static Properties props = new Properties();
	private static Connection conn = null;
	private static long lastUsed = System.currentTimeMillis();

	/**
	 * Create
	 * 
	 * @param _props
	 *            database properties
	 * @throws Exception
	 *             error creating a new connection
	 */
	public static void init() throws Exception {
		LOG.log(Level.INFO, "initiating database connection...");
		try {
			FileInputStream fis = new FileInputStream("database.xml");
			props.loadFromXML(fis);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "error loading database properties", e);
			throw new Exception("error loading database properties");
		}
		connect();
	}

	private static void connect() throws Exception {
		LOG.log(Level.INFO, "connecting to database...");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "error loading mysql driver", e);
			throw new Exception("error loading mysql driver");
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + props.getProperty("host") + ":"
							+ props.getProperty("port") + "/"
							+ props.getProperty("name"),
					props.getProperty("username"),
					props.getProperty("password"));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "error connecting to database", e);
			throw new Exception("error connecting to database "
					+ e.getMessage());
		}
	}

	/**
	 * Get the database connection, renews the connection if the connection has
	 * not been used for 5 minutes
	 * 
	 * @return the connection
	 * @throws Exception
	 *             error getting the connection
	 */
	public static Connection getConnection() throws Exception {
		if (conn == null) {
			throw new Exception("connection is null");
		}
		if (System.currentTimeMillis() - lastUsed > 300000) {
			try {
				lastUsed = System.currentTimeMillis();
				conn.close();
				connect();
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "error refreshing database connection", e);
				throw new Exception("error refreshing database connection");
			}
		}
		return conn;
	}

	/**
	 * Close the database connection
	 * 
	 * @throws Exception
	 *             error closing the database connection
	 */
	public static void close() throws Exception {
		if (conn == null) {
			throw new Exception("connection is null");
		}
		conn.close();
	}

}