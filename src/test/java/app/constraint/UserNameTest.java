package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import jakarta.validation.ConstraintViolation;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UserNameTest extends ConstraintsTest {

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
        arguments(new StringValue("user-name", false), null),
        arguments(new StringValue(null, false), null),
        arguments(new StringValue("", true), new String[]{"{param_name}は^.*[1-9a-z-]$の形式で入力してください。",
            "{param_name}は1~256文字以内で入力してください。"}),
        arguments(new StringValue(" ", true), new String[]{"{param_name}は^.*[1-9a-z-]$の形式で入力してください。"}),
        arguments(new StringValue("!", true), new String[]{"{param_name}は^.*[1-9a-z-]$の形式で入力してください。"}));

  }

  public interface Value {

    boolean isError();
  }

  @AllArgsConstructor
  @Getter
  public static class StringValue implements Value {

    @UserName
    private final String value;
    private final boolean error;
  }

}
