import com.amazonaws.SdkClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3AdapterTest {
	private S3Adapter adapter;

	@BeforeEach
	void setUp() {
		adapter = mock(S3Adapter.class);
	}

	@Test
	void mappingLocalFileToRemoteFolder() {
		when(adapter.toFileInRemoteFolder("s3Uploader", "file")).thenCallRealMethod();
		assertEquals("s3Uploader/file", adapter.toFileInRemoteFolder("s3Uploader", "file"));
	}

	@Test
	void uploadToInnexistentBucket() {
		UploadObjectRequest innexistentBucket = new UploadObjectRequest()
				.withBucket("innexistent_bucket")
				.withRemoteFile("123")
				.withLocalFile(LocalFile.fromFile(new File("")));
		when(adapter.putObject(innexistentBucket)).thenThrow(SdkClientException.class);
		assertThrows(SdkClientException.class, () -> adapter.putObject(innexistentBucket));
	}
}
