package decorator.ea;

public class BaseRequestHandler implements RequestHandler {
    private final ServiceClient serviceClient;

    public BaseRequestHandler(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public Response handle(Request request) throws Exception {
        return serviceClient.call(request);
    }
}
