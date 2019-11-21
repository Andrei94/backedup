import java.nio.file.Path;

class S3UploaderMock implements ObjectUploader {
	private int timesUploadDirectoryCalled = 0;

	@Override
	public void uploadDirectory(Path directory) {
		timesUploadDirectoryCalled++;
	}

	int getTimesUploadDirectoryCalled() {
		return timesUploadDirectoryCalled;
	}
}
