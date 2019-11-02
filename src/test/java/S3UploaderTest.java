import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class S3UploaderTest {
	private S3Adapter s3Adapter;
	private S3ObjectUploader uploader;
	private LocalFileWalkerStub walker;

	@BeforeEach
	void setUp() {
		s3Adapter = mock(S3Adapter.class);
		walker = new LocalFileWalkerStub();
		uploader = new S3ObjectUploader(s3Adapter, walker);
		when(s3Adapter.toFileInRemoteFolder(anyString(), anyString())).thenCallRealMethod();
	}

	@Test
	void uploadFileObject() {
		LocalFile dir = createMockDirectory("directory");
		LocalFile localFile = createMockFile("directory/file");
		when(s3Adapter.putObject(any())).thenReturn(true);

		assertTrue(uploader.uploadFileFrom(dir, localFile));

		verify(s3Adapter).putObject(assertArg(argument -> assertAll(() -> {
			assertEquals("backedup-storage", argument.getBucket());
			assertEquals("directory/file", argument.getRemotePath());
			assertEquals(localFile, argument.getLocalPathFile());
			assertEquals("STANDARD", argument.getStorageClass());
		})));
	}

	@Test
	void uploadDirectoryWithOneFile() {
		LocalFile fileUnderDirectory = createMockFile("path/to/directory/file1");
		walker.setFile(fileUnderDirectory);

		uploader.uploadDirectory(createMockDirectory("path/to/directory"));

		verify(s3Adapter).putObject(assertArg(argument -> assertAll(() -> {
			assertEquals("backedup-storage", argument.getBucket());
			assertEquals("directory/" + fileUnderDirectory.getName(), argument.getRemotePath());
			assertEquals(fileUnderDirectory, argument.getLocalPathFile());
			assertEquals("STANDARD", argument.getStorageClass());
		})));
	}

	@Test
	void uploadDirectoryWithOneFileInSubdirectory() {
		LocalFile fileUnderSubdirectory = createMockFile("path/to/directory/secondDirectory/file2");
		walker.setFile(fileUnderSubdirectory);

		uploader.uploadDirectory(createMockDirectory("path/to/directory"));

		verify(s3Adapter).putObject(assertArg(argument -> assertAll(() -> {
			assertEquals("backedup-storage", argument.getBucket());
			assertEquals("directory/secondDirectory/" + fileUnderSubdirectory.getName(), argument.getRemotePath());
			assertEquals(fileUnderSubdirectory, argument.getLocalPathFile());
			assertEquals("STANDARD", argument.getStorageClass());
		})));
	}

	@Test
	void uploadDirectoryWithAnotherDirectory() {
		LocalFile subDirectory = createMockDirectory("path/to/directory/secondDirectory");
		walker.setFile(subDirectory);

		uploader.uploadDirectory(createMockDirectory("path/to/directory"));

		verify(s3Adapter, never()).putObject(createUploadRequest("directory" + subDirectory.getName(), subDirectory));
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