import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringInputStream;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class ClientWithDownloadOfOneFile extends DummyS3Client {
	@Override
	public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws SdkClientException {
		ObjectListing objectListing = new ObjectListing();
		S3ObjectSummary e = new S3ObjectSummary();
		e.setBucketName("backedup-storage");
		e.setKey("testFolder/file1");
		e.setStorageClass("STANDARD");
		objectListing.getObjectSummaries().add(e);
		return objectListing;
	}
	@Override
	public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest) throws SdkClientException {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setLastModified(new Date());
		return objectMetadata;
	}

	@Override
	public S3Object getObject(GetObjectRequest getObjectRequest) throws SdkClientException {
		try {
			S3Object s3Object = new S3Object();
			s3Object.setObjectContent(new StringInputStream(""));
			return s3Object;
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
