//@@author A0126289W
package simplyamazing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import simplyamazing.logic.Logic;

public class FixedSizedUI {
	private static final String MESSAGE_WELCOME = "Please enter \"help\" to learn about available commands and their formats";
	private static final String MESSAGE_LOG_USER_COMMAND_EXECUTED = "user command is successfully executed.";
	private static final String MESSAGE_EMPTY_LIST = "List is empty";
	private static final String MESSAGE_NO_TASKS_FOUND = "There are no tasks containing the given keyword";
	
	private static final Color COLOR_DARK_GREEN = new Color(0, 128, 0);

	private static final String CHARACTER_NEW_LINE = "\n";
	public static final String FIELD_SEPARATOR = ",";
	private static final String STRING_ERROR = "Error";
	
	private static Logger logger = Logger.getLogger("UI");
	
	private JFrame frame;
	private JSeparator separator, separator_1;
	private JTextArea txtrHeader;
	private JScrollPane scrollPane;
	
	private static Logic logic;
	private static CommandBarController commandBarController;
	private static TaskDataPanel taskDataPanel;
	private static FeedbackArea feedbackArea;
	private static InstructionPanel instructionPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FixedSizedUI window = new FixedSizedUI();
					window.frame.setVisible(true);
					commandBarController.getCommandBar().requestFocusInWindow();
					logic = new Logic();
					String taskDataString = window.getTaskData();
					if (taskDataString.contains(CHARACTER_NEW_LINE)) {
						window.updateTaskTable();
					} else {
						window.scrollPane.setVisible(false);
					}
					feedbackArea.colorCodeFeedback(Color.BLACK);
					feedbackArea.setFeedback(MESSAGE_WELCOME);
					logger.log(Level.INFO, MESSAGE_WELCOME);
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
	 * @throws Exception 
	 */
	public FixedSizedUI() {
		initialize();
		addUIComponentsToFrame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		setupAppLogo();
		setupFeedbackArea();
		setupCommandBar();
		setupScrollPane();
		setupSeparators();
	}

	private void setupScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(10, 57, 744, 278);
	}

	private void addUIComponentsToFrame() {
		frame.getContentPane().add(txtrHeader);
		frame.getContentPane().add(separator_1);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(separator);
		frame.getContentPane().add(feedbackArea.getFeedbackArea());
		frame.getContentPane().add(commandBarController.getCommandBar());
	}

	private void setupFeedbackArea() {
		feedbackArea = new FeedbackArea();
		feedbackArea.getFeedbackArea().setBounds(10, 346, 744, 22);
	}

	private void setupInstructionPanel() {
		instructionPanel = new InstructionPanel();
		instructionPanel.getInstrctionPanel().setBounds(10, 57, 744, 311);
	}
	
	private void setupTaskDataPanel(Object[][] taskData) {
		taskDataPanel = new TaskDataPanel(taskData);
	}

	private void setupAppLogo() {
		txtrHeader = new JTextArea();
		txtrHeader.setFont(new Font("Lucida Calligraphy", Font.BOLD, 18));
		txtrHeader.setEditable(false);
		txtrHeader.setText("Welcome to SimplyAmazing!");
		txtrHeader.setBounds(227, 6, 310, 27);
	}

	private void setupSeparators() {
		separator = new JSeparator();
		separator.setBounds(10, 379, 744, 4);
		separator_1 = new JSeparator();
		separator_1.setBounds(10, 44, 744, 4);
	}

	private void setupCommandBar() {
		commandBarController = new CommandBarController();
		commandBarController.getCommandBar().setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		commandBarController.getCommandBar().setBounds(10, 392, 744, 33);
		commandBarController.handleKeyPressedEvent(this);
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBounds(100, 100, 770, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public void executeUserCommand() {
		String feedback = null;
		String command = commandBarController.getCommand();
		feedbackArea.clear();
		try {
			feedback = logic.executeCommand(command);
			logger.log(Level.INFO, MESSAGE_LOG_USER_COMMAND_EXECUTED);
			
			if (feedback.contains(CHARACTER_NEW_LINE)) {
				String[] tasks = feedback.split(CHARACTER_NEW_LINE);
				
				if (tasks[0].split(FIELD_SEPARATOR).length == 6) {
					String[][] taskData = new String[tasks.length][6];
					for (int i = 0; i < tasks.length; i++) {
						taskData[i] = tasks[i].split(FIELD_SEPARATOR);
					}
					scrollPane.setVisible(true);
					setupTaskDataPanel(taskData); 
					scrollPane.setViewportView(taskDataPanel.getTaskDataPanel());
					scrollPane.getViewport().setBackground(Color.WHITE);
				} else {
					setupInstructionPanel();
					scrollPane.setVisible(true);
					scrollPane.setViewportView(instructionPanel.getInstrctionPanel());
					instructionPanel.setInstruction(feedback);
				}
			} else { // only feedback
				if (getTaskData().contains(CHARACTER_NEW_LINE)) {
					updateTaskTable();
				} else {
					scrollPane.setVisible(false);
				}
				if (feedback.contains(STRING_ERROR)) {
					feedbackArea.colorCodeFeedback(Color.RED);
					logger.log(Level.WARNING, feedback);
				} else {
					if (feedback.matches(MESSAGE_EMPTY_LIST) || feedback.matches(MESSAGE_NO_TASKS_FOUND)) {
						scrollPane.setVisible(false);
					}
					feedbackArea.colorCodeFeedback(COLOR_DARK_GREEN);
					logger.log(Level.INFO, feedback);
				}
				feedbackArea.setFeedback(feedback);
			}
			commandBarController.clear(); 
		} catch (Exception e1) {
			feedback = getErrorMessage(e1);
			logger.log(Level.WARNING, feedback);
			feedbackArea.colorCodeFeedback(Color.RED);
			feedbackArea.setFeedback(feedback);
		}
	}
	
	private void updateTaskTable() throws Exception {
		String[] tasks = getTaskData().split(CHARACTER_NEW_LINE);
		
		String[][] taskData = new String[tasks.length][6];
		for (int i = 0; i < tasks.length; i++) {
			taskData[i] = tasks[i].split(FIELD_SEPARATOR);
		}
		setupTaskDataPanel(taskData); 
		scrollPane.setVisible(true);
		scrollPane.setViewportView(taskDataPanel.getTaskDataPanel());
		scrollPane.getViewport().setBackground(Color.WHITE);
	}

	private String getTaskData() throws Exception {
		String taskDataString = logic.getView();
		return taskDataString;
	}

	public void getUserCommand() {
		String command = logic.getPreviousCommand();
		commandBarController.setCommand(command);
	}
}
