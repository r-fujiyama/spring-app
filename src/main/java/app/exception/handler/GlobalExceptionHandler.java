package app.exception.handler;

import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.exception.BaseException;
import app.util.MessageUtils;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final static String PARAM_NAME_CODE = "param.name.";
  private final static String REPLACE_TARGET_PARAM_NAME = "{param_name}";
  private final MessageUtils messageUtils;

  /**
   * システム独自の例外をハンドリング
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
   * パラメーターの引数でDTOを指定した場合のエラーハンドリグを行う。
   * <p>
   * パスパラメーター、クエリパラメーターのバインディングに失敗(型の不一致など)したことを
   * "fieldError.isBindingFailure() == true"(型の不一致など)で判定する。
   * 以下のメッセージを固定で設定し、レスポンスを返却する。
   * {0}で指定された値の型に誤に誤りがあります。
   * <p>
   * 以下のハンドラーで"JSON body"のバインディング失敗(型の不一致など)をハンドリングする。
   * {@link #handleHttpMessageNotReadableException(HttpMessageNotReadableException)}
   * <p>
   * "fieldError.isBindingFailure() == false"の場合、
   * 通常のバリデーションエラーメッセージを設定し、レスポンスを返却する。
   *
   * @param ex {@link BindException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleBindException(BindException ex) {
    log.info(ex.getMessage(), ex);
    List<Error> errors = new ArrayList<>();
    ex.getFieldErrors().forEach(fieldError -> {
      String message;
      var paramName = messageUtils.getMessage(PARAM_NAME_CODE + fieldError.getField(), null, fieldError.getField());
      // このエラーがバインディングの失敗(型の不一致など)であるかどうかを判定する。
      if (fieldError.isBindingFailure()) {
        message = messageUtils.getMessage(ErrorMessage.TYPE_MISMATCH, paramName, null);
      } else {
        // バリデーションエラーの場合
        message = Objects.requireNonNull(fieldError.getDefaultMessage()).replace(REPLACE_TARGET_PARAM_NAME, paramName);
      }
      errors.add(new Error(ErrorCode.BAD_REQUEST, message));
    });
    return new Response(errors);
  }

  /**
   * "@RequestBody"のバインディング失敗(型の不一致、JSONフォーマットエラーなど)をハンドリングする。
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
   * パスパラメーター、クエリパラメーターを引数で直接指定し、バリデーションエラーが発生した時のハンドリング
   *
   * @param ex {@link ConstraintViolationException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleConstraintViolationException(ConstraintViolationException ex) {
    log.info(ex.getMessage(), ex);
    var errors = new ArrayList<Error>();
    ex.getConstraintViolations().forEach(constraintViolation -> {
      // constraintViolation.getPropertyPath().toString() を実行すると以下のような文字列が取得できる。
      // 例：getUser.userName
      //    {controller_method_name}.{parameter_name}
      // {parameter_name}を取得するためsplitし最後尾の文字列を取得している。
      var path = constraintViolation.getPropertyPath().toString().split("\\.");
      var lastPath = path[path.length - 1];
      var paramName = messageUtils.getMessage(PARAM_NAME_CODE + lastPath, null, lastPath);
      var message = Objects.requireNonNull(constraintViolation.getMessage())
          .replace(REPLACE_TARGET_PARAM_NAME, paramName);
      errors.add(new Error(ErrorCode.BAD_REQUEST, message));
    });
    return new Response(errors);
  }

  /**
   * パスパラメーター、クエリパラメーターを引数で直接指定し、型変換エラーが発生した時のハンドリング
   *
   * @param ex {@link MethodArgumentTypeMismatchException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    log.info(ex.getMessage(), ex);
    var paramName = messageUtils.getMessage(PARAM_NAME_CODE + ex.getName(), null, ex.getName());
    var message = messageUtils.getMessage(ErrorMessage.TYPE_MISMATCH, paramName);
    var error = new Error(ErrorCode.BAD_REQUEST, message);
    return new Response(error);
  }

  /**
   * 必須となっているクエリパラメーターが指定されていない場合のハンドリング
   *
   * @param ex {@link MissingServletRequestParameterException}
   * @return Failure {@link Response}
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    log.info(ex.getMessage(), ex);
    var paramName = messageUtils.getMessage(PARAM_NAME_CODE + ex.getParameterName(), null, ex.getParameterName());
    var error = new Error(ErrorCode.BAD_REQUEST,
        messageUtils.getMessage(ErrorMessage.MISSING_REQUEST_PARAMETER, paramName, null));
    return new Response(error);
  }

  /**
   * サポートしていないリクエストメソッドが指定された場合のハンドリング
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
   * サポートしていないコンテンツタイプが指定された場合のハンドリング
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
   * アクセス権限が存在しない場合のハンドリング
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
   * 想定されていない例外をハンドリング
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
