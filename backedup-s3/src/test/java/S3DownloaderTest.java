import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class S3DownloaderTest {
	private S3ObjectDownloader objectDownloader;

	@BeforeEach
	void setUp() {
		objectDownloader = new S3ObjectDownloader(new S3Adapter(new ClientWithDownloadOfOneFile()));
	}

	@Test
	void downloadDirectoryFromS3() {
		assertTrue(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}

	@Test
	void downloadDirectoryFromS3ToInnexistentLocalFolder() {
		assertFalse(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("Y:\\"))));
	}
}
