package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserType implements CodeValueEnum {

  UNKNOWN(Integer.MIN_VALUE, "Unknown"),
  PRIVATE(1, "Private"),
  FREELANCE(2, "Freelance"),
  CORPORATE(3, "Corporate");

  private final int code;
  private final String value;

  @Override
  public int getCode() {
    return this.code;
  }

  @JsonValue
  @Override
  public String getValue() {
    return this.value;
  }

  public static UserType fromCode(Integer code) {
    return CodeValueEnum.fromCode(UserType.class, code);
  }

  @JsonCreator
  public static UserType fromValue(String value) {
    return CodeValueEnum.fromValue(UserType.class, value);
  }

}
