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
  @SuppressWarnings({"unchecked", "rawtypes"})
  public <S extends T> Converter<String, S> getConverter(@NonNull Class<S> targetType) {
    return new StringToCodeValueEnum(targetType);
  }

  private final class StringToCodeValueEnum<S extends Enum<S> & CodeValueEnum> implements Converter<String, S> {

    private final Class<S> targetType;

    public StringToCodeValueEnum(Class<S> targetType) {
      this.targetType = targetType;
    }

    @Override
    public S convert(@Nullable String source) {
      return CodeValueEnum.fromValue(targetType, source);
    }

  }

}
