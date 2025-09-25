package vn.com.fortis.exception;

public class SendEmailFailException extends RuntimeException {
    public SendEmailFailException(String message) {
        super(message);
    }
}
