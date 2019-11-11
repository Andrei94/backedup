import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class S3UploaderTest {
	private S3Adapter s3Adapter;
	private S3ObjectUploader uploader;
	private LocalFileWalkerStub walker = new LocalFileWalkerStub();

	@Test
	void uploadFileObject() {
		LocalFile dir = createMockDirectory("directory");
		LocalFile localFile = createMockFile("directory/file");
		s3Adapter = new S3Adapter(new ClientWithPutRequestVerifyingParameters(
				createUploadRequest("directory/" + localFile.getName(), localFile)
		));
		uploader = new S3ObjectUploader(s3Adapter, walker);
		assertTrue(uploader.uploadFileFrom(dir, localFile));
	}

	@Test
	void uploadDirectoryWithOneFile() {
		LocalFile fileUnderDirectory = createMockFile("path/to/directory/file1");
		walker.setFile(fileUnderDirectory);
		s3Adapter = new S3Adapter(new ClientWithPutRequestVerifyingParameters(
				createUploadRequest("directory/" + fileUnderDirectory.getName(), fileUnderDirectory)
		));
		uploader = new S3ObjectUploader(s3Adapter, walker);

		uploader.uploadDirectory(createMockDirectory("path/to/directory"));
	}

	@Test
	void uploadDirectoryWithOneFileInSubdirectory() {
		LocalFile fileUnderSubdirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker.setFile(fileUnderSubdirectory);
		s3Adapter = new S3Adapter(new ClientWithPutRequestVerifyingParameters(
				createUploadRequest("directory/secondDirectory/" + fileUnderSubdirectory.getName(), fileUnderSubdirectory)
		));
		uploader = new S3ObjectUploader(s3Adapter, walker);
		uploader.uploadDirectory(createMockDirectory("path/to/directory"));
	}

	@Test
	void uploadDirectoryWithAnotherDirectory() {
		LocalFile subDirectory = createMockDirectory("path/to/directory/secondDirectory");
		walker.setFile(subDirectory);
		s3Adapter = new S3Adapter(new ClientPutRequestNotCalled());
		uploader = new S3ObjectUploader(s3Adapter, walker);
		uploader.uploadDirectory(createMockDirectory("path/to/directory"));
	}

	private LocalFile createMockFile(String filePath) {
		return LocalFile.fromFile(new File(filePath) {
			@Override
			public boolean isFile() {
				return true;
			}
		});
	}

	private LocalFile createMockDirectory(String directoryPath) {
		return LocalFile.fromFile(new File(directoryPath) {
			@Override
			public boolean isFile() {
				return false;
			}
		});
	}

	private UploadObjectRequest createUploadRequest(String remoteFile, LocalFile localFile) {
		return new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile(remoteFile)
				.withLocalFile(localFile)
				.withStorageClass("STANDARD");
	}
}