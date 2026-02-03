package factory.ec;

public class AnalyticsMain {
    public static void main(String[] args) {
        System.out.println("--- 1. First request for JSON Serializer ---");
        // This should trigger initialization (and print "Jackson ObjectMapper Init")
        EventSerializer json1 = SerializerFactory.getSerializer(SerializationFormat.JSON);
        System.out.println("Got serializer: " + json1.getFormatName());

        System.out.println("\n--- 2. Second request for JSON Serializer ---");
        // This should return the CACHED instance (No "Init" print log)
        EventSerializer json2 = SerializerFactory.getSerializer(SerializationFormat.JSON);

        // specific check to prove reference equality
        boolean isSameInstance = (json1 == json2);
        System.out.println("Is same instance? " + isSameInstance); // Should be true

        System.out.println("\n--- 3. Request for Protobuf Serializer ---");
        // This simulates latency as the schema loads
        long start = System.currentTimeMillis();
        EventSerializer proto = SerializerFactory.getSerializer(SerializationFormat.PROTOBUF);
        long end = System.currentTimeMillis();
        System.out.println("Got serializer: " + proto.getFormatName() + " (Took " + (end - start) + "ms)");
    }
}
