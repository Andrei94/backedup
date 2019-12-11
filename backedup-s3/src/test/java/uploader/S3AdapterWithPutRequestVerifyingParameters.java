package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

class S3AdapterWithPutRequestVerifyingParameters extends S3Adapter {
	public S3AdapterWithPutRequestVerifyingParameters(UploadObjectRequest request) {
		super(TransferManagerBuilder.standard().withS3Client(new ClientWithPutRequestVerifyingParameters(request)).build());
	}
}
