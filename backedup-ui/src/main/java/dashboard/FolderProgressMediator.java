package dashboard;

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