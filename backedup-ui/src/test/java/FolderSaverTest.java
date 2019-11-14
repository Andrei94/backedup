import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FolderSaverTest {
	@Nested
	class SaveFoldersPathStructure {
		private StringWriter acc;
		private SyncFolderSaver saver;

		@BeforeEach
		void setUp() {
			acc = new StringWriter();
			saver = new SyncFolderSaverWithAccumulator(acc);
		}

		@Test
		void saveEmptyList() {
			saver.save(Collections.emptyList());
			assertEquals("", acc.toString());
		}

		@Test
		void saveOneElementList() {
			saver.save(Collections.singletonList("directory"));
			assertEquals("directory" + System.lineSeparator(), acc.toString());
		}

		@Test
		void saveWindowsFolder() {
			saver.save(Collections.singletonList("D:\\directory"));
			assertEquals("D:/directory" + System.lineSeparator(), acc.toString());
		}

		@Test
		void saveUnixFolder() {
			saver.save(Collections.singletonList("/home/directory"));
			assertEquals("/home/directory" + System.lineSeparator(), acc.toString());
		}
	}

	@Test
	void throwIOExceptionWhenCreatingABufferedWriter() {
		SyncFolderSaver saver = new SyncFolderSaverBufferThrowingIOException();
		assertFalse(saver.save(Collections.emptyList()));
	}

	@Test
	void throwIOExceptionWhenWritingToFile() {
		SyncFolderSaver saver = new SyncFolderSaverWritingThrowsIOException();
		assertFalse(saver.save(Collections.singletonList("directory")));
	}
}
