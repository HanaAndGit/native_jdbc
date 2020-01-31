package native_jdbc.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import native_jdbc.dto.Employee;
import native_jdbc.ui.list.EmployeeTblPanel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class DlgEmployee extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private EmployeeTblPanel pEmployee;

	/**
	 * Launch the application.
	 */

	public DlgEmployee() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			pEmployee = new EmployeeTblPanel();
			contentPanel.add(pEmployee, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			okButtonActionPerformed(e);
		}
	}
	protected void okButtonActionPerformed(ActionEvent e) {
		//닫힘 
		dispose();
	}
	
	
	public void setEmpList(List<Employee> items) {
		pEmployee.loadData(items);
		
	}
	
	
	
	
}
