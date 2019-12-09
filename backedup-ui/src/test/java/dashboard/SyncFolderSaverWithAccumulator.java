package dashboard;

import java.io.BufferedWriter;
import java.io.StringWriter;

class SyncFolderSaverWithAccumulator extends SyncFolderSaver {
	private final StringWriter accumulator;

	SyncFolderSaverWithAccumulator(StringWriter accumulator) {
		this.accumulator = accumulator;
	}

	@Override
	BufferedWriter newBufferedWriter() {
		return new BufferedWriter(accumulator);
	}
}
