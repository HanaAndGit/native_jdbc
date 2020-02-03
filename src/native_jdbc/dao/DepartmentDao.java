package native_jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc.dto.Department;

public interface DepartmentDao {//DepartmentDaoImpl 에서 thorws 복사해서 붙임 
	List<Department> selectDepartmentByAll(Connection con) throws SQLException;
	int insertDepartment(Connection con, Department department) throws SQLException;
	int updateDepartment(Connection con, Department department) throws SQLException;
	int deleteDepartment(Connection con, Department department) throws SQLException;
	Department selectDepartmentByNo(Connection con, int dno) throws SQLException;
}
