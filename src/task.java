import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Comparable<Task>{

	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";

	private static final String FIELD_SEPARATOR = ",";
	
	private String description;
	private Date startTime, endTime;
	private int priority;
	private boolean done; 

	public Task(String description) {
		this.description = description;
		this.startTime = new Date(0);
		this.endTime = new Date(0);
		this.priority = 0;
		this.done = false;
	}

	public Task(String description, String endTime) throws Exception {
		this.description = description;
		this.startTime = new Date(0);
		this.endTime = convertStringToDate(endTime);
		this.priority = 0;
		this.done = false;
	}

	public Task(String description, String startTime, String endTime) throws Exception {
		this.description = description;
		this.startTime = convertStringToDate(startTime);
		this.endTime = convertStringToDate(endTime);
		this.priority = 0;
		this.done = false;
	}

	public Date convertStringToDate(String s) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
        Date date = formatter.parse(s);
        return date;
    }

    public String convertDateToString(Date date) {
        String timeString = "";
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) throws Exception {
		this.endTime = convertStringToDate(endTime);
	}

	public int getPriority() {
		return priority;
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
		return description + FIELD_SEPARATOR + startTime + FIELD_SEPARATOR + endTime + FIELD_SEPARATOR + priority + FIELD_SEPARATOR + done;
	}
}