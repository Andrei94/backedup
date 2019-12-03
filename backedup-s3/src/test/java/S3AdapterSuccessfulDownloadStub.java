import com.amazonaws.services.s3.AmazonS3;

public class S3AdapterSuccessfulDownloadStub extends S3Adapter {
	public S3AdapterSuccessfulDownloadStub(AmazonS3 client) {
		super(client);
	}

	@Override
	public boolean downloadDirectoryExcludingGlacier(String name, String destPath) {
		return true;
	}
}
