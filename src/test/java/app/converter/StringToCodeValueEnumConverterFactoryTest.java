package app.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.enums.CodeValue;
import app.exception.InternalServerErrorException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringToCodeValueEnumConverterFactoryTest {

  private static final StringToCodeValueEnumConverterFactory converterFactory = new StringToCodeValueEnumConverterFactory();

  @ParameterizedTest
  @MethodSource("testValueProvider")
  public void convert(Class<CodeValue<?, ?>> codeValueClass, String value, CodeValue<?, ?> expected) {
    var actual = converterFactory.getConverter(codeValueClass).convert(value);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(TestEnum.class, null, null),
        arguments(TestEnum.class, "", null),
        arguments(TestEnum.class, "Enable", TestEnum.ENABLE),
        arguments(TestEnum.class, "Unknown", TestEnum.UNKNOWN),
        arguments(TestEnum.class, " ", TestEnum.UNKNOWN),
        arguments(TestEnum.class, "illegal-value", TestEnum.UNKNOWN)
    );
  }

  @Test
  public void internalErrorException() {
    assertThatThrownBy(() -> converterFactory.getConverter(InternalErrorExceptionEnum.class).convert("Enable"))
        .isInstanceOf(InternalServerErrorException.class)
        .hasMessage(
            "InternalServerError:Failed to execute method: app.converter.StringToCodeValueEnumConverterFactoryTest$InternalErrorExceptionEnum#fromValue");
  }

  @Test
  public void illegalArgumentException() {
    assertThatThrownBy(() -> converterFactory.getConverter(IllegalArgumentExceptionEnum.class).convert("Enable"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "Method with @JsonCreator implemented in app.converter.StringToCodeValueEnumConverterFactoryTest$IllegalArgumentExceptionEnum does not exist");
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

  @AllArgsConstructor
  public enum InternalErrorExceptionEnum implements CodeValue<String, String> {

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
    public static InternalErrorExceptionEnum fromValue(String value1, String value2) {
      return CodeValue.fromValue(Arrays.asList(values()), value1, UNKNOWN);
    }

  }

  @AllArgsConstructor
  public enum IllegalArgumentExceptionEnum implements CodeValue<String, String> {
    ;
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

  }

}
