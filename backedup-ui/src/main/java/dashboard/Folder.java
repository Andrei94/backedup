package dashboard;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

class Folder {
	Path path;

	Folder(Path path) {
		this.path = path;
	}

	Optional<Path> relativize(File other) {
		try {
			return Optional.of(path.relativize(other.toPath()));
		} catch(IllegalArgumentException ex) {
			return Optional.empty();
		}
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

	static Folder createFolder(String path) {
		return new Folder(Paths.get(path));
	}
}
