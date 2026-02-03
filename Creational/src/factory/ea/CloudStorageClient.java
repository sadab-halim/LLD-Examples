package factory.ea;

// 1. The Interface
public interface CloudStorageClient {
    String upload(String bucket, String key, byte[] data);
    byte[] download(String bucket, String key);
}
