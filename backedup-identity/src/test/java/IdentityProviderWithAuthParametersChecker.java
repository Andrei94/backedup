import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;

import java.util.Map;

public class IdentityProviderWithAuthParametersChecker extends DummyAWSCognitoIdentityProvider {
	@Override
	public InitiateAuthResult initiateAuth(InitiateAuthRequest initiateAuthRequest) {
		Map<String, String> authParameters = initiateAuthRequest.getAuthParameters();
		if(matches(authParameters.get("USERNAME"), "username") &&
				matches(authParameters.get("PASSWORD"), "Password123!"))
			return new InitiateAuthResult()
					.withAuthenticationResult(new AuthenticationResultType()
							.withIdToken("eyJraWQiOiJNdVJHazljaWRMbFZiaTU4VjhiWWtZUjdCWGsrMVNzY1V0UGVJNHpMQUhZPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJiZTc0OTNlMi0zZGE2LTQx" +
									"MDktOWNlYS1kODkxYzgzYTQyYTAiLCJhdWQiOiIxZjRmZmd0cmlybDRlM2owYWxlNjM1OXRsNCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJldmVudF9pZCI6IjQwY" +
									"jcyZGMxLWYxZWQtNDA1YS1iMzg2LTU4MTVhOTA0ZDA2ZSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTc3MzcwMzc5LCJpc3MiOiJodHRwczpcL1wvY2" +
									"9nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9aNDBVSlZLYkkiLCJjb2duaXRvOnVzZXJuYW1lIjoidXNlcm5hbWUyIiwiZXhwIjo" +
									"xNTc3MzczOTc5LCJpYXQiOjE1NzczNzAzNzksImVtYWlsIjoiZ2lnZWtlcDk2MUBtYWlsaHViMjQuY29tIn0.hb62IG4H-RvGcinaSO2cl-b3oeq42KRICTRhhC3G" +
									"VbdTv7FafQYfGS7_jYc7fyBv7nyTNWTNpOt6HvdilHmhvvpFjbr7rVPnrOJuQ4jCCOaE08H-6OKgT1lJyFNXWAwbZ0Ix91GsiimJ09ZRc0iMs5onHu_vEDGPSf67L" +
									"DltOTow48vyS9QzROXegcT1OVnZTKpv0pbLIVJc8f-wB3WZ7vYJKrr8_2_NujDqzOzDb0ql8mlJ3ADdeuG5i7-TeoqslP8OSQIrJs41NqQCD4LH6-OqHkpJYaGzvb" +
									"e5ICjE-DsPCsr415Xu15J3NZJsK8Aaxnc2iXAT66L1H8osDNpedQ"));
		throw new AWSCognitoIdentityProviderException("Unauthorised");
	}

	private boolean matches(String authParam, String expectedValue) {
		return authParam.equals(expectedValue);
	}
}
