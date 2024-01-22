package app.converter;

import app.enums.CodeValueEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class StringToCodeValueEnumConverterFactory<T extends Enum<T> & CodeValueEnum> implements
    ConverterFactory<String, T> {

  @Override
  @NonNull
  public <S extends T> Converter<String, S> getConverter(@NonNull Class<S> targetType) {
    return new StringToCodeValueEnum<>(targetType);
  }

  private static class StringToCodeValueEnum<S extends Enum<S> & CodeValueEnum, U extends S> implements
      Converter<String, U> {

    private final Class<U> targetType;

    public StringToCodeValueEnum(Class<U> targetType) {
      this.targetType = targetType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public U convert(@Nullable String source) {
      return (U) CodeValueEnum.fromValue((Class<S>) targetType, source);
    }

  }

}
