//@@author A0126289W
package simplyamazing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TaskDataPanel {
	private JTable taskDataTable;
	
	private Object columnNames[] = { "#", "Description", "Start time", "End time", "Priority", "Status"};
	
	public TaskDataPanel(Object[][] rowData) {
		DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
		taskDataTable = new JTable(model);
		taskDataTable.setToolTipText("Task Data will be shown here.");
		taskDataTable.setBackground(Color.WHITE);
		taskDataTable.setForeground(Color.BLACK);
		taskDataTable.setRowHeight(30);
		taskDataTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taskDataTable.setEnabled(false);
		taskDataTable.getTableHeader().setBackground(Color.WHITE);
		taskDataTable.getTableHeader().setBorder(BorderFactory.createEtchedBorder());
		taskDataTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
		taskDataTable.setBorder(BorderFactory.createEtchedBorder());
		TableColumnModel columnModel = taskDataTable.getColumnModel();
		taskDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(300);
		columnModel.getColumn(2).setPreferredWidth(150);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(4).setPreferredWidth(64);
		columnModel.getColumn(5).setPreferredWidth(99);
	}
	
	public JTable getTaskDataPanel() {
		return taskDataTable;
	}
	
	public void clear() {
		taskDataTable.setVisible(false);
	}
}
