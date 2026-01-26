package chainOfResponsibility.ea;

public class LoggingHandler extends AbstractRequestHandler {
    private final Logger logger;

    public LoggingHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected Response doHandle(Request request) {
        long startTime = System.currentTimeMillis();

        logger.info("Request started: {} {} - RequestId: {}",
                request.getMethod(), request.getPath(), request.getRequestId());

        // Always continue - logging doesn't block
        return Response.success();
    }
}
