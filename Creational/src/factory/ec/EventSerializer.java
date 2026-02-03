package factory.ec;

// 1. The Interface
public interface EventSerializer {
    byte[] serialize(Object event);
    String getFormatName();
}
