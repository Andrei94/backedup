package drive.adapters;

import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClient {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private final OkHttpClient httpClient;

	public HttpClient(OkHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String makePutRequest(String url, String body) {
		logger.info("Sending request " + body);
		try(Response response = httpClient.newCall(createPutRequest(url, body)).execute()) {
			String responseBody = Objects.requireNonNull(response.body()).string();
			logger.info("Response from subscription-plan for request " + body + ": " + responseBody);
			return responseBody;
		} catch(IOException ex) {
			logger.log(Level.SEVERE, "An error occurred while sending request to subscription server", ex);
			return "";
		}
	}

	private Request createPutRequest(String url, String json) {
		return new Request.Builder()
				.url(url)
				.put(RequestBody.create(json, MediaType.parse("application/json; charset=utf-8")))
				.build();
	}

	public String makeGetRequest(String url) {
		logger.info("Sending request to " + url);
		try(Response response = httpClient.newCall(createGetRequest(url)).execute()) {
			String responseBody = Objects.requireNonNull(response.body()).string();
			logger.info("Response from subscription-plan for request " + url + ": " + responseBody);
			return responseBody;
		} catch(IOException ex) {
			logger.log(Level.SEVERE, "An error occurred while sending request to subscription server", ex);
			return "";
		}
	}

	private Request createGetRequest(String url) {
		return new Request.Builder().url(url).get().build();
	}
}
