//@@author A0126289W
package simplyamazing.storage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileVerifier {
	
	private static final int SIZE_EMPTY = 0;
	
	public boolean isAbsolutePath(String location) {
		return new File(location).isDirectory();
	}
	
	public boolean isEmptyFile(File file) {
		return file.length() == SIZE_EMPTY;
	}
	
	public boolean isFileExisting(File file) {
		return file.exists();
	}
}
