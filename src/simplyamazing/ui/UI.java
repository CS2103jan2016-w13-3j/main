//@@author A0126289W
package simplyamazing.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import simplyamazing.data.Task;
import simplyamazing.logic.Logic;

public class UI {
	
	private static final int X_POSITION_FRAME = 100;
	private static final int X_POSITION_APP_LOGO = 273;

	private static final int Y_POSITION_INSTRUCTION_PANEL = 57;
	private static final int Y_POSITION_COMMAND_BAR = 575;
	private static final int Y_POSITION_FEEDBACK_AREA = 542;
	private static final int Y_POSITION_SEPARATOR1 = 54;
	private static final int Y_POSITION_SCROLL_PANE = 67;
	private static final int Y_POSITION_SEPARATOR = 529;
	private static final int Y_POSITION_FRAME = 100;
	
	private static final int HEIGHT_SEPARATOR = 2;
	private static final int HEIGHT_FEEDBACK_AREA = 22;
	private static final int HEIGHT_COMMAND_BAR = 33;
	private static final int HEIGHT_INSTRUCTION_PANEL = 539;
	private static final int HEIGHT_SCROLL_PANE = 442;
	private static final int HEIGHT_APP_LOGO = 33;
	private static final int HEIGHT_FRAME = 657;

	private static final int WIDTH = 804;
	private static final int WIDTH_INSTRUCTION_PANEL = 676;
	private static final int WIDTH_APP_LOGO = 278;
	private static final int WIDTH_FRAME = 830;
	
	private static final int FONT_SIZE_COMMAND_BAR = 14;
	private static final int FONT_SIZE_DEFAULT = 16;
	private static final String FONT_APP_LOGO = "Lucida Calligraphy";
	private static final String FONT_DEFAULT = "Tahoma";
	
	private static final int OFFSET = 10;

	private static final int NUM_TASK_FIELDS = 6;

	private static final String LOGGER_NAME = "simplyamazing";
	private static final String FILENAME_LOGGER = "\\logFile.txt";

	private static final String DIRECTORY_SYSTEM = "C:\\Users\\Public\\SimplyAmazing";
	
	private static final String TEXT_APP_LOGO = "Welcome to SimplyAmazing!";
	private static final String TEXT_TIP = "Type your command here.";
	
	private static final String MESSAGE_LOGGER_FILE_CREATION_FAILED = "Logger creation failed";
	private static final String MESSAGE_WELCOME = "Please enter \"help\" to learn about available commands and their formats";
	private static final String MESSAGE_EMPTY_LIST = "List is empty";
	private static final String MESSAGE_NO_TASKS_FOUND = "There are no tasks containing the given keyword";

	private static final Color COLOR_DARK_GREEN = new Color(0, 128, 0);

	private static final String CHARACTER_NEW_LINE = "\n";
	private static final String STRING_NULL = "";
	private static final String STRING_ERROR = "Error";

	private static final String MESSAGE_LOG_WELCOME = "Ready to use the system";
	private static final String MESSAGE_LOG_COMPONENTS_INIITIALIZED = "UI components are initialized";
	private static final String MESSAGE_LOG_COMPONENTS_ADDED = "UI components are added to the frame";
	private static final String MESSAGE_LOG_USER_COMMAND_EXECUTED = "User command is successfully executed.";
	
	public static String feedback = null;

	private JFrame frame;
	private JSeparator separator, separator_1;
	private JTextArea appLogo;
	private JScrollPane scrollPane;
	public JTextField commandBar;	
	public JTextArea feedbackArea;

	private static Logic logic;
	private static CommandBarController commandBarController;
	public static TaskDataPanel taskDataPanel;
	public static InstructionPanel instructionPanel;
	private static Logger logger;

	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static String getErrorMessage(Exception e) {
		return e.getMessage();
	}

