import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;

import java.util.Map;

public class IdentityProviderWithAuthParametersChecker extends DummyAWSCognitoIdentityProvider {
	@Override
	public InitiateAuthResult initiateAuth(InitiateAuthRequest initiateAuthRequest) {
		Map<String, String> authParameters = initiateAuthRequest.getAuthParameters();
		if(matches(authParameters.get("USERNAME"), "username") &&
				matches(authParameters.get("PASSWORD"), "Password123!"))
			return new InitiateAuthResult();
		throw new AWSCognitoIdentityProviderException("Unauthorised");
	}

	private boolean matches(String authParam, String expectedValue) {
		return authParam.equals(expectedValue);
	}
}
