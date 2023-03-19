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
public class BusinessException extends BaseException {

  public BusinessException(HttpStatus httpStatus, ErrorCode code, String message) {
    super(httpStatus, code, message);
  }

  public BusinessException(HttpStatus httpStatus, ErrorCode code, String message, Throwable cause) {
    super(httpStatus, code, message, cause);
  }

  public BusinessException(HttpStatus httpStatus, Error error) {
    super(httpStatus, Collections.singletonList(error));
  }

  public BusinessException(HttpStatus httpStatus, Error error, Throwable cause) {
    super(httpStatus, Collections.singletonList(error), cause);
  }

  public BusinessException(HttpStatus httpStatus, List<Error> errors) {
    super(httpStatus, errors);
  }

  public BusinessException(HttpStatus httpStatus, List<Error> errors, Throwable cause) {
    super(httpStatus, errors, cause);
  }

}
