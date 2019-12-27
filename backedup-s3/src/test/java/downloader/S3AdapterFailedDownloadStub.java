package downloader;

import adapter.S3Adapter;
import com.amazonaws.services.s3.transfer.TransferManager;
import file.LocalFile;

import java.util.Optional;

class S3AdapterFailedDownloadStub extends S3Adapter {
	public boolean shutdownCalled = false;

	public S3AdapterFailedDownloadStub() {
		super((TransferManager) null);
	}

	@Override
	public Optional<LocalFile> downloadDirectory(String name, String destPath) {
		return Optional.empty();
	}

	@Override
	public void shutdownTransferManager() {
		shutdownCalled = true;
	}
}
