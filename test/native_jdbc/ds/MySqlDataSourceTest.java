package native_jdbc.ds;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import junit.framework.Assert;

public class MySqlDataSourceTest {
	private static Logger logger = LogManager.getLogger();
	@Test
	public void testGetConnection() throws SQLException {
		Connection con = MySqlDataSource.getConnection();
		logger.debug(con);
		Assert.assertNotNull(con);
	}

}
