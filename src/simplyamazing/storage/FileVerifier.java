package simplyamazing.storage;

import java.io.File;

public class FileVerifier {
	
	private static final int SIZE_EMPTY = 0;
	
	public boolean isDirectory(String location) {
		return new File(location).isDirectory();
	}
	
	public boolean isEmptyFile(File file) {
		return file.length() == SIZE_EMPTY;
	}
}
