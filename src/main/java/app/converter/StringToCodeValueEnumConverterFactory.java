package app.converter;

import app.enums.CodeValueEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class StringToCodeValueEnumConverterFactory<T1, T3 extends Enum<T3> & CodeValueEnum<T1, String>> implements
    ConverterFactory<String, T3> {

  @Override
  @NonNull
  public <T4 extends T3> Converter<String, T4> getConverter(@NonNull Class<T4> targetType) {
    return new StringToCodeValueEnum<>(targetType);
  }

  private static class StringToCodeValueEnum<T1, T3 extends Enum<T3> & CodeValueEnum<T1, String>, T4 extends T3> implements
      Converter<String, T4> {

    private final Class<T4> targetType;

    public StringToCodeValueEnum(Class<T4> targetType) {
      this.targetType = targetType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T4 convert(@Nullable String source) {
      return (T4) CodeValueEnum.fromValue((Class<T3>) targetType, source);
    }

  }

}
