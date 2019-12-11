package uploader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectUploaderFactoryTest {
	@Test
	void createDefaultObjectDownloader() {
		assertNotNull(ObjectUploaderFactory.createS3ObjectUploader());
	}
}
