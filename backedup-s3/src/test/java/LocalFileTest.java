import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileTest {
	@Test
	void getPathOfLocalFile() {
		assertEquals("path\\to\\file2", LocalFile.fromFile(new File("path/to/file2")).getPath());
	}

	@Test
	void getNameOfLocalFile() {
		assertEquals("file2", LocalFile.fromFile(new File("path/to/file2")).getName());
	}

	@Test
	void isALocalFileAFile() {
		File f = new File("path/to/file2") {
			@Override
			public boolean isFile() {
				return true;
			}
		};
		assertTrue(LocalFile.fromFile(f).isFile());
	}

	@Test
	void localFileFromPath() {
		Path p = new DummyPathWithToFile("path/to/file2");
		assertNotNull(LocalFile.fromPath(p));
	}
}
