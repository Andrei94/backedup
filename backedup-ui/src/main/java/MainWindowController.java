import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class MainWindowController {
	private List<Folder> folders = new ArrayList<>();
	private SaveFolderSyncList saver = new SaveFolderSyncList();

	Optional<Folder> addToSyncList(File folder) {
		if (folder == null || isSubfolder(folder))
			return Optional.empty();
		Folder f = new Folder(Paths.get(folder.getPath()));
		folders.add(f);
		return Optional.of(f);
	}

	private boolean isSubfolder(File folder) {
		if (folders.isEmpty())
			return false;
		for (Folder value : folders) {
			Path subpath = value.path.relativize(folder.toPath());
			if (!hasToGoBackInPath(subpath))
				return true;
		}
		return false;
	}

	private boolean hasToGoBackInPath(Path subpath) {
		return subpath.startsWith("..");
	}

	boolean removeFromSyncList(Folder folder) {
		if (folder == null)
			return false;
		return folders.remove(folder);
	}

	void sync() {
		saver.save(folders.stream().map(folder -> folder.path.toString()).collect(Collectors.toList()));
	}

	String getWarningTitle() {
		return "Remove folder from Sync";
	}

	String getWarningText() {
		return "Are you sure you want to remove the folder from the sync?";
	}
}

