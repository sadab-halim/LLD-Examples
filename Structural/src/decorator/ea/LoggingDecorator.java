package decorator.ea;

import java.util.UUID;
import java.util.logging.Logger;

public class LoggingDecorator extends RequestHandlerDecorator {
    private final Logger logger;

    public LoggingDecorator(RequestHandler handler, Logger logger) {
        super(handler);
        this.logger = logger;
    }

    @Override
    public Response handle(Request request) throws Exception {
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();

        /*logger.info("Request started: id={}, path={}, method={}",
                requestId, request.getPath(), request.getHeader("X-HTTP-Method"));

        try {
            Response response = wrappedHandler.handle(request);

            long duration = System.currentTimeMillis() - startTime;
            logger.info("Request completed: id={}, status={}, duration={}ms",
                    requestId, response.getStatusCode(), duration);

            return response.withHeader("X-Request-Id", requestId);

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("Request failed: id={}, duration={}ms, error={}",
                    requestId, duration, e.getMessage(), e);
            throw e;
        }*/
        return null;
    }
}
