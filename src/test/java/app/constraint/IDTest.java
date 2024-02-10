package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IDTest extends ConstraintsTest {

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
        arguments(new BigDecimalValue(null, false), null),
        arguments(new LongValue(0L, true), "{param_name}は1~9223372036854775807以内の値を入力してください。"),
        arguments(new LongValue(1L, false), null),
        arguments(new LongValue(9223372036854775807L, false), null),
        arguments(new BigDecimalValue(new BigDecimal("0"), true),
            "{param_name}は1~9223372036854775807以内の値を入力してください。"),
        arguments(new BigDecimalValue(new BigDecimal("1"), false), null),
        arguments(new BigDecimalValue(new BigDecimal("9223372036854775807"), false), null),
        arguments(new BigDecimalValue(new BigDecimal("9223372036854775808"), true),
            "{param_name}は1~9223372036854775807以内の値を入力してください。"),
        arguments(new StringValue("null", true), "{param_name}は1~9223372036854775807以内の値を入力してください。"),
        arguments(new StringValue("", true), "{param_name}は1~9223372036854775807以内の値を入力してください。"),
        arguments(new StringValue("0", true), "{param_name}は1~9223372036854775807以内の値を入力してください。"),
        arguments(new StringValue("1", false), null),
        arguments(new StringValue("9223372036854775807", false), null),
        arguments(new StringValue("9223372036854775808", true),
            "{param_name}は1~9223372036854775807以内の値を入力してください。"));

  }

  public interface Value {

    boolean isError();
  }

  @AllArgsConstructor
  @Getter
  public static class LongValue implements Value {

    @ID
    private final Long value;
    private final boolean error;
  }

  @AllArgsConstructor
  @Getter
  public static class BigDecimalValue implements Value {

    @ID
    private final BigDecimal value;
    private final boolean error;
  }

  @AllArgsConstructor
  @Getter
  public static class StringValue implements Value {

    @ID
    private final String value;
    private final boolean error;
  }

}
