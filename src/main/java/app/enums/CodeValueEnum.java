package app.enums;

import java.util.Arrays;

public interface CodeValueEnum<T1, T2> {

  T1 getCode();

  T2 getValue();

  static <T1, T2, T3 extends Enum<T3> & CodeValueEnum<T1, T2>> T3 fromCode(Class<T3> clazz, T1 code) {
    if (code == null) {
      return null;
    }
    return Arrays.stream(clazz.getEnumConstants())
        .filter(e -> code.equals(e.getCode()))
        .findFirst()
        .orElse(getUnknown(clazz));
  }

  static <T1, T2, T3 extends Enum<T3> & CodeValueEnum<T1, T2>> T3 fromValue(Class<T3> clazz, T2 value) {
    if (value == null) {
      return null;
    }
    return Arrays.stream(clazz.getEnumConstants())
        .filter(e -> value.equals(e.getValue()))
        .findFirst()
        .orElse(getUnknown(clazz));
  }

  private static <T1, T2, T3 extends Enum<T3> & CodeValueEnum<T1, T2>> T3 getUnknown(Class<T3> clazz) {
    return Arrays.stream(clazz.getEnumConstants())
        .filter(e -> e.name().equals("UNKNOWN"))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("The 'UNKNOWN' constant is not implemented in the " + clazz.getName()));
  }

}
