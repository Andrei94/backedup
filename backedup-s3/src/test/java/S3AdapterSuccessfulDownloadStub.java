import com.amazonaws.services.s3.AmazonS3;

import java.io.File;
import java.util.Optional;

public class S3AdapterSuccessfulDownloadStub extends S3Adapter {
	public S3AdapterSuccessfulDownloadStub(AmazonS3 client) {
		super(client);
	}

	@Override
	public Optional<LocalFile> downloadDirectoryExcludingGlacier(String name, String destPath) {
		return Optional.of(LocalFile.fromFile(new File(destPath, name)));
	}
}
