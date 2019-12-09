package dashboard;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SyncFoldersLoaderTest {
	@Test
	void loadEmptyFile() {
		SyncFolderLoader loader = new SyncFolderLoaderStub(Collections.emptyList());
		assertTrue(loader.load().isEmpty());
	}

	@Test
	void loadFileContents() {
		SyncFolderLoader loader = new SyncFolderLoaderStub(Arrays.asList(Folder.createFolder("directory1"), Folder.createFolder("directory2")));
		assertEquals(2, loader.load().size());
	}

	@Test
	void loadWindowsAndUnixFolders() {
		SyncFolderLoader loader = new SyncFolderLoaderStub(Arrays.asList(Folder.createFolder("/home/directory1"), Folder.createFolder("D:\\directory2")));
		assertEquals(2, loader.load().size());
	}

	@Test
	void loadFromInnexistentFile() {
		SyncFolderLoader loader = new SyncFolderLoaderThrowingIOException();
		assertTrue(loader.load().isEmpty());
	}
}
