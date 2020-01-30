package native_jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByDno(Connection con, Department dept) throws SQLException;
	
	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;
	
	
}
