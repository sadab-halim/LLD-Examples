package chainOfResponsibility.ea;

public class AuthorizationHandler extends AbstractRequestHandler {
    private final PermissionService permissionService;

    public AuthorizationHandler(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    protected Response doHandle(Request request) {
        UserContext userContext = request.getUserContext();

        if (userContext == null) {
            return Response.error(401, "User context not found",
                    "AuthorizationHandler");
        }

        String resource = request.getPath();
        String action = request.getMethod();

        if (!permissionService.hasPermission(userContext, resource, action)) {
            return Response.error(403, "Access denied", "AuthorizationHandler");
        }

        return Response.success();
    }
}
