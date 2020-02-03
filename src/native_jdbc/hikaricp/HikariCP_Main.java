package native_jdbc.hikaricp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;

import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.EmployeeDao;
import native_jdbc.dao.impl.DepartmentDaoImpl;
import native_jdbc.dao.impl.EmployeeDaoImpl;
import native_jdbc.ds.MySqlDataSource;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;
import native_jdbc.ui.DlgEmployee;

public class HikariCP_Main {

	public static void main(String[] args)  {
		
		try (Connection con = MySqlDataSource.getConnection()) {
			//소속부서사원검색테스트(con);
			DepartmentDao dao = DepartmentDaoImpl.getInstance();
			//추가할 부서 정보 생성
			Department newDept = new Department(7, "경영지원", 60);
			//dao.insertDepartment(con, newDept);
			//dao.updateDepartment(con, newDept);
			dao.deleteDepartment(con, newDept);
			
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
			//한번 더 추가하면 에러남
			// System.out.println(e.getMessage()); -> Duplicate entry '6' for key 'PRIMARY'
			// System.out.println(e.getErrorCode()); -> 1062
		}
		
		//일반
		//native_jdbc.dao.EmployeeDaoImpl@15db9742
		//native_jdbc.dao.EmployeeDaoImpl@6d06d69c
		//2개의 객체가 생성됨 dao1, dao2 / 할 때마다 계속 생성 
		//객체가 100개 생성되면 메모리 과부하
		//EmployeeDao dao1 = new EmployeeDaoImpl();
		//EmployeeDao dao2 = new EmployeeDaoImpl();
		//System.out.println(dao1);
		//System.out.println(dao2);
		
		//Singleton Pattern 
		//native_jdbc.dao.EmployeeDaoImpl@7852e922
		//native_jdbc.dao.EmployeeDaoImpl@7852e922
		//1개의 객체 (주소 같음) / 계속 생성 x 1개로 계속 쓰기 
		//EmployeeDao dao3 = EmployeeDaoImpl.getInstance();
		//EmployeeDao dao4 = EmployeeDaoImpl.getInstance();
		//System.out.println(dao3);
		//System.out.println(dao4);
		
		
		
		
		/*
		 * EmployeeDao dao3 = EmployeeDaoImpl.getInstance(); try { List<Employee> list =
		 * dao3.selectEmployeeByAll(Hikari_DataSource2.getConnection()); for(Employee
		 * e:list) { System.out.println(e); } } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		
		
		
		
		
		
		
		
		/*
		 * 
		 * for(int i=0; i<100; i++){ EmployeeDao dao3 = EmployeeDaoImpl.getInstance();
		 * System.out.println(dao3); }
		 */
		
		/*
		 * try(Connection con = Hikari_DataSource.getConnection();) {
		 * System.out.println(con); } catch (SQLException e) { e.printStackTrace(); }
		 * 
		 * try(Connection con2 = Hikari_DataSource.getConnection();) {
		 * System.out.println(con2); } catch (SQLException e) { e.printStackTrace(); }
		 * 
		 * 
		 * try(Connection con3 = DBCP_DataSource.getConnection();) {
		 * System.out.println(con3); } catch (SQLException e) { e.printStackTrace(); }
		 */
	}

	private static void 소속부서사원검색테스트(Connection con) throws SQLException {
		EmployeeDao dao = EmployeeDaoImpl.getInstance();
		Department dept = new Department();
		dept.setDeptNo(4);

		List<Employee> list = dao.selectEmployeeGroupByDno(con, dept);
		for (Employee e : list) {
			System.out.println(e);
		}
		//DlgEmployee main에서 가져옴
		DlgEmployee dialog = new DlgEmployee();
		dialog.setEmpList(list);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}

