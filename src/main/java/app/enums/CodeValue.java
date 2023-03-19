package app.enums;

import java.util.List;

public interface CodeValue<T, S> {

  T getCode();

  S getValue();

  static <T, S, U extends CodeValue<T, S>> U fromCode(List<U> enums, T code, U unknown) {
    if (code == null) {
      return null;
    }
    return enums.stream()
        .filter(e -> e.getCode().equals(code))
        .findFirst()
        .orElse(unknown);
  }

  static <T, S, U extends CodeValue<T, S>> U fromValue(List<U> enums, S value, U unknown) {
    if (value == null) {
      return null;
    }
    return enums.stream()
        .filter(e -> e.getValue().equals(value))
        .findFirst()
        .orElse(unknown);
  }

}
