package adapter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.PauseResult;
import com.amazonaws.services.s3.transfer.PersistableUpload;
import com.amazonaws.services.s3.transfer.TransferProgress;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.exception.PauseException;
import com.amazonaws.services.s3.transfer.model.UploadResult;

public class DummyUpload implements Upload {
	@Override
	public UploadResult waitForUploadResult() throws AmazonClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PersistableUpload pause() throws PauseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PauseResult<PersistableUpload> tryPause(boolean forceCancelTransfers) {
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
