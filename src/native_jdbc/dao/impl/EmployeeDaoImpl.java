package native_jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import native_jdbc.LogUtil;
import native_jdbc.dao.EmployeeDao;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
//singleton pattern
	private static Logger logger = LogManager.getLogger();
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	
	
	
	private EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}



	@Override
	public Employee selectEmployeeByEmpno(Connection con, Employee emp) throws SQLException {
		String sql = "select empno, empname, title, manager, salary, dno from employee where empno = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, emp.getEmpNo());
			try(ResultSet rs = pstmt.executeQuery()){
				//if(rs.next()) {
					return getEmployeeFull(rs);
				//}
			}
		} 
		
		//return null;
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
			//System.out.println(pstmt);
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
			LogUtil.prnLog(pstmt);
			while(rs.next()) {
				list.add(getEmployee(rs, false));
			}
		}
		
		return list;
	}



	private Employee getEmployee(ResultSet rs, boolean isPic)  {
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
			Employee employee = new Employee(empNo, empName, title, manager, salary, dept); 
			if(isPic) {
				employee.setPic(rs.getBytes("pic"));
			}
			return new Employee(empNo, empName, title, manager, salary, dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public int deleteEmployee(Connection con, Employee employee) throws SQLException {
		String sql = "delete from employee where empno = ?";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			res = pstmt.executeUpdate();
		}
		return res;
	}



	@Override
	public int insertEmployee(Connection con, Employee employee) throws SQLException {
		String sql = null;
		if(employee.getPic()==null) {
			//logger.debug("pic is null");
			sql = "insert into employee(empno, empname, title, manager, salary, dno) values (?, ?, ?, ?, ?, ?)";
		}else{
			//logger.debug("pic is not null");
			sql = "insert into employee values (?, ?, ?, ?, ?, ?, ?)";
		}
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getTitle());
			pstmt.setInt(4, employee.getManager().getEmpNo());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getDept().getDeptNo());
			if(employee.getPic()!=null) {
				pstmt.setBytes(7, employee.getPic());
			}
			return pstmt.executeUpdate();
		}
		
	}



	@Override
	public int updateEmployee(Connection con, Employee employee) throws SQLException {
		String sql = "update employee set empno = ?, empname = ?, title = ?, manager = ?, salary = ?, dno = ? where empno = ?";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getTitle());
			pstmt.setInt(4, employee.getManager().getEmpNo());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getDept().getDeptNo());
			pstmt.setInt(7, employee.getEmpNo());
			LogUtil.prnLog(pstmt);
		//	System.out.println(pstmt + " !!");
			res = pstmt.executeUpdate();
		}
		return res;
	}




}
