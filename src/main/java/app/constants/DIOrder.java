package app.constants;

import org.springframework.core.Ordered;

public class DIOrder {

  public static final int LOGGING_FILTER = Ordered.HIGHEST_PRECEDENCE;
  public static final int VALIDATION_ERROR_HANDLER = LOGGING_FILTER + 1;
  public static final int GLOBAL_EXCEPTION_HANDLER = VALIDATION_ERROR_HANDLER + 1;
  public static final int TRANSACTION_INFO_FILTER = Ordered.LOWEST_PRECEDENCE - 1;

}
