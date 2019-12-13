package adapter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
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
	void uploadFile() {
		UploadObjectRequest req = new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile("123")
				.withLocalFile(LocalFile.fromFile(new File("file1")))
				.withStorageClass("STANDARD");
		adapter = new S3Adapter(null) {
			@Override
			Upload uploadAsync(UploadObjectRequest request) {
				return new DummyUpload() {
					@Override
					public void waitForCompletion() throws AmazonClientException {
					}
				};
			}
		};
		assertTrue(adapter.putObject(req));
	}

	@Test
	void uploadToInnexistentBucket() {
		UploadObjectRequest req = new UploadObjectRequest()
				.withBucket("innexistent_bucket")
				.withRemoteFile("123")
				.withLocalFile(LocalFile.fromFile(new File("file1")))
				.withStorageClass("STANDARD");
		adapter = new S3Adapter(null) {
			@Override
			Upload uploadAsync(UploadObjectRequest request) {
				return new DummyUpload() {
					@Override
					public void waitForCompletion() throws AmazonClientException {
						throw new SdkClientException("Exception");
					}
				};
			}
		};
		assertFalse(adapter.putObject(req));
	}

	@Test
	void downloadFolder() {
		adapter = new S3Adapter(null) {
			@Override
			long downloadAsync(String name, String destPath) {
				return 10;
			}
		};
		assertEquals("D:\\username\\testFolder",
				adapter.downloadDirectory("username/testFolder", "D:\\")
						.orElseThrow(RuntimeException::new).getPath()
		);
	}

	@Test
	void downloadFolderThrowsExceptionWhileWaiting() {
		adapter = new S3Adapter(null) {
			@Override
			long downloadAsync(String name, String destPath) throws InterruptedException {
				throw new InterruptedException();
			}
		};
		assertFalse(adapter.downloadDirectory("username/testFolder", "D:\\").isPresent());
	}

	@Test
	void downloadFolderDoesntDownloadAnything() {
		adapter = new S3Adapter(null) {
			@Override
			long downloadAsync(String name, String destPath) {
				return 0;
			}
		};
		assertFalse(adapter.downloadDirectory("username/testFolder", "D:\\").isPresent());
	}

	@Test
	void shutdownTransferManagerShutsDownClientAsWell() {
		final boolean[] shutdownCalledOnClient = {false};
		adapter = new S3Adapter(TransferManagerBuilder.standard().withS3Client(new DummyS3Client() {
			@Override
			public void shutdown() {
				shutdownCalledOnClient[0] = true;
			}
		}).build());
		adapter.shutdownTransferManager();
		assertTrue(shutdownCalledOnClient[0]);
	}
}
