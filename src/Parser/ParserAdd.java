package Parser;

import Data.Task;

public class ParserAdd {
	public Task parseAddCommand(String taskInfo) throws Exception {
		Task task = new Task();
		
		if (taskInfo.contains("from") && taskInfo.contains("to")) {	// For events
			String description  = taskInfo.trim().split("from")[0].trim();
			String startTime = taskInfo.trim().split("from")[1].trim().split("to")[0].trim();
			String endTime = taskInfo.trim().split("from")[1].trim().split("to")[1].trim();
			task.setDescription(description);
			task.setStartTime(startTime);
			task.setEndTime(endTime);
		} else if (taskInfo.contains("by")) { // For deadlines
			String description  = taskInfo.trim().split("by")[0].trim();
			String endTime = taskInfo.trim().split("by")[1].trim();
			task.setDescription(description);
			task.setEndTime(endTime);
		} else {
			task.setDescription(taskInfo.trim());
		}
		return task;
	}
}
