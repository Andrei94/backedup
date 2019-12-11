package adapter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferProgress;

class DummyMultipleFileDownload implements MultipleFileDownload {
	@Override
	public String getKeyPrefix() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getBucketName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void abort() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForCompletion() throws AmazonClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AmazonClientException waitForException() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDescription() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TransferState getState() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addProgressListener(ProgressListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeProgressListener(ProgressListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TransferProgress getProgress() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void addProgressListener(com.amazonaws.services.s3.model.ProgressListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void removeProgressListener(com.amazonaws.services.s3.model.ProgressListener listener) {
		throw new UnsupportedOperationException();
	}
}
