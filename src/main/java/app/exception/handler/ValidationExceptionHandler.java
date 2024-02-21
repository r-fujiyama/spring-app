package app.exception.handler;

import app.constants.DIOrder;
import app.constants.ErrorMessage;
import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.util.MessageUtils;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
@Order(DIOrder.VALIDATION_ERROR_HANDLER)
public class ValidationExceptionHandler {

  private final static String PARAM_NAME_CODE = "param.name.";
  private final static String REPLACE_TARGET_PARAM_NAME = "{param_name}";
  private final MessageUtils messageUtils;

  /**
   * コントローラーでリクエストをDTOで受け取る場合のエラーハンドリグを行う。<p>
   * クエリパラメーターのバインディングに失敗(型の不一致など)したことを
   * {@code fieldError.isBindingFailure() == true}で判定する。<p>
   * 以下のメッセージを固定で設定し、レスポンスを返却する。<p>
   * {0}で指定された値の型に誤に誤りがあります。<p>
   * 以下のハンドラーでRequestBodyのバインディング失敗(フォーマットエラーなど)をハンドリングする。<p>
   * {@link GlobalExceptionHandler#handleHttpMessageNotReadableException(HttpMessageNotReadableException)}<p>
   * {@code fieldError.isBindingFailure() == false}の場合、通常のバリデーションエラーメッセージを設定し、レスポンスを返却する。
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
   * コントローラーでパスパラメーター、クエリパラメーターを
   * {@link org.springframework.web.bind.annotation.PathVariable},
   * {@link org.springframework.web.bind.annotation.RequestParam}
   * で指定し、バリデーションエラーが発生した時のハンドリングを行う。
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
   * コントローラーでパスパラメーター、クエリパラメーターを
   * {@link org.springframework.web.bind.annotation.PathVariable},
   * {@link org.springframework.web.bind.annotation.RequestParam}
   * で指定し、型変換エラーが発生した時のハンドリングを行う。
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
   * 必須となっているクエリパラメーターが指定されていない場合のハンドリングを行う。
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

}
