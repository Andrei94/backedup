package downloader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectDownloaderFactoryTest {
	@Test
	void createDefaultObjectDownloader() {
		assertNotNull(ObjectDownloaderFactory.createObjectDownloader());
	}
}
