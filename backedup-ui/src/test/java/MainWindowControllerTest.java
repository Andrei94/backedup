import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainWindowControllerTest {
	private MainWindowController controller;

	@Nested
	class TextTests {
		@BeforeEach
		void setUp() {
			controller = new MainWindowController(null, new SyncFolderLoader(), new FolderSaver());
		}

		@Test
		void warningTextForDeletionConfirmationPopup() {
			assertEquals("Are you sure you want to remove the folder from the sync?", controller.getWarningText());
		}

		@Test
		void warningTitleForDeletionConfirmationPopup() {
			assertEquals("Remove folder from Sync", controller.getWarningTitle());
		}

		@Test
		void loggedInUsername() {
			assertEquals("Welcome user", controller.getLoggedInText("user"));
		}

		@Test
		void loggedInUsernameNull() {
			assertEquals("", controller.getLoggedInText(null));
		}

		@Test
		void loggedInUsernameEmpty() {
			assertEquals("", controller.getLoggedInText(""));
		}
	}

	@Test
	void loadSyncFolders() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(Collections.singletonList(Folder.createFolder(""))), new FolderSaver());
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addFolderToSyncList() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(new ArrayList<>()), new FolderSaver());
		controller.addToSyncList(new File("directory"));
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addSubfolderOfFolderToSyncList() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(Collections.singletonList(Folder.createFolder("directory"))), new FolderSaver());
		controller.addToSyncList(new File("directory/subdirectory"));
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addFolderToOneFolderList() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("directory"));
		}}), new FolderSaver());
		controller.addToSyncList(new File("directory2"));
		assertEquals(2, getSizeOfFoldersToSync());
	}

	@Test
	void addWindowsFolderToLinuxFolderList() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
		controller.addToSyncList(new File("D:\\directory2"));
		assertEquals(2, getSizeOfFoldersToSync());
	}

	@Test
	void removeFolderFromSyncList() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
		controller.removeFromSyncList(new Folder(Paths.get("/home/directory")));
		assertEquals(0, getSizeOfFoldersToSync());
	}

	@Test
	void removeNullFolderFromSyncList() {
		controller = new MainWindowController(null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
		controller.removeFromSyncList(null);
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void syncFolders() {
		S3UploaderMock uploader = new S3UploaderMock();
		FolderSaver saver = new FolderSaver();
		controller = new MainWindowController(uploader, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), saver);
		controller.sync();
		assertEquals(2, saver.getFoldersSavedCount());
		assertEquals(2, uploader.getTimesUploadDirectoryCalled());
	}

	private int getSizeOfFoldersToSync() {
		return controller.getSyncList().size();
	}
}