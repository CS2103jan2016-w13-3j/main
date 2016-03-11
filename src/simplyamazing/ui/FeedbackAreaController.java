package simplyamazing.ui;

import java.awt.Color;

import javax.swing.JTextArea;

public class FeedbackAreaController {
	private JTextArea feedbackArea;

	public JTextArea getFeedbackArea() {
		return feedbackArea;
	}

	public void setFeedbackArea(JTextArea textArea) {
		this.feedbackArea = textArea;
	}
	
	public void setFeedback(String feedback) {
		feedbackArea.setText(feedback);
	}
	
	public void clearFeedback(String feedback) {
		feedbackArea.setText("");
	}
	
	public void colorCodeFeedback(Color color) {
		feedbackArea.setForeground(color);
	}
}
