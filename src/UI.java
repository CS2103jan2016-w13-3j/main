import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class UI {
	private JFrame frame;
	private static JTextField txtCommand;
	private static JTextPane textPane;
	private static Logic logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
					logic = new Logic();
					executeUserCommand();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static String getErrorMessage(Exception e) {
		return e.getMessage();
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBounds(100, 100, 700, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtCommand = new JTextField();
		txtCommand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCommand.setText("");
			}
		});
		txtCommand.setText("Type your command here.");
		txtCommand.setToolTipText("Type your command here.");
		txtCommand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCommand.setBounds(10, 392, 664, 33);
		frame.getContentPane().add(txtCommand);
		txtCommand.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 379, 664, 2);
		frame.getContentPane().add(separator);
		
		JTextArea txtrHeader = new JTextArea();
		txtrHeader.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
		txtrHeader.setEditable(false);
		txtrHeader.setText("Welcome to SimplyAmazing!");
		txtrHeader.setBounds(203, 11, 278, 22);
		frame.getContentPane().add(txtrHeader);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 44, 664, 2);
		frame.getContentPane().add(separator_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 664, 311);
		frame.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setForeground(Color.RED);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPane.setEditable(false);
		textPane.setBounds(10, 57, 664, 311);
		scrollPane.setViewportView(textPane);
	}

	private static void executeUserCommand() {
		txtCommand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String feedback = null;
					
					try {
						feedback = logic.executeCommand(txtCommand.getText());
						textPane.setForeground(Color.BLUE);
					} catch (Exception e1) {
						feedback = getErrorMessage(e1);
					}
					textPane.setText(feedback);
					txtCommand.setText("");
				}
			}
		});
	}
}