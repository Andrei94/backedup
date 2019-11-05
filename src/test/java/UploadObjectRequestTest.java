import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class UploadObjectRequestTest {
	@Test
	void uploadInnexistentFile() {
		assertThrows(IllegalArgumentException.class, new UploadObjectRequest()
				.withBucket("bucket")
				.withRemoteFile("123")
				.withLocalFile(LocalFile.fromFile(new File("")))::toS3PutObjectRequest);
	}

	@Test
	void toS3PutRequest() {
		PutObjectRequest request = new UploadObjectRequest()
				.withBucket("bucket")
				.withRemoteFile("123")
				.withLocalFile(LocalFile.fromFile(new File("D:\\Programming\\Intellij\\backedup\\src\\main\\resources\\Cities.Skylines.Demo\\file1")))
				.withStorageClass("GLACIER")
				.toS3PutObjectRequest();
		assertAll(
				() -> assertEquals("bucket", request.getBucketName()),
				() -> assertEquals("123", request.getKey()),
				() -> assertEquals(1, request.getMetadata().getContentLength()),
				() -> assertEquals(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION, request.getMetadata().getSSEAlgorithm())
		);
	}
}
