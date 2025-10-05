package vn.com.fortis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MultipartJacksonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    ObjectMapper objectMapper;

    protected MultipartJacksonHttpMessageConverter(ObjectMapper objectMapper) {
        super(MediaType.APPLICATION_OCTET_STREAM);
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return super.canRead(mediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return objectMapper.readValue(inputMessage.getBody(), clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        objectMapper.writeValue(outputMessage.getBody(), o);
    }
}
