package native_jdbc.ds;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import com.mysql.jdbc.PacketTooBigException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBCP_DataSource {
	private static BasicDataSource ds = new BasicDataSource();
	
	static {
		try(InputStream is = ClassLoader.getSystemResourceAsStream("dbcp.properties")){
			Properties prop = new Properties();
			prop.loadFromXML(is);
			
			ds = new BasicDataSource();
			ds.setUrl(prop.getProperty("jdbcUrl"));
			ds.setUsername(prop.getProperty("dbUser"));
			ds.setPassword(prop.getProperty("dbPwd"));
			ds.setMinIdle(Integer.parseInt(prop.getProperty("minIdle")));
			ds.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
			ds.setMaxOpenPreparedStatements(Integer.parseInt(prop.getProperty("MaxOpenPreparedStatements")));
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	private DBCP_DataSource() {} //외부에서 new datasource 금지
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	
	
}
