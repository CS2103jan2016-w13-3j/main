package simplyamazing.ui;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import simplyamazing.logic.Logic;

public class UI {
	private static final CharSequence CHARACTER_NEW_LINE = "\n";
	
	private JFrame frame;
	private JTextField txtCommand;
	private JTextPane textPane;
	private JSeparator separator, separator_1;
	private JScrollPane scrollPane;
	private JTextArea txtrHeader;
	private JTextArea textArea;
	
	private static Logic logic;
	private static CommandBarController commandBarController;
	private static TaskDataPanelController taskDataPanelController;
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
					commandBarController.getCommandBar().requestFocusInWindow();
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
		addUIComponentsToFrame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		setupAppLogo();
		setupTaskDataPanel();
		setupFeedbackArea();
		setupCommandBar();
		setupSeparators();
	}

	private void addUIComponentsToFrame() {
		frame.getContentPane().add(txtrHeader);
		frame.getContentPane().add(separator);
		frame.getContentPane().add(separator_1);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(txtCommand);
		frame.getContentPane().add(textArea);
	}

	private void setupFeedbackArea() {
		textArea = new JTextArea();
		textArea.setBounds(10, 346, 664, 22);
		feedbackAreaController = new FeedbackAreaController();
		feedbackAreaController.setFeedbackArea(textArea);
	}

	private void setupTaskDataPanel() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 664, 278);
		textPane = new JTextPane();
		textPane.setToolTipText("Feedback message will be shown here.");
		textPane.setForeground(Color.BLACK);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPane.setEditable(false);
		textPane.setBounds(10, 57, 664, 311);
		scrollPane.setViewportView(textPane);
		taskDataPanelController = new TaskDataPanelController();
		taskDataPanelController.setFeedbackPane(textPane);	
	}

	private void setupAppLogo() {
		txtrHeader = new JTextArea();
		txtrHeader.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
		txtrHeader.setEditable(false);
		txtrHeader.setText("Welcome to SimplyAmazing!");
		txtrHeader.setBounds(203, 11, 278, 22);
	}

	private JSeparator setupSeparators() {
		separator = new JSeparator();
		separator.setBounds(10, 379, 664, 2);
		separator_1 = new JSeparator();
		separator_1.setBounds(10, 44, 664, 2);
		return separator;
	}

	private void setupCommandBar() {
		txtCommand = new JTextField();
		txtCommand.setToolTipText("Type your command here.");
		txtCommand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCommand.setBounds(10, 392, 664, 33);
		txtCommand.setColumns(10);
		commandBarController = new CommandBarController();
		commandBarController.setCommandBar(txtCommand);
		commandBarController.handleKeyPressedEvent(this);
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBounds(100, 100, 700, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public void executeUserCommand() {
		String feedback = null;
		try {
			feedback = logic.executeCommand(commandBarController.getCommand());
			if(feedback.contains(CHARACTER_NEW_LINE)) {
				taskDataPanelController.setFeedback(feedback);
			} else {
				feedbackAreaController.colorCodeFeedback(Color.GREEN);
				feedbackAreaController.setFeedback(feedback);
			}
			commandBarController.clearCommand();
		} catch (Exception e1) {
			feedback = getErrorMessage(e1);
			feedbackAreaController.colorCodeFeedback(Color.RED);
			feedbackAreaController.setFeedback(feedback);
		}
	}
}
