package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserCheckEdit {
	private static final String CHARACTER_SPACE = "\\s";
	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";
	private static String firstWord = "";
	private static String removeFirstWord = "";
	private static final String EMPTY_STRING = "";
	private static final String DESCRIPTON = "description";
	private static final String STARTTIME = "startTime";
	private static final String ENDTIME = "endTime";
	private static final String PRIORITY = "priority";
	private static final String DONE = "done";
	
	public boolean isEditingValid(String taskInfo) throws Exception {
		firstWord = taskInfo.trim().split(CHARACTER_SPACE)[0];
		removeFirstWord = taskInfo.replaceFirst(firstWord, EMPTY_STRING).trim();
		
		if(firstWord.equals( EMPTY_STRING)){
			return false;
		}else if (firstWord.equals(DONE)){
			if(removeFirstWord.equals("true")||removeFirstWord.equals("false")){
			  return true;
			}else{
			  return false;	
			}
		}else if (firstWord.equals(DESCRIPTON)){
			  return true;	
		}else if (firstWord.equals(STARTTIME)){
			if(removeFirstWord.equals("")){
				return false;
			}else{
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(false);
					Date startDate = sdf.parse(removeFirstWord);
					Date todayDate = sdf.parse(sdf.format(new Date()));
					
					if (startDate.before(todayDate)){
						return false;
					}else{
						return true;
					}
				}catch (ParseException e) {
					return false;
				}			
			}
		}else if (firstWord.equals(ENDTIME)){
			if(removeFirstWord.equals("")){
				return false;
			}else{
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(false);
					Date endDate = sdf.parse(removeFirstWord);
					Date todayDate = sdf.parse(sdf.format(new Date()));
					
					if (endDate.before(todayDate)){
						return false;
					}else{
						return true;
					}
				}catch (ParseException e) {
					return false;
				}			
			}
		}else if (firstWord.equals(PRIORITY)){
			if(removeFirstWord.equals("low") || removeFirstWord.equals("high") || removeFirstWord.equals("medium")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}
