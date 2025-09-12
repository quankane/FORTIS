package vn.com.fortis.domain.dto.utils;

import java.io.Serializable;

public class ResponseError<T> extends ResponseData<T> implements Serializable {
    public ResponseError(int status, String message, T data) {
        super(status, message, data);
    }

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
