package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import authentication.AuthenticatedUser;
import authentication.User;
import authentication.UserCredentials;
import file.LocalFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class S3UploaderTest {
	private S3Adapter s3Adapter;
	private S3ObjectUploader uploader;
	private LocalFileWalkerStub walker = new LocalFileWalkerStub();

	@Test
	void uploadFileObject() {
		LocalFile dir = createMockDirectory("directory");
		LocalFile localFile = createMockFile("directory/file");
		s3Adapter = new S3AdapterWithPutRequestVerifyingParameters(
				createUploadRequest("username/directory/" + localFile.getName(), localFile)
		);
		uploader = new S3ObjectUploader(s3Adapter, walker);
		uploader.setLoggedInUser(createAuthenticatedUser("username"));
		assertTrue(uploader.uploadFileFrom(dir, localFile));
	}

	@Test
	void skipFileUploadWhenUsernameNotSet() {
		LocalFile dir = createMockDirectory("directory");
		LocalFile localFile = createMockFile("directory/file");
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker);
		assertFalse(uploader.uploadFileFrom(dir, localFile));
	}

	@Test
	void uploadDirectoryWithOneFile() {
		LocalFile fileUnderDirectory = createMockFile("path/to/directory/file1");
		walker.setFile(fileUnderDirectory);
		s3Adapter = new S3AdapterWithPutRequestVerifyingParameters(
				createUploadRequest("username/directory/" + fileUnderDirectory.getName(), fileUnderDirectory)
		);
		uploader = new S3ObjectUploader(s3Adapter, walker);
		uploader.setLoggedInUser(createAuthenticatedUser("username"));
		assertTrue(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void uploadDirectoryWithOneFileInSubdirectory() {
		LocalFile fileUnderSubdirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker.setFile(fileUnderSubdirectory);
		s3Adapter = new S3AdapterWithPutRequestVerifyingParameters(
				createUploadRequest("username/directory/secondDirectory/" + fileUnderSubdirectory.getName(), fileUnderSubdirectory)
		);
		uploader = new S3ObjectUploader(s3Adapter, walker);
		uploader.setLoggedInUser(createAuthenticatedUser("username"));
		assertTrue(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void uploadDirectoryWithAnotherDirectory() {
		LocalFile subDirectory = createMockDirectory("path/to/directory/secondDirectory");
		walker.setFile(subDirectory);
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker);
		assertTrue(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void directoryTraversingThrowsRuntimeException() {
		LocalFile fileUnderSubdirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker = new LocalFileWalkerStub() {
			@Override
			protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) {
				throw new RuntimeException();
			}
		};
		walker.setFile(fileUnderSubdirectory);
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker);
		assertFalse(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
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
				.withBucket("backedup-storage-2")
				.withRemoteFile(remoteFile)
				.withLocalFile(localFile)
				.withStorageClass("INTELLIGENT_TIERING");
	}

	private User createAuthenticatedUser(String username) {
		return new AuthenticatedUser(username,
				new UserCredentials("accessKey",
						"secretKey",
						"sessionToken",
						new Date(new Date().getTime() + 12 * 3600 * 1000))
		);
	}
}