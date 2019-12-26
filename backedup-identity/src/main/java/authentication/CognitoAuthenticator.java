package authentication;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.auth.policy.conditions.S3ConditionFactory;
import com.amazonaws.auth.policy.conditions.StringCondition;
import com.amazonaws.auth.policy.resources.S3BucketResource;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidentity.model.AmazonCognitoIdentityException;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AWSSecurityTokenServiceException;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CognitoAuthenticator implements Authenticator {
	private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
	private AmazonCognitoIdentity cognitoIdentity;
	private final AWSSecurityTokenService stsClient;

	public CognitoAuthenticator() {
		this(AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
				.withRegion(Regions.US_EAST_1)
				.build(), AmazonCognitoIdentityClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
				.withRegion(Regions.US_EAST_1)
				.build(), AWSSecurityTokenServiceClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
				.withRegion(Regions.US_EAST_1)
				.build()
		);
	}

	CognitoAuthenticator(AWSCognitoIdentityProvider awsCognitoIdentityProvider, AmazonCognitoIdentity cognitoIdentity, AWSSecurityTokenService stsClient) {
		this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
		this.cognitoIdentity = cognitoIdentity;
		this.stsClient = stsClient;
	}

	@Override
	public User authenticate(String username, String password) {
		try {
			AuthenticationResultType authenticationResult = performAuthentication(username, password);
			String openIdToken = getOpenIdTokenFromAuthenticatedUser(authenticationResult);
			return new AuthenticatedUser(username, assumeRoleWebIdentityWithPolicy(openIdToken, username));
		} catch(AWSCognitoIdentityProviderException | AWSSecurityTokenServiceException | AmazonCognitoIdentityException ex) {
			return new UnauthenticatedUser(username);
		}
	}

	private AuthenticationResultType performAuthentication(String username, String password) {
		return awsCognitoIdentityProvider.initiateAuth(
				new InitiateAuthRequest()
						.withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
						.withClientId("1f4ffgtrirl4e3j0ale6359tl4")
						.withAuthParameters(constructCredentialsMap(username, password))
		).getAuthenticationResult();
	}

	private String getOpenIdTokenFromAuthenticatedUser(AuthenticationResultType authenticationToken) {
		GetIdRequest idRequest = new GetIdRequest();
		idRequest.setIdentityPoolId("us-east-1:716c0519-8401-480f-acbc-b54fa2d416f7");
		idRequest.addLoginsEntry(getProviderFromIdToken(authenticationToken.getIdToken()), authenticationToken.getIdToken());
		return cognitoIdentity.getOpenIdToken(new GetOpenIdTokenRequest()
				.withIdentityId(cognitoIdentity.getId(idRequest).getIdentityId())
				.withLogins(idRequest.getLogins())).getToken();
	}

	private String getProviderFromIdToken(String idToken) {
		JSONObject payload = CognitoJWTParser.getPayload(idToken);
		return payload.get("iss").toString().replace("https://", "");
	}

	private UserCredentials assumeRoleWebIdentityWithPolicy(String openIdToken, String username) {
		Policy allowAccessToUsersFolderInS3Policy = new Policy().withStatements(
				new Statement(Statement.Effect.Allow)
						.withActions(S3Actions.ListObjects)
						.withResources(new S3BucketResource("backedup-storage-2"))
						.withConditions(new StringCondition(StringCondition.StringComparisonType.StringLike, S3ConditionFactory.PREFIX_CONDITION_KEY, username + "/"))
		);
		Credentials credentials = stsClient.assumeRoleWithWebIdentity(new AssumeRoleWithWebIdentityRequest()
				.withWebIdentityToken(openIdToken)
				.withDurationSeconds(12 * 3600)
				.withRoleSessionName("session_" + username)
				.withRoleArn("arn:aws:iam::816033825058:role/CognitoAfterAuth")
				.withPolicy(allowAccessToUsersFolderInS3Policy.toJson())
		).getCredentials();
		return new UserCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken(), credentials.getExpiration());
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