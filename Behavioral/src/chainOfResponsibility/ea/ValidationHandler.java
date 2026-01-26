package chainOfResponsibility.ea;

import java.util.List;

public class ValidationHandler extends AbstractRequestHandler {
    private final RequestValidator validator;

    public ValidationHandler(RequestValidator validator) {
        this.validator = validator;
    }

    @Override
    protected Response doHandle(Request request) {
        List<String> errors = validator.validate(request);

        if (!errors.isEmpty()) {
            return Response.error(400, "Validation failed: " + String.join(", ", errors),
                    "ValidationHandler");
        }

        return Response.success();
    }
}
