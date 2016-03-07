package Parser;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Data.Task;

public class ParserCheckAdd {
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String KEYWORD_PRIORITY = "priority";
	private static final String EMPTY_STRING = "";
	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";
	private static String description = "";
	private static String startTime = "";
	private static String endTime = "";
	
	
	
	private static final String[] KEYWORDS = {KEYWORD_PRIORITY,KEYWORD_DEADLINE, KEYWORD_SCHEDULE_FROM, KEYWORD_SCHEDULE_TO};
	public boolean isAddingValid(String taskInfo) throws Exception {
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {
			 description  = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[0].trim();
			 startTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[0].trim();
			 endTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[1].trim();
			 
			 if(description.equals(EMPTY_STRING) || startTime.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)){
				 return false;
			 }
			 else if(!startTime.equals(EMPTY_STRING) &&!endTime.equals(EMPTY_STRING)){
				 try {
			            DateFormat df = new SimpleDateFormat(TIME_FORMAT);
			            DateFormat tf = new SimpleDateFormat(TIME_FORMAT);
			            df.setLenient(false);
			            df.parse(startTime);
			            tf.setLenient(false);
			            tf.parse(endTime);
			            
			            
			            return true;
			        } catch (ParseException e) {
			            return false;
			        }
			 }
			 
		}
		return true;
	}
}
