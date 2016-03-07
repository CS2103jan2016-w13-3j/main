package Logic;

import java.util.ArrayList;
import Storage.Storage;
import Data.Task;

public class ViewHandler {
	ArrayList<Task> list;
	String keyWord;
	
	public void setKeyword(String keyWord) {
		this.keyWord = keyWord;
	}

	public boolean checkKeywordValid() {
		if (keyWord.equals("")){
			return true;
		} else if (keyWord.equalsIgnoreCase("events")) {
			return true;
		} else if (keyWord.equalsIgnoreCase("deadlines")) {
			return true;
		} else if (keyWord.equalsIgnoreCase("tasks")) {
			return true;
		} else if (keyWord.equalsIgnoreCase("done")) {
			return true;
		} else if (keyWord.equalsIgnoreCase("overdue")) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Task> getList(Storage storage) throws Exception {
		list = storage.load(keyWord);
		return list;
	}
}