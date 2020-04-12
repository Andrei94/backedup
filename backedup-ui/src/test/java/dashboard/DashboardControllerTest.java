package dashboard;

import authentication.AuthenticatedUser;
import authentication.User;
import authentication.UserCredentials;
import drive.DriveGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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

		@Test
		void getWIPImageUrl() {
			assertTrue(controller.getWIPImageUrl().startsWith("file"));
			assertTrue(controller.getWIPImageUrl().endsWith("icons/dashboard/refresh_40px.png"));
		}

		@Test
		void getFailedImageUrl() {
			assertTrue(controller.getFailedImageUrl().startsWith("file"));
			assertTrue(controller.getFailedImageUrl().endsWith("icons/dashboard/cancel_40px.png"));
		}

		@Test
		void getSucceededImageUrl() {
			assertTrue(controller.getSucceededImageUrl().startsWith("file"));
			assertTrue(controller.getSucceededImageUrl().endsWith("icons/dashboard/ok_40px.png"));
		}
	}

	@Test
	void loadSyncFolders() {
		controller = getControllerWithOneLoadedFile();
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addFolderToSyncList() {
		controller = getControllerWithNoLoadedFiles();
		controller.addToSyncList(new File("directory"));
		assertEquals(1, getSizeOfFoldersToSync());
	}

	private DashboardController getControllerWithNoLoadedFiles() {
		return new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<>()), new FolderSaver());
	}

	@Test
	void addSubfolderOfFolderToSyncList() {
		controller = getControllerWithOneLoadedFile();
		controller.addToSyncList(new File("/home/directory/subdirectory"));
		assertEquals(1, getSizeOfFoldersToSync());
	}

	@Test
	void addFolderToOneFolderList() {
		controller = getControllerWithOneLoadedFile();
		controller.addToSyncList(new File("directory2"));
		assertEquals(2, getSizeOfFoldersToSync());
	}

	@Test
	void addWindowsFolderToLinuxFolderList() {
		controller = getControllerWithOneLoadedFile();
		controller.addToSyncList(new File("D:\\directory2"));
		assertEquals(2, getSizeOfFoldersToSync());
	}

	private DashboardController getControllerWithOneLoadedFile() {
		return new DashboardController(null, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
		}}), new FolderSaver());
	}

	@Test
	void uploadFolders() {
		S3UploaderMock uploader = new S3UploaderMock();
		controller = getControllerForUpload(uploader, new FolderSaver());
		controller.setLoggedInUser(createAuthenticatedUser());
		controller.upload(Folder.createFolder("/home/directory"));
		controller.upload(Folder.createFolder("/home/directory2"));
		assertEquals(2, uploader.getTimesUploadDirectoryCalled());
	}

	@Test
	void skipUploadWhenUserNotLoggedIn() {
		S3UploaderMock uploader = new S3UploaderMock();
		controller = getControllerForUpload(uploader, new FolderSaver());
		controller.upload(Folder.createFolder("/home/directory"));
		assertEquals(0, uploader.getTimesUploadDirectoryCalled());
	}

	@Test
	void skipUploadWhenUserCredentialsExpired() {
		S3UploaderMock uploader = new S3UploaderMock();
		controller = getControllerForUpload(uploader, new FolderSaver());
		controller.setLoggedInUser(createUserWithExpiredCredentials());
		controller.setRefresher(refreshToken -> createUserWithExpiredCredentials());
		controller.upload(Folder.createFolder("/home/directory"));
		assertEquals(0, uploader.getTimesUploadDirectoryCalled());
	}

	@Test
	void refreshExpiredCredentialsOfAuthenticatedUserWhenUploading() {
		S3UploaderMock uploader = new S3UploaderMock();
		controller = getControllerForUpload(uploader, new FolderSaver());
		controller.setLoggedInUser(createUserWithExpiredCredentials());
		controller.setRefresher(refreshToken -> createAuthenticatedUser());
		controller.upload(Folder.createFolder("/home/directory"));
		assertEquals(1, uploader.getTimesUploadDirectoryCalled());
	}

	private int getSizeOfFoldersToSync() {
		return controller.getSyncList().size();
	}

	@Test
	void downloadFolder() {
		controller = getControllerForDownload(new S3DownloaderMock());
		controller.setLoggedInUser(createAuthenticatedUser());
		assertTrue(controller.download(Folder.createFolder("/home/directory")));
		assertTrue(controller.download(Folder.createFolder("/home/directory2")));
	}

	@Test
	void skipDownloadIfUserNotLoggedIn() {
		controller = getControllerForDownload(new S3DownloaderMock());
		assertFalse(controller.download(Folder.createFolder("/home/directory")));
		assertFalse(controller.download(Folder.createFolder("/home/directory2")));
	}

	@Test
	void skipDownloadWhenUserCredentialsExpired() {
		controller = getControllerForDownload(new S3DownloaderMock());
		controller.setLoggedInUser(createUserWithExpiredCredentials());
		controller.setRefresher(refreshToken -> createUserWithExpiredCredentials());
		assertFalse(controller.download(Folder.createFolder("/home/directory")));
	}

	@Test
	void refreshExpiredCredentialsOfAuthenticatedUserWhenDownloading() {
		controller = getControllerForDownload(new S3DownloaderMock());
		controller.setLoggedInUser(createUserWithExpiredCredentials());
		controller.setRefresher(refreshToken -> createAuthenticatedUser());
		assertTrue(controller.download(Folder.createFolder("/home/directory")));
	}

	@Test
	void cleanupCallsDownloaderShutdown() {
		S3DownloaderMock downloader = new S3DownloaderMock();
		controller = getControllerForDownload(downloader);
		controller.setDriveGateway(new DriveGateway() {
			@Override
			public void unmount() {
			}
		});
		controller.cleanup();
		assertTrue(downloader.shutdownCalled);
	}

	private DashboardController getControllerForDownload(S3DownloaderMock downloader) {
		return new DashboardController(null, downloader, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), null);
	}

	@Test
	void saveWhenUserLoggedIn() {
		FolderSaver saver = new FolderSaver();
		controller = getControllerForUpload(new S3UploaderMock(), saver);
		controller.setLoggedInUser(createAuthenticatedUser());
		controller.saveFolders();
		assertEquals(2, saver.getFoldersSavedCount());
	}

	@Test
	void skipSaveWhenUserIsNotLoggedIn() {
		FolderSaver saver = new FolderSaver();
		controller = getControllerForUpload(new S3UploaderMock(), saver);
		controller.saveFolders();
		assertEquals(0, saver.getFoldersSavedCount());
	}

	@Test
	void skipSaveWhenUserCredentialsExpired() {
		FolderSaver saver = new FolderSaver();
		controller = getControllerForUpload(new S3UploaderMock(), saver);
		controller.setLoggedInUser(createUserWithExpiredCredentials());
		controller.saveFolders();
		assertEquals(0, saver.getFoldersSavedCount());
	}

	private DashboardController getControllerForUpload(S3UploaderMock uploader, FolderSaver saver) {
		return new DashboardController(uploader, null, new SyncFolderLoaderStub(new ArrayList<Folder>() {{
			add(Folder.createFolder("/home/directory"));
			add(Folder.createFolder("/home/directory2"));
		}}), saver);
	}

	private User createAuthenticatedUser() {
		return new AuthenticatedUser("username",
				new UserCredentials("accessKey",
						"secretKey",
						"sessionToken",
						new Date(new Date().getTime() + 12 * 3600 * 1000)),
				"refreshToken");
	}

	private User createUserWithExpiredCredentials() {
		return new AuthenticatedUser("username",
				new UserCredentials("accessKey",
						"secretKey",
						"sessionToken",
						new Date(new Date().getTime() - 12 * 3600 * 1000)),
				"refreshToken");
	}
}