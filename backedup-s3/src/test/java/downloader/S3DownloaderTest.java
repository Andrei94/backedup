package downloader;

import adapter.S3AdapterStub;
import authentication.AuthenticatedUser;
import authentication.User;
import authentication.UserCredentials;
import file.LocalFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class S3DownloaderTest {
	private S3ObjectDownloader objectDownloader = new S3ObjectDownloader(new S3AdapterStub());

	@Test
	void downloadDirectoryFromS3() {
		objectDownloader = new S3ObjectDownloader(new S3AdapterSuccessfulDownloadStub()) {
			@Override
			void moveDownloadedFolder(String remoteDir, LocalFile localDir) {
				assertEquals("testFolder", remoteDir);
				assertEquals("D:\\", localDir.getPath());
			}
		};
		objectDownloader.setLoggedInUser(createAuthenticatedUser());
		assertTrue(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}

	@Test
	void skipDownloadIfUserNotLoggedIn() {
		assertFalse(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}

	@Test
	void downloadDirectoryFromS3ToInnexistentLocalFolder() {
		assertFalse(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("Y:\\"))));
	}

	@Test
	void failedDownload() {
		objectDownloader = new S3ObjectDownloader(new S3AdapterFailedDownloadStub());
		objectDownloader.setLoggedInUser(createAuthenticatedUser());
		assertFalse(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}

	@Test
	void getDestination() {
		assertEquals("D:\\testFolder", objectDownloader.getDestination("testFolder", LocalFile.fromPath(Paths.get("D:\\"))).toString());
	}

	@Test
	void getDestinationUnderRoot() {
		assertEquals("D:\\folder\\testFolder", objectDownloader.getDestination("testFolder", LocalFile.fromPath(Paths.get("D:\\folder"))).toString());
	}

	@Test
	void moveDownloadedFolder() {
		objectDownloader = new S3ObjectDownloader(new S3AdapterStub()) {
			@Override
			void moveFolder(Path src, Path dst) {
				assertEquals("D:\\username\\My Local PC\\testFolder", src.toString());
				assertEquals("D:\\testFolder", dst.toString());
			}

			@Override
			void deleteFolder(Path localDownloadDirectory) {
				assertEquals("D:\\username\\My Local PC", localDownloadDirectory.toString());
			}
		};
		objectDownloader.setLoggedInUser(createAuthenticatedUser());
		objectDownloader.moveDownloadedFolder("testFolder", LocalFile.fromPath(Paths.get("D:\\")));
	}

	@Test
	void movingInnexistentDownloadedFolderDoesntCallDelete() {
		objectDownloader = new S3ObjectDownloader(new S3AdapterStub()) {
			@Override
			void moveFolder(Path src, Path dst) throws IOException {
				throw new IOException();
			}

			@Override
			void deleteFolder(Path localDownloadDirectory) {
				fail();
			}
		};
		objectDownloader.setLoggedInUser(createAuthenticatedUser());
		objectDownloader.moveDownloadedFolder("testFolder", LocalFile.fromPath(Paths.get("D:\\")));
	}

	@Test
	void skipDownloadWhenUserCredentialsExpired() {
		objectDownloader.setLoggedInUser(createUserWithExpiredCredentials());
		assertFalse(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}

	@Test
	void shutdownOnAdapterIsCalled() {
		S3AdapterSuccessfulDownloadStub adapter = new S3AdapterSuccessfulDownloadStub();
		new S3ObjectDownloader(adapter).shutdown();
		Assertions.assertTrue(adapter.shutdownCalled);
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
