package factory.ea;

public class GCSStorageClient implements CloudStorageClient {
    private final String jsonKeyPath;

    public GCSStorageClient(String jsonKeyPath) {
        this.jsonKeyPath = jsonKeyPath;
        System.out.println("Initializing GCS Client via JSON auth");
    }

    @Override
    public String upload(String bucket, String key, byte[] data) {
        return "gs://" + bucket + "/" + key;
    }

    @Override
    public byte[] download(String bucket, String key) { return new byte[0]; }
}
