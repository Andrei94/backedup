import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LocalFileWalker {
	protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) {
		try {
			return Files.walk(Paths.get(root.getPath())).map(path -> LocalFile.fromFile(path.toFile()));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}
}
