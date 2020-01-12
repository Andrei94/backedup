package subscription;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriptionStorageChecker implements SubscriptionChecker {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Gson jsonSerializer;
	private OkHttpClient httpClient;

	public SubscriptionStorageChecker() {
		this.jsonSerializer = new Gson();
		this.httpClient = new OkHttpClient();
	}

	@Override
	public Subscription checkSubscription(long usedSize, String username) {
		String json = "{" +
				"\"usedSize\": " + usedSize + "," +
				"\"user\": " + "\"" + username + "\"" +
				"}";
		return getUserSubscription(json);
	}

	private Subscription getUserSubscription(String requestJson) {
		Request request = createRequest(requestJson);
		logger.info("Sending request " + requestJson);
		try(Response response = httpClient.newCall(request).execute()) {
			String responseBody = Objects.requireNonNull(response.body()).string();
			logger.info("Response from subscription-checker: " + responseBody);
			return jsonSerializer.fromJson(responseBody, Subscription.class);
		} catch(IOException ex) {
			logger.log(Level.SEVERE, "An error occurred while sending request to subscription server", ex);
			return new Subscription();
		}
	}

	private Request createRequest(String json) {
		RequestBody body = RequestBody.create(
				MediaType.parse("application/json; charset=utf-8"),
				json);
		return new Request.Builder()
				.url("http://localhost:8080/checkStorage")
				.put(body)
				.build();
	}
}