	/*
	 * Create the application. 
	 */
	public UI() {
		createLogger();
		
		initialize();
		addUIComponentsToFrame();
		frame.setVisible(true);
		commandBar.requestFocusInWindow();
		
		logic = new Logic();
		
		try {
			String taskDataString = getTaskData();
			if (taskDataString.contains(CHARACTER_NEW_LINE)) {
				updateTaskDataTable(taskDataString);
			} else {
				scrollPane.setVisible(false); // Hide table if no task
			}
			setFeedback(MESSAGE_WELCOME, Color.BLACK);
			logger.log(Level.INFO, MESSAGE_LOG_WELCOME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createLogger() {
		logger = Logger.getLogger(LOGGER_NAME);
		new File(DIRECTORY_SYSTEM).mkdir(); // create system directory if not exist
		try {
			FileHandler fh = new FileHandler(DIRECTORY_SYSTEM + FILENAME_LOGGER, true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);
		} catch (Exception e){
			System.out.println(MESSAGE_LOGGER_FILE_CREATION_FAILED);
		}
	}
	
	private String getTaskData() throws Exception {
		String taskDataString = Logic.getView();
		return taskDataString;
	}
	
	private void updateTaskDataTable(String taskDataString) throws Exception {
		String[] tasks = taskDataString.split(CHARACTER_NEW_LINE);

		String[][] taskData = new String[tasks.length][NUM_TASK_FIELDS];
		for (int i = 0; i < tasks.length; i++) {
			taskData[i] = tasks[i].split(Task.FIELD_SEPARATOR);
		}
		setupTaskDataPanel(taskData); 
		scrollPane.setVisible(true);
		scrollPane.setViewportView(taskDataPanel.getTaskDataPanel());
		scrollPane.getViewport().setBackground(Color.WHITE);
	}

	private void initialize() {
		setupFrame();
		setupAppLogo();
		setupScrollPane();
		setupSeparators();
		setupFeedbackArea();
		setupCommandBar();
		logger.log(Level.INFO, MESSAGE_LOG_COMPONENTS_INIITIALIZED);
	}
	
	private void setupFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(X_POSITION_FRAME, Y_POSITION_FRAME, WIDTH_FRAME, HEIGHT_FRAME);
		frame.setForeground(Color.BLACK);
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	private void setupAppLogo() {
		appLogo = new JTextArea();
		appLogo.setFont(new Font(FONT_APP_LOGO, Font.BOLD, FONT_SIZE_DEFAULT));
		appLogo.setEditable(false);
		appLogo.setText(TEXT_APP_LOGO);
		appLogo.setBounds(X_POSITION_APP_LOGO, OFFSET, WIDTH_APP_LOGO, HEIGHT_APP_LOGO);
	}

	private void setupScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(OFFSET, Y_POSITION_SCROLL_PANE, WIDTH, HEIGHT_SCROLL_PANE);
	}

	private void setupSeparators() {
		separator = new JSeparator();
		separator.setBounds(OFFSET, Y_POSITION_SEPARATOR, WIDTH, HEIGHT_SEPARATOR);
		separator_1 = new JSeparator();
		separator_1.setBounds(OFFSET, Y_POSITION_SEPARATOR1, WIDTH, HEIGHT_SEPARATOR);
	}

	private void setupFeedbackArea() {
		feedbackArea = new JTextArea();
		feedbackArea.setEditable(false);
		feedbackArea.setBounds(OFFSET, Y_POSITION_FEEDBACK_AREA, WIDTH, HEIGHT_FEEDBACK_AREA);
	}

	private void setupCommandBar() {
		commandBar = new JTextField();
		commandBar.setForeground(Color.BLACK);
		commandBar.setToolTipText(TEXT_TIP);
		commandBar.setFont(new Font(FONT_DEFAULT, Font.PLAIN, FONT_SIZE_COMMAND_BAR));
		commandBar.setColumns(OFFSET);
		commandBar.setBounds(OFFSET, Y_POSITION_COMMAND_BAR, WIDTH, HEIGHT_COMMAND_BAR);
		commandBarController = new CommandBarController();
		commandBarController.handleKeyPressedEvent(this, commandBar);
	}
	
	private void addUIComponentsToFrame() {
		frame.getContentPane().add(appLogo);
		frame.getContentPane().add(separator_1);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(separator);
		frame.getContentPane().add(feedbackArea);
		frame.getContentPane().add(commandBar);
		logger.log(Level.INFO, MESSAGE_LOG_COMPONENTS_ADDED);
	}

	public void executeUserCommand() {
		String command = commandBar.getText();
		commandBarController.clearCommand(commandBar);
		feedbackArea.setText(STRING_NULL);
		try {
			feedback = logic.executeCommand(command);
			logger.log(Level.INFO, MESSAGE_LOG_USER_COMMAND_EXECUTED);

			if (feedback.contains(CHARACTER_NEW_LINE)) {
				String[] tasks = feedback.split(CHARACTER_NEW_LINE);

				if (tasks[0].split(Task.FIELD_SEPARATOR).length == NUM_TASK_FIELDS) {
					String[][] taskData = new String[tasks.length][NUM_TASK_FIELDS];
					for (int i = 0; i < tasks.length; i++) {
						taskData[i] = tasks[i].split(Task.FIELD_SEPARATOR);
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
				String taskDataString = getTaskData();
				if (taskDataString.contains(CHARACTER_NEW_LINE)) {
					updateTaskDataTable(taskDataString);
				} else {
					scrollPane.setVisible(false); // Hide table if no task
				}
				if (feedback.contains(STRING_ERROR)) {
					setFeedback(feedback, Color.RED);
					logger.log(Level.WARNING, feedback);
				} else {
					if (feedback.matches(MESSAGE_EMPTY_LIST) || feedback.matches(MESSAGE_NO_TASKS_FOUND)) {
						scrollPane.setVisible(false); // Hide table if no task
					}
					setFeedback(feedback, COLOR_DARK_GREEN);
					logger.log(Level.INFO, feedback);
				}
			} 
		} catch (Exception e1) {
			feedback = getErrorMessage(e1);
			logger.log(Level.WARNING, feedback);
			feedbackArea.setForeground(Color.RED);
			feedbackArea.setText(feedback);
		}
	}

	private void setupInstructionPanel() {
		instructionPanel = new InstructionPanel();
		instructionPanel.getInstrctionPanel().setBounds(OFFSET, Y_POSITION_INSTRUCTION_PANEL, WIDTH_INSTRUCTION_PANEL, HEIGHT_INSTRUCTION_PANEL);
	}

	private void setupTaskDataPanel(Object[][] taskData) {
		taskDataPanel = new TaskDataPanel(taskData);
	}
	
	private void setFeedback(String feedback, Color color) {
		feedbackArea.setForeground(color);
		feedbackArea.setText(feedback);
	}

	public String getPreviousUserCommand() {
		String command = logic.getPreviousCommand();
		return command;
	}
}
