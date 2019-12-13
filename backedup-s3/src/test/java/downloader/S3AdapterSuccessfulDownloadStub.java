package downloader;

import adapter.S3Adapter;
import file.LocalFile;

import java.io.File;
import java.util.Optional;

class S3AdapterSuccessfulDownloadStub extends S3Adapter {
	public boolean shutdownCalled = false;

	public S3AdapterSuccessfulDownloadStub() {
		super(null);
	}

	@Override
	public Optional<LocalFile> downloadDirectory(String name, String destPath) {
		return Optional.of(LocalFile.fromFile(new File(destPath, name)));
	}

	@Override
	public void shutdownTransferManager() {
		shutdownCalled = true;
	}
}
