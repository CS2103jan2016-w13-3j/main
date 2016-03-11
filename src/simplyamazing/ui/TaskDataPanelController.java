package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JTextPane;

public class TaskDataPanelController {
	private JTextPane feedbackPane;

	public JTextPane getFeedbackPane() {
		return feedbackPane;
	}
	
	public void setFeedbackPane(JTextPane feedbackPane) {
		this.feedbackPane = feedbackPane;
	}
	
	public void setFeedback(String feedback) {
		feedbackPane.setText(feedback);
	}
}
