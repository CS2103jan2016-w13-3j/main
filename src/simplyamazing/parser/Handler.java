//@@author A0112659A
package simplyamazing.parser;

import java.util.ArrayList;

import simplyamazing.data.Task;

public class Handler {
	private Task task;
	private String commandType;
	private String keyWord;
	private String feedback;
	private boolean hasError;
	private boolean hasEndDate;
	private ArrayList<Integer> indexes;

	private static final String INDEX_DEFAULT_VALUE = null;
	private static final String EMPTY_STRING = "";
	private static final boolean HASERROR_DEFAULT_VALUE = false;
	private static final boolean HASENDDATE_DEFAULT_VALUE = false;
	private static final Task TASK_DEFAULT_VALUE = null;

	public Handler() {
		task = new Task();
		this.commandType = EMPTY_STRING;
		this.keyWord = EMPTY_STRING;
		this.feedback = EMPTY_STRING;
		this.hasError = HASERROR_DEFAULT_VALUE;
		this.hasEndDate = HASENDDATE_DEFAULT_VALUE;
		this.indexes = new ArrayList<Integer>();
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public void setIndex(String index) {
		this.indexes.add(Integer.parseInt(index));
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public void setFeedBack(String feedback) {
		this.feedback = feedback;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public void setHasEndDate(boolean hasEndDate) {
		this.hasEndDate = hasEndDate;
	}

	public Task getTask() {
		return task;
	}

	public String getCommandType() {
		return commandType;
	}

	public ArrayList<Integer> getIndexList() {
		return indexes;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public String getFeedBack() {
		return feedback;
	}

	public boolean getHasError() {
		return hasError;
	}

	public boolean getHasEndDate() {
		return hasEndDate;
	}

}
