/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest;

import java.util.ArrayList;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * JAX-RS Exception Mapper für beliebige Exceptions. Dieser sorgt dafür, dass
 * bei Auftreten einer Exception dennoch eine ordentliche Antwort an den Client
 * gesendet wird.
 */
@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ExceptionResponse result = new ExceptionResponse();
        result.exception = ex.getClass().getName();
        result.message = ex.getMessage();

        if (ex instanceof ConstraintViolationException) {
            result.violations = new ArrayList<>();

            ConstraintViolationException cex = (ConstraintViolationException) ex;
            
            for (ConstraintViolation<?> constraintViolation : cex.getConstraintViolations()) {
                ExceptionResponse.Violation violation = new ExceptionResponse.Violation();
                
                violation.path = constraintViolation.getPropertyPath().toString();
                violation.message = constraintViolation.getMessage();
                
                result.violations.add(violation);
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

}
