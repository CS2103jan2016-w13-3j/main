package Parser;
import Data.Task;

public class Command {
	private Task task;
	private CommandType type;
	
	public Command(Task task, CommandType type) {
		super();
		this.task = task;
		this.type = type;
	}

	public Task getTask() {
		return task;
	}

	public CommandType getType() {
		return type;
	}
}
