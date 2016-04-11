//@@author A0126289W
package test;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import simplyamazing.data.Task;
import simplyamazing.storage.Storage;
import simplyamazing.ui.CommandBarController;
import simplyamazing.ui.UI;

public class UITest {
	
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	
	private static final String CHARACTER_NEW_LINE = "\n";
	private static final String CHARACTER_SPACE = " ";
	
	private static final String COMMAND_EMPTY = "";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_VIEW = "view";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_ADD_EVENT = COMMAND_ADD + CHARACTER_SPACE 
			+ "hackathon in SOC from 09:30 26 May 2017 to 10:00 27 May 2017";
	private static final String COMMAND_ADD_DEADLINE = COMMAND_ADD + CHARACTER_SPACE 
			+ "cs2103 peer review by 23:59 25 May 2017";
	
	private static final String PARAM_SET_LOCATION_DIRECTORY = "C:\\Users\\Public\\Documents";
	
	private static final String FEEDBACK_COMMAND_EMPTY = "Error: Invalid command entered."
			+ " Please enter \"help\" to view all commands and their format";
	private static final String FEEDBACK_NO_PREVIOUS_COMMAND = "Error: There is no previous command to undo";
	private static final String FEEDBACK_EMPTY_LIST = "List is empty";
	
	private static final String FEEDBACK_HELP_ALL = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help search \n6. help mark\n"
			+ "7. help unmark\n8. help undo\n9. help redo\n10. help location \n11. help exit\n";
	
	@BeforeClass
	public static void setup() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
	}
	
	/*
	 * Operation to test: executeUserCommand(String command): String
	 * Equivalence partition: 
	 * command: [null] [not null] [valid] [not valid]
	 * Boundary values: Empty String, a String of some length
	 */	
	
	@Test
	public void testEmptyCommand() {
		UI ui = new UI();
		
		new CommandBarController().clearCommand(ui.commandBar);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_COMMAND_EMPTY, ui.feedbackArea.getText()); 
	}
	
	@Test
	public void testUndoCommandWithoutAnyPreviousCommand() throws Exception {
		UI ui = new UI();
		
		ui.commandBar.setText(COMMAND_UNDO);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_NO_PREVIOUS_COMMAND, ui.feedbackArea.getText());
	}
	
	@Test
	public void testHelpCommand() {
		UI ui = new UI();
		ui.commandBar.setText(COMMAND_HELP);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_HELP_ALL, ui.instructionPanel.getInstrctionPanel().getText());
	}
	
	@Test
	public void testViewCommandForNoTask() {
		UI ui = new UI();
		
		ui.commandBar.setText(COMMAND_VIEW);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_EMPTY_LIST, ui.feedbackArea.getText());
	}
	
	@Test
	public void testViewCommandWithTasks() throws Exception {
		UI ui = new UI();
		ui.commandBar.setText(COMMAND_ADD_EVENT);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
        
        ui.commandBar.setText(COMMAND_ADD_DEADLINE);
        ui.commandBar.dispatchEvent(event); 
	    
        ui.commandBar.setText(COMMAND_VIEW);
        ui.commandBar.dispatchEvent(event); 
        
        File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
        Storage storage = new Storage();
        ArrayList<String> lines = storage.getFileManager().readFile(todo);
        String tasks = COMMAND_EMPTY;
        for (int i=0; i<lines.size(); i++) {
        	tasks += (i+1) + Task.FIELD_SEPARATOR + lines.get(i) + CHARACTER_NEW_LINE;
        }
        assertEquals(tasks, ui.feedback);
        storage.getFileManager().cleanFile(todo);
	}
}
