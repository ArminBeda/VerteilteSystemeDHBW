package dhbwka.wwi.vertsys.javaee.checkit.rest;

import java.util.List;

/**
 * Antwortobjekt, das bei Auftreten einer Exception an den Client gesendet
 * wird.
 */
public class ExceptionResponse {
    public String exception;
    public String message;
    public List<Violation> violations;
    
    public static class Violation {
        public String path = "";
        public String message = "";
    }
}
