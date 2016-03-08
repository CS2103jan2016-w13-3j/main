package simplyamazing.storage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import simplyamazing.data.Task;

public class FileManager {
	
	private static final int SIZE_EMPTY = 0;
	
	public void createDirectory(String directoryName) {
		FileBuilder fileBuilder = new FileBuilder();
		fileBuilder.createDirectory(directoryName);
	}
	
	public void createFileIfNotExist(File file) throws Exception {
		FileBuilder fileBuilder = new FileBuilder();
		fileBuilder.createFileIfNotExist(file);
	}
	
	public File createFile(String filename) {
		FileBuilder fileBuilder = new FileBuilder();
		return fileBuilder.createFile(filename);
	}
	
	public boolean isDirectory(String location) {
		FileVerifier fileVerifier = new FileVerifier();
		return fileVerifier.isDirectory(location);
	}
	
	public boolean isEmptyFile(File file) {
		FileVerifier fileVerifier = new FileVerifier();
		return fileVerifier.isEmptyFile(file);
	}
	
	public void importListToFile(ArrayList<Task> tasks, File file) throws Exception {
		FileVerifier fileVerifier = new FileVerifier();
		if (!fileVerifier.isEmptyFile(file)) { 
			cleanFile(file);
		}
		for (int i = 0; i < tasks.size(); i++) {
			writeToFile(file, tasks.get(i).toString());
		}	
	}
	
	public void writeToFile(File file, String text) throws Exception {
		WriteManager writeManager = new WriteManager();
		writeManager.write(file, text);
	}
	
	public ArrayList<String> readFile(File file) throws Exception {
		ReadManager readManager = new ReadManager();
		return readManager.read(file);
	}
	
	public void cleanFile(File file) throws Exception {
		FileOutputStream writer = new FileOutputStream(file);
		writer.close();
	}
	
	public void createBackup(File file, File backupFile) throws Exception {
		if(!isEmptyFile(file)) {
			Files.copy(file.toPath(), backupFile.toPath(), REPLACE_EXISTING);
		}
	}
}