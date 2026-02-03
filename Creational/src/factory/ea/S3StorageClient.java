package factory.ea;

// 2. Concrete Implementations
public class S3StorageClient implements CloudStorageClient{
    private final String region;
    private final String accessKey;
    // Assume AWS SDK client is a field here

    public S3StorageClient(String region, String accessKey) {
        this.region = region;
        this.accessKey = accessKey;
        // logic to init AWS SDK
        System.out.println("Initializing AWS S3 Client for region: " + region);
    }

    @Override
    public String upload(String bucket, String key, byte[] data) {
        return "S3://" + bucket + "/" + key;
    }

    @Override
    public byte[] download(String bucket, String key) { return new byte[0]; }
}
