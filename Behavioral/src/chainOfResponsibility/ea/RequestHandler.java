package chainOfResponsibility.ea;

public interface RequestHandler {
    Response handle(Request request);
    RequestHandler setNext(RequestHandler next);
}
