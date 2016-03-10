package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CommandBarController {
	private static JTextField txtCommand;
	
	private UI ui;
	
	public CommandBarController(final UI ui) {
		this.ui = ui;
		txtCommand = new JTextField();
		txtCommand.setToolTipText("Type your command here.");
		txtCommand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCommand.setBounds(10, 392, 664, 33);
		txtCommand.setColumns(10);
		txtCommand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ui.executeUserCommand();
					clearCommand();
				}
			}
		});
	}

	public JTextField getTxtCommand() {
		return txtCommand;
	}

	public String getCommand() {
		return txtCommand.getText();
	}
	
	public void clearCommand() {
		txtCommand.setText("");
	}
}
