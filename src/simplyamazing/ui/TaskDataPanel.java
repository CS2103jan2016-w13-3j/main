package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableColumnModel;

public class TaskDataPanel {
	private JTable taskDataTable;
	
	private Object columnNames[] = { "#", "Description", "Start time", "End time", "Priority", "Status"};
	
	public TaskDataPanel(Object[][] rowData) {
		taskDataTable = new JTable(rowData, columnNames);
		taskDataTable.setToolTipText("Task Data will be shown here.");
		taskDataTable.setForeground(Color.BLACK);
		taskDataTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		taskDataTable.setEnabled(false);
		taskDataTable.getTableHeader().setBackground(Color.WHITE);
		taskDataTable.getTableHeader().setBorder(BorderFactory.createEtchedBorder());
		taskDataTable.setBorder(BorderFactory.createEtchedBorder());
		//taskDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = taskDataTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(29);
		columnModel.getColumn(1).setPreferredWidth(280);
		columnModel.getColumn(2).setPreferredWidth(125);
		columnModel.getColumn(3).setPreferredWidth(125);
		columnModel.getColumn(4).setPreferredWidth(60);
		columnModel.getColumn(5).setPreferredWidth(45);
		taskDataTable.setPreferredScrollableViewportSize(taskDataTable.getMinimumSize());
	}
	
	public JTable getTaskDataPanel() {
		return taskDataTable;
	}
	
	public void clear() {
		taskDataTable.setVisible(false);
	}
}
