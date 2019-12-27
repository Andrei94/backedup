package uploader;

import file.LocalFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class LocalFileWalker {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) throws IOException {
		return Files.walk(Paths.get(root.getPath())).map(path -> {
			logger.info("Discovered " + path.toAbsolutePath());
			return LocalFile.fromFile(path.toFile());
		});
	}
}
