package simplyamazing.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import simplyamazing.logic.Logic;

public class UI {
	private JFrame frame;
	
	private static Logic logic;
	private static CommandBarController commandBarController;
	private static FeedbackAreaController feedbackAreaController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
					commandBarController.getTxtCommand().requestFocusInWindow();
					logic = new Logic();
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
		frame.setResizable(false);
		frame.setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBounds(100, 100, 700, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		commandBarController = new CommandBarController(this);
		frame.getContentPane().add(commandBarController.getTxtCommand());
		
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
		
		feedbackAreaController = new FeedbackAreaController();
		scrollPane.setViewportView(feedbackAreaController.getTextPane());
	}

	public void executeUserCommand() {
		String feedback = null;
		try {
			feedback = logic.executeCommand(commandBarController.getCommand());
			feedbackAreaController.colorCodeFeedback(Color.BLUE);
			commandBarController.clearCommand();
		} catch (Exception e1) {
			feedback = getErrorMessage(e1);
			feedbackAreaController.colorCodeFeedback(Color.RED);
		}
		feedbackAreaController.setFeedback(feedback);
	}
}