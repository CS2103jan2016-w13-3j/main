//@@author A0126289W
package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class InstructionPanel {
	
	private static final int FONT_SIZE_DEFAULT = 16;
	private static final String FONT_DEFAULT = "Tahoma";
	
	private static final String STRING_NULL = "";
	
	private JTextPane instructionPanel;
	
	public InstructionPanel() {
		instructionPanel = new JTextPane();
		instructionPanel.setForeground(Color.BLACK);
		instructionPanel.setFont(new Font(FONT_DEFAULT, Font.PLAIN, FONT_SIZE_DEFAULT));
		instructionPanel.setEditable(false);
	}
	
	public JTextPane getInstrctionPanel() {
		return instructionPanel;
	}
	
	public void setInstruction(String instruction) {
		instructionPanel.setText(instruction);
	}
	
	public void clear() {
		instructionPanel.setText(STRING_NULL);
	}
}
