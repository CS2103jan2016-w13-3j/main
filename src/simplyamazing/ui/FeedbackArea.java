package simplyamazing.ui;

import java.awt.Color;

import javax.swing.JTextArea;

public class FeedbackArea {
	private final String STRING_NULL = "";
	
	private JTextArea feedbackArea;
	
	public FeedbackArea() {
		feedbackArea = new JTextArea();
		feedbackArea.setEditable(false);
	}

	public JTextArea getFeedbackArea() {		
		return feedbackArea;
	}

	public void setFeedback(String feedback) {
		feedbackArea.setText(feedback);
	}
	
	public void clear() {
		feedbackArea.setText(STRING_NULL);
	}
	
	public void colorCodeFeedback(Color color) {
		feedbackArea.setForeground(color);
	}
}
