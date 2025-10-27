package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * 
 * JBCDataSource is used to create 
 * connections via combo pool data source.
 * 
 * @author Nidhi
 * @version 1.0
 */
public final class JDBCDataSource {
	
	private static JDBCDataSource jds=null;
	private ComboPooledDataSource cpds=null;
	
	//Declaring private constructor
	private JDBCDataSource() {
		try {
			cpds=new ComboPooledDataSource();
			ResourceBundle rb=ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");
			cpds.setDriverClass(rb.getString("driver"));
			cpds.setJdbcUrl(rb.getString("url"));
			cpds.setUser(rb.getString("username"));
			cpds.setPassword(rb.getString("password"));
			cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
			cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));
			cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Declare getInstance() method
	public static JDBCDataSource getInstance() {
		if(jds==null) {
			jds=new JDBCDataSource();
		}
		return jds;
	}
	
	//Define connection method
	public static Connection getconnection() {
		try {
			return getInstance().cpds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Close Connection
	public static void closeConnection(Connection conn,Statement stmt,ResultSet rs) {
		try {
			if(rs!=null) {rs.close();}
			if(stmt!=null) {stmt.close();}
			if(conn!=null) {conn.close();}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//2 argument closeConnection
		public static void closeConnection(Connection conn,Statement stmt) {
			closeConnection(conn, stmt,null);
		}
	//1 argument closeConnection
		public static void closeConnection(Connection conn) {
			closeConnection(conn,null);
		}
	
}
