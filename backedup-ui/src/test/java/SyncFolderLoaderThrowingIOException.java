import java.io.IOException;
import java.util.stream.Stream;

class SyncFolderLoaderThrowingIOException extends SyncFolderLoader {
	@Override
	Stream<String> getLines() throws IOException {
		throw new IOException();
	}
}