package decorator.eb;

public class ServiceClientDecorator implements ServiceClient {
    protected final ServiceClient delegate;

    public ServiceClientDecorator(ServiceClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public ServiceResponse call(ServiceRequest request) throws ServiceException {
        return delegate.call(request);
    }
}
