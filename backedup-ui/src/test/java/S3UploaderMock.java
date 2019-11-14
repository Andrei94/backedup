class S3UploaderMock extends S3ObjectUploader {
	private int timesUploadDirectoryCalled = 0;

	S3UploaderMock() {
		this(null, null);
	}

	private S3UploaderMock(S3Adapter adapter, LocalFileWalker walker) {
		super(adapter, walker);
	}

	@Override
	void uploadDirectory(LocalFile directory) {
		timesUploadDirectoryCalled++;
	}

	int getTimesUploadDirectoryCalled() {
		return timesUploadDirectoryCalled;
	}
}
