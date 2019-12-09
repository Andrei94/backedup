package adapter;

import file.LocalFile;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void downloadFolder() {
		adapter = new S3Adapter(new ClientWithDownloadOfOneFile());
		assertEquals("D:\\username\\testFolder", adapter.downloadDirectoryExcludingGlacier("username/testFolder", "D:\\").orElseThrow(RuntimeException::new).getPath());
	}

	@Test
	void downloadFolderThrowsExceptionAtListObjects() {
		adapter = new S3Adapter(new ClientWithListObjectsThrowingException());
		assertFalse(adapter.downloadDirectoryExcludingGlacier("username/testFolder", "D:\\").isPresent());
	}

	@Test
	void downloadFolderThrowsExceptionAtGetObjectMetadata() {
		adapter = new S3Adapter(new ClientWithGetObjectMetadataThrowingException());
		assertFalse(adapter.downloadDirectoryExcludingGlacier("username/testFolder", "D:\\").isPresent());
	}

	@Test
	void downloadFolderThrowsExceptionAtGetObject() {
		adapter = new S3Adapter(new ClientWithGetObjectThrowingException());
		assertFalse(adapter.downloadDirectoryExcludingGlacier("username/testFolder", "D:\\").isPresent());
	}

	@Test
	void shutdownTransferManagerShutsDownClientAsWell() {
		final boolean[] shutdownCalledOnClient = {false};
		adapter = new S3Adapter(new DummyS3Client() {
			@Override
			public void shutdown() {
				shutdownCalledOnClient[0] = true;
			}
		});
		adapter.shutdownTransferManager();
		assertTrue(shutdownCalledOnClient[0]);
	}
}
