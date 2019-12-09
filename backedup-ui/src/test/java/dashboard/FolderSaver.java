package dashboard;

import dashboard.SyncFolderSaver;

import java.util.List;

class FolderSaver extends SyncFolderSaver {
	private int foldersSavedCount = 0;

	@Override
	boolean save(List<String> folders) {
		folders.forEach(f -> foldersSavedCount++);
		return true;
	}

	int getFoldersSavedCount() {
		return foldersSavedCount;
	}
}
