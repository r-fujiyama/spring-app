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
public class ConflictException extends BaseException {

  public ConflictException(String message) {
    super(HttpStatus.CONFLICT, ErrorCode.CONFLICT, message);
  }

  public ConflictException(String message, Throwable cause) {
    super(HttpStatus.CONFLICT, ErrorCode.CONFLICT, message, cause);
  }

  public ConflictException(Error error) {
    super(HttpStatus.CONFLICT, Collections.singletonList(error));
  }

  public ConflictException(Error error, Throwable cause) {
    super(HttpStatus.CONFLICT, Collections.singletonList(error), cause);
  }

  public ConflictException(List<Error> errors) {
    super(HttpStatus.CONFLICT, errors);
  }

  public ConflictException(List<Error> errors, Throwable cause) {
    super(HttpStatus.CONFLICT, errors, cause);
  }

}
