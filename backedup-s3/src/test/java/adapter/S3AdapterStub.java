package adapter;

import com.amazonaws.services.s3.transfer.TransferManager;
import file.LocalFile;

import java.util.Optional;

public class S3AdapterStub extends S3Adapter {
	public S3AdapterStub() {
		super((TransferManager) null);
	}

	@Override
	public Optional<LocalFile> downloadDirectory(String name, String destPath) {
		return Optional.empty();
	}
}
