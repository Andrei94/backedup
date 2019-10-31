import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;

public class S3Adapter {
	private final AmazonS3 client = AmazonS3ClientBuilder
			.standard()
			.withCredentials(new ProfileCredentialsProvider("backedup-storage"))
			.withRegion(Regions.US_EAST_1)
			.build();

	public boolean putObject(PutObjectRequest request) {
		try {
			PutObjectResult result = client.putObject(request);
			return result.getETag().equals(result.getContentMd5());
		} catch(SdkClientException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public String toFileInRemoteFolder(String folderName, File localFile) {
		return makeS3FolderFromName(folderName) + localFile.getName();
	}

	private String makeS3FolderFromName(String folderName) {
		return folderName + "/";
	}
}
