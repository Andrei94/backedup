package authentication;

import java.util.Date;

public class UserCredentials {
	private String accessKeyId;
	private String secretAccessKey;
	private String sessionToken;
	private Date expiration;

	public UserCredentials(String accessKeyId, String secretAccessKey, String sessionToken, java.util.Date expiration) {
		setAccessKeyId(accessKeyId);
		setSecretAccessKey(secretAccessKey);
		setSessionToken(sessionToken);
		setExpiration(expiration);
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeyId() {
		return this.accessKeyId;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	public String getSecretAccessKey() {
		return this.secretAccessKey;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getSessionToken() {
		return this.sessionToken;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Date getExpiration() {
		return this.expiration;
	}
}
