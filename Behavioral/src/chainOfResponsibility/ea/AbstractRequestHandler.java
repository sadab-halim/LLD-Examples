package chainOfResponsibility.ea;

abstract class AbstractRequestHandler implements RequestHandler {
    protected RequestHandler next;

    @Override
    public RequestHandler setNext(RequestHandler next) {
        this.next = next;
        return next;
    }

    @Override
    public Response handle(Request request) {
        Response response = doHandle(request);

        if (response.shouldContinue() && next != null) {
            return next.handle(request);
        }

        return response;
    }

    protected abstract Response doHandle(Request request);
}
