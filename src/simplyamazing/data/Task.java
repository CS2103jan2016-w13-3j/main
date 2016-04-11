//@@author A0126289W
package simplyamazing.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task implements Comparable<Task> {
	
	private static final String KEYWORD_PRIORITY = " priority";
	private static final String KEYWORD_DEADLINE = " by ";
	private static final String KEYWORD_END_TIME = " to ";
	private static final String KEYWORD_START_TIME = " from ";
	
	private static final int DEFAULT_PRIORITY_LEVEL = 0;
	private static final int DEFAULT_PRIORITY_LEVEL_LOW = 1;
	private static final int DEFAULT_PRIORITY_LEVEL_MEDIUM = 2;
	private static final int DEFAULT_PRIORITY_LEVEL_HIGH = 3;
	
	private static final int DEFAULT_TASK_TYPE = 1;
	private static final int DEFAULT_TASK_TYPE_FOR_EVENTS = 2;
	private static final int DEFAULT_TASK_TYPE_FOR_DEADLINES = 3;
	
	public static final Date DEFAULT_DATE_VALUE = new Date(0);
	public static final Date DEFAULT_DATE_VALUE_FOR_NULL = new Date(1);

	public static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	
	public static final String FIELD_SEPARATOR = ",";
	
	private static final String CHARACTER_SPACE = " ";
	
	private static final String STRING_CONNECTOR_FOR_PRIORITY = " with ";
	private static final String STRING_OBJECT_NAME = "Task [";
	private static final String STRING_END = "]";
	
	private static final String STRING_HIGH_PRIORITY = "high";
	private static final String STRING_MEDIUM_PRIORITY = "medium";
	private static final String STRING_LOW_PRIORITY = "low";
	private static final String STRING_NO_PRIORITY = "none";
	private static final String STRING_STATUS_INCOMPLETE = "incomplete";
	private static final String STRING_STATUS_OVERDUE = "overdue";
	public static final String STRING_STATUS_DONE = "done";
	
	public static final int NUM_FIELDS_STORED = 5;
	
	public static final int ARRAY_POSITION_FOR_DESCRIPTION = 0;
	public static final int ARRAY_POSITION_FOR_START_TIME = 1;
	public static final int ARRAY_POSITION_FOR_END_TIME = 2;
	public static final int ARRAY_POSITION_FOR_PRIORITY = 3;
	public static final int ARRAY_POSITION_FOR_STATUS = 4;
	
	private static final int INDEX_FIRST = 0;
	private static final int INDEX_SECOND = 1;
	private static final int INDEX_FOURTH = 3;
	
	public static final int INDEX_TIME = 0;
	public static final int INDEX_DAY = 1;
	public static final int INDEX_MONTH = 2;
	public static final int INDEX_YEAR = 3;
	
	private static final String MESSAGE_INVALID_PRIORITY_LEVEL = "Priority level can be only high, medium, low "
			+ "or none.";
	
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

	/*
	 * This method converts the given date as a date-time string.
	 * The standard format is "HH:mm dd MMM YYYY" where MMM is a string of length 3, describing the month, 
	 * with first word capitalized.
	 */
    public static String convertDateToString(Date date) {
        String timeString = CHARACTER_SPACE;
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        timeString = formatter.format(date);
        String[] fields = timeString.split(CHARACTER_SPACE);
        assert(fields.length > 0);
        String monthFirstWord = fields[INDEX_MONTH].toLowerCase().substring(INDEX_FIRST, INDEX_SECOND)
        		.toUpperCase();
        String monthFormatted = monthFirstWord + fields[INDEX_MONTH].substring(INDEX_SECOND,INDEX_FOURTH);
        timeString = fields[INDEX_TIME] + CHARACTER_SPACE + fields[INDEX_DAY] + CHARACTER_SPACE 
        		+ monthFormatted + CHARACTER_SPACE + fields[INDEX_YEAR];
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
		if (this.startTime == DEFAULT_DATE_VALUE) { // For floating tasks and deadlines
			this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		}
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		if (this.startTime == DEFAULT_DATE_VALUE) { // For floating tasks and deadlines
			this.taskType = DEFAULT_TASK_TYPE_FOR_DEADLINES;
		}
	}

	public int getPriority() {
		return priority;
	}

	/*
	 * This method sets the priority level of a task.
	 * Valid priority levels are high, medium, low and none(default).
	 */
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
	
	/*
	 * This method is to be used by Collections.sort(List<T>).
	 * Tasks are sorted by priority, end time, start time and lexicographical order of their descriptions.
	 * Because default date-time value is 00:00 1 Jan 1970, 
	 * floating tasks will be on top since their start and end times are set to default values
	 * To fix that, this method checks if the end times or start times of the tasks are set as default value first 
	 * when they are of different values.
	 * If one of them is with default date-time value, sort them by task type, 
	 * deadline first, followed by event and floating task.
	 */
	@Override
	public int compareTo(Task task) {
		if (task.getPriority() == this.priority) { // Same priority
			if (task.getEndTime().compareTo(this.endTime) == 0) { // Same end time 
				if (task.getStartTime().compareTo(this.startTime) == 0) { // Same start time
					return this.description.compareToIgnoreCase(task.getDescription());
				} 
				return this.startTime.compareTo(task.getStartTime());	
			} else { // Different end time
				if (this.endTime.compareTo(DEFAULT_DATE_VALUE) == 0 
						|| task.getEndTime().compareTo(DEFAULT_DATE_VALUE) == 0) { 
					// One floating task and other event/deadline
					return task.getTaskType() - this.taskType;
				}
				return this.endTime.compareTo(task.getEndTime());		
			} 
		}
		return task.getPriority() - this.priority;
	}
	
	/*
	 * This method is to be used by Storage component to write to the file.
	 * The format is description,startTime,endTime,priority,status.
	 * If any of the fields is default value, it will be indicated by a space.
	 * Status is "done" for tasks marked as completed, "overdue" when its end time was already passed, 
	 * and the rest regarded as "incomplete".
	 */
	@Override
	public String toString() {
		String startTimeString = CHARACTER_SPACE;
		String endTimeString = CHARACTER_SPACE;
		String priorityLevel = CHARACTER_SPACE;
		String status = STRING_STATUS_INCOMPLETE;
		
		if (this.startTime.compareTo(DEFAULT_DATE_VALUE) != 0) {
			startTimeString = convertDateToString(this.startTime);
			assert(!startTimeString.matches(CHARACTER_SPACE));
		}
		
		if (this.endTime.compareTo(DEFAULT_DATE_VALUE) != 0) {
			endTimeString = convertDateToString(this.endTime);
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
		
		Date now = new Date();
		Date endTime = this.getEndTime();
		if (endTime.before(now) && endTime != Task.DEFAULT_DATE_VALUE) {
			status = STRING_STATUS_OVERDUE;
			assert (!status.matches(STRING_STATUS_INCOMPLETE));
		}
		if (this.isDone()) {
			status = STRING_STATUS_DONE;
			assert (!status.matches(STRING_STATUS_INCOMPLETE));
		}
		
		return this.description.trim() + FIELD_SEPARATOR + startTimeString + FIELD_SEPARATOR 
				+ endTimeString + FIELD_SEPARATOR + priorityLevel + FIELD_SEPARATOR + status;
	}
	
	/*
	 * This method converts the description of the task into more user readable format.
	 * Events are shown by "from" and "to" keywords.
	 * Deadlines are shown by "by" keyword.
	 * If there is any priority set, it will be shown by keyword "with" followed by the priority level.
	 */
	public String toFilteredString() {
		String[] fields = this.toString().split(FIELD_SEPARATOR);
		assert(fields.length == NUM_FIELDS_STORED);
		String filteredString = STRING_OBJECT_NAME;
		for (int i = 0; i < ARRAY_POSITION_FOR_STATUS; i++) {
			if (!fields[ARRAY_POSITION_FOR_START_TIME].matches(CHARACTER_SPACE)) {
				if (i == ARRAY_POSITION_FOR_START_TIME) {
					filteredString += KEYWORD_START_TIME;
				}
				if (i == ARRAY_POSITION_FOR_END_TIME) {
					filteredString += KEYWORD_END_TIME;
				}
			} else {
				if (!fields[ARRAY_POSITION_FOR_END_TIME].matches(CHARACTER_SPACE) 
						&& i == ARRAY_POSITION_FOR_END_TIME) {
					filteredString += KEYWORD_DEADLINE;
				} 
			}
			if (i == ARRAY_POSITION_FOR_PRIORITY && !fields[i].matches(CHARACTER_SPACE)) {
				filteredString += STRING_CONNECTOR_FOR_PRIORITY + fields[i] + KEYWORD_PRIORITY;
			}
			if (i != ARRAY_POSITION_FOR_PRIORITY) {
				filteredString += fields[i].trim();
			}
		}
		filteredString += STRING_END;
		return filteredString;
	}
}
