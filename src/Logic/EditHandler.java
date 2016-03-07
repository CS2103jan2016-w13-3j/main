package Logic;

import java.util.ArrayList;

import Data.Task;
import Parser.Parser;
import Storage.Storage;

public class EditHandler {
	private static ArrayList<Task> list;
	private static int indexToEdit;
	private static String fieldValues;
	
	public void setList(ArrayList<Task> logicList){
		list = logicList;
	}
	
	public void setIndex(String commandContent, Parser parser) {
		indexToEdit = Integer.parseInt(parser.getFirstWord(commandContent));
	}
	
	public boolean checkIndexValid(){
		if (indexToEdit <= 0 || indexToEdit>list.size()) {
			return false;
		} else{
			return true;
		}
	}
	
	public void setFieldValues(String commandContent, Parser parser) {
		fieldValues = parser.removeFirstWord(commandContent);
	}

	public boolean checkFieldValid(Parser parser) throws Exception {
		if(parser.parserCheckEditCommand(fieldValues) == true){
			return true;
		} else {
			return false;
		}
	}
	
	public String editTask(Parser parser, Storage storage) throws Exception {
		Task updatedContent = parser.parseEditCommand(fieldValues);
		Task originalTask = list.get(indexToEdit - 1);
		return storage.editTask(originalTask, updatedContent);
	}
}
