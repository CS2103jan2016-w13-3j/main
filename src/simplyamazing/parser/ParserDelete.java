package simplyamazing.parser;

public class ParserDelete {
	private final String COMMAND_INVALID = "Error: Index provided is not an Integer.";
	
	public Handler parserDeleteCommand(Handler handler, String taskInfo) throws Exception{
		if (isInteger(taskInfo)){
			handler.setIndex(taskInfo);
		}else{
			handler.setHasError(true);
			handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}
	public static boolean isInteger(String taskInfo) {
	    try { 
	        Integer.parseInt(taskInfo); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
