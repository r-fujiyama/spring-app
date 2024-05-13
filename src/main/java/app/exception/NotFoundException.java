package app.exception;

import app.controller.response.Error;
import app.enums.ErrorCode;
import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends BaseException {

  public NotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message, cause);
  }

  public NotFoundException(Error error) {
    super(HttpStatus.NOT_FOUND, Collections.singletonList(error));
  }

  public NotFoundException(Error error, Throwable cause) {
    super(HttpStatus.NOT_FOUND, Collections.singletonList(error), cause);
  }

  public NotFoundException(List<Error> errors) {
    super(HttpStatus.NOT_FOUND, errors);
  }

  public NotFoundException(List<Error> errors, Throwable cause) {
    super(HttpStatus.NOT_FOUND, errors, cause);
  }

}
