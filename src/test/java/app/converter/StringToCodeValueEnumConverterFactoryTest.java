package app.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.enums.CodeValueEnum;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringToCodeValueEnumConverterFactoryTest {

  @ParameterizedTest
  @MethodSource("testValueProvider")
  public void convertTest(Class<StringEnum> clazz, String value, StringEnum expected) {
    var converterFactory = new StringToCodeValueEnumConverterFactory<StringEnum>();
    var actual = converterFactory.getConverter(clazz).convert(value);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> testValueProvider() {
    return Stream.of(
        arguments(StringEnum.class, null, null),
        arguments(StringEnum.class, "Unknown", StringEnum.UNKNOWN),
        arguments(StringEnum.class, "Disable", StringEnum.DISABLE),
        arguments(StringEnum.class, "Enable", StringEnum.ENABLE),
        arguments(StringEnum.class, "", StringEnum.UNKNOWN),
        arguments(StringEnum.class, " ", StringEnum.UNKNOWN),
        arguments(StringEnum.class, "illegal-value", StringEnum.UNKNOWN)
    );
  }

  @AllArgsConstructor
  @Getter
  public enum StringEnum implements CodeValueEnum {

    UNKNOWN(Integer.MIN_VALUE, "Unknown"),
    DISABLE(0, "Disable"),
    ENABLE(1, "Enable");

    private final int code;
    private final String value;

  }

  @Test
  public void noUnknownConvertTest() {
    var converterFactory = new StringToCodeValueEnumConverterFactory<NoUnknown>();
    var actual = converterFactory.getConverter(NoUnknown.class).convert("illegal-value");
    assertThat(actual).isEqualTo(null);
  }

  @AllArgsConstructor
  @Getter
  public enum NoUnknown implements CodeValueEnum {

    DISABLE(0, "Disable"),
    ENABLE(1, "Enable");

    private final int code;
    private final String value;

  }

}
