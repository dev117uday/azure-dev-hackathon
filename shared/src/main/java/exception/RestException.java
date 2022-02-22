package exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestException extends Exception {

    private HttpStatus httpStatus;
    private String message;

    public RestException() {
        super();
    }

    public RestException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

