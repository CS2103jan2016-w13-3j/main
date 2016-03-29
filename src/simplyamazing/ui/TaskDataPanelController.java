package simplyamazing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableColumnModel;

public class TaskDataPanelController {
	private final String STRING_NULL = "";
	
	private JScrollPane scrollPane;
	private JTextPane taskDataPanel;
	private JTable taskDataTable;
	
	private Object columnNames[] = { "#", "Task", "Start time", "End time", "Priority", "Status"};
	
	public TaskDataPanelController() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 57, 664, 278);
	}
	
	public TaskDataPanelController(Object[][] rowData) {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(10, 57, 664, 278);
		
		taskDataTable = new JTable(rowData, columnNames);
		taskDataTable.setToolTipText("Task Data will be shown here.");
		taskDataTable.setForeground(Color.BLACK);
		taskDataTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		taskDataTable.setEnabled(false);
		taskDataTable.getTableHeader().setBackground(Color.WHITE);
		taskDataTable.getTableHeader().setBorder(BorderFactory.createEtchedBorder());
		taskDataTable.setBorder(BorderFactory.createEtchedBorder());
		taskDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = taskDataTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(30);
		columnModel.getColumn(1).setPreferredWidth(280);
		columnModel.getColumn(2).setPreferredWidth(125);
		columnModel.getColumn(3).setPreferredWidth(125);
		columnModel.getColumn(4).setPreferredWidth(60);
		columnModel.getColumn(5).setPreferredWidth(45);
		taskDataTable.setPreferredScrollableViewportSize(taskDataTable.getMinimumSize());
		
		scrollPane.setViewportView(taskDataTable);
		scrollPane.getViewport().setBackground(Color.WHITE);
	}
	
	public JScrollPane getTaskDataPanel() {
		return scrollPane;
	}
	
	public void setTaskData(String taskData) {
		taskDataPanel.setText(taskData);
	}
	
	public void clear() {
		scrollPane.setVisible(false);
	}
}
