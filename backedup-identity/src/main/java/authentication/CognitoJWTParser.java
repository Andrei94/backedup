package authentication;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Base64;

public class CognitoJWTParser {
	private static final int PAYLOAD = 1;
	private static final int JWT_PARTS = 3;

	public static JSONObject getPayload(String jwt) {
		try {
			validateJWT(jwt);
			final String payload = jwt.split("\\.")[PAYLOAD];
			final String jwtSection = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8);
			return new JSONObject(jwtSection);
		} catch (final Exception e) {
			throw new InvalidParameterException("error in parsing JSON");
		}
	}

	static void validateJWT(String jwt) {
		final String[] jwtParts = jwt.split("\\.");
		if (jwtParts.length != JWT_PARTS) {
			throw new InvalidParameterException("not a JSON Web Token");
		}
	}
}