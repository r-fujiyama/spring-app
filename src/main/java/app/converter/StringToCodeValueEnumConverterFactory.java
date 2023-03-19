package app.converter;

import app.enums.CodeValue;
import app.exception.InternalServerErrorException;
import app.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.lang.reflect.Method;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToCodeValueEnumConverterFactory implements ConverterFactory<String, CodeValue<?, ?>> {

  @Override
  public <T extends CodeValue<?, ?>> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToCodeValueEnum<>(targetType);
  }

  private static class StringToCodeValueEnum<T extends CodeValue<?, ?>> implements Converter<String, T> {

    private final Class<T> enumType;

    private StringToCodeValueEnum(Class<T> enumType) {
      this.enumType = enumType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T convert(String source) {
      if (StringUtils.isEmpty(source)) {
        return null;
      }

      for (Method method : enumType.getMethods()) {
        if (method.getAnnotation(JsonCreator.class) != null) {
          try {
            return (T) method.invoke(enumType, source);
          } catch (Exception e) {
            throw new InternalServerErrorException(
                "Failed to execute method: " + enumType.getName() + "#" + method.getName(), e);
          }
        }
      }
      throw new IllegalArgumentException(
          "Method with @JsonCreator implemented in " + enumType.getName() + " does not exist");
    }
  }
}
