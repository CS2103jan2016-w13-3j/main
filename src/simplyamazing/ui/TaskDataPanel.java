//@@author A0126289W
package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TaskDataPanel {
	
	private static final int WIDTH_COLUMN_STATUS = 99;
	private static final int WIDTH_COLUMN_PRIORITY = 64;
	private static final int WIDTH_COLUMN_END_TIME = 150;
	private static final int WIDTH_COLUMN_START_TIME = 150;
	private static final int WIDTH_COLUMN_DESCRIPTION = 300;
	private static final int WIDTH_COLUMN_INDEX = 40;
	
	private static final int COLUMN_STATUS = 5;
	private static final int COLUMN_PRIORITY = 4;
	private static final int COLUMN_END_TIME = 3;
	private static final int COLUMN_START_TIME = 2;
	private static final int COLUMN_DESCRIPTION = 1;
	private static final int COLUMN_INDEX = 0;
	
	private static final String TEXT_TIP = "Task Data will be shown here.";
	
	private static final int HEIGHT_ROW = 30;
	
	private static final int FONT_SIZE_HEADER = 15;
	private static final String FONT_HEADER = "Times New Roman";
	private static final String FONT_DEFAULT = "Tahoma";
	private static final int FONT_SIZE_DEFAULT = 16;
	
	private Object columnNames[] = { "#", "Description", "Start time", "End time", "Priority", "Status"};
	
	private JTable taskDataTable;
	
	public TaskDataPanel(Object[][] rowData) {
		DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
		taskDataTable = new JTable(model);
		taskDataTable.setToolTipText(TEXT_TIP);
		taskDataTable.setBackground(Color.WHITE);
		taskDataTable.setForeground(Color.BLACK);
		taskDataTable.setRowHeight(HEIGHT_ROW);
		taskDataTable.setFont(new Font(FONT_DEFAULT, Font.PLAIN, FONT_SIZE_DEFAULT));
		taskDataTable.setEnabled(false);
		taskDataTable.getTableHeader().setBackground(Color.WHITE);
		taskDataTable.getTableHeader().setBorder(BorderFactory.createEtchedBorder());
		taskDataTable.getTableHeader().setFont(new Font(FONT_HEADER, Font.BOLD, FONT_SIZE_HEADER));
		taskDataTable.setBorder(BorderFactory.createEtchedBorder());
		TableColumnModel columnModel = taskDataTable.getColumnModel();
		taskDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModel.getColumn(COLUMN_INDEX).setPreferredWidth(WIDTH_COLUMN_INDEX);
		columnModel.getColumn(COLUMN_DESCRIPTION).setPreferredWidth(WIDTH_COLUMN_DESCRIPTION);
		columnModel.getColumn(COLUMN_START_TIME).setPreferredWidth(WIDTH_COLUMN_START_TIME);
		columnModel.getColumn(COLUMN_END_TIME).setPreferredWidth(WIDTH_COLUMN_END_TIME);
		columnModel.getColumn(COLUMN_PRIORITY).setPreferredWidth(WIDTH_COLUMN_PRIORITY);
		columnModel.getColumn(COLUMN_STATUS).setPreferredWidth(WIDTH_COLUMN_STATUS);
	}
	
	public JTable getTaskDataPanel() {
		return taskDataTable;
	}
	
	public void clear() {
		taskDataTable.setVisible(false);
	}
}
