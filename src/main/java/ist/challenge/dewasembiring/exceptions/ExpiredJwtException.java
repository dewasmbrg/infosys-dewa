package ist.challenge.dewasembiring.exceptions;

public class ExpiredJwtException extends RuntimeException {

    public ExpiredJwtException(String message) {
        super(message);
    }
}

