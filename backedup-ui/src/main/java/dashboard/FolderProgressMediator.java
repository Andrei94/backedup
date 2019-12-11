package dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FolderProgressMediator {
	private Folder folder;
	private ImageViewAdapter progressImage;

	public FolderProgressMediator(Folder folder, ImageViewAdapter progressImage) {
		this.folder = folder;
		this.progressImage = progressImage;
	}

	void update(String progressImageUrl) {
		progressImage.setImage(progressImageUrl);
	}

	public Folder getFolder() {
		return folder;
	}
}

class ImageViewAdapter {
	ImageView progressImage;

	ImageViewAdapter(ImageView progressImage) {
		this.progressImage = progressImage;
	}

	void setImage(String imageUrl) {
		progressImage.setImage(new Image(imageUrl));
	}
}