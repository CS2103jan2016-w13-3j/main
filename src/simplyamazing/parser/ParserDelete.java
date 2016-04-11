//@@author A0112659A
package simplyamazing.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserDelete {
	private final String COMMAND_INVALID = "Error: Index provided is not an Integer.";
	private final String SPACE = " ";
	private int size;
	public Handler parserDeleteCommand(Handler handler, String taskInfo, Logger logger) throws Exception{

		String[] indexes = taskInfo.split(SPACE);
		size = indexes.length;
		for (int i = 0; i < size;i++){
			if (isInteger(indexes[i],logger)){
				handler.setIndex(indexes[i]);
			}else{
				handler.setHasError(true);
				handler.setFeedBack(COMMAND_INVALID);
				return handler;
			}
		}
		return handler;
	}
	public static boolean isInteger(String taskInfo, Logger logger) {
		logger.log(Level.INFO,"start to analyze the index");
		try { 
			Integer.parseInt(taskInfo); 
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "the index is not an Integer");
			return false; 
		}
		// only got here if we didn't return false
		return true;
	}
}
