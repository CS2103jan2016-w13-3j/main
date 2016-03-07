package Logic;

import Data.Task;
import Parser.Parser;
import Storage.Storage;
import java.util.ArrayList;

public class DeleteHandler {
	private static ArrayList<Task> list;
	private static Parser parser;
	private static Storage storage;
	private static int indexToDelete;

	public DeleteHandler(){
		parser = new Parser();
		storage = new Storage();
	}
	public void setList(ArrayList<Task> logicList){
		list = logicList;
	}
	public static ArrayList<Task> getList(){
		return list;
	}
	public void setIndex(String commandContent) {
		indexToDelete = Integer.parseInt(commandContent);
	}
	public boolean checkIndexValid(){
		if (indexToDelete <= 0 || indexToDelete>list.size()) {
			return false;
		} else{
			return true;
		}
	}
	public String deleteTask() throws Exception {
		Task taskToDelete = list.get(indexToDelete - 1);
		list.remove(indexToDelete - 1);
		return storage.deleteTask(taskToDelete);
	}
}
