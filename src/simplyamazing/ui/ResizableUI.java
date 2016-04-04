package simplyamazing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import simplyamazing.logic.Logic;

public class ResizableUI {
	private static final String MESSAGE_LOG_USER_COMMAND_EXECUTED = "user command is successfully executed.";

	private static final Color COLOR_DARK_GREEN = new Color(0, 128, 0);

	private static final String CHARACTER_NEW_LINE = "\n";
	public static final String FIELD_SEPARATOR = ",";
	private static final String STRING_ERROR = "Error";
	
	private static Logger logger = Logger.getLogger("UI");
	
	private JFrame frame;
	private JPanel panel;
	private JSeparator separator, separator_1;
	private JTextArea txtrHeader;
	private JScrollPane scrollPane;
	private GridBagConstraints gbc;
	
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
					ResizableUI window = new ResizableUI();
					window.frame.setVisible(true);
					commandBarController.getCommandBar().requestFocusInWindow();
					logic = new Logic();
					String taskDataString = window.getTaskData();
					if (taskDataString.contains(CHARACTER_NEW_LINE)) {
						window.updateTaskTable();
					} else {
						window.scrollPane.setVisible(false);
						feedbackArea.colorCodeFeedback(COLOR_DARK_GREEN);
						feedbackArea.setFeedback(taskDataString);
						logger.log(Level.INFO, taskDataString);
					}
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
	public ResizableUI() {
		initialize();
		addUIComponentsToFrame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFrame();
		setupPanels();
		setupAppLogo();
		setupFeedbackArea();
		setupCommandBar();
		setupScrollPane();
		setupSeparators();
	}

	private void setupScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		//scrollPane.setBounds(10, 57, 664, 278);
	}

	private void addUIComponentsToFrame() {
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		GridBagConstraints gbc_header = gbc;
		gbc_header.insets = new Insets(20,180,0,180);
		panel.add(txtrHeader, gbc_header);
		
		gbc.gridy++;
		gbc.insets = new Insets(0,10,0,10);
		panel.add(separator_1, gbc);
		
		gbc.gridy++;
		panel.add(scrollPane, gbc);
		
		gbc.gridy++;
		panel.add(separator, gbc);
		
		gbc.gridy++;
		panel.add(feedbackArea.getFeedbackArea(), gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(0,10,10,10);
		panel.add(commandBarController.getCommandBar(), gbc);
	}

	private void setupFeedbackArea() {
		feedbackArea = new FeedbackArea();
	}

	private void setupInstructionPanel() {
		instructionPanel = new InstructionPanel();
	}
	
	private void setupTaskDataPanel(Object[][] taskData) {
		taskDataPanel = new TaskDataPanel(taskData);
	}

	private void setupAppLogo() {
		txtrHeader = new JTextArea();
		txtrHeader.setFont(new Font("Lucida Calligraphy", Font.BOLD, 16));
		txtrHeader.setEditable(false);
		txtrHeader.setText("Welcome to SimplyAmazing!");
		txtrHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		//txtrHeader.setBounds(203, 11, 278, 22);
	}

	private void setupSeparators() {
		separator = new JSeparator();
		//separator.setBounds(10, 379, 664, 2);
		separator_1 = new JSeparator();
		//separator_1.setBounds(10, 44, 664, 2);
	}

	private void setupCommandBar() {
		commandBarController = new CommandBarController();
		commandBarController.handleKeyPressedEvent(this);
	}

	private void setupFrame() {
		frame = new JFrame();
		//frame.setBackground(Color.WHITE);
		frame.setForeground(Color.WHITE);
		frame.setBounds(100, 100, 683, 732);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setupPanels() {
		panel = new JPanel(new GridBagLayout());
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		//panel.setPreferredSize(frame.getContentPane().getSize());
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
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
				} 
				if (feedback.contains(STRING_ERROR)) {
					feedbackArea.colorCodeFeedback(Color.RED);
					logger.log(Level.WARNING, feedback);
				} else {
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
