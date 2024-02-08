package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.constants.RegExp;
import app.constraint.Pattern.Flag;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PatternTest extends ConstraintsTest {

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
        arguments(new Normal(null, false), null),
        arguments(new Normal("", false), null),
        arguments(new Normal("test", false), null),
        arguments(new Normal(" ", true), "{param_name}は^[a-zA-Z]+$の形式で入力してください。"),
        arguments(new Normal("　", true), "{param_name}は^[a-zA-Z]+$の形式で入力してください。"),
        arguments(new Normal("1234", true), "{param_name}は^[a-zA-Z]+$の形式で入力してください。"),
        arguments(new SkipBlank(null, false), null),
        arguments(new SkipBlank("", false), null),
        arguments(new SkipBlank("test", false), null),
        arguments(new SkipBlank(" ", false), null),
        arguments(new SkipBlank("　", false), null),
        arguments(new SkipBlank("1234", true), "{param_name}は^[a-zA-Z]+$の形式で入力してください。"));

  }

  public interface Value {

    boolean isError();
  }

  @AllArgsConstructor
  @Getter
  public static class Normal implements Value {

    @Pattern(flag = Flag.NORMAL, regexp = RegExp.ALL_HALF_WIDTH_ALPHABET)
    private final String value;
    private final boolean error;
  }

  @AllArgsConstructor
  @Getter
  public static class SkipBlank implements Value {

    @Pattern(flag = Flag.SKIP_BLANK, regexp = RegExp.ALL_HALF_WIDTH_ALPHABET)
    private final String value;
    private final boolean error;
  }

}
