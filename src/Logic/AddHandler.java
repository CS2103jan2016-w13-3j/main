package Logic;

import Data.Task;
import Parser.Parser;
import Storage.Storage;


public class AddHandler {
	public static String command;
	public static Parser parser;
	public static Storage storage;
	public static Boolean isContentValid;
	
	public AddHandler(){
		parser = new Parser();
		storage = new Storage();
		isContentValid = false;
	}
	public boolean checkContentValid() throws Exception{
		 isContentValid = parser.parserCheckAddCommand(command);
		 return isContentValid;
	}
	public void setContent(String taskContent) {
		command = taskContent;
	}
	public String addTask() throws Exception {
		Task taskToAdd = parser.parseAddCommand(command);;
		return storage.addTask(taskToAdd);
	}
}
