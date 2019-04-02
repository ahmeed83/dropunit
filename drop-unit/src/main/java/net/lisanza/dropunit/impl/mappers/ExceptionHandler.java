package net.lisanza.dropunit.impl.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionHandler implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public Response toResponse(final Exception exception) {

        // Initial status to internal server error
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        String body = exception.getMessage();

        if (exception instanceof NotSupportedException) {
            status = Status.UNSUPPORTED_MEDIA_TYPE;
        } else if (exception instanceof ForbiddenException) {
            status = Response.Status.FORBIDDEN;
        } else if (exception instanceof NotFoundException) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof BadRequestException) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof ServiceUnavailableException) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        } else if (exception instanceof NotAuthorizedException) {
            status = Response.Status.UNAUTHORIZED;
        }

        String message = String.format("%s exception \"%s\"", status.getReasonPhrase(), exception.getMessage());
        LOGGER.error(message, exception);
        return Response.status(status).entity(body).build();
    }
}
