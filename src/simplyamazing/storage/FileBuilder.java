package simplyamazing.storage;

import java.io.File;

public class FileBuilder {
	public void createDirectory(String directoryName) {
		new File(directoryName).mkdirs();
		assert(new File(directoryName).exists());
	}
	
	public void createNewFile(File file) throws Exception {
		file.createNewFile();
		assert(file.exists());
	}
	
	public File createFile(String filename) {
		File file = new File(filename);
		return file;
	}
}
