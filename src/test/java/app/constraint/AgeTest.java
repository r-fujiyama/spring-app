package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AgeTest extends ConstraintsTest {

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
        arguments(new IntegerValue(null, false), null),
        arguments(new IntegerValue(-1, true), "{param_name}は0~999以内の値を入力してください。"),
        arguments(new IntegerValue(0, false), null),
        arguments(new IntegerValue(999, false), null),
        arguments(new IntegerValue(1000, true), "{param_name}は0~999以内の値を入力してください。"),
        arguments(new StringValue("null", true), "{param_name}は0~999以内の値を入力してください。"),
        arguments(new StringValue("", true), "{param_name}は0~999以内の値を入力してください。"),
        arguments(new StringValue("-1", true), "{param_name}は0~999以内の値を入力してください。"),
        arguments(new StringValue("0", false), null),
        arguments(new StringValue("999", false), null),
        arguments(new StringValue("1000", true), "{param_name}は0~999以内の値を入力してください。"),
        arguments(new StringValue("test", true), "{param_name}は0~999以内の値を入力してください。"));

  }

  public interface Value {

    boolean isError();
  }

  @AllArgsConstructor
  @Getter
  public static class IntegerValue implements Value {

    @Age
    private final Integer value;
    private final boolean error;
  }

  @AllArgsConstructor
  @Getter
  public static class StringValue implements Value {

    @Age
    private final String value;
    private final boolean error;
  }

}
