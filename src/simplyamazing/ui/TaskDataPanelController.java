package simplyamazing.ui;

import javax.swing.JTextPane;

public class TaskDataPanelController {
	private JTextPane taskDataPanel;
	private final String STRING_NULL = "";

	public JTextPane getTaskDataPanel() {
		return taskDataPanel;
	}
	
	public void setTaskDataPanel(JTextPane feedbackPane) {
		this.taskDataPanel = feedbackPane;
	}
	
	public void setTaskData(String taskData) {
		taskDataPanel.setText(taskData);
	}
	
	public void clear() {
		taskDataPanel.setText(STRING_NULL);
	}
}
