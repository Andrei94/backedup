package dashboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DashboardControllerTest {
	private DashboardController controller;

	@Nested
	class TextTests {
		@BeforeEach
		void setUp() {
			controller = new DashboardController(null, null, new SyncFolderLoader(), new FolderSaver());
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

		@Test
		void getFolderImagePath() {
			assertTrue(controller.getFolderImagePath().startsWith("file"));
			assertTrue(controller.getFolderImagePath().endsWith("icons/dashboard/folder_80px.png"));
		}

		@Test
		void getDownloadFinishedText() {
			assertEquals("Download finished", controller.getDownloadFinishedText());
		}

		@Test
		void getInformationTitle() {
			assertEquals("Backedup", controller.getInformationTitle());
		}

		@Test
		void getUploadFinishedText() {
			assertEquals("Upload Finished", controller.getUploadFinishedText());
		}
	}

	@Test
	void loadSyncFolders() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(Collections.singletonList(Folder.createFolder(""))), new FolderSaver());
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addFolderToSyncList() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<>()), new FolderSaver());
		controller.addToSyncList(new File("directory"));
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addSubfolderOfFolderToSyncList() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(Collections.singletonList(Folder.createFolder("directory"))), new FolderSaver());
		controller.addToSyncList(new File("directory/subdirectory"));
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addFolderToOneFolderList() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("directory"));
		}}), new FolderSaver());
		controller.addToSyncList(new File("directory2"));
		assertEquals(2, getSizeOfFoldersToSync());
	}

	@Test
	void addWindowsFolderToLinuxFolderList() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
		controller.addToSyncList(new File("D:\\directory2"));
		assertEquals(2, getSizeOfFoldersToSync());
	}

	@Test
	void removeFolderFromSyncList() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
		controller.removeFromSyncList(new Folder(Paths.get("/home/directory")));
		assertEquals(0, getSizeOfFoldersToSync());
	}

	@Test
	void removeNullFolderFromSyncList() {
		controller = new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
		controller.removeFromSyncList(null);
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void uploadFolders() {
		S3UploaderMock uploader = new S3UploaderMock();
		FolderSaver saver = new FolderSaver();
		controller = new DashboardController(uploader, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), saver);
		controller.setLoggedInUsername("username");
		controller.upload(Folder.createFolder("/home/directory"));
		controller.upload(Folder.createFolder("/home/directory2"));
		Assertions.assertEquals(2, uploader.getTimesUploadDirectoryCalled());
	}

	@Test
	void skipUploadWhenUserNotLoggedIn() {
		S3UploaderMock uploader = new S3UploaderMock();
		FolderSaver saver = new FolderSaver();
		controller = new DashboardController(uploader, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), saver);
		controller.upload(Folder.createFolder("/home/directory"));
		controller.upload(Folder.createFolder("/home/directory2"));
		Assertions.assertEquals(0, uploader.getTimesUploadDirectoryCalled());
	}

	private int getSizeOfFoldersToSync() {
		return controller.getSyncList().size();
	}

	@Test
	void downloadFolder() {
		S3DownloaderMock downloader = new S3DownloaderMock();
		controller = new DashboardController(null, downloader, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), null);
		controller.setLoggedInUsername("username");
		assertTrue(controller.download(Folder.createFolder("/home/directory")));
		assertTrue(controller.download(Folder.createFolder("/home/directory2")));
	}

	@Test
	void skipDownloadIfUserNotLoggedIn() {
		S3DownloaderMock downloader = new S3DownloaderMock();
		controller = new DashboardController(null, downloader, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), null);
		assertFalse(controller.download(Folder.createFolder("/home/directory")));
		assertFalse(controller.download(Folder.createFolder("/home/directory2")));
	}

	@Test
	void cleanupCallsDownloaderShutdown() {
		S3DownloaderMock downloader = new S3DownloaderMock();
		controller = new DashboardController(null, downloader, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), null);
		controller.cleanup();
		Assertions.assertTrue(downloader.shutdownCalled);
	}
}