package dashboard;

import dashboard.SyncFolderLoader;

import java.util.List;
import java.util.stream.Stream;

class SyncFolderLoaderStub extends SyncFolderLoader {
	private final List<Folder> initialList;

	SyncFolderLoaderStub(List<Folder> initialList) {
		this.initialList = initialList;
	}

	@Override
	Stream<String> getLines() {
		return initialList.stream().map(folder -> folder.path.toString());
	}
}
