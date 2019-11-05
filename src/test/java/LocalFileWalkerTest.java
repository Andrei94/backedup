import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalFileWalkerTest {
	private LocalFileWalker localFileWalker = new LocalFileWalker();
	@Test
	void walkTreeFromRoot() {
		assertEquals(Arrays.asList("Cities.Skylines.Demo", "file1", "file2"),
				localFileWalker.walkTreeFromRoot(LocalFile.fromPath(Paths.get("D:\\Programming\\Intellij\\backedup\\src\\test\\resources\\Cities.Skylines.Demo")))
				.map(LocalFile::getName)
				.collect(Collectors.toList()));
	}
	@Test
	void walkTreeFromRootWhenRootIsAFile() {
		assertEquals(Collections.singletonList("file1"),
				localFileWalker.walkTreeFromRoot(LocalFile.fromPath(Paths.get("D:\\Programming\\Intellij\\backedup\\src\\test\\resources\\Cities.Skylines.Demo\\file1")))
						.map(LocalFile::getName)
						.collect(Collectors.toList()));
	}

	@Test
	void innexistentRoot() {
		assertEquals(0, localFileWalker.walkTreeFromRoot(LocalFile.fromPath(Paths.get("JK"))).count());
	}
}