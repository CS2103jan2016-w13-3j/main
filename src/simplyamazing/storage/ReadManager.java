package simplyamazing.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadManager {
	
	public ArrayList<String> read(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		ArrayList<String> lines = new ArrayList<String>();
		
		while ((line = bufferedReader.readLine()) != null) {	
			lines.add(line);
		}
		bufferedReader.close();
		return lines;	 
	}
}
