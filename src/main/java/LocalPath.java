import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalPath {
	private LocalFile file;

	private LocalPath() {
	}

	static LocalPath fromPath(Path path) {
		LocalPath localPath = new LocalPath();
		localPath.file = LocalFile.fromFile(path.toFile());
		return localPath;
	}

	Path toPath() {
		return Paths.get(file.getPath());
	}

	public boolean isFile() {
		return file.isFile();
	}

	public String relativize(LocalPath other) {
		return this.toPath()
				.toUri()
				.relativize(other.toPath().toUri())
				.getPath();
	}

	public String getFilename() {
		return file.getName();
	}

	public LocalFile getLocalFile() {
		return file;
	}
}
