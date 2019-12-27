package authentication;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.model.*;

public class DummyAWSSecurityTokenService implements AWSSecurityTokenService {
	@Override
	@Deprecated
	public void setEndpoint(String endpoint) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void setRegion(Region region) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssumeRoleResult assumeRole(AssumeRoleRequest assumeRoleRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssumeRoleWithSAMLResult assumeRoleWithSAML(AssumeRoleWithSAMLRequest assumeRoleWithSAMLRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssumeRoleWithWebIdentityResult assumeRoleWithWebIdentity(AssumeRoleWithWebIdentityRequest assumeRoleWithWebIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DecodeAuthorizationMessageResult decodeAuthorizationMessage(DecodeAuthorizationMessageRequest decodeAuthorizationMessageRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetAccessKeyInfoResult getAccessKeyInfo(GetAccessKeyInfoRequest getAccessKeyInfoRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetCallerIdentityResult getCallerIdentity(GetCallerIdentityRequest getCallerIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetFederationTokenResult getFederationToken(GetFederationTokenRequest getFederationTokenRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetSessionTokenResult getSessionToken(GetSessionTokenRequest getSessionTokenRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetSessionTokenResult getSessionToken() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shutdown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
		throw new UnsupportedOperationException();
	}
}
