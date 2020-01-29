package native_jdbc.hikaricp;

import java.sql.Connection;
import java.sql.SQLException;

import native_jdbc.ds.DBCP_DataSource;
import native_jdbc.ds.Hikari_DataSource;

public class HikariCP_Main {

	public static void main(String[] args) {
		try(Connection con = Hikari_DataSource.getConnection();) {
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try(Connection con2 = Hikari_DataSource.getConnection();) {
				System.out.println(con2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		
		try(Connection con3 = DBCP_DataSource.getConnection();) {
			System.out.println(con3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
