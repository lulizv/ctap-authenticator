package mock.ctap.exceptions;

public class AuthenticatorException extends Exception{
    public AuthenticatorException(String errorMessage) {
        super(errorMessage);
    }
}
