package native_jdbc.ui.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import native_jdbc.LogUtil;
import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.EmployeeDao;
import native_jdbc.dao.impl.DepartmentDaoImpl;
import native_jdbc.dao.impl.EmployeeDaoImpl;
import native_jdbc.ds.MySqlDataSource;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public class TransactionService {
	private String deptSql = "insert into department values(?, ?, ?)";
	private String empSql = "insert into employee(empno, empname, title, manager, salary, dno) values (?, ?, ?, ?, ?, ?)";
	private String deptRemovesql = "delete from department where deptno = ?";
	private String empRemovesql = "delete from employee where empno = ?";
	//p.138
	//transaction 
	//insert (dept, emp 를 동시에)
	public int transAddEmpAndDept (Employee emp, Department dept) throws SQLException {
		int res = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		/* (PreparedStatement pstmt = con.prepareStatement(deptSql) )*/{
			//☆★ 오토커밋 false를 해줘야 1개가 커밋실패하면 다른 1개를 롤백할 수 있음
			//2개 모두 성공 한 후에 수동으로 커밋할 수 있음
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			//department insert 
			pstmt = con.prepareStatement(deptSql);
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
			
			//employee insert
			pstmt = con.prepareStatement(empSql);
			pstmt.setInt(1, emp.getEmpNo());
	        pstmt.setString(2, emp.getEmpName());
	        pstmt.setString(3, emp.getTitle());
	        pstmt.setInt(4, emp.getManager().getEmpNo());
	        pstmt.setInt(5, emp.getSalary());
	        pstmt.setInt(6, emp.getDept().getDeptNo());
	        LogUtil.prnLog(pstmt);
	        res += pstmt.executeUpdate();
			
			//insert일 경우에는 바로 커밋해도 됨
			//update 일 경우에는 리턴 되는 값으로 구분해야함
			//update를 할 때는 없는 부서로 set 하더라도 에러가 아니라 update row가 0으로 나오기때문임 
			if(res == 2) {//2개 다 성공함
				con.commit();
				LogUtil.prnLog("result " + res + "commit()");
			}else {
				throw new SQLException();
				//아래에 있는 catch 문이 받음 
			}
			
		} catch (SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result " + res + "rollback()");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try{con.setAutoCommit(true);
				pstmt.close();
				con.close();
			}catch(Exception e) {
					
			}
		}
		return res;
	}
	

	
	
	
	
	
	public void transAddEmpAndDeptWithConnection(Employee emp, Department dept) {
		DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		Connection con = null;
		
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			deptDao.insertDepartment(con, dept);
			empDao.insertEmployee(con, emp);
			con.commit();
			con.setAutoCommit(true);
			LogUtil.prnLog("result commit()");
		} catch (RuntimeException e) {
			try{
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}catch(Exception e1) {}
			LogUtil.prnLog("result rollback()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public int transRemoveEmpAndDept(Employee emp, Department dept) {
		//1. 사원 삭제
		//2. 사원이 소속된 부서 삭제
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName()+"()");
		int res = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			//department remove 
			pstmt = con.prepareStatement(deptRemovesql);
			pstmt.setInt(1, dept.getDeptNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
			
			//employee remove
			pstmt = con.prepareStatement(empRemovesql);
			pstmt.setInt(1, emp.getEmpNo());
	        LogUtil.prnLog(pstmt);
	        res += pstmt.executeUpdate();
	        
	        if(res == 2) {//2개 다 성공함
				con.commit();
				LogUtil.prnLog("result " + res + "commit()");
			}else {
				throw new SQLException();
				//아래에 있는 catch 문이 받음 
			}
	        
		} catch (SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result " + res + "rollback()");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try{con.setAutoCommit(true);
			pstmt.close();
			con.close();
		}catch(Exception e) {
				
		}
	}		
		return res;
}
	public void transRemoveEmpAndDeptWithConnection(Employee emp, Department dept) {
		DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		Connection con = null;
		
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			deptDao.deleteDepartment(con, dept);
			empDao.deleteEmployee(con, emp);
			con.commit();
			con.setAutoCommit(true);
			LogUtil.prnLog("result commit()");
		} catch (RuntimeException e) {
			try{
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}catch(Exception e1) {}
			
			LogUtil.prnLog("result rollback()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
