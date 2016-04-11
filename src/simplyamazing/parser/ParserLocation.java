//@@author A0112659A
package simplyamazing.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserLocation {
	private static final String STRING_EMPTY = "";
	private static final String INVALID_LOCATION = "Error: Location provided is invalid";

	public Handler parseLocationCmd(Handler handler, String taskInfo, Logger logger) throws Exception {
		logger.log(Level.INFO,"start to parse the location command");
		if (taskInfo.equals(STRING_EMPTY)) {
			logger.log(Level.WARNING,"the location address is empty");
			handler.setHasError(true);
			handler.setFeedBack(INVALID_LOCATION);
		} else {
			handler.setKeyWord(taskInfo);
		}
		return handler;
	}
}
