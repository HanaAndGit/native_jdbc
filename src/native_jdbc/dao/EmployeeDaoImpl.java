package native_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
//singleton pattern
	
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	
	
	
	private EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}



	@Override
	public Employee selectEmployeeByDno(Connection con, Department dept) throws SQLException {
		
		return null;
	}
	

	@Override
	public List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException {
		//String sql = "select empno, empname, title, manager, salary, dno from employee where dno = ?";
		String sql = "select e.empno, e.empname, e.title, m.empno as manager_no, m.empname as manager_name, e.salary, e.dno, d.deptname\r\n" + 
				"	  from employee e left join employee m on e.manager = m.empno join department d on e.dno = d.deptno\r\n" + 
				"	  where deptno = ?";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);){
			//pstmt.setInt(1,dept.getDeptNo()... -> 불가능) -> try Reset을 아래에 써줘야함
			pstmt.setInt(1, dept.getDeptNo());
			System.out.println(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getEmployeeFull(rs));
				}
			}
		}
		return list;
	}
	
	private Employee getEmployeeFull(ResultSet rs) throws SQLException {
		//실제 칼럼명
		int empNo = rs.getInt("empno");
		//System.out.println(rs.getInt("empno"));
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager_no"));
		manager.setEmpName(rs.getString("manager_name"));//sql에서 as로 쓰는 이름
		int salary = rs.getInt("salary");
		Department dept = new Department(); 
		dept.setDeptNo(rs.getInt("dno"));
		dept.setDeptName(rs.getString("deptname"));
		return new Employee(empNo, empName, title, manager, salary, dept);
	}



	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		// 1. sql 만들기
		//2. try, catch 자바가 쿼리를 날릴 preparestatement
		//3. 만들어놓은 걸 가지고 sql로 던지기 ResultSet rs
		//4. getEmployee(rs) 메서드 작성
		String sql = "select empno, empname, title, manager, salary, dno from employee";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
		}
		
		return list;
	}



	private Employee getEmployee(ResultSet rs)  {
		int empNo;
		try {
			empNo = rs.getInt("empno");
			System.out.println(rs.getInt("empno"));
			String empName = rs.getString("empname");
			String title = rs.getString("title");
			Employee manager = new Employee(rs.getInt("manager"));
			int salary = rs.getInt("salary");
			Department dept = new Department(); 
			dept.setDeptNo(rs.getInt("dno"));
			return new Employee(empNo, empName, title, manager, salary, dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




}
