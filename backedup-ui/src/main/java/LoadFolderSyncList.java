import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class LoadFolderSyncList {
	List<Folder> load() {
		Path path = Paths.get("list.txt");
		try {
			return Files.lines(path).map(s -> new Folder(Paths.get(s))).collect(Collectors.toList());
		} catch(IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
