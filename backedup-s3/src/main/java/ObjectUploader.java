import java.nio.file.Path;

public interface ObjectUploader {
	void uploadDirectory(Path path);

	void setLoggedInUsername(String username);
}
