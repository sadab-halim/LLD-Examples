package decorator.eb;

public interface ServiceClient {
    ServiceResponse call(ServiceRequest request) throws ServiceException;
}
