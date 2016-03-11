package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JTextPane;

public class FeedbackPaneController {
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
	
	public void colorCodeFeedback(Color color) {
		feedbackPane.setForeground(color);
	}
}
