package dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FolderProgressMediator {
	private Folder folder;
	private ImageView progressImage;

	public FolderProgressMediator(Folder folder, ImageView progressImage) {
		this.folder = folder;
		this.progressImage = progressImage;
	}

	void update(String progressImageUrl) {
		progressImage.setImage(new Image(progressImageUrl));
	}

	public Folder getFolder() {
		return folder;
	}
}
