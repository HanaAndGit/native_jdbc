package native_jdbc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

import native_jdbc.dao.impl.DepartmentDaoImpl;
import native_jdbc.ds.MySqlDataSource;
import native_jdbc.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private static Logger logger = LogManager.getLogger();
	private Connection con;
	private static DepartmentDao dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass()");
		dao = DepartmentDaoImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.debug("tearDownAfterClass()");
		//setUpBeforeClass 가 끝났기때문에 dao를 null 로 변경
		dao = null; 
	}

	@Before
	public void setUp() throws Exception {
		logger.debug("setUp()");
		con = MySqlDataSource.getConnection();
		//fail("Not yet implemented");
	}

	@After
	public void tearDown() throws Exception {
		logger.debug("tearDown()");
		con.close();
		//fail("Not yet implemented");
		
	}

	@Test
	public void test01SelectDepartmentByAll() {
		logger.debug("testSelectDepartmentByAll()");
		try {
			List<Department> lists = dao.selectDepartmentByAll(con);
			Assert.assertNotEquals(-1, lists.size());
			for(Department d : lists) {
				logger.trace(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fail("Not yet implemented");
	}

	@Test
	public void test02InsertDepartment() throws SQLException {
		logger.debug("testInsertDepartment()");
		Department department = new Department(5, "마케팅", 4);
		int res = dao.insertDepartment(con, department);
		Assert.assertEquals(1, res);
		//fail("Not yet implemented");
	}

	@Test
	public void test03UpdateDepartment() throws SQLException {
		logger.debug("testUpdateDepartment()");
		Department department = new Department(5, "마케팅3", 41);
		int res = dao.updateDepartment(con, department);
		Assert.assertEquals(1, res);
		//fail("Not yet implemented");
	}

	@Test
	public void test04DeleteDepartment() throws SQLException {
		logger.debug("testDeleteDepartment()");
		Department department = new Department(5, "마케팅3", 41);
		int res = dao.deleteDepartment(con, department);
		Assert.assertEquals(1, res);
		//fail("Not yet implemented");
	}
	
	@Test
	public void test05selectDepartmentByNo() throws SQLException {
		logger.debug("test05selectDepartmentByNo()");
		Department department = dao.selectDepartmentByNo(con, 1);
		//없으면 null 
		Assert.assertNotNull(department);
		logger.trace(department);
		
	}

}
