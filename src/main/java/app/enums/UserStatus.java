package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus implements CodeValueEnum<Integer, String> {

  UNKNOWN(Integer.MIN_VALUE, "Unknown"),
  UNREGISTERED(0, "Unregistered"),
  REGISTERED(1, "Registered"),
  BLOCKED(2, "blocked"),
  DELETED(3, "deleted");

  private final int code;
  private final String value;

  @Override
  public Integer getCode() {
    return this.code;
  }

  @JsonValue
  @Override
  public String getValue() {
    return this.value;
  }

  public static UserStatus fromCode(Integer code) {
    return CodeValueEnum.fromCode(UserStatus.class, code);
  }

  @JsonCreator
  public static UserStatus fromValue(String value) {
    return CodeValueEnum.fromValue(UserStatus.class, value);
  }

}
