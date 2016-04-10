//@@author A0126289W
package simplyamazing.ui;

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
import javax.swing.JTextField;

import simplyamazing.logic.Logic;

public class UI {
	private static final String MESSAGE_WELCOME = "Please enter \"help\" to learn about available commands and their formats";
	private static final String MESSAGE_LOG_USER_COMMAND_EXECUTED = "user command is successfully executed.";
	private static final String MESSAGE_EMPTY_LIST = "List is empty";
	private static final String MESSAGE_NO_TASKS_FOUND = "There are no tasks containing the given keyword";
	
	private static final Color COLOR_DARK_GREEN = new Color(0, 128, 0);

	private static final String CHARACTER_NEW_LINE = "\n";
	public static final String FIELD_SEPARATOR = ",";
	private static final String STRING_NULL = "";
	private static final String STRING_ERROR = "Error";
	
	private static Logger logger = Logger.getLogger("UI");
	
	private JFrame frame;
	private static JSeparator separator, separator_1;
	private static JTextArea appLogo;
	private JScrollPane scrollPane;
	private static JTextField commandBar;	
	private static JTextArea feedbackArea;
	
	
	private static Logic logic;
	private static CommandBarController commandBarController;
	private static TaskDataPanel taskDataPanel;
	private static InstructionPanel instructionPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
					commandBar.requestFocusInWindow();
					logic = new Logic();
					String taskDataString = window.getTaskData();
					if (taskDataString.contains(CHARACTER_NEW_LINE)) {
						window.updateTaskDataTable();
					} else {
						window.scrollPane.setVisible(false);
					}
					setFeedback(MESSAGE_WELCOME, Color.BLACK);
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
		setupFeedbackArea();
		setupCommandBar();
		setupScrollPane();
		setupSeparators();
	}

	private void setupScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(10, 67, 804, 442);
	}

	private void addUIComponentsToFrame() {
		frame.getContentPane().add(appLogo);
		frame.getContentPane().add(separator_1);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(separator);
		frame.getContentPane().add(feedbackArea);
		frame.getContentPane().add(commandBar);
	}

	private void setupFeedbackArea() {
		feedbackArea = new JTextArea();
		feedbackArea.setEditable(false);
		feedbackArea.setBounds(10, 542, 804, 22);
	}

	private void setupInstructionPanel() {
		instructionPanel = new InstructionPanel();
		instructionPanel.getInstrctionPanel().setBounds(10, 57, 676, 539);
	}
	
	private void setupTaskDataPanel(Object[][] taskData) {
		taskDataPanel = new TaskDataPanel(taskData);
	}

	private void setupAppLogo() {
		appLogo = new JTextArea();
		appLogo.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
		appLogo.setEditable(false);
		appLogo.setText("Welcome to SimplyAmazing!");
		appLogo.setBounds(273, 10, 278, 33);
	}

	private void setupSeparators() {
		separator = new JSeparator();
		separator.setBounds(10, 529, 804, 2);
		separator_1 = new JSeparator();
		separator_1.setBounds(10, 54, 804, 2);
	}

	private void setupCommandBar() {
		commandBar = new JTextField();
		commandBar.setForeground(Color.BLACK);
		commandBar.setToolTipText("Type your command here.");
		commandBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		commandBar.setColumns(10);
		commandBar.setBounds(10, 575, 804, 33);
		commandBarController = new CommandBarController();
		commandBarController.handleKeyPressedEvent(this, commandBar);
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 830, 657);
		frame.setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public void executeUserCommand() {
		String feedback = null;
		String command = commandBar.getText();
		commandBarController.clearCommand(commandBar);
		feedbackArea.setText(STRING_NULL);
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
					updateTaskDataTable();
				} else {
					scrollPane.setVisible(false);
				}
				if (feedback.contains(STRING_ERROR)) {
					setFeedback(feedback, Color.RED);
				} else {
					if (feedback.matches(MESSAGE_EMPTY_LIST) || feedback.matches(MESSAGE_NO_TASKS_FOUND)) {
						scrollPane.setVisible(false);
					}
					setFeedback(feedback, COLOR_DARK_GREEN);
				}
			} 
		} catch (Exception e1) {
			feedback = getErrorMessage(e1);
			logger.log(Level.WARNING, feedback);
			feedbackArea.setForeground(Color.RED);
			feedbackArea.setText(feedback);
		}
	}

	private static void setFeedback(String feedback, Color color) {
		feedbackArea.setForeground(color);
		logger.log(Level.WARNING, feedback);
		feedbackArea.setText(feedback);
	}
	
	private void updateTaskDataTable() throws Exception {
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

	public String getPreviousUserCommand() {
		String command = logic.getPreviousCommand();
		return command;
	}
}
