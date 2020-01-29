package native_jdbc.hikaricp;

import java.sql.Connection;
import java.sql.SQLException;

import native_jdbc.ds.DBCP_DataSource;
import native_jdbc.ds.Hikari_DataSource;

public class DBCP_Main {

	public static void main(String[] args) {
		try(Connection con = DBCP_DataSource.getConnection();) {
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		

}
}
