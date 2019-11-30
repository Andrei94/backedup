import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class S3DownloaderTest {
	@Test
	void downloadDirectoryFromS3() {
		S3ObjectDownloader objectDownloader = new S3ObjectDownloader();
		assertTrue(objectDownloader.downloadDirectory("testFolder", LocalFile.fromPath(Paths.get("D:\\"))));
	}
}
