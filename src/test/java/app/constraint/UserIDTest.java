package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UserIDTest extends ConstraintsTest {

  @ParameterizedTest
  @MethodSource("testValueProvider")
  public void validationTest(Value value, String expectedMessage) {
    var violation = validator.validate(value);
    if (!value.isError()) {
      assertThat(violation.isEmpty()).isTrue();
    } else {
      assertThat(violation).hasSize(1);
      var actualMessage = violation.stream().toList().get(0).getMessage();
      assertThat(actualMessage).isEqualTo(expectedMessage);
    }
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(new StringValue("user-id", false), null),
        arguments(new StringValue("", true), "{param_name}は1~256文字以内で入力してください。"),
        arguments(new StringValue("a".repeat(257), true), "{param_name}は1~256文字以内で入力してください。"));

  }

  public interface Value {

    boolean isError();
  }

  @AllArgsConstructor
  @Getter
  public static class StringValue implements Value {

    @UserID
    private final String value;
    private final boolean error;
  }

}
