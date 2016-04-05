//@@author A0126289W
package simplyamazing.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task implements Comparable<Task>{
	private static final int DEFAULT_PRIORITY_LEVEL = 0;
	private static final int DEFAULT_PRIORITY_LEVEL_LOW = 1;
	private static final int DEFAULT_PRIORITY_LEVEL_MEDIUM = 2;
	private static final int DEFAULT_PRIORITY_LEVEL_HIGH = 3;
	
	private static final int DEFAULT_TASK_TYPE = 1;
	private static final int DEFAULT_TASK_TYPE_FOR_EVENTS = 2;
	private static final int DEFAULT_TASK_TYPE_FOR_DEADLINES = 3;
	
	public static final Date DEFAULT_DATE_VALUE = new Date(0);

	public static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	public static final String FIELD_SEPARATOR = ",";
	
	private static final String CHARACTER_SPACE = " ";
	
	private static final String STRING_HIGH_PRIORITY = "high";
	private static final String STRING_MEDIUM_PRIORITY = "medium";
	private static final String STRING_LOW_PRIORITY = "low";
	private static final String STRING_NO_PRIORITY = "none";
	
	public static final int ARRAY_POSITION_FOR_DESCRIPTION = 0;
	public static final int ARRAY_POSITION_FOR_START_TIME = 1;
	public static final int ARRAY_POSITION_FOR_END_TIME = 2;
	public static final int ARRAY_POSITION_FOR_PRIORITY = 3;
	public static final int ARRAY_POSITION_FOR_STATUS = 4;
	
	private static final String MESSAGE_INVALID_PRIORITY_LEVEL = "Priority level can be only high, medium, low or none.";
	
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
		this.description = description.trim();
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = DEFAULT_DATE_VALUE;
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE;
		this.done = false;
	}

	public Task(String description, String endTime) throws Exception {
		this.description = description.trim();
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = convertStringToDate(endTime.trim(), TIME_FORMAT);
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE_FOR_EVENTS;
		this.done = false;
	}

	public Task(String description, String startTime, String endTime) throws Exception {
		this.description = description.trim();
		this.startTime = convertStringToDate(startTime.trim(), TIME_FORMAT);
		this.endTime = convertStringToDate(endTime.trim(), TIME_FORMAT);
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		this.done = false;
	}

	public static Date convertStringToDate(String timeString, String format) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = formatter.parse(timeString);
        return date;
    }

    public static String convertDateToString(Date date, String format) {
        String timeString = CHARACTER_SPACE;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        timeString = formatter.format(date);
        String[] fields = timeString.split(CHARACTER_SPACE);
        assert(fields.length > 0);
        String monthFirstWord = fields[2].toLowerCase().substring(0,1).toUpperCase();
        String monthFormatted = monthFirstWord + fields[2].substring(1, 3);
        timeString = fields[0] + CHARACTER_SPACE + fields[1] + CHARACTER_SPACE + monthFormatted + CHARACTER_SPACE + fields[3];
        return timeString;
    }
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) throws Exception {
		this.startTime = convertStringToDate(startTime.trim(), TIME_FORMAT);
		this.taskType = DEFAULT_TASK_TYPE_FOR_EVENTS;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		this.taskType = DEFAULT_TASK_TYPE_FOR_EVENTS;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) throws Exception {
		this.endTime = convertStringToDate(endTime.trim(), TIME_FORMAT);
		if (this.startTime.compareTo(DEFAULT_DATE_VALUE) == 0) { // For floating tasks and deadlines
			this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		}
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
		switch (priorityLevel.trim().toLowerCase()) {
			case STRING_HIGH_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL_HIGH;
				break;
			case STRING_MEDIUM_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL_MEDIUM;
				break;
			case STRING_LOW_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL_LOW;
				break;
			case STRING_NO_PRIORITY :
				this.priority = DEFAULT_PRIORITY_LEVEL;
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
		if (task.getPriority() == this.priority) { // Same priority
			if (task.getEndTime().compareTo(this.endTime) == 0) { // Same end time 
				if (task.getStartTime().compareTo(this.startTime) == 0) { // Same start time
					return this.description.compareToIgnoreCase(task.getDescription());
				} 
				return this.startTime.compareTo(task.getStartTime());	
			} else { // Different end time
				if (this.endTime.compareTo(DEFAULT_DATE_VALUE) == 0 || task.getEndTime().compareTo(DEFAULT_DATE_VALUE) == 0) { // One floating task and other event/deadline
					return task.getTaskType() - this.taskType;
				}
				return this.endTime.compareTo(task.getEndTime());		
			}
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
			startTimeString = convertDateToString(this.startTime, TIME_FORMAT);
			assert(!startTimeString.matches(CHARACTER_SPACE));
		}
		if (this.endTime != DEFAULT_DATE_VALUE) {
			endTimeString = convertDateToString(this.endTime, TIME_FORMAT);
			assert(!endTimeString.matches(CHARACTER_SPACE));
		}
		switch (this.priority) {
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
			assert(!status.matches(CHARACTER_SPACE));
		}
		return this.description.trim() + FIELD_SEPARATOR + startTimeString + FIELD_SEPARATOR + endTimeString + FIELD_SEPARATOR + priorityLevel + FIELD_SEPARATOR + status;
	}
	
	public String toFilteredString() {
		String[] fields = this.toString().split(FIELD_SEPARATOR);
		assert(fields.length == 5);
		String filteredString = "Task [";
		for (int i = 0; i < ARRAY_POSITION_FOR_STATUS; i++) {
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
			if (i != ARRAY_POSITION_FOR_PRIORITY) {
				filteredString += fields[i].trim();
			}
		}
		filteredString = filteredString+"]";
		return filteredString;
	}
}
