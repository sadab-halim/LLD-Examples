package factory.ec;

public class JsonSerializer implements EventSerializer {
    public JsonSerializer() { System.out.println("Jackson ObjectMapper Init"); }

    @Override
    public byte[] serialize(Object event) { return new byte[0]; }
    @Override
    public String getFormatName() { return "JSON"; }
}
