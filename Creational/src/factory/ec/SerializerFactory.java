package factory.ec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializerFactory {
    // Cache to avoid re-initializing heavy serializers
    private static final Map<SerializationFormat, EventSerializer> cache = new ConcurrentHashMap<>();

    public static EventSerializer getSerializer(SerializationFormat format) {
        return cache.computeIfAbsent(format, key -> {
            switch (key) {
                case JSON:
                    return new JsonSerializer();
                case PROTOBUF:
                    return new ProtobufSerializer();
                case AVRO:
                    throw new UnsupportedOperationException("Avro missing");
                default:
                    throw new IllegalArgumentException("Unknown format");
            }
        });
    }
}
