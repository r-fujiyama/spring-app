package app.exception.handler;

import app.constants.DIOrder;
import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.exception.BaseException;
import app.util.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
@Order(DIOrder.GLOBAL_EXCEPTION_HANDLER)
public class GlobalExceptionHandler {

  private final MessageUtils messageUtils;

  /**
   * システム独自の例外をハンドリングを行う。
   *
   * @param ex {@link BaseException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<Response> handleBaseException(BaseException ex) {
    log.info(ex.getMessage(), ex);
    return new ResponseEntity<>(new Response(ex.getErrors()), ex.getHttpStatus());
  }

  /**
   * "@RequestBody"のバインディング失敗(型の不一致、JSONフォーマットエラーなど)のハンドリングを行う。
   *
   * @param ex {@link HttpMessageNotReadableException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.info(ex.getMessage(), ex);
    return new Response(new Error(ErrorCode.BAD_REQUEST, messageUtils.getMessage(ErrorMessage.PARSE_JSON_FAILED)));
  }

  /**
   * サポートしていないリクエストメソッドが指定された場合のハンドリングを行う。
   *
   * @param ex {@link HttpRequestMethodNotSupportedException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    log.info(ex.getMessage(), ex);
    var error = new Error(ErrorCode.BAD_REQUEST,
        messageUtils.getMessage(ErrorMessage.METHOD_NOT_SUPPORTED, ex.getMethod(), null));
    return new Response(error);
  }

  /**
   * サポートしていないコンテンツタイプが指定された場合のハンドリングを行う。
   *
   * @param ex {@link HttpMediaTypeNotSupportedException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
    log.info(ex.getMessage(), ex);
    var error = new Error(ErrorCode.BAD_REQUEST, messageUtils.getMessage(ErrorMessage.UNSUPPORTED_MEDIA_TYPE));
    return new Response(error);
  }

  /**
   * アクセス権限が存在しない場合のハンドリングを行う。
   *
   * @param ex {@link AccessDeniedException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public Response handleAccessDeniedException(AccessDeniedException ex) {
    log.info(ex.getMessage(), ex);
    var error = new Error(ErrorCode.FORBIDDEN, messageUtils.getMessage(ErrorMessage.ACCESS_DENIED));
    return new Response(error);
  }

  /**
   * 想定されていない例外のハンドリングを行う。
   *
   * @param ex {@link Exception}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Response handleException(Exception ex) {
    log.error(ex.getMessage(), ex);
    return new Response(new Error(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
  }

}
