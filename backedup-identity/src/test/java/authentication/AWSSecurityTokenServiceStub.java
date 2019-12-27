package authentication;

import com.amazonaws.services.securitytoken.model.AWSSecurityTokenServiceException;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityResult;
import com.amazonaws.services.securitytoken.model.Credentials;

import java.util.Date;

public class AWSSecurityTokenServiceStub extends DummyAWSSecurityTokenService {
	@Override
	public AssumeRoleWithWebIdentityResult assumeRoleWithWebIdentity(AssumeRoleWithWebIdentityRequest assumeRoleWithWebIdentityRequest) {
		if(assumeRoleWithWebIdentityRequest.getPolicy() == null)
			throw new AWSSecurityTokenServiceException("Policy must be specified");
		return new AssumeRoleWithWebIdentityResult()
				.withCredentials(new Credentials()
						.withAccessKeyId("accessKeyId")
						.withSecretAccessKey("secretKey")
						.withSessionToken("sessionToken")
						.withExpiration(getExpiration(assumeRoleWithWebIdentityRequest.getDurationSeconds())));
	}

	private Date getExpiration(long durationInSeconds) {
		return new Date(new Date().getTime() + durationInSeconds * 1000);
	}
}
