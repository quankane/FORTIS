package vn.com.fortis.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}