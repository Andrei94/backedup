import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class S3UploaderTest {
	private S3Adapter clientMock;
	private S3ObjectUploader uploader;

	@BeforeEach
	void setUp() {
		clientMock = mock(S3Adapter.class);
		uploader = new S3ObjectUploader(clientMock);
	}

	@Test
	void uploadFileObject() {
		File localFile = new File("path\\to\\file");
		String s3FolderName = "s3FolderName";
		when(clientMock.toFileInRemoteFolder(s3FolderName, localFile)).thenCallRealMethod();

		uploader.upload(s3FolderName, localFile);

		verify(clientMock).putObject(assertArg(argument -> assertAll(() -> {
			assertEquals("backedup-storage", argument.getBucketName());
			assertEquals(s3FolderName + "/file", argument.getKey());
			assertEquals(localFile, argument.getFile());
			assertEquals("GLACIER", argument.getStorageClass());
		})));
	}
}
