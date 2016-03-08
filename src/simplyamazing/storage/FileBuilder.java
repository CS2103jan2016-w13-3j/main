package simplyamazing.storage;

import java.io.File;

public class FileBuilder {
	public void createDirectory(String directoryName) {
		new File(directoryName).mkdirs();
	}
	
	public void createFileIfNotExist(File file) throws Exception {
		if(!file.exists()) {
			file.createNewFile();
		}
	}
	
	public File createFile(String filename) {
		File file = new File(filename);
		return file;
	}
}
