package app.exception;

import app.controller.response.Error;
import app.enums.ErrorCode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class BaseException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final List<Error> errors;

  public BaseException(HttpStatus httpStatus, ErrorCode code, String message) {
    super(convertToErrorMessage(code, message));
    this.httpStatus = httpStatus;
    var error = new Error(code, message);
    this.errors = Collections.singletonList(error);
  }

  public BaseException(HttpStatus httpStatus, ErrorCode code, String message, Throwable cause) {
    super(convertToErrorMessage(code, message), cause);
    this.httpStatus = httpStatus;
    var error = new Error(code, message);
    this.errors = Collections.singletonList(error);
  }

  public BaseException(HttpStatus httpStatus, Error error) {
    super(convertToErrorMessage(error));
    this.httpStatus = httpStatus;
    this.errors = Collections.singletonList(error);
  }

  public BaseException(HttpStatus httpStatus, Error error, Throwable cause) {
    super(convertToErrorMessage(error), cause);
    this.httpStatus = httpStatus;
    this.errors = Collections.singletonList(error);
  }

  public BaseException(HttpStatus httpStatus, List<Error> errors) {
    super(convertToErrorMessage(errors));
    this.httpStatus = httpStatus;
    this.errors = errors;
  }

  public BaseException(HttpStatus httpStatus, List<Error> errors, Throwable cause) {
    super(convertToErrorMessage(errors), cause);
    this.httpStatus = httpStatus;
    this.errors = errors;
  }

  private static String convertToErrorMessage(ErrorCode code, String message) {
    return "ErrorCode:" + code.getValue() + ", Message:" + message;
  }

  private static String convertToErrorMessage(Error error) {
    return convertToErrorMessage(error.getCode(), error.getMessage());
  }

  private static String convertToErrorMessage(List<Error> errors) {
    return errors.stream().map(BaseException::convertToErrorMessage)
        .collect(Collectors.joining(","));
  }

}
