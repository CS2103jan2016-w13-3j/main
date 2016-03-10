package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;

public class FeedbackAreaController {
	private static JTextPane textPane;

	public FeedbackAreaController() {
		textPane = new JTextPane();
		textPane.setToolTipText("Feedback message will be shown here.");
		textPane.setForeground(Color.BLACK);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPane.setEditable(false);
		textPane.setBounds(10, 57, 664, 311);
	}

	public JTextPane getTextPane() {
		return textPane;
	}
	
	public void setFeedback(String feedback) {
		textPane.setText(feedback);
	}
	
	public void colorCodeFeedback(Color color) {
		textPane.setForeground(color);
	}
}
