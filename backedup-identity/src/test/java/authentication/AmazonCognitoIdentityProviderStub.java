package authentication;

import com.amazonaws.services.cognitoidentity.model.*;

public class AmazonCognitoIdentityProviderStub extends DummyAmazonCognitoIdentity {
	@Override
	public GetIdResult getId(GetIdRequest getIdRequest) {
		if(getIdRequest.getLogins().isEmpty() || getIdRequest.getIdentityPoolId() == null || getIdRequest.getIdentityPoolId().isEmpty())
			throw new AmazonCognitoIdentityException("Cannot obtain id");
		return new GetIdResult().withIdentityId("123");
	}

	@Override
	public GetOpenIdTokenResult getOpenIdToken(GetOpenIdTokenRequest getOpenIdTokenRequest) {
		if(getOpenIdTokenRequest.getLogins().isEmpty() || getOpenIdTokenRequest.getIdentityId() == null || getOpenIdTokenRequest.getIdentityId().isEmpty())
			throw new AmazonCognitoIdentityException("Cannot obtain OpenId");
		return new GetOpenIdTokenResult().withIdentityId("123").withToken("openIdToken");
	}
}
