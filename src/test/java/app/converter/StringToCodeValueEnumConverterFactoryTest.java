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
  public void convert(Class<CodeValue<?, String>> codeValueClass, String value, CodeValue<?, String> expected) {
    var actual = converterFactory.getConverter(codeValueClass).convert(value);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(StringEnum.class, null, null),
        arguments(StringEnum.class, "", StringEnum.UNKNOWN),
        arguments(StringEnum.class, "Unknown", StringEnum.UNKNOWN),
        arguments(StringEnum.class, "Enable", StringEnum.ENABLE),
        arguments(IntegerEnum.class, null, null),
        arguments(IntegerEnum.class, "0", IntegerEnum.UNKNOWN),
        arguments(IntegerEnum.class, "1", IntegerEnum.ENABLE),
        arguments(StringEnum.class, " ", StringEnum.UNKNOWN),
        arguments(StringEnum.class, "illegal-value", StringEnum.UNKNOWN)
    );
  }

  @AllArgsConstructor
  public enum StringEnum implements CodeValue<String, String> {

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
    public static StringEnum fromValue(String value) {
      return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
    }
  }

  @AllArgsConstructor
  public enum IntegerEnum implements CodeValue<String, Integer> {

    UNKNOWN("Unknown", 0),
    ENABLE("1", 1);

    private final String code;
    private final Integer value;

    @Override
    public String getCode() {
      return this.code;
    }

    @JsonValue
    @Override
    public Integer getValue() {
      return this.value;
    }

    @JsonCreator
    public static IntegerEnum fromValue(Integer value) {
      return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
    }
  }

  @Test
  public void unmatchedMethodParameterCount() {
    assertThatThrownBy(() -> converterFactory.getConverter(UnmatchedMethodParameterCount.class).convert("Enable"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "app.converter.StringToCodeValueEnumConverterFactoryTest$UnmatchedMethodParameterCount#fromValue must have one argument");
  }

  @AllArgsConstructor
  public enum UnmatchedMethodParameterCount implements CodeValue<String, String> {

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
    public static UnmatchedMethodParameterCount fromValue(String value1, String value2) {
      return CodeValue.fromValue(Arrays.asList(values()), value1, UNKNOWN);
    }
  }

  @Test
  public void failedToConvertToArgumentType() {
    assertThatThrownBy(() -> converterFactory.getConverter(IntegerEnum.class).convert("integer"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("java.lang.String cannot be convert to java.lang.Integer");
  }

  @Test
  public void unsupportedArgumentType() {
    assertThatThrownBy(() -> converterFactory.getConverter(ObjectEnum.class).convert("Enable"))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Unsupported java.lang.Object");
  }

  @AllArgsConstructor
  public enum ObjectEnum implements CodeValue<String, Object> {

    UNKNOWN("Unknown", "Unknown"),
    ENABLE("1", "Enable");

    private final String code;
    private final Object value;

    @Override
    public String getCode() {
      return this.code;
    }

    @JsonValue
    @Override
    public Object getValue() {
      return this.value;
    }

    @JsonCreator
    public static ObjectEnum fromValue(Object value) {
      return CodeValue.fromValue(Arrays.asList(values()), value, UNKNOWN);
    }
  }

  @Test
  public void jsonCreatorIsNotImplemented() {
    assertThatThrownBy(() -> converterFactory.getConverter(JsonCreatorIsNotImplemented.class).convert("Enable"))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage(
            "Method with @JsonCreator implemented in app.converter.StringToCodeValueEnumConverterFactoryTest$JsonCreatorIsNotImplemented does not exist");
  }

  @AllArgsConstructor
  public enum JsonCreatorIsNotImplemented implements CodeValue<String, String> {
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

  @Test
  public void failedToExecuteMethod() {
    assertThatThrownBy(() -> converterFactory.getConverter(FailedToExecuteMethod.class).convert("Enable"))
        .isInstanceOf(InternalServerErrorException.class)
        .hasMessage(
            "ErrorCode:InternalServerError, Message:Failed to execute method: app.converter.StringToCodeValueEnumConverterFactoryTest$FailedToExecuteMethod#fromValue");
  }

  @AllArgsConstructor
  public enum FailedToExecuteMethod implements CodeValue<String, String> {
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

    @JsonCreator
    public static FailedToExecuteMethod fromValue(String value) {
      throw new RuntimeException();
    }
  }

}
