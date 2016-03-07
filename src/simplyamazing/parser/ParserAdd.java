package simplyamazing.parser;

import simplyamazing.data.Task;

public class ParserAdd {
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String KEYWORD_PRIORITY = "priority";
	
	
	private static final String[] KEYWORDS = {KEYWORD_PRIORITY,KEYWORD_DEADLINE, KEYWORD_SCHEDULE_FROM, KEYWORD_SCHEDULE_TO};
	
	public Task parseAddCommand(String taskInfo) throws Exception {
		Task task = new Task();
		
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {	// For events
			String description  = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[0].trim();
			String startTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[0].trim();
			String endTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[1].trim();
			task.setDescription(description);
			task.setStartTime(startTime);
			task.setEndTime(endTime);
		} else if (taskInfo.contains(KEYWORD_DEADLINE)) { // For deadlines
			String description  = taskInfo.trim().split(KEYWORD_DEADLINE)[0].trim();
			String endTime = taskInfo.trim().split(KEYWORD_DEADLINE)[1].trim();
			task.setDescription(description);
			task.setEndTime(endTime);
		} else {
			task.setDescription(taskInfo.trim());
		}
		return task;
	}
}
