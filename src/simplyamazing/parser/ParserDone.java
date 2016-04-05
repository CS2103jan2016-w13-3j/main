//@@author A0112659A
package simplyamazing.parser;

public class ParserDone {
	private final String COMMAND_INVALID = "Error: Index provided is not an Integer.";
	private final String SPACE = " ";
	private int size;

	public Handler parserDoneCommand(Handler handler, String taskInfo) throws Exception {

		String[] indexes = taskInfo.split(SPACE);
		size = indexes.length;
		for (int i = 0; i < size;i++){
			if (isInteger(indexes[i])){
				handler.setIndex(indexes[i]);
			}else{
				handler.setHasError(true);
				handler.setFeedBack(COMMAND_INVALID);
				return handler;
			}
		}
		return handler;
	}

	private boolean isInteger(String taskInfo) {
		try {
			Integer.parseInt(taskInfo);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
