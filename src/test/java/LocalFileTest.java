import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Nested
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
		File f = mock(File.class);
		when(f.getPath()).thenReturn("path/to/file2");
		when(f.getName()).thenReturn("file2");
		when(f.isFile()).thenReturn(true);
		assertTrue(LocalFile.fromFile(f).isFile());
	}

	@Test
	void localFileFromPath() {
		Path p = mock(Path.class);
		when(p.toFile()).thenReturn(new File("path/to/file2"));
		assertNotNull(LocalFile.fromPath(p));
	}
}
