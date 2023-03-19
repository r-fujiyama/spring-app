package app.constraints;

import app.enums.CodeValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class DisableEnumValidator implements ConstraintValidator<DisableEnum, CodeValue<?, ?>> {

  private List<String> disableEnums;

  public void initialize(DisableEnum annotation) {
    this.disableEnums = Arrays.asList(annotation.value());
  }

  @Override
  public boolean isValid(CodeValue<?, ?> value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return disableEnums.stream().noneMatch(disableValue -> value.getValue().equals(disableValue));
  }
}
