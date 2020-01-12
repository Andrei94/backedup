package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import authentication.User;
import com.google.gson.Gson;
import file.LocalFile;
import okhttp3.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class S3ObjectUploader implements ObjectUploader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final S3Adapter adapter;
	private final LocalFileWalker walker;
	private User user;

	public S3ObjectUploader(S3Adapter adapter, LocalFileWalker walker) {
		this.adapter = adapter;
		this.walker = walker;
	}

	@Override
	public boolean uploadDirectory(Path path) {
		return uploadDirectory(LocalFile.fromPath(path));
	}

	@Override
	public void setLoggedInUser(User user) {
		this.user = user;
		user.getCredentials().ifPresent(adapter::updateCredentials);
	}

	boolean uploadDirectory(LocalFile directory) {
		try {
			List<LocalFile> filesFromDirectory = walker.walkTreeFromRoot(directory)
					.filter(LocalFile::isFile)
					.collect(Collectors.toList());
			long dirSize = filesFromDirectory.parallelStream().map(LocalFile::getSize).reduce(0L, Long::sum);
			String json = "{" +
					"\"usedSize\": " + dirSize + "," +
					"\"user\": " + "\"" + user.getName() + "\"" +
					"}";

			Subscription subscription = getUserSubscription(json);
			return filesFromDirectory
					.parallelStream()
					.allMatch(localFile -> uploadFileFrom(directory, localFile, subscription));
		} catch(IOException ex) {
			logger.log(Level.SEVERE, "An error occurred while uploading " + directory.getPath(), ex);
			return false;
		}
	}

	private Subscription getUserSubscription(String requestJson) {
		Request request = createRequest(requestJson);
		logger.info("Sending request " + requestJson);
		try(Response response = new OkHttpClient().newCall(request).execute()) {
			String responseBody = Objects.requireNonNull(response.body()).string();
			logger.info("Response from subscription-checker: " + responseBody);
			return new Gson().fromJson(responseBody, Subscription.class);
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

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile, Subscription subscription) {
		if(user == null || !user.isAuthenticated() || subscription.getFreeSize() == 0)
			return false;
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket(subscription.getBucketName())
				.withRemoteFile(subscription.getUserPath() + adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass(subscription.getStorageClass());
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}
}
