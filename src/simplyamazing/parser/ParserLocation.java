//@@author A0112659A
package simplyamazing.parser;

public class ParserLocation {
	private static final String STRING_EMPTY = "";
	private static final String INVALID_LOCATION = "Error: Location provided is invalid";

	public Handler parseLocationCmd(Handler handler, String taskInfo) throws Exception {
		
		if (taskInfo.equals(STRING_EMPTY)) {
			handler.setHasError(true);
			handler.setFeedBack(INVALID_LOCATION);
		} else {
			handler.setKeyWord(taskInfo);
		}
		return handler;
	}
}
