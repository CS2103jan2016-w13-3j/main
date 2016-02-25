import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class UI {

	private JFrame frame;
	private JTextField txtCommand;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 664, 311);
		frame.getContentPane().add(scrollPane);
		
		final JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(textPane);
		textPane.setToolTipText("Feedback text will be shown here.");
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(634, 57, 17, 311);
		frame.getContentPane().add(scrollBar);
		
		txtCommand = new JTextField();
		txtCommand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCommand.setText("");
			}
		});
		txtCommand.setText("Type your command here.");
		txtCommand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textPane.setText("Command: "+txtCommand.getText()+System.lineSeparator());
					txtCommand.setText("");
				}
			}
		});
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
	}
}
