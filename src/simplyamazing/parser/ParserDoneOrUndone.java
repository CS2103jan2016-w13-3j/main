//@@author A0112659A
package simplyamazing.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserDoneOrUndone {
	private final String COMMAND_INVALID = "Error: Index provided is not an Integer.";
	private final String SPACE = " ";
	private int size;

	public Handler parserDoneOrUndoneCommand(Handler handler, String taskInfo, Logger logger) throws Exception {

		String[] indexes = taskInfo.split(SPACE);
		size = indexes.length;
		logger.log(Level.INFO,"start to analyze the command");
		for (int i = 0; i < size;i++){
			if (isInteger(indexes[i], logger)){
				handler.setIndex(indexes[i]);
			}else{
				logger.log(Level.WARNING, "the index is invalid");
				handler.setHasError(true);
				handler.setFeedBack(COMMAND_INVALID);
				return handler;
			}
		}
		return handler;
	}

	private boolean isInteger(String taskInfo, Logger logger) {
		logger.log(Level.INFO,"start to analyze the index");
		try {
			Integer.parseInt(taskInfo);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "the index is not an Integer");
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
