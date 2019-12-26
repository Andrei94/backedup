import authentication.CognitoJWTParser;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.auth.policy.conditions.S3ConditionFactory;
import com.amazonaws.auth.policy.conditions.StringCondition;
import com.amazonaws.auth.policy.resources.S3BucketResource;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class CognitoLearningTest {
	private AmazonCognitoIdentity cognitoIdentity;

	@BeforeEach
	void setUp() {
		cognitoIdentity = AmazonCognitoIdentityClientBuilder.standard()
				.withCredentials(createAnonymousCredentials())
				.withRegion(Regions.US_EAST_1)
				.build();
	}

	@Test
	@Disabled
	void assumeRoleWebIdentity() {
		AuthenticationResultType authenticationResult = authenticate("username2", "Password123!");
		String openIdToken = getOpenIdTokenFromAuthenticatedUser(authenticationResult);
		Credentials session_username2 = assumeRoleWebIdentityWithPolicy(openIdToken);

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(session_username2.getAccessKeyId(), session_username2.getSecretAccessKey(), session_username2.getSessionToken())))
				.withRegion(Regions.EU_CENTRAL_1)
				.build();
		ListObjectsV2Result listObjectsV2Result = s3Client.listObjectsV2("backedup-storage-2", "username2/");
		for(S3ObjectSummary objectSummary : listObjectsV2Result.getObjectSummaries()) {
			System.out.println(objectSummary.getKey());
		}
	}

	private AuthenticationResultType authenticate(String username, String password) {
		return AWSCognitoIdentityProviderClientBuilder
				.standard()
				.withCredentials(createAnonymousCredentials())
				.withRegion(Regions.US_EAST_1)
				.build().initiateAuth(
						new InitiateAuthRequest()
								.withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
								.withClientId("1f4ffgtrirl4e3j0ale6359tl4")
								.withAuthParameters(new HashMap<String, String>() {{
									put("USERNAME", username);
									put("PASSWORD", password);
								}})
				).getAuthenticationResult();
	}

	private String getOpenIdTokenFromAuthenticatedUser(AuthenticationResultType idToken) {
		JSONObject payload = CognitoJWTParser.getPayload(idToken.getIdToken());
		GetIdRequest idRequest = new GetIdRequest();
		idRequest.setIdentityPoolId("us-east-1:716c0519-8401-480f-acbc-b54fa2d416f7");
		idRequest.addLoginsEntry(payload.get("iss").toString().replace("https://", ""), idToken.getIdToken());
		return cognitoIdentity.getOpenIdToken(new GetOpenIdTokenRequest()
				.withIdentityId(cognitoIdentity.getId(idRequest).getIdentityId())
				.withLogins(idRequest.getLogins())).getToken();
	}

	private Credentials assumeRoleWebIdentityWithPolicy(String openIdToken) {
		Policy allowAccessToUsersFolderInS3Policy = new Policy().withStatements(
				new Statement(Statement.Effect.Allow)
						.withActions(S3Actions.ListObjects)
						.withResources(new S3BucketResource("backedup-storage-2"))
						.withConditions(new StringCondition(StringCondition.StringComparisonType.StringLike, S3ConditionFactory.PREFIX_CONDITION_KEY, "username2/"))
		);
		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
				.withCredentials(createAnonymousCredentials()).withRegion(Regions.US_EAST_1).build();
		return stsClient.assumeRoleWithWebIdentity(new AssumeRoleWithWebIdentityRequest()
				.withWebIdentityToken(openIdToken)
				.withDurationSeconds(12 * 3600)
				.withRoleSessionName("session_username2")
				.withRoleArn("arn:aws:iam::816033825058:role/CognitoAfterAuth")
				.withPolicy(allowAccessToUsersFolderInS3Policy.toJson())
		).getCredentials();
	}

	private AWSStaticCredentialsProvider createAnonymousCredentials() {
		return new AWSStaticCredentialsProvider(new AnonymousAWSCredentials());
	}
}
