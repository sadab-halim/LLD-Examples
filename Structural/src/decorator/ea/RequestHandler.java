package decorator.ea;

public interface RequestHandler {
    Response handle(Request request) throws Exception;
}
