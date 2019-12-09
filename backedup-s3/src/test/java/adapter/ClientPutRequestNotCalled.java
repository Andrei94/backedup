package adapter;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import static org.junit.jupiter.api.Assertions.*;

public class ClientPutRequestNotCalled extends DummyS3Client {
	@Override
	public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws SdkClientException {
		fail();
		return new PutObjectResult();
	}
}
