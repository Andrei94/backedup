package subscription;

import adapters.HttpClient;
import adapters.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionStorageCheckerTest {
	@Test
	void invalidSubscriptionCheck() {
		HttpClient httpClient = new HttpClient(null) {
			@Override
			public String makePutRequest(String url, String body) {
				return "";
			}
		};
		JsonSerializer jsonSerializer = new JsonSerializer(null) {
			@Override
			public String toJson(Object src) {
				SubscriptionRequest sub = (SubscriptionRequest)src;
				return "{\"usedSize\": " + sub.getUsedSize() + ", " + "\"user\": \"" + sub.getUser() + "\"}";
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T fromJson(String json, Type type) {
				return (T) json;
			}
		};
		Subscription subscription = new SubscriptionStorageChecker(jsonSerializer, httpClient).checkSubscription(10, "username");
		assertNull(subscription.getBucketName());
		assertNull(subscription.getStorageClass());
		assertNull(subscription.getUserPath());
		assertFalse(subscription.isValid());
	}

	@Test
	void validSubscriptionCheck() {
		HttpClient httpClient = new HttpClient(null) {
			@Override
			public String makePutRequest(String url, String body) {
				return "{\n" +
						"  \"freeSize\": 1099511627775,\n" +
						"  \"bucketName\": \"backedup-storage-2\",\n" +
						"  \"storageClass\": \"INTELLIGENT_TIERING\",\n" +
						"  \"userPath\": \"username/\"\n" +
						"}";
			}
		};
		JsonSerializer jsonSerializer = new JsonSerializer(null) {
			@Override
			public String toJson(Object src) {
				SubscriptionRequest sub = (SubscriptionRequest)src;
				return "{\"usedSize\": " + sub.getUsedSize() + ", " + "\"user\": \"" + sub.getUser() + "\"}";
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T fromJson(String json, Type type) {
				return (T) new Subscription().withBucketName("backedup-storage-2").withStorageClass("INTELLIGENT_TIERING").withUserPath("username/").withFreeSize(1099511627775L);
			}
		};
		Subscription subscription = new SubscriptionStorageChecker(jsonSerializer, httpClient).checkSubscription(10, "username");
		assertEquals("backedup-storage-2", subscription.getBucketName());
		assertEquals("INTELLIGENT_TIERING", subscription.getStorageClass());
		assertEquals("username/", subscription.getUserPath());
		assertTrue(subscription.isValid());
	}
}
