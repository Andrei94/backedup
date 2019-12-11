package dashboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FolderProgressMediatorTest {
	@Test
	void newImageIsSetWhenCallingUpdate() {
		FolderProgressMediator mediator = new FolderProgressMediator(Folder.createFolder("home"), new ImageViewAdapter(null) {
			@Override
			void setImage(String imageUrl) {
				assertEquals("testImage", imageUrl);
			}
		});
		mediator.update("testImage");
	}

	@Test
	void getFolder() {
		FolderProgressMediator mediator = new FolderProgressMediator(Folder.createFolder("home"), new ImageViewAdapter(null) {
			@Override
			void setImage(String imageUrl) {
			}
		});
		assertNotNull(mediator.getFolder());
	}
}
