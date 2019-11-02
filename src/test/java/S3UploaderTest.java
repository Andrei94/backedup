import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.stream.Stream;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
		LocalFile localFile = LocalFile.fromFile(new File("file"));
		String s3FolderName = "s3FolderName";
		when(clientMock.toFileInRemoteFolder(s3FolderName, localFile.getPath())).thenCallRealMethod();

		uploader.upload(s3FolderName, localFile);

		verify(clientMock).putObject(assertArg(argument -> assertAll(() -> {
			assertEquals("backedup-storage", argument.getBucketName());
			assertEquals(s3FolderName + "/file", argument.getKey());
			assertEquals(localFile.toFile(), argument.getFile());
			assertEquals("GLACIER", argument.getStorageClass());
		})));
	}

	@Nested
	class DirectoryStructureUpload {
		private LocalPath pathUnderRoot;

		@BeforeEach
		void setUp() {
			uploader = new S3ObjectUploader(clientMock) {
				@Override
				protected Stream<LocalPath> walkTreeFromRoot(LocalPath root) {
					pathUnderRoot = mock(LocalPath.class);
					when(pathUnderRoot.isFile()).thenReturn(true);

					LocalFile fileMock = mock(LocalFile.class);
					when(fileMock.getPath()).thenReturn("path\\to\\file2");

					when(pathUnderRoot.getLocalFile()).thenReturn(fileMock);
					when(pathUnderRoot.getLocalFile().toFile()).thenReturn(new File("file2"));
					return Stream.of(pathUnderRoot);
				}
			};
		}

		@Test
		void uplodDirectory() {
			LocalPath root = mock(LocalPath.class);
			String rootFilename = "s3FolderName";
			when(root.getFilename()).thenReturn(rootFilename);
			when(root.relativize(any())).thenReturn("path/to/file2");
			when(clientMock.toFileInRemoteFolder(rootFilename, "path/to/file2")).thenCallRealMethod();

			uploader.uploadDirectory(root);

			verify(clientMock).putObject(assertArg(argument -> assertAll(() -> {
				assertEquals("backedup-storage", argument.getBucketName());
				assertEquals("s3FolderName/path/to/file2", argument.getKey());
				assertEquals(pathUnderRoot.getLocalFile().toFile(), argument.getFile());
				assertEquals("STANDARD", argument.getStorageClass());
			})));
		}
	}
}
