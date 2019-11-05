import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFile {
	private String name;
	private String path;
	private boolean isFile;

	private LocalFile() {
	}

	public static LocalFile fromFile(File file) {
		LocalFile localFile = new LocalFile();
		localFile.name = file.getName();
		localFile.path = file.getPath();
		localFile.isFile = file.isFile();
		return localFile;
	}

	static LocalFile fromPath(Path path) {
		return LocalFile.fromFile(path.toFile());
	}

	File toFile() {
		return new File(path);
	}

	String getName() {
		return name;
	}

	boolean isFile() {
		return isFile;
	}

	public String getPath() {
		return path;
	}

	String relativize(LocalFile other) {
		return Paths.get(path)
				.toUri()
				.relativize(Paths.get(other.path).toUri())
				.getPath();
	}

	@Override
	public String toString() {
		return "path='" + path + '\'';
	}
}
