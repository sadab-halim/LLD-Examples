package factory.ea;

public class Main {
    public static void main(String[] args) {
        StorageConfig config = new StorageConfig();
        config.provider = ProviderType.AWS;
        config.region = "us-east-1";
        config.apiKey = "secret_key";

        CloudStorageClient client = StorageClientFactory.createClient(config);
        client.upload("my-bucket", "file.txt", new byte[0]);
    }
}
