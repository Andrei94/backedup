import java.nio.file.Path;
import java.util.Objects;

class Folder {
	Path path;

	Folder(Path path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return path.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Folder folder = (Folder) o;
		return path.equals(folder.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}
}
