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
public class InternalServerErrorException extends BaseException {

  public InternalServerErrorException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, message);
  }

  public InternalServerErrorException(String message, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, message, cause);
  }

  public InternalServerErrorException(Error error) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(error));
  }

  public InternalServerErrorException(Error error, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(error), cause);
  }

  public InternalServerErrorException(List<Error> errors) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, errors);
  }

  public InternalServerErrorException(List<Error> errors, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, errors, cause);
  }

}
