package authentication;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.*;

public class DummyAmazonCognitoIdentity implements AmazonCognitoIdentity {
	@Override
	public void setEndpoint(String endpoint) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRegion(Region region) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CreateIdentityPoolResult createIdentityPool(CreateIdentityPoolRequest createIdentityPoolRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteIdentitiesResult deleteIdentities(DeleteIdentitiesRequest deleteIdentitiesRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteIdentityPoolResult deleteIdentityPool(DeleteIdentityPoolRequest deleteIdentityPoolRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DescribeIdentityResult describeIdentity(DescribeIdentityRequest describeIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DescribeIdentityPoolResult describeIdentityPool(DescribeIdentityPoolRequest describeIdentityPoolRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetCredentialsForIdentityResult getCredentialsForIdentity(GetCredentialsForIdentityRequest getCredentialsForIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetIdResult getId(GetIdRequest getIdRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetIdentityPoolRolesResult getIdentityPoolRoles(GetIdentityPoolRolesRequest getIdentityPoolRolesRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetOpenIdTokenResult getOpenIdToken(GetOpenIdTokenRequest getOpenIdTokenRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetOpenIdTokenForDeveloperIdentityResult getOpenIdTokenForDeveloperIdentity(GetOpenIdTokenForDeveloperIdentityRequest getOpenIdTokenForDeveloperIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIdentitiesResult listIdentities(ListIdentitiesRequest listIdentitiesRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIdentityPoolsResult listIdentityPools(ListIdentityPoolsRequest listIdentityPoolsRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListTagsForResourceResult listTagsForResource(ListTagsForResourceRequest listTagsForResourceRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupDeveloperIdentityResult lookupDeveloperIdentity(LookupDeveloperIdentityRequest lookupDeveloperIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MergeDeveloperIdentitiesResult mergeDeveloperIdentities(MergeDeveloperIdentitiesRequest mergeDeveloperIdentitiesRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetIdentityPoolRolesResult setIdentityPoolRoles(SetIdentityPoolRolesRequest setIdentityPoolRolesRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TagResourceResult tagResource(TagResourceRequest tagResourceRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UnlinkDeveloperIdentityResult unlinkDeveloperIdentity(UnlinkDeveloperIdentityRequest unlinkDeveloperIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UnlinkIdentityResult unlinkIdentity(UnlinkIdentityRequest unlinkIdentityRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UpdateIdentityPoolResult updateIdentityPool(UpdateIdentityPoolRequest updateIdentityPoolRequest) {
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
