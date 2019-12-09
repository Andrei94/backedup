import uploader.ObjectUploader;
import uploader.ObjectUploaderFactory;

import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		ObjectUploader uploader = ObjectUploaderFactory.createS3ObjectUploader();
		uploader.setLoggedInUsername("andreiprecup");
		uploader.uploadDirectory(Paths.get("D:\\Programming\\Intellij\\backedup\\src\\main"));
	}
}
