package native_jdbc.ui.service;

//import sql.connection
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.DepartmentDaoImpl;
import native_jdbc.ds.Hikari_DataSource2;
import native_jdbc.dto.Department;

public class DepartmentUIService {
	private Connection con;
	private DepartmentDao deptDao;
	//employee에서 department가 필요하면 여기에서 private EmployeeDao 생성해서 다시 사용
	//결과적으로 service는 수정할 필요가 x 
	
	public DepartmentUIService() {
		try {
			con = Hikari_DataSource2.getConnection();
			deptDao = DepartmentDaoImpl.getInstance(); 
		} catch (SQLException e) {
			//여기서 예외가 뜨면 아이디가 존재하지 않거나 비밀번호가 틀렸거나, 데이터베이스가 없을 때
			JOptionPane.showMessageDialog(null, "오류, 접속 정보 확인");
		}
	}
	
	public List<Department> showDepartments(){
		
		try {
			return deptDao.selectDepartmentByAll(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
