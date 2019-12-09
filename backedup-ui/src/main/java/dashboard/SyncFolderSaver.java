package dashboard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class SyncFolderSaver {
	boolean save(List<String> folders) {
		try(BufferedWriter writer = newBufferedWriter()) {
			for(String s : folders) {
				try {
					write(writer, s);
				} catch(IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	BufferedWriter newBufferedWriter() throws IOException {
		return Files.newBufferedWriter(Paths.get("list.txt"));
	}

	void write(BufferedWriter writer, String s) throws IOException {
		writer.write(s.replace("\\", "/"));
		writer.newLine();
	}
}
