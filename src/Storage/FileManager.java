package Storage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import Data.Task;

public class FileManager {
	
	private static final int SIZE_EMPTY = 0;
	
	public void createDirectory(String directoryName) {
		new File(directoryName).mkdirs();
	}
	
	public File setupFile(String filename) {
		File file = new File(filename);
		return file;
	}

	public void createFileIfNotExist(File file) throws Exception {
		if(!file.exists()) {
			file.createNewFile();
		}
	}
	
	public boolean isEmptyFile(File file) {
		return file.length() == SIZE_EMPTY;
	}
	
	public boolean isDirectory(String location) {
		return new File(location).isDirectory();
	}
	
	public void createBackup(File file, File backupFile) throws Exception {
		if(isEmptyFile(file)) {
			Files.copy(file.toPath(), backupFile.toPath(), REPLACE_EXISTING);
		}
	}
	
	public void writeToFile(ArrayList<Task> tasks, File file) throws Exception {
		if (isEmptyFile(file)) { 
			cleanFile(file);
		}
		for (int i = 0; i < tasks.size(); i++) {
			WriteManager writeManager = new WriteManager();
			writeManager.writeToFile(file, tasks.get(i).toString());
		}	
	}
	
	public void cleanFile(File file) throws Exception {
		FileOutputStream writer = new FileOutputStream(file);
		writer.close();
	}
}