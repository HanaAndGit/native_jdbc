package native_jdbc.ui.list;

import javax.swing.SwingConstants;

import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public class EmployeeTblPanel extends AbstractTblPanel<Employee> {

	@Override//각 컬럼의 폭 / 각 컬럼에 대한 정렬
	protected void setTblWidthAlign() {
		//empno, empname, title, manager, salary, dno
		tableSetWidth(100, 150, 50, 150, 150, 100);
		tableCellAlign(SwingConstants.CENTER, 0, 1, 2, 3, 5);//salary 빼고 중앙정렬
		tableCellAlign(SwingConstants.RIGHT, 4);//salary는 오른쪽 정렬;
		
	}

	@Override//컬럼 이름
	protected String[] getColNames() {
		return new String[] {"사원번호", "사원명", "직책", "직속상사", "급여", "부서번호"};
	}

	@Override//Object 배열로 변경
	protected Object[] toArray(Employee item) {
		String manager;
		if(item.getManager().getEmpName()==null) {
			manager = "";
		}else {
			manager = String.format("%s(%d)", item.getManager().getEmpName(), item.getManager().getEmpNo());
		}
		
		return new Object[] {
				item.getEmpNo(),
				item.getEmpName(),
				item.getTitle(),
				//item.getManager().getEmpNo(), //<- 객체자체기때문에 매니저의 사원번호를 리턴 
				//직속상사명(사원번호) 로 표시될것
				//String.format("%s(%d)", item.getManager().getEmpName(), item.getManager().getEmpNo()),
				//직속상사명이 null일 경우 빈 칸으로 표시
				manager,
				//item.getSalary(),
				//천단위 구분기호
				String.format("%,d", item.getSalary()),
				//item.getDept().getDeptNo() //<- 부서객체 자체.getDeptNo()
				//부서명(부서번호) 로 표시될것
				String.format("%s(%d)", item.getDept().getDeptName(), item.getDept().getDeptNo())
		};
	}

	@Override //업데이트하고자 하는 Row 인덱스 번호
	public void updateRow(Employee item, int updateIdx) {
		model.setValueAt(item.getEmpNo(), updateIdx, 0);
		model.setValueAt(item.getEmpName(), updateIdx, 1);
		model.setValueAt(item.getTitle(), updateIdx, 2);
		model.setValueAt(item.getManager().getEmpNo(), updateIdx, 3);//직속상사의 사원번호
		model.setValueAt(item.getSalary(), updateIdx, 4);
		model.setValueAt(item.getDept().getDeptNo(), updateIdx, 5);//부서의 부서번호
	}

	@Override
	public Employee getSelectedItem() {
		int selectedIdx = getSelectedRowIdx();
		int empNo =(int) model.getValueAt(selectedIdx, 0);
		String empName = (String) model.getValueAt(selectedIdx, 1);
		String title = (String) model.getValueAt(selectedIdx, 2);
		Employee manager = new Employee((int)model.getValueAt(selectedIdx, 3));
		int salary = (int) model.getValueAt(selectedIdx, 4);
		Department dept = new Department();
		dept.setDeptNo((int)model.getValueAt(selectedIdx, 5));
		String pic = (String) model.getValueAt(selectedIdx, 6);
		
		return new Employee(empNo, empName, title, manager, salary, dept);
		
	}

}
