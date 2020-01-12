package subscription;

import adapters.HttpClient;
import adapters.JsonSerializer;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;

public class SubscriptionStorageChecker implements SubscriptionChecker {
	private JsonSerializer jsonSerializer;
	private HttpClient httpClient;

	public SubscriptionStorageChecker() {
		this(new JsonSerializer(new Gson()), new HttpClient(new OkHttpClient()));
	}

	public SubscriptionStorageChecker(JsonSerializer jsonSerializer, HttpClient httpClient) {
		this.jsonSerializer = jsonSerializer;
		this.httpClient = httpClient;
	}

	@Override
	public Subscription checkSubscription(long usedSize, String username) {
		return getUserSubscription(new SubscriptionRequest(usedSize, username));
	}

	private Subscription getUserSubscription(SubscriptionRequest requestJson) {
		String responseBody = httpClient.makePutRequest("http://localhost:8080/checkStorage", jsonSerializer.toJson(requestJson));
		try {
			return jsonSerializer.fromJson(responseBody, Subscription.class);
		} catch(ClassCastException ex) {
			return new Subscription().withFreeSize(0);
		}
	}
}
