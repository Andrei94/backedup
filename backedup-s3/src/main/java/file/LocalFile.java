package file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFile {
	private String name;
	private String path;
	private boolean isFile;
	private long size;

	private LocalFile() {
	}

	public static LocalFile fromFile(File file) {
		LocalFile localFile = new LocalFile();
		localFile.name = file.getName();
		localFile.path = file.getPath();
		localFile.isFile = file.isFile();
		localFile.size = file.length();
		return localFile;
	}

	public static LocalFile fromPath(Path path) {
		return LocalFile.fromFile(path.toFile());
	}

	public File toFile() {
		return new File(path);
	}

	public String getName() {
		return name;
	}

	public boolean isFile() {
		return isFile;
	}

	public String getPath() {
		return path;
	}

	public String relativize(LocalFile other) {
		return Paths.get(path)
				.toUri()
				.relativize(Paths.get(other.path).toUri())
				.getPath();
	}

	public long getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "path='" + path + '\'';
	}
}
