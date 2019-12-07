import java.nio.file.Path;

public interface ObjectUploader {
	boolean uploadDirectory(Path path);

	void setLoggedInUsername(String username);
}
