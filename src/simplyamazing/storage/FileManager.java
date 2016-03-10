package simplyamazing.storage;

import java.io.File;
import java.util.ArrayList;

import simplyamazing.data.Task;

public class FileManager {
	
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
		WriteManager writeManager = new WriteManager();
		writeManager.writeEmptyFile(file);
	}
	
	public void createBackup(File file, File backupFile) throws Exception {
		if(!isEmptyFile(file)) {
			FileCopier fileCopier = new FileCopier();
			fileCopier.copy(file, backupFile);
		}
	}
	
	public void restoreFromBackup(File file, File backupFile) throws Exception {
		FileCopier fileCopier = new FileCopier();
		fileCopier.copy(backupFile, file);
	}
}
