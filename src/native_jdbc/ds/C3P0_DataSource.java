package native_jdbc.ds;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.mchange.v2.c3p0.DataSources;
import com.mysql.jdbc.PacketTooBigException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class C3P0_DataSource {
	private static DataSource datasource;
	
	static {
		try(InputStream is = ClassLoader.getSystemResourceAsStream("c3p0.properties")){
			Properties prop = new Properties();
			prop.load(is);
			
			DataSource ds_unpooled = DataSources.unpooledDataSource(prop.getProperty("url"), prop);
			Map<String, Object> overrides = new HashMap<>();
			overrides.put("maxStatements", "200");
			overrides.put("maxPoolSize", new Integer(50));
			
			datasource = DataSources.pooledDataSource(ds_unpooled, overrides);
		}catch(IOException | SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	private C3P0_DataSource() {} //외부에서 new datasource 금지
	
	public static Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}
	
	
	
}
