package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.annotation.constraint.Password;
import jakarta.validation.ConstraintViolation;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PasswordTest extends ConstraintsTest {

  @ParameterizedTest
  @MethodSource("testValueProvider")
  public void validationTest(Value value, String[] expectedMessage) {
    var violation = validator.validate(value);
    if (!value.isError()) {
      assertThat(violation.isEmpty()).isTrue();
    } else {
      assertThat(violation).hasSize(expectedMessage.length);
      var actualMessage = violation.stream().map(ConstraintViolation::getMessage).toList();
      assertThat(actualMessage).containsExactlyInAnyOrder(expectedMessage);
    }
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(new StringValue("Password123!#$%&@", false), null),
        arguments(new StringValue(null, false), null),
        arguments(new StringValue("", true), new String[]{"{param_name}は8~64文字以内で入力してください。",
            "{param_name}は^.*[1-9a-zA-Z!#$%&@]$の形式で入力してください。"}),
        arguments(new StringValue("a".repeat(7), true), new String[]{"{param_name}は8~64文字以内で入力してください。"}),
        arguments(new StringValue("a".repeat(65), true), new String[]{"{param_name}は8~64文字以内で入力してください。"}),
        arguments(new StringValue("<<<<<<<<", true),
            new String[]{"{param_name}は^.*[1-9a-zA-Z!#$%&@]$の形式で入力してください。"}),
        arguments(new StringValue("<", true), new String[]{"{param_name}は8~64文字以内で入力してください。",
            "{param_name}は^.*[1-9a-zA-Z!#$%&@]$の形式で入力してください。"}));

  }

  public interface Value {

    boolean isError();
  }

  @AllArgsConstructor
  @Getter
  public static class StringValue implements Value {

    @Password
    private final String value;
    private final boolean error;
  }

}
