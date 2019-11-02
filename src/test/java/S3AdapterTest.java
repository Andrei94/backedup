import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3AdapterTest {
	@Test
	void mappingLocalFileToRemoteFolder() {
		S3Adapter adapter = mock(S3Adapter.class);
		when(adapter.toFileInRemoteFolder("s3Uploader", "file")).thenCallRealMethod();
		assertEquals("s3Uploader/file", adapter.toFileInRemoteFolder("s3Uploader", "file"));
	}

	@Test
	void uploadToInnexistentBucket() {
		S3Adapter adapter = mock(S3Adapter.class);
		PutObjectRequest innexistentBucket = new PutObjectRequest("innexistent_bucket", "123", mock(File.class));

		when(adapter.putObject(innexistentBucket)).thenThrow(SdkClientException.class);

		assertThrows(SdkClientException.class, () ->adapter.putObject(innexistentBucket));
	}
}
