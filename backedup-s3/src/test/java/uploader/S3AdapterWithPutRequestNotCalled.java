package uploader;

import adapter.S3Adapter;
import authentication.UserCredentials;
import com.amazonaws.services.s3.transfer.TransferManager;

class S3AdapterWithPutRequestNotCalled extends S3Adapter {
	public S3AdapterWithPutRequestNotCalled() {
		super((TransferManager) null);
	}

	@Override
	public void updateCredentials(UserCredentials credentials) {
	}
}
