//@@author A0126289W
package simplyamazing.storage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.nio.file.Files;

public class FileCopier {
	public void copy(File source, File destination) throws Exception {
		Files.copy(source.toPath(), destination.toPath(), REPLACE_EXISTING);
	}
}
