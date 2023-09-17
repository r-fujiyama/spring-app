package app.constraint;

import app.enums.CodeValueEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class DisableEnumValidator implements ConstraintValidator<DisableEnum, CodeValueEnum> {

  private List<String> disableEnums;

  public void initialize(DisableEnum annotation) {
    this.disableEnums = Arrays.asList(annotation.value());
  }

  @Override
  public boolean isValid(CodeValueEnum value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return disableEnums.stream().noneMatch(disableValue -> value.getValue().equals(disableValue));
  }
}
