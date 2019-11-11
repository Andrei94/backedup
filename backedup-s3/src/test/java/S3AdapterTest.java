import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class S3AdapterTest {
	private S3Adapter adapter;

	@Test
	void mappingLocalFileToRemoteFolder() {
		adapter = new S3Adapter(null);
		assertEquals("s3Uploader/file", adapter.toFileInRemoteFolder("s3Uploader", "file"));
	}

	@Test
	void uploadToInnexistentBucket() {
		UploadObjectRequest req = new UploadObjectRequest()
				.withBucket("innexistent_bucket")
				.withRemoteFile("123")
				.withLocalFile(LocalFile.fromFile(new File("file1")))
				.withStorageClass("STANDARD");
		adapter = new S3Adapter(new ClientWithPutRequestThrowingException());
		assertFalse(adapter.putObject(req));
	}
}
