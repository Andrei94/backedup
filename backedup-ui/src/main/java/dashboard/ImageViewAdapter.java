package dashboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class ImageViewAdapter {
	ImageView progressImage;

	ImageViewAdapter(ImageView progressImage) {
		this.progressImage = progressImage;
	}

	void setImage(String imageUrl) {
		progressImage.setImage(new Image(imageUrl));
	}
}
