package downloader;

import adapter.S3AdapterStub;
import file.LocalFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		objectDownloader.setLoggedInUsername("username");
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
		objectDownloader.setLoggedInUsername("username");
		assertFalse(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}

	@Test
	void getDestination() {
		assertEquals("D:\\testFolder", objectDownloader.getDestination("testFolder", LocalFile.fromPath(Paths.get("D:\\"))).toString());
	}

	@Test
	void moveDownloadedFolder() {
		objectDownloader = new S3ObjectDownloader(new S3AdapterStub()) {
			@Override
			void moveFolder(Path src, Path dst) {
				assertEquals("D:\\username\\testFolder", src.toString());
				assertEquals("D:\\testFolder", dst.toString());
			}

			@Override
			void deleteFolder(Path localDownloadDirectory) {
				assertEquals("D:\\username", localDownloadDirectory.toString());
			}
		};
		objectDownloader.setLoggedInUsername("username");
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
		objectDownloader.setLoggedInUsername("username");
		objectDownloader.moveDownloadedFolder("testFolder", LocalFile.fromPath(Paths.get("D:\\")));
	}

	@Test
	void shutdownOnAdapterIsCalled() {
		S3AdapterSuccessfulDownloadStub adapter = new S3AdapterSuccessfulDownloadStub();
		new S3ObjectDownloader(adapter).shutdown();
		Assertions.assertTrue(adapter.shutdownCalled);
	}
}
