package vn.com.fortis.base;

import vn.com.fortis.domain.dto.utils.ResponseData;
import vn.com.fortis.domain.dto.utils.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseUtil {
    public static ResponseEntity<ResponseData<?>> success(String message, Object data) {
        return success(HttpStatus.OK, message, data);
    }

    public static ResponseEntity<ResponseData<?>> success(HttpStatus status, String message) {
        ResponseData<?> response = new ResponseData<>(status.value(), message);

        return new ResponseEntity<>(response, status == HttpStatus.NO_CONTENT ? HttpStatus.OK : status);
    }

    public static ResponseEntity<ResponseData<?>> success(HttpStatus status, String message, Object data) {
        ResponseData<?> response = new ResponseData<>(status.value(), message, data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseData<?>> success(MultiValueMap<String, String> header, Object data) {
        return success(HttpStatus.OK, header, data);
    }

    public static ResponseEntity<ResponseData<?>> success(HttpStatus status, MultiValueMap<String, String> header, Object data) {
        ResponseData<?> response = new ResponseData<>(data);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.addAll(header);
        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    public static ResponseEntity<ResponseError<?>> error(HttpStatus status, String message) {
        ResponseError<?> response = new ResponseError<>(status.value(), message);
        return new ResponseEntity<>(response, status);
    }
}
