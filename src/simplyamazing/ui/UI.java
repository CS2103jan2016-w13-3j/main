package simplyamazing.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import simplyamazing.logic.Logic;

public class UI {
	private static final String MESSAGE_LOG_USER_COMMAND_EXECUTED = "user command is successfully executed.";

	private static final Color COLOR_DARK_GREEN = new Color(0, 128, 0);

	private static final CharSequence CHARACTER_NEW_LINE = "\n";
	
	private static Logger logger = Logger.getLogger("UI");
	
	private JFrame frame;
	private JSeparator separator, separator_1;
	private JTextArea txtrHeader;
	
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
		frame.getContentPane().add(taskDataPanelController.getTaskDataPanel());
		frame.getContentPane().add(commandBarController.getCommandBar());
		frame.getContentPane().add(feedbackAreaController.getFeedbackArea());
	}

	private void setupFeedbackArea() {
		feedbackAreaController = new FeedbackAreaController();
	}

	private void setupTaskDataPanel() {
		taskDataPanelController = new TaskDataPanelController();
	}

	private void setupAppLogo() {
		txtrHeader = new JTextArea();
		txtrHeader.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
		txtrHeader.setEditable(false);
		txtrHeader.setText("Welcome to SimplyAmazing!");
		txtrHeader.setBounds(203, 11, 278, 22);
	}

	private void setupSeparators() {
		separator = new JSeparator();
		separator.setBounds(10, 379, 664, 2);
		separator_1 = new JSeparator();
		separator_1.setBounds(10, 44, 664, 2);
	}

	private void setupCommandBar() {
		commandBarController = new CommandBarController();
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
		feedbackAreaController.clear();
		try {
			feedback = logic.executeCommand(commandBarController.getCommand());
			logger.log(Level.INFO, MESSAGE_LOG_USER_COMMAND_EXECUTED);
			if(feedback.contains(CHARACTER_NEW_LINE)) {
				taskDataPanelController.setTaskData(feedback);
			} else {
				taskDataPanelController.clear();
				feedbackAreaController.colorCodeFeedback(COLOR_DARK_GREEN);
				feedbackAreaController.setFeedback(feedback);
				logger.log(Level.INFO, feedback);
			}
			commandBarController.clear();
		} catch (Exception e1) {
			feedback = getErrorMessage(e1);
			logger.log(Level.WARNING, feedback);
			feedbackAreaController.colorCodeFeedback(Color.RED);
			feedbackAreaController.setFeedback(feedback);
		}
	}

	public void getUserCommand() {
		String command = logic.getPreviousCommand();
		commandBarController.setCommand(command);
	}
}
