import java.nio.file.Path;

class TreeViewFile {
	private String filename;
	Path path;

	TreeViewFile(String filename, Path path) {
		this.filename = filename;
		this.path = path;
	}

	@Override
	public String toString() {
		return filename;
	}
}