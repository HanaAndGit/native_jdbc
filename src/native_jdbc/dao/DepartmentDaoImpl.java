package native_jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc.dto.Department;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentDaoImpl implements DepartmentDao {
	//singleton pattern 
	
	//static, final로 
	private static final DepartmentDaoImpl instance = new DepartmentDaoImpl();

	//private으로 지정/ 외부에서 생성할 수 없도록 
	private DepartmentDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	//static, final 이기 때문에 getter만 있음 (setter없음)
	//테이블에 dao로 접근 / UI 에서 dao에 접근해서 테이블에 접근 / 다른 ui에서도 dept 테이블에 접근할 때 하나의 dao 에만 접근하면됨 
	//비교 설명 : HikariCP_Main.java 파일 메인 메서드 
	public static DepartmentDaoImpl getInstance() {
		return instance;
	}



	@Override
	public List<Department> selectDepartmentByAll(Connection con) throws SQLException {//Connection con에서 연결
		String sql = "select deptno, deptname, floor from department"; 
		//쿼리를 날릴 sql
		//디비버에서 꼭 해당 쿼리가 실행되는지 테스트 후에 붙여넣기 하기
		List<Department> list = new ArrayList<Department>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				list.add(getDepartment(rs));
			}
			
		}
		
		
		return list;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptno = rs.getInt("deptno"); //sql 쿼리의 필드 네임과 같아야함
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptno, deptName, floor);
	}

}