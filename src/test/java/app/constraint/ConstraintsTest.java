package app.constraint;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;

public abstract class ConstraintsTest {

  private final static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
  protected final static Validator validator = validatorFactory.getValidator();

  @AfterAll
  public static void afterAll() {
    validatorFactory.close();
  }

}
