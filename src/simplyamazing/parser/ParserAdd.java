package simplyamazing.parser;

import simplyamazing.data.Task;

public class ParserAdd {
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String KEYWORD_PRIORITY = "priority";
	
	public Handler parseAddCommand(Handler handler, String taskInfo) throws Exception {		
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {	// For events
			String description  = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[0].trim();
			String startTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[0].trim();
			String endTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[1].trim();
			handler.getTask().setDescription(description);
			handler.getTask().setStartTime(startTime);
			handler.getTask().setEndTime(endTime);
		} else if (taskInfo.contains(KEYWORD_DEADLINE)) { // For deadlines
			String description  = taskInfo.trim().split(KEYWORD_DEADLINE)[0].trim();
			String endTime = taskInfo.trim().split(KEYWORD_DEADLINE)[1].trim();
			handler.getTask().setDescription(description);
			handler.getTask().setEndTime(endTime);
		} else {
			handler.getTask().setDescription(taskInfo.trim());
		}
		return handler;
	}
}
