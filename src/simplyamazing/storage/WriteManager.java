//@@author A0126289W
package simplyamazing.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class WriteManager {
	
	private static final String CHARACTER_NEW_LINE = System.lineSeparator();
	
	public void write(File file, String content) throws Exception {
		FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(content);
		fileWriter.write(CHARACTER_NEW_LINE);
		fileWriter.close();	
	}
	
	public void writeEmptyFile(File file) throws Exception {
		FileOutputStream writer = new FileOutputStream(file);
		writer.close();
	}
}
