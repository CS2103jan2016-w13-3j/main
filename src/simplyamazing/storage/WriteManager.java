package simplyamazing.storage;

import java.io.File;
import java.io.FileWriter;

public class WriteManager {
	private static final String CHARACTER_NEW_LINE = System.lineSeparator();
	
	public void writeToFile(File file, String content) throws Exception {
		FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(content);
		fileWriter.write(CHARACTER_NEW_LINE);
		fileWriter.close();	
	}
}
