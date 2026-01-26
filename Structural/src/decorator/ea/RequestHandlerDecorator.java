package decorator.ea;

abstract class RequestHandlerDecorator implements RequestHandler {
    protected final RequestHandler wrappedHandler;

    RequestHandlerDecorator(RequestHandler wrappedHandler) {
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public Response handle(Request request) throws Exception{
        return wrappedHandler.handle(request);
    }
}
