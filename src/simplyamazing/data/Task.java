package simplyamazing.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Comparable<Task>{
	public static final int DEFAULT_PRIORITY_LEVEL = 0;
	public static final int DEFAULT_PRIORITY_LEVEL_LOW = 1;
	private static final int DEFAULT_PRIORITY_LEVEL_MEDIUM = 2;
	private static final int DEFAULT_PRIORITY_LEVEL_HIGH = 3;
	
	public static final int DEFAULT_TASK_TYPE = 1;
	public static final int DEFAULT_TASK_TYPE_FOR_EVENTS = 2;
	public static final int DEFAULT_TASK_TYPE_FOR_DEADLINES = 3;
	
	public static final Date DEFAULT_DATE_VALUE = new Date(0);

	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";
	public static final String FIELD_SEPARATOR = ",";
	
	private static final String CHARACTER_SPACE = " ";
	
	private static final String STRING_HIGH_PRIORITY = "high";
	private static final String STRING_MEDIUM_PRIORITY = "medium";
	private static final String STRING_LOW_PRIORITY = "low";
	
	public static final int ARRAY_POSITION_FOR_DESCRIPTION = 0;
	public static final int ARRAY_POSITION_FOR_START_TIME = 1;
	public static final int ARRAY_POSITION_FOR_END_TIME = 2;
	public static final int ARRAY_POSITION_FOR_PRIORITY = 3;
	public static final int ARRAY_POSITION_FOR_STATUS = 4;
	
	private static final String MESSAGE_INVALID_PRIORITY_LEVEL = "Priority level can be only high, medium or low.";
	
	private String description;
	private Date startTime, endTime;
	private int priority, taskType;
	private boolean done; 

	public Task() {
		this.description = CHARACTER_SPACE;
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = DEFAULT_DATE_VALUE;
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE;
		this.done = false;
	}
	
	public Task(String description) {
		this.description = description;
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = DEFAULT_DATE_VALUE;
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE;
		this.done = false;
	}

	public Task(String description, String endTime) throws Exception {
		this.description = description;
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = convertStringToDate(endTime);
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE_FOR_EVENTS;
		this.done = false;
	}

	public Task(String description, String startTime, String endTime) throws Exception {
		this.description = description;
		this.startTime = convertStringToDate(startTime);
		this.endTime = convertStringToDate(endTime);
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		this.done = false;
	}

	public static Date convertStringToDate(String timeString) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
        Date date = formatter.parse(timeString);
        return date;
    }

    public static String convertDateToString(Date date) {
        String timeString = CHARACTER_SPACE;
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
        timeString = formatter.format(date);
        return timeString;
    }
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) throws Exception {
		this.startTime = convertStringToDate(startTime);
		this.taskType = DEFAULT_TASK_TYPE_FOR_EVENTS;
		/*if (this.priority < DEFAULT_PRIORITY_LEVEL_EVENT) {
			this.priority++;
		}*/
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		this.taskType = DEFAULT_TASK_TYPE_FOR_EVENTS;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) throws Exception {
		this.endTime = convertStringToDate(endTime);
		if (this.startTime.compareTo(DEFAULT_DATE_VALUE) == 0) { // For floating tasks and deadlines
			this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		}
		/*if (this.taskType < DEFAULT_TASK_TYPE_FOR_EVENTS) {
			this.priority++;
		}*/
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		if (this.startTime.compareTo(DEFAULT_DATE_VALUE) == 0) { // For floating tasks and deadlines
			this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		}
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(String priorityLevel) throws Exception {
		switch(priorityLevel.toLowerCase()) {
			case STRING_HIGH_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL_HIGH;
				break;
			case STRING_MEDIUM_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL_MEDIUM;
				break;
			case STRING_LOW_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL_LOW;
				break;
			default :
				throw new Exception(MESSAGE_INVALID_PRIORITY_LEVEL);
		}
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getTaskType() {
		return taskType;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	@Override
	public int compareTo(Task task) {
		if(task.getPriority() == this.priority) {
			if(task.getTaskType() == this.taskType) {
				if(task.getEndTime().compareTo(this.endTime) == 0) {
					if(task.getStartTime().compareTo(this.startTime) == 0) {
						return this.description.compareToIgnoreCase(task.getDescription());
					}
					return this.startTime.compareTo(task.getStartTime());
				} 
				return this.endTime.compareTo(task.getEndTime());
			}
			return task.getTaskType() - this.taskType;
		}
		return task.getPriority() - this.priority;
	}

	@Override
	public String toString() {
		String startTimeString = CHARACTER_SPACE;
		String endTimeString = CHARACTER_SPACE;
		String priorityLevel = CHARACTER_SPACE;
		String status = CHARACTER_SPACE;
		
		if (this.startTime != DEFAULT_DATE_VALUE) {
			startTimeString = convertDateToString(this.startTime);
		}
		if (this.endTime != DEFAULT_DATE_VALUE) {
			endTimeString = convertDateToString(this.endTime);
		}
		switch(this.priority) {
			case DEFAULT_PRIORITY_LEVEL_HIGH :
				priorityLevel = STRING_HIGH_PRIORITY;
				break;
			case DEFAULT_PRIORITY_LEVEL_MEDIUM :
				priorityLevel = STRING_MEDIUM_PRIORITY;
				break;
			case DEFAULT_PRIORITY_LEVEL_LOW : 
				priorityLevel = STRING_LOW_PRIORITY;
				break;
			default :
				priorityLevel = CHARACTER_SPACE;
				break;
		}
		if (this.isDone()) {
			status = "done";
		}
		return this.description + FIELD_SEPARATOR + startTimeString + FIELD_SEPARATOR + endTimeString + FIELD_SEPARATOR + priorityLevel + FIELD_SEPARATOR + status;
	}
	
	public String toFilteredString() {
		boolean hasStatusChecked = false;
		String[] fields = this.toString().split(FIELD_SEPARATOR);
		String filteredString = "Task[";
		for (int i = 0; i <= ARRAY_POSITION_FOR_STATUS; i++) {
			if(!hasStatusChecked && !fields[ARRAY_POSITION_FOR_STATUS].matches(CHARACTER_SPACE)) {
				filteredString += fields[ARRAY_POSITION_FOR_STATUS].toUpperCase() + ": ";
				hasStatusChecked = true;
			}
			if (!fields[ARRAY_POSITION_FOR_START_TIME].matches(CHARACTER_SPACE)) {
				if (i == ARRAY_POSITION_FOR_START_TIME) {
					filteredString += " from ";
				}
				if (i == ARRAY_POSITION_FOR_END_TIME) {
					filteredString += " to ";
				}
			} else {
				if (!fields[ARRAY_POSITION_FOR_END_TIME].matches(CHARACTER_SPACE)) {
					if (i == ARRAY_POSITION_FOR_END_TIME) {
						filteredString += " by ";
					}
				} 
			}
			if (i == ARRAY_POSITION_FOR_PRIORITY && !fields[i].matches(CHARACTER_SPACE)) {
				filteredString += " with " + fields[i] + " priority";
			}
			if (i != ARRAY_POSITION_FOR_PRIORITY && i != ARRAY_POSITION_FOR_STATUS) {
				filteredString += fields[i].trim();
			}
		}
		return filteredString+"]";
	}
}
