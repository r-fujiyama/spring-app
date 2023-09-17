package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum APIResult implements CodeValueEnum {

  UNKNOWN(Integer.MIN_VALUE, "Unknown"),
  SUCCESS(1, "Success"),
  FAILURE(9, "Failure");

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

  public static APIResult fromCode(Integer code) {
    return CodeValueEnum.fromCode(APIResult.class, code);
  }

  @JsonCreator
  public static APIResult fromValue(String value) {
    return CodeValueEnum.fromValue(APIResult.class, value);
  }

}
