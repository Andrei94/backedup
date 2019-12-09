package authentication;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;

import java.util.HashMap;
import java.util.Map;

public class CognitoAuthenticator implements Authenticator {
	private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

	public CognitoAuthenticator() {
		this(AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new ProfileCredentialsProvider("s3manager"))
				.withRegion(Regions.US_EAST_1)
				.build());
	}

	public CognitoAuthenticator(AWSCognitoIdentityProvider awsCognitoIdentityProvider) {
		this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
	}

	@Override
	public boolean authenticate(String username, String password) {
		try {
			awsCognitoIdentityProvider.initiateAuth(
					new InitiateAuthRequest().withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
							.withClientId("1f4ffgtrirl4e3j0ale6359tl4")
							.withAuthParameters(constructCredentialsMap(username, password))
			);
		} catch(AWSCognitoIdentityProviderException ex) {
			return false;
		}
		return true;
	}

	private Map<String, String> constructCredentialsMap(String username, String password) {
		return new HashMap<String, String>() {
			{
				put("USERNAME", username);
				put("PASSWORD", password);
			}
		};
	}
}
