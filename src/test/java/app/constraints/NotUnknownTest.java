package app.constraints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.enums.CodeValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NotUnknownTest extends ConstraintsTest {

  @ParameterizedTest
  @MethodSource("testValueProvider")
  public void validationTest(EnumValue value, String expectedMessage) {
    var violation = validator.validate(value);
    if (!value.hasError()) {
      assertThat(violation.isEmpty()).isTrue();
    } else {
      assertThat(violation).hasSize(1);
      var actualMessage = violation.stream().toList().get(0).getMessage();
      assertThat(actualMessage).isEqualTo(expectedMessage);
    }
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(new EnumValue(null, false), null),
        arguments(new EnumValue(TestEnum.ENABLE, false), null),
        arguments(new EnumValue(TestEnum.UNKNOWN, true), "{param_name}に指定された値は許可されていません。"));

  }

  @AllArgsConstructor
  public enum TestEnum implements CodeValue<String, String> {

    UNKNOWN("Unknown", "Unknown"),
    ENABLE("1", "Enable");

    private final String code;
    private final String value;

    @Override
    public String getCode() {
      return this.code;
    }

    @JsonValue
    @Override
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static TestEnum fromValue(String value) {
      return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
    }

  }

  private record EnumValue(@NotUnknown TestEnum value, boolean hasError) {

  }

  private record StringValue(@NotUnknown String value) {

  }

}
