package adapter;

import file.LocalFile;

import java.util.Optional;

public class S3AdapterStub extends S3Adapter {
	public S3AdapterStub() {
		super(null);
	}

	@Override
	public Optional<LocalFile> downloadDirectory(String name, String destPath) {
		return Optional.empty();
	}
}
