package native_jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public interface EmployeeDao {
	//Employee selectEmployeeByEmpno(Connection con, Employee emp) throws SQLException;
	
	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;
	//선택한 부서에 해당하는 사원 출력
	List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException;
	
	int deleteEmployee(Connection con, Employee employee) throws SQLException;
	
	int insertEmployee(Connection con, Employee employee) throws SQLException;
	
	int updateEmployee(Connection con, Employee employee) throws SQLException;

	Employee selectEmployeeByEmpNo(Connection con, Employee emp) throws SQLException;
	
	/* procedure call */
	List<Employee> procedureEmployeeByDno(Connection con, int dno) throws SQLException;
	
}
