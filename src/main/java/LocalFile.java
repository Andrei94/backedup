import java.io.File;

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

	public File toFile() {
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
}
