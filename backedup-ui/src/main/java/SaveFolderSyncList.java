import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class SaveFolderSyncList {
	void save(List<String> folders) {
		Path path = Paths.get("list.txt");

		try(BufferedWriter writer = Files.newBufferedWriter(path)) {
			folders.forEach(s -> {
				try {
					writer.write(s);
					writer.newLine();
				} catch(IOException e) {
					e.printStackTrace();
				}
			});
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
