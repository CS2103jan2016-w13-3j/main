//@@author A0126289W
package simplyamazing.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CommandBarController {
	
	private static final String STRING_NULL = "";

	public void handleKeyPressedEvent(final UI ui, final JTextField commandBar) {
		commandBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isActionKey()) {
					if (e.getKeyCode() == KeyEvent.VK_UP) { // "Up" key used to get previous command
						commandBar.setText(ui.getPreviousUserCommand());
					}
				} else {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) { // "Enter" key used to execute command
						ui.executeUserCommand();
					} 
				}
			}
		});
	}
	
	public void clearCommand(final JTextField commandBar) {
		commandBar.setText(STRING_NULL);
	}
}
