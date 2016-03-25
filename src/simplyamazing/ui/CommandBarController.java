package simplyamazing.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CommandBarController {
	private static JTextField commandBar;
	
	public CommandBarController() {
		commandBar = new JTextField();
		commandBar.setForeground(Color.BLACK);
		commandBar.setToolTipText("Type your command here.");
		commandBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		commandBar.setBounds(10, 392, 664, 33);
		commandBar.setColumns(10);
	}
	
	public void handleKeyPressedEvent(final UI ui) {
		commandBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isActionKey()) {
					if(e.getKeyCode() == KeyEvent.VK_UP) {
						ui.getUserCommand();
					}
				} else {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						ui.executeUserCommand();
						clear();
					} 
				}
			}
		});
	}

	public JTextField getCommandBar() {
		return commandBar;
	}

	public String getCommand() {
		return commandBar.getText();
	}
	
	public void setCommand(String command) {
		commandBar.setText(command);
	}
	
	public void clear() {
		commandBar.setText("");
	}
}
