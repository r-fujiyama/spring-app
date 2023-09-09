package app.constraint;

import app.constraint.Pattern.Flag;
import app.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatternValidator implements ConstraintValidator<Pattern, String> {

  private String regexp;
  private Flag flag;

  public void initialize(Pattern annotation) {
    this.regexp = annotation.regexp();
    this.flag = annotation.flag();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }
    if (Flag.SKIP_BLANK == flag && StringUtils.isBlank(value)) {
      return true;
    }
    return value.matches(this.regexp);
  }
}
