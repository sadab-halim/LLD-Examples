package chainOfResponsibility.ea;

public class Response {
    private final int statusCode;
    private final String message;
    private final boolean shouldContinue;
    private final String handlerName;

    private Response(int statusCode, String message, boolean shouldContinue,
                     String handlerName) {
        this.statusCode = statusCode;
        this.message = message;
        this.shouldContinue = shouldContinue;
        this.handlerName = handlerName;
    }

    public static Response success() {
        return new Response(200, "Success", true, null);
    }

    public static Response error(int statusCode, String message, String handler) {
        return new Response(statusCode, message, false, handler);
    }

    public static Response ok(String message) {
        return new Response(200, message, false, null);
    }

    public boolean shouldContinue() { return shouldContinue; }
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public String getHandlerName() { return handlerName; }
}
