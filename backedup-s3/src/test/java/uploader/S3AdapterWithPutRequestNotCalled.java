package uploader;

import adapter.S3Adapter;
import com.amazonaws.services.s3.transfer.TransferManager;

class S3AdapterWithPutRequestNotCalled extends S3Adapter {
	public S3AdapterWithPutRequestNotCalled() {
		super((TransferManager) null);
	}
}
