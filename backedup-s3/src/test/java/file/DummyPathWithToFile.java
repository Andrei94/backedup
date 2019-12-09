package file;

import java.io.File;

public class DummyPathWithToFile extends DummyPath {
	private final String filepath;

	DummyPathWithToFile(String filePath) {
		this.filepath = filePath;
	}

	@Override
	public File toFile() {
		return new File(filepath);
	}
}
