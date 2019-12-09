package adapter;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class ClientWithPutRequestThrowingException extends DummyS3Client {
	@Override
	public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws SdkClientException {
		throw new SdkClientException("Exception");
	}
}
