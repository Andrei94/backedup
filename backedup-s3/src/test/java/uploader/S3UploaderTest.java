package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import authentication.AuthenticatedUser;
import authentication.User;
import authentication.UserCredentials;
import file.LocalFile;
import org.junit.jupiter.api.Test;
import subscription.Subscription;
import subscription.SubscriptionChecker;

import java.io.File;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class S3UploaderTest {
	private S3Adapter s3Adapter;
	private S3ObjectUploader uploader;
	private LocalFileWalkerStub walker = new LocalFileWalkerStub();
	private SubscriptionChecker subscriptionStorageChecker = new ValidSubscriptionStorageChecker();

	@Test
	void uploadFileObject() {
		LocalFile dir = createMockDirectory("directory");
		LocalFile localFile = createMockFile("directory/file");
		s3Adapter = new S3AdapterWithPutRequestVerifyingParameters(
				createUploadRequest("username/directory/" + localFile.getName(), localFile)
		);
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		uploader.setLoggedInUser(createAuthenticatedUser());
		assertTrue(uploader.uploadFileFrom(dir, localFile, getValidSubscription()));
	}

	@Test
	void skipFileUploadWhenUsernameNotSet() {
		LocalFile dir = createMockDirectory("directory");
		LocalFile localFile = createMockFile("directory/file");
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		assertFalse(uploader.uploadFileFrom(dir, localFile, getValidSubscription()));
	}

	private Subscription getValidSubscription() {
		return new Subscription().withBucketName("backedup-storage-2").withFreeSize(1).withStorageClass("INTELLIGENT_TIERING").withUserPath("username/");
	}

	@Test
	void uploadDirectoryWithOneFile() {
		LocalFile fileUnderDirectory = createMockFile("path/to/directory/file1");
		walker.setFile(fileUnderDirectory);
		s3Adapter = new S3AdapterWithPutRequestVerifyingParameters(
				createUploadRequest("username/directory/" + fileUnderDirectory.getName(), fileUnderDirectory)
		);
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		uploader.setLoggedInUser(createAuthenticatedUser());
		assertTrue(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void uploadDirectoryWithOneFileInSubdirectory() {
		LocalFile fileUnderSubdirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker.setFile(fileUnderSubdirectory);
		s3Adapter = new S3AdapterWithPutRequestVerifyingParameters(
				createUploadRequest("username/directory/secondDirectory/" + fileUnderSubdirectory.getName(), fileUnderSubdirectory)
		);
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		uploader.setLoggedInUser(createAuthenticatedUser());
		assertTrue(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void uploadDirectoryWithAnotherDirectory() {
		LocalFile subDirectory = createMockDirectory("path/to/directory/secondDirectory");
		walker.setFile(subDirectory);
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		uploader.setLoggedInUser(createAuthenticatedUser());
		assertTrue(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void skipUploadWhenSubscriptionNotValid() {
		LocalFile subDirectory = createMockDirectory("path/to/directory/secondDirectory");
		walker.setFile(subDirectory);
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		subscriptionStorageChecker = new InvalidSubscriptionStorageChecker();
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		uploader.setLoggedInUser(createAuthenticatedUser());
		assertFalse(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	@Test
	void skipUploadWhenCredentialsAreExpired() {
		LocalFile subDirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker.setFile(subDirectory);
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
		uploader.setLoggedInUser(createUserWithExpiredCredentials());
		assertFalse(uploader.uploadDirectory(createMockDirectory("path/to/directory")));
	}

	private User createUserWithExpiredCredentials() {
		return new AuthenticatedUser("username",
				new UserCredentials("accessKey",
						"secretKey",
						"sessionToken",
						new Date(new Date().getTime() - 12 * 3600 * 1000)),
				"refreshToken");
	}

	@Test
	void directoryTraversingThrowsSecurityException() {
		LocalFile fileUnderSubdirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker = new LocalFileWalkerStub() {
			@Override
			protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) {
				throw new SecurityException();
			}
		};
		walker.setFile(fileUnderSubdirectory);
		s3Adapter = new S3AdapterWithPutRequestNotCalled();
		uploader = new S3ObjectUploader(s3Adapter, walker, subscriptionStorageChecker);
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

	private User createAuthenticatedUser() {
		return new AuthenticatedUser("username",
				new UserCredentials("accessKey",
						"secretKey",
						"sessionToken",
						new Date(new Date().getTime() + 12 * 3600 * 1000)),
				"refreshToken");
	}
}