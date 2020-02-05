package native_jdbc.ui.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc.LogUtil;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest {
	
	private static TransactionService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName()+ "()");
		service = new TransactionService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName()+ "()");
		service = null;
	}


	@Test
	public void test01TransAddEmpAndDept_DeptFail() throws SQLException {//res 가 1 (실패함)
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName()+ "()");
		//-> 함수 이름이 출력되도록 하는 코드
		int res = 0;
		Department dept = new Department(1, "마케팅", 8);
		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, dept);
		res = service.transAddEmpAndDept(emp, dept);
		//2와 같지 않으면 성공한 것
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test02TransAddEmpAndDept_EmpFail() throws SQLException {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(6, "마케팅", 8);
		Employee emp = new Employee(4377, "수지", "사원", new Employee(1003), 1500000, dept);//존재하는 사원
		
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test //6번 부서 마케팅, 1005 수지 추가 성공 
	public void test03TransAddEmpAndDept_Success() throws SQLException {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(6, "마케팅", 8);
		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, dept);
		
		int res = service.transAddEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	
	@Test
	public void test04TransRemoveEmpAndDept_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(9, "마케팅", 6); //없는 부서
		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, dept);

		service.transRemoveEmpAndDept(emp, dept);
	}
	
	@Test
	public void test05TransRemoveEmpAndDept_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(7, "마케팅2", 6);
		Employee emp = new Employee(1008, "수지", "사원", new Employee(1003), 1500000, dept);

		service.transRemoveEmpAndDept(emp, dept);
	}

	
	@Test
	public void test06TransRemoveEmpAndDept_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = -1;
		Department dept = new Department(6, "마케팅", 8);
		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, dept);

		service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	
	
	@Test
	public void test07TransAddEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(1, "마케팅", 8);// 존재하는 부서
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		service.transAddEmpAndDeptWithConnection(emp, dept);
	}
	
	@Test
	public void test08TransAddEmpAndDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(6, "마케팅6", 8);
		Employee emp = new Employee(4377, "이유영", "사원", new Employee(1003), 1500000, dept);// 존재하는 사원

		service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test //7번 부서 마케팅2, 1006 이유영 추가 성공 
	public void test09TransAddEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(7, "마케팅2", 6);
		Employee emp = new Employee(1006, "이유영", "사원", new Employee(1003), 1500000, dept);

		service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	
	

	@Test
	public void test10TransRemoveEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(8, "마케팅", 6);
		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, dept);

		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}

	@Test
	public void test11TransRemoveEmpAndDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(6, "마케팅", 8);
		Employee emp = new Employee(1008, "수지", "사원", new Employee(1003), 1500000, dept);

		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}

	@Test
	public void test12TransRemoveEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(6, "마케팅", 8);
		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, dept);

		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}

}
