package app.constants;

import org.springframework.core.Ordered;

public class DIOrder {

  public static final int LOGGING_FILTER = Ordered.HIGHEST_PRECEDENCE;
  public static final int TRANSACTION_INFO_FILTER = Ordered.LOWEST_PRECEDENCE;

}
