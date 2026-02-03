package factory.ec;

// 2. Concrete Implementations
public class ProtobufSerializer implements EventSerializer {
    // Heavy initialization (loading schema descriptors)
    public ProtobufSerializer() {
        try { Thread.sleep(200); } catch (Exception e) {} // Simulate latency
        System.out.println("Protobuf Schema Loaded");
    }

    @Override
    public byte[] serialize(Object event) { return new byte[0]; } // Mock
    @Override
    public String getFormatName() { return "PROTOBUF"; }
}
