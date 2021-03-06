//@@author A0126289W
package simplyamazing.storage;

import java.io.File;
import java.util.ArrayList;

import simplyamazing.data.Task;

public class FileManager {
	
	private static final String DIRECTORY_CURRENT_USER = "user.home";
	private static String absolutePath = null;
	
	private static FileBuilder fileBuilder;
	private static FileVerifier fileVerifier;
	private static FileCopier fileCopier;
	private static ReadManager readManager;
	private static WriteManager writeManager;
	
	public FileManager() {
		fileBuilder = new FileBuilder();
		fileVerifier = new FileVerifier();
		fileCopier = new FileCopier();
		readManager = new ReadManager();
		writeManager = new WriteManager();
	}
	
	public void createDirectory(String directoryName) {
		fileBuilder.createDirectory(directoryName);
	}
	
	public void createNewFile(File file) throws Exception {
		if (fileVerifier.isFileExisting(file)) {
			file.delete();
		}
		fileBuilder.createNewFile(file);
	}
	
	public File createFile(String filename) {
		return fileBuilder.createFile(filename);
	}
	
	public File createTempFile(File file, String tempFilePath) throws Exception {
		File tempFile = createFile(tempFilePath);
		createNewFile(tempFile);
		createBackup(file, tempFile);
		file.delete();
		file = tempFile;
		return file;
	}
	
	public boolean isAbsolutePath(String location) {
		return fileVerifier.isAbsolutePath(location);
	}
	
	public boolean isValidDirectory(String location) {
		return fileVerifier.isFileExisting(new File(location));
	}

	public String getAbsolutePath(String location) {
		absolutePath = System.getProperty(DIRECTORY_CURRENT_USER)+ File.separator + location;
		return absolutePath;
	}
	
	public boolean isEmptyFile(File file) {
		return fileVerifier.isEmptyFile(file);
	}
	
	public boolean isFileExisting(File file) {
		return fileVerifier.isFileExisting(file);
	}
	
	public int getLineCount(File file) throws Exception {
		return readManager.read(file).size();
	}
	
	public void importListToFile(ArrayList<Task> tasks, File file) throws Exception {
		if (!fileVerifier.isEmptyFile(file)) { 
			cleanFile(file);
		}
		for (int i = 0; i < tasks.size(); i++) {
			writeToFile(file, tasks.get(i).toString());
		}	
	}
	
	public void writeToFile(File file, String text) throws Exception {
		writeManager.write(file, text);
	}
	
	public ArrayList<String> readFile(File file) throws Exception {
		return readManager.read(file);
	}
	
	public void cleanFile(File file) throws Exception {
		writeManager.writeEmptyFile(file);
	}
	
	public void createBackup(File file, File backupFile) throws Exception {
		fileCopier.copy(file, backupFile);
	}
	
	public void restoreFromBackup(File file, File backupFile) throws Exception {
		fileCopier.copy(backupFile, file);
	}
}
