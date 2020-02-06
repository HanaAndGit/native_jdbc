package native_jdbc.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc.LogUtil;
import native_jdbc.dao.impl.DepartmentDaoImpl;
import native_jdbc.dao.impl.EmployeeDaoImpl;
import native_jdbc.ds.MySqlDataSource;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//메서드 실행을 이름 순으로 
public class EmployeeDaoTest {

	private static Logger logger = LogManager.getLogger();
	private Connection con;
	private static EmployeeDao dao;
	private static File imagesDir;
	private static File picsDir;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//LogUtil.prnLog("setUpBeforeClass()");
		dao = EmployeeDaoImpl.getInstance();
		picsDir = new File(System.getProperty("user.dir")+ File.separator +"pics" + File.separator); 
		if(!picsDir.exists()) { //picsDir 폴더가 없으면 만들어주기
			picsDir.mkdir();
		}
		imagesDir = new File(System.getProperty("user.dir")+ File.separator +"images" + File.separator); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//LogUtil.prnLog("tearDownAfterClass()");
		//setUpBeforeClass 가 끝났기때문에 dao를 null 로 변경
		dao = null; 
	}

	@Before
	public void setUp() throws Exception {
		//LogUtil.prnLog("setUp()");
		con = MySqlDataSource.getConnection();
		//fail("Not yet implemented");
	}

	@After
	public void tearDown() throws Exception {
		//LogUtil.prnLog("tearDown()");
		con.close();
		//fail("Not yet implemented");
		
	}

	@Test
	public void test01SelectEmployeeByEmpno() throws SQLException {
		//LogUtil.prnLog("test01SelectEmployeeByDno()");
		Employee emp = new Employee(1004);
		try {
			Employee selectedEmp = dao.selectEmployeeByEmpNo(con, emp);
			if(selectedEmp.getPic()!=null) {
				getImageToPic(selectedEmp.getPic(), emp.getEmpNo()); //프로젝트 폴더의 pics 폴더에 사원번호.jpg 파일이 생성됨
			}
			Assert.assertNotNull(selectedEmp);
		}catch(RuntimeException e) {
			//logger.debug(e.getMessage());
			e.printStackTrace();
		}
		
		
	}

	private void getImageToPic(byte[] pic, int empno) {
		File file = new File(picsDir, empno+".jpg");
		try(FileOutputStream fis = new FileOutputStream(file)){
			fis.write(pic);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test02SelectEmployeeByAll() {
		//logger.debug("test02SelectEmployeeByAll()");
		try {
			List<Employee> lists = dao.selectEmployeeByAll(con);
			Assert.assertNotEquals(-1, lists.size());
			for(Employee e : lists) {
				LogUtil.prnLog(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//logger.debug("test02SelectEmployeeByAll()");
		//fail("Not yet implemented");
	}

	@Test
	public void test03SelectEmployeeGroupByDno() {
		//logger.debug("test03SelectEmployeeGroupByDno()");
		Department dept = new Department();
		dept.setDeptNo(1);
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeGroupByDno(con, dept);
			Assert.assertNotEquals(0, lists.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//fail("Not yet implemented");
	}

	@Test
	public void test06DeleteEmployee() {
		LogUtil.prnLog("test06DeleteEmployee()");
		Employee employee = new Employee();
		//logger.debug("test06DeleteEmployee()");
		//fail("Not yet implemented");
	}

	@Test
	public void test04InsertEmployee() throws SQLException {
		//logger.debug("test04InsertEmployee()");
		Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, new Department(1), getImage("seohyunjin.jpg"));
		//logger.debug(emp);
		int res = dao.insertEmployee(con, emp);
		LogUtil.prnLog(res);
		Assert.assertEquals(1, res);
		//fail("Not yet implemented");
	}
	
	

	private byte[] getImage(String imgName) {
		File file = new File(imagesDir, imgName);
		//logger.debug(file.getAbsolutePath());
		try(InputStream is = new FileInputStream(file)){
			byte[] pic = new byte[is.available()];
			// available - 현재 읽을수 있는 바이트수를 반환한다.
			is.read(pic);
			return pic;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void test05UpdateEmployee() throws SQLException {
		Employee employee = new Employee(1004, "서현진", "대리", new Employee(1003), 1500000, new Department(1));
		int res = dao.updateEmployee(con, employee);
		Assert.assertEquals(1, res);
		//LogUtil.prnLog("test05UpdateEmployee()");
		//fail("Not yet implemented");
	}
	
	
	@Test
	public void test06procedureEmployeeByDno() throws SQLException {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Employee> list = dao.procedureEmployeeByDno(con, 2);
		//0이 아니라면 list.size
		Assert.assertNotEquals(0, list.size());
		
		for(Employee e : list) {
			LogUtil.prnLog(e);
		}
	}

}
