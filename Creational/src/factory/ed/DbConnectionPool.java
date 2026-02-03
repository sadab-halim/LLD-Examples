package factory.ed;

// 1. The Interface (Wrapper around actual DB Pool)
public interface DbConnectionPool {
    void executeQuery(String sql);
    void close();
}
