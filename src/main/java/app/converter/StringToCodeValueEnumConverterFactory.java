package app.converter;

import app.enums.CodeValue;
import app.exception.InternalServerErrorException;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.lang.reflect.Method;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class StringToCodeValueEnumConverterFactory implements ConverterFactory<String, CodeValue<?, ?>> {

  @Override
  @NonNull
  public <T extends CodeValue<?, ?>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
    return new StringToCodeValueEnum<>(targetType);
  }

  private static class StringToCodeValueEnum<T extends CodeValue<?, ?>> implements Converter<String, T> {

    private final Class<T> enumType;

    private StringToCodeValueEnum(Class<T> enumType) {
      this.enumType = enumType;
    }

    @Override
    public T convert(@Nullable String source) {
      if (source == null) {
        return null;
      }

      for (Method method : enumType.getMethods()) {
        if (method.getAnnotation(JsonCreator.class) != null) {
          checkMethodParameterCount(method);
          var arg = toTargetType(source, method.getParameterTypes()[0]);
          return enumType.cast(methodInvoke(method, arg));
        }
      }
      throw new UnsupportedOperationException(
          "Method with @JsonCreator implemented in " + enumType.getName() + " does not exist");
    }

    private void checkMethodParameterCount(Method method) {
      if (method.getParameterCount() != 1) {
        throw new IllegalArgumentException(enumType.getName() + "#" + method.getName() + " must have one argument");
      }
    }

    private Object toTargetType(String source, Class<?> targetType) {
      try {
        if (targetType == String.class) {
          return source;
        }
        if (targetType == Integer.class) {
          return Integer.valueOf(source);
        }
      } catch (Exception e) {
        throw new IllegalArgumentException(source.getClass().getName() + " cannot be convert to " + targetType.getName(),
            e);
      }
      throw new UnsupportedOperationException("Unsupported " + targetType.getName());
    }

    private Object methodInvoke(Method method, Object source) {
      try {
        return method.invoke(enumType, source);
      } catch (Exception e) {
        throw new InternalServerErrorException(
            "Failed to execute method: " + enumType.getName() + "#" + method.getName(), e);
      }
    }

  }

}
