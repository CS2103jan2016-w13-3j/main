//@@author A0126289W
package simplyamazing.storage;

import java.io.File;

public class FileBuilder {
	
	public void createDirectory(String directoryName) {
		new File(directoryName).mkdirs();
	}
	
	public void createNewFile(File file) throws Exception {
		file.createNewFile();
	}
	
	public File createFile(String filename) {
		File file = new File(filename);
		return file;
	}
}
