package adapter;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.*;

class ClientWithListObjectsThrowingException extends ClientWithDownloadOfOneFile {
	@Override
	public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws SdkClientException {
		throw new SdkClientException("Exception");
	}
}

class ClientWithGetObjectMetadataThrowingException extends ClientWithDownloadOfOneFile {

	@Override
	public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest) throws SdkClientException {
		throw new SdkClientException("Exception");
	}
}

class ClientWithGetObjectThrowingException extends ClientWithDownloadOfOneFile {
	@Override
	public S3Object getObject(GetObjectRequest getObjectRequest) throws SdkClientException {
		throw new SdkClientException("Exception");
	}
}