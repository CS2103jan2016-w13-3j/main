package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class TaskDataPanelController {
	private final String STRING_NULL = "";
	
	private JScrollPane scrollPane;
	private JTextPane taskDataPanel;
	
	public TaskDataPanelController() {
		taskDataPanel = new JTextPane();
		taskDataPanel.setToolTipText("Feedback message will be shown here.");
		taskDataPanel.setForeground(Color.BLACK);
		taskDataPanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		taskDataPanel.setEditable(false);
		taskDataPanel.setBounds(10, 57, 664, 311);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 57, 664, 278);
		scrollPane.setViewportView(taskDataPanel);
	}

	public JScrollPane getTaskDataPanel() {
		return scrollPane;
	}
	
	public void setTaskData(String taskData) {
		taskDataPanel.setText(taskData);
	}
	
	public void clear() {
		taskDataPanel.setText(STRING_NULL);
	}
}
