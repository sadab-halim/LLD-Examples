package factory.ea;

// 4. The Factory
public class StorageClientFactory {
    // In Spring/Guice, this would be a @Bean or @Inject
    public static CloudStorageClient createClient(StorageConfig config) {
        if (config == null) throw new IllegalArgumentException("Config cannot be null");

        switch (config.provider) {
            case AWS:
                if (config.region == null || config.apiKey == null) {
                    throw new IllegalArgumentException("AWS requires region and apiKey");
                }
                return new S3StorageClient(config.region, config.apiKey);

            case GCP:
                if (config.authFilePath == null) {
                    throw new IllegalArgumentException("GCP requires authFilePath");
                }
                return new GCSStorageClient(config.authFilePath);

            case AZURE:
                throw new UnsupportedOperationException("Azure not yet implemented");

            default:
                throw new IllegalArgumentException("Unknown provider: " + config.provider);
        }
    }
}
