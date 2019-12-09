package adapter;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientWithPutRequestVerifyingParameters extends DummyS3Client {
	private final UploadObjectRequest expectedReq;

	public ClientWithPutRequestVerifyingParameters(UploadObjectRequest expectedRequest) {
		this.expectedReq = expectedRequest;
	}

	@Override
	public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws SdkClientException {
		assertAll(
				() -> assertEquals(expectedReq.getBucket(), putObjectRequest.getBucketName()),
				() -> assertEquals(expectedReq.getRemotePath(), putObjectRequest.getKey()),
				() -> assertEquals(expectedReq.getLocalPathFile().toFile(), putObjectRequest.getFile()),
				() -> assertEquals("AES256", putObjectRequest.getMetadata().getSSEAlgorithm()),
				() -> assertEquals(expectedReq.getStorageClass(), putObjectRequest.getStorageClass())
		);
		PutObjectResult result = new PutObjectResult();
		result.setETag("success");
		result.setContentMd5("success");
		return result;
	}
}
