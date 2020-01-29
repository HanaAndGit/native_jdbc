package native_jdbc.hikaricp;

import java.sql.Connection;
import java.sql.SQLException;

import native_jdbc.ds.C3P0_DataSource;

public class C3P0_Main {

	public static void main(String[] args) throws SQLException {
		try(Connection con = C3P0_DataSource.getConnection();){
			System.out.println(con);
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

}
