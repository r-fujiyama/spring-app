package app.enums;

import java.util.Arrays;

public interface CodeValueEnum {

  int getCode();

  String getValue();

  static <T extends Enum<T> & CodeValueEnum> T fromCode(Class<T> clazz, Integer code) {
    if (code == null) {
      return null;
    }
    return Arrays.stream(clazz.getEnumConstants())
        .filter(e -> code.equals(e.getCode()))
        .findFirst()
        .orElse(getUnknown(clazz));
  }

  static <T extends Enum<T> & CodeValueEnum> T fromValue(Class<T> clazz, String value) {
    if (value == null) {
      return null;
    }
    return Arrays.stream(clazz.getEnumConstants())
        .filter(e -> value.equals(e.getValue()))
        .findFirst()
        .orElse(getUnknown(clazz));
  }

  static <T extends Enum<T> & CodeValueEnum> T getUnknown(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }
    return Arrays.stream(clazz.getEnumConstants())
        .filter(e -> e.name().equals("UNKNOWN"))
        .findFirst()
        .orElse(null);
  }

}
