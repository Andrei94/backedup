import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class S3DownloaderTest {
	private S3ObjectDownloader objectDownloader;

	@BeforeEach
	void setUp() {
		objectDownloader = new S3ObjectDownloader(new S3Adapter(new ClientWithDownloadOfOneFile())) {
			@Override
			boolean exists(String remoteDir, LocalFile localDir) {
				return true;
			}
		};
	}

	@Test
	void downloadDirectoryFromS3() {
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
}
