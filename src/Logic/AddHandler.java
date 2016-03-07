package Logic;

import Data.Task;
import Parser.Parser;
import Storage.Storage;


public class AddHandler {
	public static String command;
	public static Boolean isContentValid;
	
	public AddHandler(){
		isContentValid = false;
	}
	public boolean checkContentValid(Parser parser) throws Exception{
		 isContentValid = parser.parserCheckAddCommand(command);
		 return isContentValid;
	}
	public void setContent(String taskContent) {
		command = taskContent;
	}
	public String addTask(Parser parser, Storage storage) throws Exception {
		Task taskToAdd = parser.parseAddCommand(command);;
		return storage.addTask(taskToAdd);
	}
}
