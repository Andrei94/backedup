import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
		LocalFile localFile = LocalFile.fromFile(new File("file"));
		String s3FolderName = "s3FolderName";
		when(clientMock.toFileInRemoteFolder(s3FolderName, localFile.getPath())).thenCallRealMethod();

		uploader.upload(s3FolderName, localFile);

		verify(clientMock).putObject(assertArg(argument -> assertAll(() -> {
			assertEquals("backedup-storage", argument.getBucketName());
			assertEquals(s3FolderName + "/file", argument.getKey());
			assertEquals(localFile.toFile(), argument.getFile());
			assertEquals("STANDARD", argument.getStorageClass());
		})));
	}

	@Nested
	class DirectoryStructureUpload {
		private List<LocalFile> filesUnderDirectory;

		@BeforeEach
		void setUp() {
			filesUnderDirectory = new ArrayList<>();
			uploader = new S3ObjectUploader(clientMock) {
				@Override
				protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) {
					File f1 = mock(File.class);
					when(f1.getPath()).thenReturn("path/to/directory/file1");
					when(f1.getName()).thenReturn("file1");
					when(f1.isFile()).thenReturn(true);
					filesUnderDirectory.add(LocalFile.fromFile(f1));
					File dir2 = mock(File.class);
					when(dir2.getPath()).thenReturn("path/to/directory/secondDirectory");
					when(dir2.getName()).thenReturn("secondDirectory");
					when(dir2.isFile()).thenReturn(false);
					filesUnderDirectory.add(LocalFile.fromFile(dir2));
					File f2 = mock(File.class);
					when(f2.getPath()).thenReturn("path/to/directory/secondDirectory/file2");
					when(f2.getName()).thenReturn("file2");
					when(f2.isFile()).thenReturn(true);
					filesUnderDirectory.add(LocalFile.fromFile(f2));
					return filesUnderDirectory.stream();
				}
			};
		}

		@Test
		void uploadDirectory() {
			LocalFile localFile = LocalFile.fromFile(new File("path/to/directory"));
			when(clientMock.toFileInRemoteFolder(anyString(), anyString())).thenCallRealMethod();

			uploader.uploadDirectory(localFile);

			verify(clientMock).putObject(assertArg(argument -> assertAll(() -> {
				assertEquals("backedup-storage", argument.getBucketName());
				assertEquals("directory/" + filesUnderDirectory.get(0).getName(), argument.getKey());
				assertEquals(filesUnderDirectory.get(0).toFile(), argument.getFile());
				assertEquals("STANDARD", argument.getStorageClass());
			})));
			verify(clientMock, never()).putObject(new UploadObjectRequest()
					.withBucket("backedup-storage")
					.withRemoteFile("directory" + filesUnderDirectory.get(1).getName())
					.withLocalFile(filesUnderDirectory.get(1))
					.withStorageClass("STANDARD")
					.toS3PutObjectRequest());
			verify(clientMock).putObject(assertArg(argument -> assertAll(() -> {
				assertEquals("backedup-storage", argument.getBucketName());
				assertEquals("directory/secondDirectory/" + filesUnderDirectory.get(2).getName(), argument.getKey());
				assertEquals(filesUnderDirectory.get(2).toFile(), argument.getFile());
				assertEquals("STANDARD", argument.getStorageClass());
			})));
		}
	}
}
