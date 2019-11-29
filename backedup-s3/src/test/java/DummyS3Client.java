import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.analytics.AnalyticsConfiguration;
import com.amazonaws.services.s3.model.inventory.InventoryConfiguration;
import com.amazonaws.services.s3.model.metrics.MetricsConfiguration;
import com.amazonaws.services.s3.waiters.AmazonS3Waiters;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class DummyS3Client implements AmazonS3 {

	@Override
	public void setEndpoint(String endpoint) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRegion(Region region) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setS3ClientOptions(S3ClientOptions clientOptions) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void changeObjectStorageClass(String bucketName, String key, StorageClass newStorageClass) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void setObjectRedirectLocation(String bucketName, String key, String newRedirectLocation) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectListing listObjects(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectListing listObjects(String bucketName, String prefix) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListObjectsV2Result listObjectsV2(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListObjectsV2Result listObjectsV2(String bucketName, String prefix) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VersionListing listVersions(String bucketName, String prefix) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VersionListing listNextBatchOfVersions(ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VersionListing listVersions(String bucketName, String prefix, String keyMarker, String versionIdMarker, String delimiter, Integer maxResults) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VersionListing listVersions(ListVersionsRequest listVersionsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Owner getS3AccountOwner() throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Owner getS3AccountOwner(GetS3AccountOwnerRequest getS3AccountOwnerRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public boolean doesBucketExist(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean doesBucketExistV2(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Bucket> listBuckets() throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getBucketLocation(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bucket createBucket(CreateBucketRequest createBucketRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bucket createBucket(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public Bucket createBucket(String bucketName, com.amazonaws.services.s3.model.Region region) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public Bucket createBucket(String bucketName, String region) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AccessControlList getObjectAcl(String bucketName, String key) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AccessControlList getObjectAcl(String bucketName, String key, String versionId) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AccessControlList getObjectAcl(GetObjectAclRequest getObjectAclRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectAcl(String bucketName, String key, AccessControlList acl) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectAcl(SetObjectAclRequest setObjectAclRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AccessControlList getBucketAcl(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketAcl(SetBucketAclRequest setBucketAclRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketAcl(String bucketName, AccessControlList acl) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketAcl(String bucketName, CannedAccessControlList acl) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectMetadata getObjectMetadata(String bucketName, String key) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public S3Object getObject(String bucketName, String key) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public S3Object getObject(GetObjectRequest getObjectRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File destinationFile) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getObjectAsString(String bucketName, String key) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest getObjectTaggingRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetObjectTaggingResult setObjectTagging(SetObjectTaggingRequest setObjectTaggingRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest deleteObjectTaggingRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucket(DeleteBucketRequest deleteBucketRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucket(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PutObjectResult putObject(String bucketName, String key, File file) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PutObjectResult putObject(String bucketName, String key, String content) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CopyPartResult copyPart(CopyPartRequest copyPartRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteObject(String bucketName, String key) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteObject(DeleteObjectRequest deleteObjectRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteVersion(String bucketName, String key, String versionId) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteVersion(DeleteVersionRequest deleteVersionRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketLoggingConfiguration getBucketLoggingConfiguration(GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketVersioningConfiguration getBucketVersioningConfiguration(GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketVersioningConfiguration(SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketLifecycleConfiguration getBucketLifecycleConfiguration(GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketLifecycleConfiguration(String bucketName, BucketLifecycleConfiguration bucketLifecycleConfiguration) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketLifecycleConfiguration(SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketLifecycleConfiguration(String bucketName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketLifecycleConfiguration(DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketCrossOriginConfiguration(String bucketName, BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketCrossOriginConfiguration(SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketCrossOriginConfiguration(String bucketName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketCrossOriginConfiguration(DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketTaggingConfiguration getBucketTaggingConfiguration(GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketTaggingConfiguration(String bucketName, BucketTaggingConfiguration bucketTaggingConfiguration) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketTaggingConfiguration(SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketTaggingConfiguration(String bucketName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketTaggingConfiguration(DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketNotificationConfiguration getBucketNotificationConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketNotificationConfiguration getBucketNotificationConfiguration(GetBucketNotificationConfigurationRequest getBucketNotificationConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketNotificationConfiguration(SetBucketNotificationConfigurationRequest setBucketNotificationConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketNotificationConfiguration(String bucketName, BucketNotificationConfiguration bucketNotificationConfiguration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketWebsiteConfiguration getBucketWebsiteConfiguration(GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketWebsiteConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketWebsiteConfiguration(DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketPolicy getBucketPolicy(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketPolicy(String bucketName, String policyText) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketPolicy(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public UploadPartResult uploadPart(UploadPartRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PartListing listParts(ListPartsRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void abortMultipartUpload(AbortMultipartUploadRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public S3ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void restoreObject(RestoreObjectRequest request) throws AmazonServiceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RestoreObjectResult restoreObjectV2(RestoreObjectRequest request) throws AmazonServiceException {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void enableRequesterPays(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void disableRequesterPays(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRequesterPaysEnabled(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketReplicationConfiguration(String bucketName, BucketReplicationConfiguration configuration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketReplicationConfiguration(SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketReplicationConfiguration getBucketReplicationConfiguration(GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketReplicationConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean doesObjectExist(String bucketName, String objectName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketAccelerateConfiguration getBucketAccelerateConfiguration(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BucketAccelerateConfiguration getBucketAccelerateConfiguration(GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketAccelerateConfiguration(String bucketName, BucketAccelerateConfiguration accelerateConfiguration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBucketAccelerateConfiguration(SetBucketAccelerateConfigurationRequest setBucketAccelerateConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(String bucketName, String id) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(DeleteBucketMetricsConfigurationRequest deleteBucketMetricsConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(String bucketName, String id) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(GetBucketMetricsConfigurationRequest getBucketMetricsConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(String bucketName, MetricsConfiguration metricsConfiguration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(SetBucketMetricsConfigurationRequest setBucketMetricsConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListBucketMetricsConfigurationsResult listBucketMetricsConfigurations(ListBucketMetricsConfigurationsRequest listBucketMetricsConfigurationsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(String bucketName, String id) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(DeleteBucketAnalyticsConfigurationRequest deleteBucketAnalyticsConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(String bucketName, String id) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(GetBucketAnalyticsConfigurationRequest getBucketAnalyticsConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(String bucketName, AnalyticsConfiguration analyticsConfiguration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(SetBucketAnalyticsConfigurationRequest setBucketAnalyticsConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListBucketAnalyticsConfigurationsResult listBucketAnalyticsConfigurations(ListBucketAnalyticsConfigurationsRequest listBucketAnalyticsConfigurationsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(String bucketName, String id) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(String bucketName, String id) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(String bucketName, InventoryConfiguration inventoryConfiguration) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketEncryptionResult deleteBucketEncryption(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteBucketEncryptionResult deleteBucketEncryption(DeleteBucketEncryptionRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketEncryptionResult getBucketEncryption(String bucketName) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketEncryptionResult getBucketEncryption(GetBucketEncryptionRequest request) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetBucketEncryptionResult setBucketEncryption(SetBucketEncryptionRequest setBucketEncryptionRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetPublicAccessBlockResult setPublicAccessBlock(SetPublicAccessBlockRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetPublicAccessBlockResult getPublicAccessBlock(GetPublicAccessBlockRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeletePublicAccessBlockResult deletePublicAccessBlock(DeletePublicAccessBlockRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetBucketPolicyStatusResult getBucketPolicyStatus(GetBucketPolicyStatusRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SelectObjectContentResult selectObjectContent(SelectObjectContentRequest selectRequest) throws SdkClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetObjectLegalHoldResult setObjectLegalHold(SetObjectLegalHoldRequest setObjectLegalHoldRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetObjectLegalHoldResult getObjectLegalHold(GetObjectLegalHoldRequest getObjectLegalHoldRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetObjectLockConfigurationResult setObjectLockConfiguration(SetObjectLockConfigurationRequest setObjectLockConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetObjectLockConfigurationResult getObjectLockConfiguration(GetObjectLockConfigurationRequest getObjectLockConfigurationRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SetObjectRetentionResult setObjectRetention(SetObjectRetentionRequest setObjectRetentionRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GetObjectRetentionResult getObjectRetention(GetObjectRetentionRequest getObjectRetentionRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PresignedUrlDownloadResult download(PresignedUrlDownloadRequest presignedUrlDownloadRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void download(PresignedUrlDownloadRequest presignedUrlDownloadRequest, File destinationFile) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PresignedUrlUploadResult upload(PresignedUrlUploadRequest presignedUrlUploadRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shutdown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public com.amazonaws.services.s3.model.Region getRegion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRegionName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getUrl(String bucketName, String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AmazonS3Waiters waiters() {
		throw new UnsupportedOperationException();
	}
}
