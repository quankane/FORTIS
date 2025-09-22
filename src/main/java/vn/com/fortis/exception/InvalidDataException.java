package vn.com.fortis.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
