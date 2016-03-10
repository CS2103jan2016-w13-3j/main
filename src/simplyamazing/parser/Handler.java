package simplyamazing.parser;

import simplyamazing.data.Task;

public class Handler {
	private Task task;
	private String commandType;
	private String index;
	private String keyWord;
	private String feedback;
	private boolean hasError;
   
	private static final String INDEX_DEFAULT_VALUE = null;
	private static final String EMPTY_STRING = "";
	private static final boolean HASERROR_DEFAULT_VALUE = false;
	private static final Task TASK_DEFAULT_VALUE = null;

	public Handler() {
		task = new Task();
		this.commandType = EMPTY_STRING;
		this.index = INDEX_DEFAULT_VALUE;
		this.keyWord = EMPTY_STRING;
		this.feedback = EMPTY_STRING;
		this.hasError = HASERROR_DEFAULT_VALUE;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public void setIndex(String index) {
		this.index = index;
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

	public Task getTask() {
		return task;
	}

	public String getCommandType() {
		return commandType;
	}

	public String getIndex() {
		return index;
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

}
