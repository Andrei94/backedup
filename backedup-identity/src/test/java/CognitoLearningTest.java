import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetFederationTokenRequest;
import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CognitoLearningTest {
	private final String clientId = "1f4ffgtrirl4e3j0ale6359tl4";

	@Test
	@Disabled
	void signUp() {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new ProfileCredentialsProvider("s3manager"))
				.withRegion(Regions.US_EAST_1)
				.build();
//		awsCognitoIdentityProvider.signUp(
//				new SignUpRequest()
//						.withClientId(clientId)
//						.withUsername("username2")
//						.withPassword("Password123!")
//						.withUserAttributes(
//								new AttributeType().withName("email").withValue("gigekep961@mailhub24.com")
//						)
//		);
		awsCognitoIdentityProvider.confirmSignUp(
				new ConfirmSignUpRequest().withUsername("username2")
						.withClientId(clientId)
						.withConfirmationCode("749731")
		);
	}

	@Test
	void userHasAccessToHisFolder() {
		String username = "username";
		if(authenticate(username, "Password123!")) {
			Credentials sessionCredentials = getFederationUserCredentials(username);
			AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(mapToAWSCredentials(sessionCredentials));
			listOwnBucket(credentialsProvider);
		}
	}

	private Credentials getFederationUserCredentials(String username) {
		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("s3manager")).withRegion(Regions.US_EAST_1).build();
		return stsClient.getFederationToken(new GetFederationTokenRequest().withName(username)
				.withDurationSeconds(7200)
				.withPolicyArns(new PolicyDescriptorType().withArn("arn:aws:iam::816033825058:policy/AllowUserIdToAccessHisFolder")))
				.getCredentials();
	}

	private BasicSessionCredentials mapToAWSCredentials(Credentials sessionCredentials) {
		return new BasicSessionCredentials(
				sessionCredentials.getAccessKeyId(),
				sessionCredentials.getSecretAccessKey(),
				sessionCredentials.getSessionToken()
		);
	}

	private void listOwnBucket(AWSStaticCredentialsProvider credentialsProvider) {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion(Regions.US_EAST_1)
				.build();
		ListObjectsV2Result listObjectsV2Result = s3Client.listObjectsV2("cognito-test-bucket2", "816033825058:username/");
		for(S3ObjectSummary objectSummary : listObjectsV2Result.getObjectSummaries()) {
			System.out.println(objectSummary.getKey());
		}
	}

	boolean authenticate(String username, String password) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new ProfileCredentialsProvider("s3manager"))
				.withRegion(Regions.US_EAST_1)
				.build();
		Map<String, String> credentials = new HashMap<>();
		credentials.put("USERNAME", username);
		credentials.put("PASSWORD", password);
		AuthenticationResultType authenticationResult = awsCognitoIdentityProvider.initiateAuth(
				new InitiateAuthRequest().withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH).withClientId(clientId).withAuthParameters(credentials)
		).getAuthenticationResult();
		return !authenticationResult.getAccessToken().isEmpty();
	}
}
