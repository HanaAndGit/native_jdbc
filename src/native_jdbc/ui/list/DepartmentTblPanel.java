package native_jdbc.ui.list;

import javax.swing.SwingConstants;

import native_jdbc.dto.Department;



@SuppressWarnings("serial")
public class DepartmentTblPanel extends AbstractTblPanel<Department> {

	@Override
	protected void setTblWidthAlign() {
		tableSetWidth(50, 150, 50);
		tableCellAlign(SwingConstants.CENTER, 0, 1, 2);
	}

	@Override
	protected String[] getColNames() {
		return new String[] {"부서번호", "부서명", "위치"};
	}

	@Override
	protected Object[] toArray(Department item) {
		return new Object[] {
				item.getDeptNo(), 
				item.getDeptName(), 
				item.getFloor()
				/* String.format("%d", item.getFloor()) */};
	}

	@Override
	public void updateRow(Department item, int updateIdx) {
		model.setValueAt(item.getDeptNo(), updateIdx, 0);//학생번호
		model.setValueAt(item.getDeptName(), updateIdx, 1);
		model.setValueAt(item.getFloor(), updateIdx, 2);
	}

	@Override
	public Department getSelectedItem() {
		int selectedIdx = getSelectedRowIdx();
		int deptNo = (int) model.getValueAt(selectedIdx, 0);
		String deptName = (String) model.getValueAt(selectedIdx, 1);
		int floor = (int) model.getValueAt(selectedIdx, 2);//9층
		return new Department(deptNo, deptName, floor);
	}

}
