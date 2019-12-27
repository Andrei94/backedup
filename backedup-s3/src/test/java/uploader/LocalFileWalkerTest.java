package uploader;

import file.LocalFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalFileWalkerTest {
	private LocalFileWalker localFileWalker = new LocalFileWalker();
	@Test
	void walkTreeFromRoot() throws IOException {
		assertEquals(Arrays.asList("Cities.Skylines.Demo", "file1", "file2"),
				localFileWalker.walkTreeFromRoot(LocalFile.fromPath(Paths.get("D:\\Programming\\Intellij\\backedup\\backedup-s3\\src\\test\\resources\\Cities.Skylines.Demo")))
				.map(LocalFile::getName)
				.collect(Collectors.toList()));
	}
	@Test
	void walkTreeFromRootWhenRootIsAFile() throws IOException {
		assertEquals(Collections.singletonList("file1"),
				localFileWalker.walkTreeFromRoot(LocalFile.fromPath(Paths.get("D:\\Programming\\Intellij\\backedup\\backedup-s3\\src\\test\\resources\\Cities.Skylines.Demo\\file1")))
						.map(LocalFile::getName)
						.collect(Collectors.toList()));
	}

	@Test
	void innexistentRoot() {
		assertThrows(IOException.class, () -> localFileWalker.walkTreeFromRoot(LocalFile.fromPath(Paths.get("JK"))));
	}
}