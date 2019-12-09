package dashboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SyncFolderLoader {
	List<Folder> load() {
		try {
			return getLines().map(Folder::createFolder).collect(Collectors.toList());
		} catch(IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	Stream<String> getLines() throws IOException {
		return Files.lines(Paths.get("list.txt"));
	}
}
