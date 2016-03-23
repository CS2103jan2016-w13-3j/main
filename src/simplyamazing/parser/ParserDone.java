package simplyamazing.parser;

public class ParserDone {
	private final String COMMAND_INVALID = "the index of done function is invalid.";

	public Handler parserDoneCommand(Handler handler, String taskInfo) throws Exception {
		if (isInteger(taskInfo) && Integer.parseInt(taskInfo) >= 0) {
			handler.setIndex(taskInfo);
		} else {
			handler.setHasError(true);
			handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}

	private boolean isInteger(String taskInfo) {
		try {
			Integer.parseInt(taskInfo);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
