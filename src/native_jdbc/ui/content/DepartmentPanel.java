package native_jdbc.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import native_jdbc.dto.Department;


@SuppressWarnings("serial")
public class DepartmentPanel extends AbsItemPanel<Department> {
	private JLabel lblNo;
	private JTextField tfNo;
	private JLabel lblName;
	private JTextField tfName;
	private JLabel lblFloor;
	private JTextField tfFloor;

	public DepartmentPanel() {
		initialize();
	}
	private void initialize() {
		setBorder(new TitledBorder(null, "부서 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 0));
		
		lblNo = new JLabel("부서번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNo);
		
		tfNo = new JTextField();
		add(tfNo);
		tfNo.setColumns(10);
		
		lblName = new JLabel("부서명");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		add(tfName);
		
		lblFloor = new JLabel("위치");
		lblFloor.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblFloor);
		
		tfFloor = new JTextField();
		tfFloor.setColumns(10);
		add(tfFloor);
	}

	@Override
	public Department getItem() {
		int deptNo = Integer.parseInt(tfNo.getText().trim());
		String deptName = tfName.getText().trim();
		int floor =  Integer.parseInt(tfFloor.getText().trim());
		return new Department(deptNo, deptName, floor);
	}

	@Override
	public void setItem(Department item) {
		tfNo.setText(item.getDeptNo()+"");
		tfName.setText(item.getDeptName());
		tfFloor.setText(item.getFloor() +"");
	}

	@Override
	public void clearTf() {
		tfNo.setText("");
		tfName.setText("");
		tfFloor.setText("");		
	}

}
