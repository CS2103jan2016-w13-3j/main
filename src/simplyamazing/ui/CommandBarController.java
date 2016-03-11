package simplyamazing.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CommandBarController {
	private static JTextField commandBar;
	
	public void handleKeyPressedEvent(final UI ui) {
		commandBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ui.executeUserCommand();
					clearCommand();
				}
			}
		});
	}

	public JTextField getCommandBar() {
		return commandBar;
	}

	public void setCommandBar(JTextField commandBar) {
		this.commandBar = commandBar;
	}

	public String getCommand() {
		return commandBar.getText();
	}
	
	public void clearCommand() {
		commandBar.setText("");
	}
}
