package app.util;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public final class MessageUtils {

  private final MessageSource messageSource;

  public String getMessage(String code, Object arg, String defaultMessage) {
    return messageSource.getMessage(code, new Object[]{arg}, defaultMessage,
        LocaleContextHolder.getLocale());
  }

  public String getMessage(String code, Object[] args, String defaultMessage) {
    return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
  }

  public String getMessage(String code, Object... args) {
    return messageSource.getMessage(code, args, null, LocaleContextHolder.getLocale());
  }

  public String getMessage(MessageSourceResolvable resolvable) {
    return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
  }

}
