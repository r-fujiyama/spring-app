package app.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.enums.CodeValueEnum;
import jakarta.validation.ConstraintViolation;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DisableEnumTest extends ConstraintsTest {

  @ParameterizedTest
  @MethodSource("testValueProvider")
  public void validationTest(EnumValue value, String[] expectedMessages) {
    var violation = validator.validate(value);
    if (!value.hasError()) {
      assertThat(violation.isEmpty()).isTrue();
    } else {
      assertThat(violation).hasSize(expectedMessages.length);
      var actualMessage = violation.stream().map(ConstraintViolation::getMessage).toList();
      assertThat(actualMessage).containsExactlyInAnyOrder(expectedMessages);
    }
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(new EnumValue(null, false), null),
        arguments(new EnumValue(TestEnum.ENABLE, false), null),
        arguments(new EnumValue(TestEnum.DISABLE, false), null),
        arguments(new EnumValue(TestEnum.UNKNOWN, true),
            new String[]{"{param_name}に指定された値は許可されていません。"}));
  }

  @AllArgsConstructor
  @Getter
  public enum TestEnum implements CodeValueEnum<Integer, String> {

    UNKNOWN(Integer.MIN_VALUE, "Unknown"),
    DISABLE(0, "Disable"),
    ENABLE(1, "Enable");

    private final Integer code;
    private final String value;

  }

  public record EnumValue(@DisableEnum(value = "Unknown") TestEnum value, boolean hasError) {

  }

}
