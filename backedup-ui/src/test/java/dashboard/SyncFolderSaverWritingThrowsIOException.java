package dashboard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

class SyncFolderSaverWritingThrowsIOException extends SyncFolderSaver {
	@Override
	BufferedWriter newBufferedWriter() {
		return new BufferedWriter(new StringWriter());
	}

	@Override
	void write(BufferedWriter writer, String s) throws IOException {
		throw new IOException();
	}
}
