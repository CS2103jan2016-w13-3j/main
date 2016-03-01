import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Comparable<Task>{

	public static final int DEFAULT_PRIORITY_LEVEL = 0;
	public static final Date DEFAULT_DATE_VALUE = new Date(0);

	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";
	public static final String FIELD_SEPARATOR = ",";
	
	private static final String STRING_EMPTY = "";
	
	private static final String STRING_HIGH_PRIORITY = "high";
	private static final String STRING_MEDIUM_PRIORITY = "medium";
	private static final String STRING_LOW_PRIORITY = "low";
	
	public static final int ARRAY_POSITION_FOR_DESCRIPTION = 0;
	public static final int ARRAY_POSITION_FOR_START_TIME = 1;
	public static final int ARRAY_POSITION_FOR_END_TIME = 2;
	public static final int ARRAY_POSITION_FOR_PRIORITY = 3;
	public static final int ARRAY_POSITION_FOR_STATUS = 4;
	
	private String commandType;
	private String description;
	private Date startTime, endTime;
	private int priority;
	private boolean done; 

	public Task() {
		this.commandType = STRING_EMPTY;
		this.description = STRING_EMPTY;
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = DEFAULT_DATE_VALUE;
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.done = false;
	}
	
	public Task(String description) {
		this.description = description;
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = DEFAULT_DATE_VALUE;
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.done = false;
	}

	public Task(String description, String endTime) throws Exception {
		this.description = description;
		this.startTime = DEFAULT_DATE_VALUE;
		this.endTime = convertStringToDate(endTime);
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.done = false;
	}

	public Task(String description, String startTime, String endTime) throws Exception {
		this.description = description;
		this.startTime = convertStringToDate(startTime);
		this.endTime = convertStringToDate(endTime);
		this.priority = DEFAULT_PRIORITY_LEVEL;
		this.done = false;
	}

	public Date convertStringToDate(String timeString) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
        Date date = formatter.parse(timeString);
        return date;
    }

    public String convertDateToString(Date date) {
        String timeString = STRING_EMPTY;
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
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) throws Exception {
		this.endTime = convertStringToDate(endTime);
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(String priorityLevel) throws Exception {
		switch(priorityLevel.toLowerCase()) {
			case STRING_HIGH_PRIORITY :
				this.priority = 3;
			case STRING_MEDIUM_PRIORITY :
				this.priority = 2;
			case STRING_LOW_PRIORITY :
				this.priority = 1;
			default :
				throw new Exception();
		}
	}

	public void setPriority(int priority) {
		this.priority = priority;
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
			if(task.getEndTime() == this.endTime) {
				if(task.getStartTime() == this.startTime) {
					return this.description.compareToIgnoreCase(task.getDescription());
				}
				return this.startTime.compareTo(task.getStartTime());
			} 
			return this.endTime.compareTo(task.getEndTime());
		}
		return task.getPriority() - this.priority;
	}

	@Override
	public String toString() {
		String startTimeString = STRING_EMPTY;
		String endTimeString = STRING_EMPTY;
		String priorityLevel = STRING_EMPTY;
		String status = STRING_EMPTY;
		
		if (this.startTime != DEFAULT_DATE_VALUE) {
			startTimeString = convertDateToString(this.startTime);
		}
		if (this.endTime != DEFAULT_DATE_VALUE) {
			endTimeString = convertDateToString(this.endTime);
		}
		switch(this.priority) {
			case 3 :
				priorityLevel = STRING_HIGH_PRIORITY;
			case 2 :
				priorityLevel = STRING_MEDIUM_PRIORITY;
			case 1 : 
				priorityLevel = STRING_LOW_PRIORITY;
			default :
				priorityLevel = STRING_EMPTY;
		}
		if (this.isDone()) {
			status = "done";
		}
		return description + FIELD_SEPARATOR + startTimeString + FIELD_SEPARATOR + endTimeString + FIELD_SEPARATOR + priorityLevel + FIELD_SEPARATOR + status;
	}
	
	public String toFilteredString() {
		String[] fields = this.toString().split(FIELD_SEPARATOR);
		String filteredString = "Task[";
		for (int i = 0; i < ARRAY_POSITION_FOR_STATUS; i++) {
			if (fields[ARRAY_POSITION_FOR_START_TIME] != STRING_EMPTY) {
				if (i == ARRAY_POSITION_FOR_START_TIME) {
					filteredString += " from ";
				}
				if (i == ARRAY_POSITION_FOR_END_TIME) {
					filteredString += " to ";
				}
			} else {
				if (fields[ARRAY_POSITION_FOR_END_TIME] != STRING_EMPTY) {
					if (i == ARRAY_POSITION_FOR_START_TIME && fields[i] != STRING_EMPTY) {
						filteredString += " by ";
					}
				} else {
					if (i == ARRAY_POSITION_FOR_PRIORITY && fields[i] != STRING_EMPTY) {
						filteredString += " with " + fields[i] + "priority";
					}
				}
			}
			if (i != ARRAY_POSITION_FOR_PRIORITY) {
				filteredString += fields[i];
			}
		}
		return filteredString+"]";
	}
}