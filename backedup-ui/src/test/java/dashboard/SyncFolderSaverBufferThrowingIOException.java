package dashboard;

import dashboard.SyncFolderSaver;

import java.io.BufferedWriter;
import java.io.IOException;

class SyncFolderSaverBufferThrowingIOException extends SyncFolderSaver {
	@Override
	BufferedWriter newBufferedWriter() throws IOException {
		throw new IOException();
	}
}
